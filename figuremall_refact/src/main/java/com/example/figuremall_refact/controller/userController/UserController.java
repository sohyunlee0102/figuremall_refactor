package com.example.figuremall_refact.controller.userController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@Valid @RequestBody UserRequestDTO.JoinDto request) {
        return ApiResponse.onSuccess(userService.joinUser(request));
    }

    @PutMapping
    public ApiResponse<UserResponseDTO.UpdateResponseDto> updateUser(@Valid @RequestPart UserRequestDTO.UpdateDTO request,
                                                                     @RequestPart MultipartFile file,
                                                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(userService.updateUser(request, userDetails.getUsername(), file));
    }

}
