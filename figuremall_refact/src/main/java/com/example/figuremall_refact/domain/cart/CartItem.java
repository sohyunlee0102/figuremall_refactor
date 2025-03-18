package com.example.figuremall_refact.domain.cart;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.product.Product;
import com.example.figuremall_refact.domain.product.ProductOption;
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
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Long price;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItemOption> cartItemOptions = new ArrayList<>();

}
