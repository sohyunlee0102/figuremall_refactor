package com.example.figuremall_refact.domain.iceRink;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.meeting.AvailableTime;
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
public class IceRink extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String district;

    @Column
    private String detailedAddress;

    @Column(nullable = false, length = 20)
    private String phone;

    @OneToMany(mappedBy = "iceRink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IceRinkSelection> selections = new ArrayList<>();

}
