package com.example.figuremall_refact.domain.user;

import com.example.figuremall_refact.domain.common.BaseEntity;
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
public class UserAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false, length = 250)
    private String address;

    @Column(nullable = true, length = 250)
    private String detail;

    @Column(nullable = false, length = 10)
    private Integer postalCode;

    @Column(nullable = false)
    private Boolean isDefault;
}
