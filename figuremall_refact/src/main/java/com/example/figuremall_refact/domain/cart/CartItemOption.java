package com.example.figuremall_refact.domain.cart;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.product.ProductOptionValue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class CartItemOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CartItem cartItem;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOptionValue productOptionValue;

}
