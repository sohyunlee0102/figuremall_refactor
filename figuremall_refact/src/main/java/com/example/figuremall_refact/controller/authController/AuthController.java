package com.example.figuremall_refact.controller.authController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.service.authService.AuthService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody UserRequestDTO.LoginDto request, HttpServletResponse response) {
        return ApiResponse.onSuccess(authService.login(request, response));
    }

    @PostMapping("/refresh")
    public ApiResponse<String> refreshToken(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request, HttpServletResponse response) {
        authService.refreshAccessToken(request, response, userDetails.getUsername());
        return ApiResponse.onSuccess("accessToken이 재발급되었습니다.");
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@AuthenticationPrincipal UserDetails userDetails,
                                      HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response, userDetails.getUsername());
        return ApiResponse.onSuccess("로그아웃 되었습니다.");
    }

    @PostMapping("/email")
    public ApiResponse<String> checkEmailDuplicate(@Valid @RequestBody UserRequestDTO.CheckEmailDuplicationDTO request) {
        userService.checkEmailDuplicate(request);
        return ApiResponse.onSuccess("사용 가능한 이메일입니다.");
    }

    @GetMapping("/me")
    public ApiResponse<?> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ApiResponse.onSuccess(Map.of("authenticated", false, "message", "로그인 상태가 아닙니다."));
        }

        return ApiResponse.onSuccess(Map.of(
                "authenticated", true,
                "username", authService.getUsername(userDetails.getUsername())
        ));
    }

}
