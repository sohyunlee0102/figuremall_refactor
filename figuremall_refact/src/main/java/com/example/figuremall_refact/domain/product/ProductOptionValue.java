package com.example.figuremall_refact.domain.product;

import com.example.figuremall_refact.domain.cart.CartItemOption;
import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.order.OrderItemOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class ProductOptionValue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", nullable = false)
    @JsonBackReference
    private ProductOption productOption;

    @Column(nullable = false, length = 30)
    private String valueName;

    @Column(nullable = true)
    private Long extraPrice;

    @Column(nullable = false)
    private Boolean isSoldOut = false;

    @Column(nullable = false)
    private Long stock;

    @OneToMany(mappedBy = "productOptionValue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemOption> orderItemOptions;

    @OneToMany(mappedBy = "productOptionValue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItemOption> cartItemOptions = new ArrayList<>();

}
