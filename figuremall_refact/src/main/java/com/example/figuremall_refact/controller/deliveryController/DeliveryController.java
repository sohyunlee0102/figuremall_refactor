package com.example.figuremall_refact.controller.deliveryController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.deliveryDto.DeliveryRequestDTO;
import com.example.figuremall_refact.dto.deliveryDto.DeliveryResponseDTO;
import com.example.figuremall_refact.service.deliveryService.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping
    public ApiResponse<DeliveryResponseDTO.UpdateDeliveryResponseDto> updateDelivery(@Valid @RequestBody DeliveryRequestDTO.UpdateDeliveryDto request) {
        return ApiResponse.onSuccess(deliveryService.updateDelivery(request));
    }

}
