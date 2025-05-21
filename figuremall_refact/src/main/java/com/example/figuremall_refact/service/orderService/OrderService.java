package com.example.figuremall_refact.service.orderService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.OrderHandler;
import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.domain.order.OrderItemOption;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.dto.orderDto.OrderResponseDTO;
import com.example.figuremall_refact.repository.orderRepository.OrderRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_NOT_FOUND));
    }

    @Transactional
    public OrderResponseDTO.CreateOrderResponseDto createOrder(OrderRequestDTO.CreateOrderDto request, String email) {
        User user = userService.findByEmail(email);

        Order order = Order.builder()
                .user(user)
                .totalPrice(request.getTotalPrice())
                .status(OrderStatus.TEMPORARY)
                .build();

        orderRepository.save(order);
        orderItemService.createOrderItem(request.getItems(), order);

        return new OrderResponseDTO.CreateOrderResponseDto(order.getId());
    }

    @Transactional
    public OrderResponseDTO.OrderInfo getOrderInfo(Long orderId) {
        Order order = findOrderById(orderId);
        List<OrderItem> items = order.getOrderItems();
        List<OrderResponseDTO.OrderItemInfo> orderItemInfos = new ArrayList<>();

        for (OrderItem item : items) {
            List<OrderItemOption> options = item.getOrderItemOptions();
            List<OrderResponseDTO.OrderItemOptionInfo> optionInfos = new ArrayList<>();

            for (OrderItemOption option : options) {
                optionInfos.add(new OrderResponseDTO.OrderItemOptionInfo(option.getId(), option.getProductOptionValue().getValueName(),
                        option.getProductOptionValue().getExtraPrice()));
            }

            orderItemInfos.add(new OrderResponseDTO.OrderItemInfo(item.getId(), item.getProduct().getName(),
                    item.getPrice(), item.getQuantity(), optionInfos));
        }

        return new OrderResponseDTO.OrderInfo(order.getTotalPrice(), orderItemInfos);
    }

    @Scheduled(fixedRate = 3600000) // 1시간
    public void deleteExpiredTemporaryOrders() {
        LocalDateTime now = LocalDateTime.now();
        orderRepository.deleteByStatusAndCreatedAtBefore(OrderStatus.TEMPORARY, now.minusMinutes(30));
    }

}
