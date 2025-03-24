package com.example.figuremall_refact.repository.iceRinkRepository;

import com.example.figuremall_refact.domain.iceRink.IceRinkSelection;
import com.example.figuremall_refact.domain.meeting.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IceRinkSelectionRepository extends JpaRepository<IceRinkSelection, Long> {
    void deleteAllByParticipant(MeetingParticipant meetingParticipant);
}
