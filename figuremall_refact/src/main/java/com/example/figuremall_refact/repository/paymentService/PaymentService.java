package com.example.figuremall_refact.repository.paymentService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PaymentHandler;
import com.example.figuremall_refact.domain.enums.PaymentStatus;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.payment.Payment;
import com.example.figuremall_refact.dto.paymentDto.PaymentRequestDTO;
import com.example.figuremall_refact.dto.paymentDto.PaymentResponseDTO;
import com.example.figuremall_refact.repository.paymentRepository.PaymentRepository;
import com.example.figuremall_refact.service.orderService.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentHandler(ErrorStatus.PAYMENT_NOT_FOUND));
    }

    @Transactional
    public PaymentResponseDTO.CreatePaymentResponseDto createPayment(PaymentRequestDTO.CreatePaymentDto request) {
        Order order = orderService.findOrderById(request.getOrderId());

        Payment payment = Payment.builder()
                .order(order)
                .amount(request.getAmount())
                .method(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .build();

        return new PaymentResponseDTO.CreatePaymentResponseDto(paymentRepository.save(payment).getId());
    }

    @Transactional
    public PaymentResponseDTO.CreatePaymentResponseDto updatePayment(PaymentRequestDTO.UpdatePaymentDto request) {
        Payment payment = findById(request.getPaymentId());

        if (request.getStatus() != null) {
            payment.setStatus(request.getStatus());
        }

        if (request.getTransactionId() != null) {
            payment.setTransactionId(request.getTransactionId());
        }

        return new PaymentResponseDTO.CreatePaymentResponseDto(payment.getId());
    }

}
