package com.example.figuremall_refact.domain.terms;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.user.UserAgreements;
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
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    private Float version;

    @Column(nullable = false)
    private String optional;

    @OneToMany(mappedBy = "terms", fetch = FetchType.LAZY)
    private List<UserAgreements> agreements =  new ArrayList<>();

}
