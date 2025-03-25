package com.example.figuremall_refact.service.deliveryService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.DeliveryHandler;
import com.example.figuremall_refact.domain.delivery.Delivery;
import com.example.figuremall_refact.domain.enums.DeliveryStatus;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.dto.deliveryDto.DeliveryRequestDTO;
import com.example.figuremall_refact.dto.deliveryDto.DeliveryResponseDTO;
import com.example.figuremall_refact.repository.deliveryRepository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new DeliveryHandler(ErrorStatus.DELIVERY_NOT_FOUND));
    }

    @Transactional
    public void createDelivery(DeliveryRequestDTO.CreateDeliveryDto request, OrderItem orderItem) {
        Delivery delivery = Delivery.builder()
                .orderItem(orderItem)
                .address(request.getAddress())
                .detail(request.getDetail())
                .postalCode(request.getPostalCode())
                .recipientName(request.getRecipientName())
                .recipientPhone(request.getRecipientPhone())
                .status(DeliveryStatus.PENDING)
                .build();

        deliveryRepository.save(delivery);
    }

    @Transactional
    public DeliveryResponseDTO.UpdateDeliveryResponseDto updateDelivery(DeliveryRequestDTO.UpdateDeliveryDto request) {
        Delivery delivery = findById(request.getDeliveryId());

        if (request.getStatus() != null) {
            delivery.setStatus(request.getStatus());
        }

        if (request.getShippedAt() != null) {
            delivery.setShippedAt(request.getShippedAt());
        }

        if (request.getDeliveredAt() != null) {
            delivery.setDeliveredAt(request.getDeliveredAt());
        }

        if (request.getAddress() != null && delivery.getStatus() == DeliveryStatus.PENDING) {
            delivery.setAddress(request.getAddress());

            if (request.getDetail() != null) {
                delivery.setDetail(request.getDetail());
            }

            if (request.getPostalCode() != null) {
                delivery.setPostalCode(request.getPostalCode());
            }
        }

        if (request.getRecipientName() != null) {
            delivery.setRecipientName(request.getRecipientName());
        }

        if (request.getRecipientPhone() != null) {
            delivery.setRecipientPhone(request.getRecipientPhone());
        }

        if (request.getCourierCompany() != null) {
            delivery.setCourierCompany(request.getCourierCompany());
        }

        if (request.getTrackingNumber() != null) {
            delivery.setTrackingNumber(request.getTrackingNumber());
        }

        return new DeliveryResponseDTO.UpdateDeliveryResponseDto(delivery.getId());
    }

}
