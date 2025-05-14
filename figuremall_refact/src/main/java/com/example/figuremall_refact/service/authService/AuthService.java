package com.example.figuremall_refact.service.authService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.AuthHandler;
import com.example.figuremall_refact.config.jwt.JwtTokenUtil;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.repository.userRepository.UserRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ValueOperations<String, String> redisOps;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> login(UserRequestDTO.LoginDto request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String accessToken = jwtTokenUtil.generateAccessToken(request.getEmail(), userService);
            String refreshToken = jwtTokenUtil.generateRefreshToken(request.getEmail(), userService);

            redisOps.set(request.getEmail(), refreshToken, 7, TimeUnit.DAYS);

            ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofMinutes(30))
                    .sameSite("Strict")
                    .build();

            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofDays(7))
                    .sameSite("Strict")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            return ResponseEntity.ok().body("로그인 성공");
        } catch (AuthenticationException e) {
            throw new AuthHandler(ErrorStatus.AUTHENTICATION_FAILED);
        }
    }

    @Transactional
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String email) {
        String refreshToken = extractTokenFromCookies(request, "refreshToken");

        if (refreshToken == null || redisOps.getOperations().hasKey("BLACKLIST" + refreshToken)) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }

        String storedRefreshToken = redisOps.get(email);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }

        String newAccessToken = jwtTokenUtil.generateAccessToken(email, userService);

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(30))
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, String email) {
        String accessToken = extractTokenFromCookies(request, "accessToken");
        String refreshToken = redisOps.getOperations().opsForValue().get(email);

        if (refreshToken != null) {
            redisOps.getOperations().delete(email);

            long refreshExpiration = jwtTokenUtil.getExpiration(refreshToken);
            redisOps.getOperations().opsForValue().set("BLACKLIST" + refreshToken, "logout", refreshExpiration, TimeUnit.SECONDS);
        }

        long accessExpiration = jwtTokenUtil.getExpiration(accessToken);
        redisOps.getOperations().opsForValue().set("BLACKLIST" + accessToken, "logout", accessExpiration, TimeUnit.SECONDS);

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)  // 만료 시간을 0으로 설정해서 삭제
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)  // 만료 시간을 0으로 설정해서 삭제
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    @Transactional
    public UserResponseDTO.AuthResponseDto getUsername(String email) {
        return new UserResponseDTO.AuthResponseDto(userRepository.findUsernameByEmail(email).getUsername());
    }

    private String extractTokenFromCookies(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
