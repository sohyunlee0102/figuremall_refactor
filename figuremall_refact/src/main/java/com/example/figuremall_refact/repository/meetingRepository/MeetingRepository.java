package com.example.figuremall_refact.repository.meetingRepository;

import com.example.figuremall_refact.domain.meeting.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
