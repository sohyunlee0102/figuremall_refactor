package com.example.figuremall_refact.service.orderService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.OrderHandler;
import com.example.figuremall_refact.domain.cart.CartItemOption;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.domain.order.OrderItemOption;
import com.example.figuremall_refact.domain.product.ProductOptionValue;
import com.example.figuremall_refact.dto.orderDto.OrderRequestDTO;
import com.example.figuremall_refact.repository.orderRepository.OrderItemOptionRepository;
import com.example.figuremall_refact.service.cartService.CartItemOptionService;
import com.example.figuremall_refact.service.productService.ProductOptionValueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemOptionService {

    private final OrderItemOptionRepository orderItemOptionRepository;
    private final CartItemOptionService cartItemOptionService;

    public OrderItemOption findById(Long id) {
        return orderItemOptionRepository.findById(id).orElseThrow(() -> new OrderHandler(ErrorStatus.ORDER_ITEM_OPTION_NOT_FOUND));
    }

    @Transactional
    public void createOrderItemOption(List<OrderRequestDTO.CreateOrderItemOptionDto> requestList, OrderItem orderItem) {
        List<OrderItemOption> options = new ArrayList<>();

        for (OrderRequestDTO.CreateOrderItemOptionDto request : requestList) {
            CartItemOption cartItemOption = cartItemOptionService.findById(request.getOptionId());
            ProductOptionValue productOptionValue = cartItemOption.getProductOptionValue();

            OrderItemOption orderItemOption = OrderItemOption.builder()
                    .orderItem(orderItem)
                    .productOptionValue(productOptionValue)
                    .build();

            options.add(orderItemOption);
        }

        orderItemOptionRepository.saveAll(options);
    }

}
