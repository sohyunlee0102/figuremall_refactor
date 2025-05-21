package com.example.figuremall_refact.domain.order;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.enums.OrderItemStatus;
import com.example.figuremall_refact.domain.enums.OrderStatus;
import com.example.figuremall_refact.domain.payment.Payment;
import com.example.figuremall_refact.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private Long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

}
