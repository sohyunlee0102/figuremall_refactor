package com.example.figuremall_refact.controller.orderController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.dto.orderDto.OrderResponseDTO;
import com.example.figuremall_refact.service.orderService.OrderService;
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
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponseDTO.CreateOrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDTO.CreateOrderDto request,
                                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(orderService.createOrder(request, userDetails.getUsername()));
    }

}
