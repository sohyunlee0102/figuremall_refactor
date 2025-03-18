package com.example.figuremall_refact.domain.inquiry;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.enums.InquiryStatus;
import com.example.figuremall_refact.domain.review.ReviewImage;
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
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InquiryStatus inquiryStatus;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InquiryResponse> responses = new ArrayList<>();

}
