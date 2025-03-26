package com.example.figuremall_refact.controller.authController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.service.authService.AuthService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<UserResponseDTO.LoginResponseDto> login(@Valid @RequestBody UserRequestDTO.LoginDto request) {
        return ApiResponse.onSuccess(authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<UserResponseDTO.LoginResponseDto> refreshToken(@Valid @RequestBody UserRequestDTO.RefreshTokenDTO request) {
        return ApiResponse.onSuccess(authService.refreshAccessToken(request));
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestHeader("Authorization") String token) {
        String accessToken = token.substring(7);
        authService.logout(userDetails.getUsername(), accessToken);
        return ApiResponse.onSuccess("로그아웃 되었습니다.");
    }

    @PostMapping("/email")
    public ApiResponse<String> checkEmailDuplicate(@Valid @RequestBody UserRequestDTO.CheckEmailDuplicationDTO request) {
        userService.checkEmailDuplicate(request);
        return ApiResponse.onSuccess("사용 가능한 이메일입니다.");
    }

}
