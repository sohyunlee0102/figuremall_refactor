package com.example.figuremall_refact.service.paymentService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.PaymentHandler;
import com.example.figuremall_refact.domain.enums.OrderItemStatus;
import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.domain.enums.PaymentMethod;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.domain.payment.Payment;
import com.example.figuremall_refact.dto.deliveryDto.DeliveryRequestDTO;
import com.example.figuremall_refact.dto.paymentDto.PaymentRequestDTO;
import com.example.figuremall_refact.dto.paymentDto.PaymentResponseDTO;
import com.example.figuremall_refact.repository.paymentRepository.PaymentRepository;
import com.example.figuremall_refact.service.deliveryService.DeliveryService;
import com.example.figuremall_refact.service.orderService.OrderService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final IamportClient iamportClient;
    private final DeliveryService deliveryService;

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentHandler(ErrorStatus.PAYMENT_NOT_FOUND));
    }

    public boolean verifyAndSavePayment(PaymentRequestDTO.PaymentVerifyRequest request) {
        try {
            IamportResponse<com.siot.IamportRestClient.response.Payment> response = iamportClient.paymentByImpUid(request.getImp_uid());
            com.siot.IamportRestClient.response.Payment payment = response.getResponse();

            if (payment.getAmount().intValue() != request.getAmount()) {
                return false;
            }

            Order order = orderService.findOrderById(request.getOrderId());

            order.setStatus(OrderStatus.PERMANENT);

            for (OrderItem item : order.getOrderItems()) {
                item.setStatus(OrderItemStatus.PAID);
                DeliveryRequestDTO.CreateDeliveryDto deliveryDto = new DeliveryRequestDTO.CreateDeliveryDto(
                        request.getShippingInfo().getAddress(), request.getShippingInfo().getDetail(),
                        request.getShippingInfo().getPostalCode(), request.getShippingInfo().getName(), request.getShippingInfo().getPhone());
                deliveryService.createDelivery(deliveryDto, item);
            }

            Payment paymentEntity = Payment.builder()
                    .order(order)
                    .amount(request.getAmount())
                    .method(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()))
                    .impUid(request.getImp_uid())
                    .merchantUid(request.getMerchant_uid())
                    .build();

            paymentRepository.save(paymentEntity);

            return true;
        } catch (IamportResponseException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
