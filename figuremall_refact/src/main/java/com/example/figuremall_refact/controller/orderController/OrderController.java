package com.example.figuremall_refact.controller.orderController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.dto.orderDto.OrderResponseDTO;
import com.example.figuremall_refact.service.orderService.OrderItemService;
import com.example.figuremall_refact.service.orderService.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    public ApiResponse<OrderResponseDTO.CreateOrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDTO.CreateOrderDto request,
                                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(orderService.createOrder(request, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<OrderResponseDTO.OrderItemResponseDto> updateOrderItemStatus(@Valid @RequestBody
                                                                                        OrderRequestDTO.UpdateOrderItemStatusDto request) {
        return ApiResponse.onSuccess(orderItemService.updateOrderItemStatus(request));
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponseDTO.OrderInfo> getOrderInfo(@PathVariable("orderId") Long orderId) {
        return ApiResponse.onSuccess(orderService.getOrderInfo(orderId));
    }

}
