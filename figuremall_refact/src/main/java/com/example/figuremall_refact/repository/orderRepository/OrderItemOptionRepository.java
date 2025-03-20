package com.example.figuremall_refact.repository.orderRepository;

import com.example.figuremall_refact.domain.order.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Long> {
}
