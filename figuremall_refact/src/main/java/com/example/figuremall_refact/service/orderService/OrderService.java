package com.example.figuremall_refact.service.orderService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.OrderHandler;
import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.dto.orderDto.OrderResponseDTO;
import com.example.figuremall_refact.repository.orderRepository.OrderRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .build();

        orderRepository.save(order);
        orderItemService.createOrderItem(request.getItems(), order);

        return new OrderResponseDTO.CreateOrderResponseDto(order.getId());
    }

}
