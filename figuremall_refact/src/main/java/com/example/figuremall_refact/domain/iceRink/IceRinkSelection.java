package com.example.figuremall_refact.domain.iceRink;

import com.example.figuremall_refact.domain.common.BaseEntity;
import com.example.figuremall_refact.domain.meeting.MeetingParticipant;
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
public class IceRinkSelection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MeetingParticipant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ice_rink_id", nullable = false)
    private IceRink iceRink;

}
