package com.example.figuremall_refact.service.orderService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.OrderHandler;
import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.repository.orderRepository.OrderItemRepository;
import com.example.figuremall_refact.service.productService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderItemOptionService orderItemOptionService;

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_ITEM_NOT_FOUND));
    }

    public void createOrderItem(List<OrderRequestDTO.CreateOrderItemDto> requestList, Order order) {
        for (OrderRequestDTO.CreateOrderItemDto request : requestList) {
            Product product = productService.findProductById(request.getProductId());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(request.getQuantity())
                    .price(request.getPrice())
                    .orderStatus(OrderStatus.PENDING)
                    .build();

            orderItemRepository.save(orderItem);
            orderItemOptionService.createOrderItemOption(request.getOptions(), orderItem);
        }
    }

}
