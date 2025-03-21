package com.example.figuremall_refact.controller.paymentController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.paymentDto.PaymentRequestDTO;
import com.example.figuremall_refact.dto.paymentDto.PaymentResponseDTO;
import com.example.figuremall_refact.repository.paymentService.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentResponseDTO.CreatePaymentResponseDto> createPayment(@Valid @RequestBody PaymentRequestDTO.CreatePaymentDto request) {
        return ApiResponse.onSuccess(paymentService.createPayment(request));
    }

    @PutMapping
    public ApiResponse<PaymentResponseDTO.CreatePaymentResponseDto> updatePayment(@Valid @RequestBody PaymentRequestDTO.UpdatePaymentDto request) {
        return ApiResponse.onSuccess(paymentService.updatePayment(request));
    }

}
