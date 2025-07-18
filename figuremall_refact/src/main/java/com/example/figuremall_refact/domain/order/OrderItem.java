package com.example.figuremall_refact.domain.order;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.delivery.Delivery;
import com.example.figuremall_refact.domain.enums.OrderItemStatus;
import com.example.figuremall_refact.domain.product.Product;
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
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderItemStatus status;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemOption> orderItemOptions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

}
