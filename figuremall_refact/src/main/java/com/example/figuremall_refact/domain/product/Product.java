package com.example.figuremall_refact.domain.product;

import com.example.figuremall_refact.domain.cart.CartItem;
import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.order.OrderItem;
import com.example.figuremall_refact.domain.review.Review;
import com.example.figuremall_refact.domain.wishlist.Wishlist;
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
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private Integer price;

    @Column(nullable = true)
    private Float rating;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer likeCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CartItem cartItem;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

}
