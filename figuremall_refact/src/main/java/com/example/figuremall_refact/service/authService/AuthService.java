package com.example.figuremall_refact.service.authService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.AuthHandler;
import com.example.figuremall_refact.config.jwt.JwtTokenUtil;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ValueOperations<String, String> redisOps;
    private final UserService userService;

    @Transactional
    public UserResponseDTO.LoginResponseDto login(UserRequestDTO.LoginDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String accessToken = jwtTokenUtil.generateAccessToken(request.getEmail(), userService);
            String refreshToken = jwtTokenUtil.generateRefreshToken(request.getEmail(), userService);

            redisOps.set(request.getEmail(), refreshToken, 7, TimeUnit.DAYS);

            return UserResponseDTO.LoginResponseDto.builder()
                    .email(request.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthHandler(ErrorStatus.AUTHENTICATION_FAILED);
        }
    }

    @Transactional
    public UserResponseDTO.LoginResponseDto refreshAccessToken(UserRequestDTO.RefreshTokenDTO request) {

        String storedRefreshToken = redisOps.get(request.getEmail());

        if (redisOps.getOperations().hasKey("BLACKLIST" + request.getRefreshToken())) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }

        if (storedRefreshToken == null || !storedRefreshToken.equals(request.getRefreshToken())) {
            throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
        }

        String newAccessToken = jwtTokenUtil.generateAccessToken(request.getEmail(), userService);

        return UserResponseDTO.LoginResponseDto.builder()
                .email(request.getEmail())
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .build();
    }

    @Transactional
    public void logout(String email, String accessToken) {
        System.out.println("email: " + email);
        System.out.println("accessToken: " + accessToken);

        String refreshToken = redisOps.getOperations().opsForValue().get(email);
        if (refreshToken != null) {
            redisOps.getOperations().delete(email);

            long refreshExpiration = jwtTokenUtil.getExpiration(refreshToken);
            redisOps.getOperations().opsForValue().set("BLACKLIST" + refreshToken, "logout", refreshExpiration, TimeUnit.SECONDS);
        }

        long accessExpiration = jwtTokenUtil.getExpiration(accessToken);
        redisOps.getOperations().opsForValue().set("BLACKLIST" + accessToken, "logout", accessExpiration, TimeUnit.SECONDS);
    }

}
