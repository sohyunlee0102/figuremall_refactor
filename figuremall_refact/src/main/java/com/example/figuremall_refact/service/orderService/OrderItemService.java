package com.example.figuremall_refact.service.orderService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.OrderHandler;
import com.example.figuremall_refact.domain.cart.CartItem;
import com.example.figuremall_refact.domain.enums.OrderItemStatus;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.dto.orderDto.OrderResponseDTO;
import com.example.figuremall_refact.repository.orderRepository.OrderItemRepository;
import com.example.figuremall_refact.service.cartService.CartItemService;
import com.example.figuremall_refact.service.deliveryService.DeliveryService;
import com.example.figuremall_refact.service.productService.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderItemOptionService orderItemOptionService;
    private final DeliveryService deliveryService;
    private final CartItemService cartItemService;

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_ITEM_NOT_FOUND));
    }

    @Transactional
    public void createOrderItem(List<OrderRequestDTO.CreateOrderItemDto> requestList, Order order) {
        for (OrderRequestDTO.CreateOrderItemDto request : requestList) {
            CartItem cartItem = cartItemService.findById(request.getCartItemId());
            Product product = productService.findProductById(cartItem.getProduct().getId());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPrice())
                    .status(OrderItemStatus.PENDING)
                    .build();

            orderItemRepository.save(orderItem);
            orderItemOptionService.createOrderItemOption(request.getOptions(), orderItem);
        }
    }

    @Transactional
    public OrderResponseDTO.OrderItemResponseDto updateOrderItemStatus(OrderRequestDTO.UpdateOrderItemStatusDto request) {
        OrderItem orderItem = findById(request.getOrderItemId());
        orderItem.setStatus(request.getStatus());

        return new OrderResponseDTO.OrderItemResponseDto(orderItem.getId());
    }

}
