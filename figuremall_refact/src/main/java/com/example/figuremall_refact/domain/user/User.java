package com.example.figuremall_refact.domain.user;

import com.example.figuremall_refact.domain.cart.Cart;
import com.example.figuremall_refact.domain.comment.Comment;
import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.enums.Gender;
import com.example.figuremall_refact.domain.enums.Role;
import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.domain.inquiry.Inquiry;
import com.example.figuremall_refact.domain.listener.UserEntityListener;
import com.example.figuremall_refact.domain.meeting.AvailableTime;
import com.example.figuremall_refact.domain.meeting.Meeting;
import com.example.figuremall_refact.domain.notice.Notice;
import com.example.figuremall_refact.domain.order.Order;
import com.example.figuremall_refact.domain.post.Like;
import com.example.figuremall_refact.domain.post.Post;
import com.example.figuremall_refact.domain.product.ProductImage;
import com.example.figuremall_refact.domain.report.Report;
import com.example.figuremall_refact.domain.review.Review;
import com.example.figuremall_refact.domain.term.UserAgreement;
import com.example.figuremall_refact.domain.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(UserEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = true, length = 250)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = true, length = 15)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 20)
    private Gender gender;

    @Column(nullable = true)
    private Integer point = 0;

    @Column(nullable = true)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Status status;

    @Column(nullable = true)
    private LocalDate inactiveDate;

    @Column(nullable = true)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Inquiry> inquiries =  new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private UserAddress address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserAgreement> agreements =  new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Meeting> meetings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AvailableTime> availableTimes = new ArrayList<>();

    public void encodePassword(String password) { this.password = password; }

}
