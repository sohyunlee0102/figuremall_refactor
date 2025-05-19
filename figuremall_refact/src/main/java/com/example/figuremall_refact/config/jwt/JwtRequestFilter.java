package com.example.figuremall_refact.config.jwt;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.AuthHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = extractTokenFromCookies(request, "accessToken");
        String refreshToken = extractTokenFromCookies(request, "refreshToken");
        String username = null;

        if (accessToken != null) {
            try {
                if (Boolean.TRUE.equals(redisTemplate.hasKey("BLACKLIST" + accessToken))) {
                    throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
                }

                Claims claims = JwtTokenUtil.validateToken(accessToken); // 정상 토큰
                username = claims.getSubject();

            } catch (Exception e) {
                System.out.println(accessToken);
                System.out.println("Token expired");
                if (refreshToken != null && Boolean.FALSE.equals(redisTemplate.hasKey("BLACKLIST" + refreshToken))) {
                    Claims refreshClaims = JwtTokenUtil.validateToken(refreshToken);
                    username = refreshClaims.getSubject();

                    String storedRefresh = redisTemplate.opsForValue().get(username);
                    if (storedRefresh != null && storedRefresh.equals(refreshToken)) {
                        String newAccessToken = jwtTokenUtil.generateAccessToken(username);
                        System.out.println("new : " + newAccessToken);

                        ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(Duration.ofMinutes(30))
                                .sameSite("Strict")
                                .build();

                        ResponseCookie loginCookie = ResponseCookie.from("isLoggedIn", "true")
                                .path("/")
                                .maxAge(Duration.ofHours(1))
                                .httpOnly(false)
                                .build();

                        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
                        response.addHeader(HttpHeaders.SET_COOKIE, loginCookie.toString());

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } else if (refreshToken != null) {
            if (Boolean.FALSE.equals(redisTemplate.hasKey("BLACKLIST" + refreshToken))) {
                try {
                    Claims refreshClaims = JwtTokenUtil.validateToken(refreshToken);
                    username = refreshClaims.getSubject();

                    String storedRefresh = redisTemplate.opsForValue().get(username);
                    if (storedRefresh != null && storedRefresh.equals(refreshToken)) {
                        String newAccessToken = jwtTokenUtil.generateAccessToken(username);

                        ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(Duration.ofMinutes(30))
                                .sameSite("Strict")
                                .build();

                        ResponseCookie loginCookie = ResponseCookie.from("isLoggedIn", "true")
                                .path("/")
                                .maxAge(Duration.ofHours(1))
                                .httpOnly(false)
                                .build();

                        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
                        response.addHeader(HttpHeaders.SET_COOKIE, loginCookie.toString());

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (ExpiredJwtException e) {
                    throw new AuthHandler(ErrorStatus.TOKEN_EXPIRED);
                } catch (JwtException e) {
                    throw new AuthHandler(ErrorStatus.INVALID_TOKEN);
                }
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}