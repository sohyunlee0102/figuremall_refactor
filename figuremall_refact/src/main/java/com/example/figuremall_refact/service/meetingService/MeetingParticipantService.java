package com.example.figuremall_refact.service.meetingService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.MeetingHandler;
import com.example.figuremall_refact.domain.meeting.Meeting;
import com.example.figuremall_refact.domain.meeting.MeetingParticipant;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.meetingDto.MeetingRequestDTO;
import com.example.figuremall_refact.dto.meetingDto.MeetingResponseDTO;
import com.example.figuremall_refact.repository.meetingRepository.MeetingParticipantRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingParticipantService {

    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserService userService;
    private final MeetingService meetingService;
    private final AvailableTimeService availableTimeService;

    public MeetingParticipant findById(Long id) {
        return meetingParticipantRepository.findById(id).orElseThrow(() -> new MeetingHandler(ErrorStatus.MEETING_PARTICIPANT_NOT_FOUND));
    }

    @Transactional
    public MeetingResponseDTO.CreateParticipantResponseDto createParticipant(MeetingRequestDTO.CreateParticipantDto request, String email) {
        User user = userService.findByEmail(email);
        Meeting meeting = meetingService.findById(request.getMeetingId());

        MeetingParticipant meetingParticipant = MeetingParticipant.builder()
                .user(user)
                .meeting(meeting)
                .build();

        meetingParticipantRepository.save(meetingParticipant);

        availableTimeService.saveAvailableTimes(request.getAvailableTimeDTOs(), meetingParticipant);

        return new MeetingResponseDTO.CreateParticipantResponseDto(meetingParticipant.getId());
    }

    @Transactional
    public MeetingResponseDTO.CreateParticipantResponseDto updateParticipant(MeetingRequestDTO.UpdateParticipantDto request, String email) {
        User participant = userService.findByEmail(email);
        MeetingParticipant meetingParticipant = findById(participant.getId());

        availableTimeService.deleteAll(meetingParticipant);

        availableTimeService.saveAvailableTimes(request.getAvailableTimeDTOs(), meetingParticipant);

        return new MeetingResponseDTO.CreateParticipantResponseDto(meetingParticipant.getId());
    }

}
