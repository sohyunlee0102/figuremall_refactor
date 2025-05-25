package com.example.figuremall_refact.controller.paymentController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PaymentHandler;
import com.example.figuremall_refact.dto.paymentDto.PaymentRequestDTO;
import com.example.figuremall_refact.dto.paymentDto.PaymentResponseDTO;
import com.example.figuremall_refact.service.paymentService.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/verify")
    public ApiResponse<String> verifyPayment(@Valid @RequestBody PaymentRequestDTO.PaymentVerifyRequest request) {
        boolean isVerified = paymentService.verifyAndSavePayment(request);
        if (isVerified) {
            return ApiResponse.onSuccess("결제가 완료되었습니다.");
        }
        else {
            throw new PaymentHandler(ErrorStatus.PAY_AMOUNT_NOT_ACCORD);
        }
    }

}
