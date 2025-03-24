package com.example.figuremall_refact.repository.meetingRepository;

import com.example.figuremall_refact.domain.meeting.AvailableTime;
import com.example.figuremall_refact.domain.meeting.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    void deleteAllByParticipant(MeetingParticipant meetingParticipant);
}
