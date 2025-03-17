package com.example.figuremall_refact.controller.userController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.service.userService.UserAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @PostMapping
    public ApiResponse<UserResponseDTO.AddAddressResponseDto> addAddress(@Valid @RequestBody UserRequestDTO.AddAddressDTO request,
                                                                         @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(userAddressService.addAddress(request, userDetails.getUsername()));
    }

}
