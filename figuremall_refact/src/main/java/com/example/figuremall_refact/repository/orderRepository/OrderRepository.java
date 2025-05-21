package com.example.figuremall_refact.repository.orderRepository;

import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.domain.order.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    void deleteByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime cutoff);
}
