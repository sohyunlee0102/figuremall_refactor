package com.example.figuremall_refact.repository.orderRepository;

import com.example.figuremall_refact.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
