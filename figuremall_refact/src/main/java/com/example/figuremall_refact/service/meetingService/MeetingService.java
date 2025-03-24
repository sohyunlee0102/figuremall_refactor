package com.example.figuremall_refact.service.meetingService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.MeetingHandler;
import com.example.figuremall_refact.domain.meeting.Meeting;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.dto.meetingDto.MeetingRequestDTO;
import com.example.figuremall_refact.dto.meetingDto.MeetingResponseDTO;
import com.example.figuremall_refact.repository.meetingRepository.MeetingRepository;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserService userService;

    @Value("${app.baseUrl}")
    private String baseUrl;

    public Meeting findById(Long id){
        return meetingRepository.findById(id).orElseThrow(() -> new MeetingHandler(ErrorStatus.MEETING_NOT_FOUND));
    }

    @Transactional
    public MeetingResponseDTO.CreateMeetingResponseDto createMeeting(MeetingRequestDTO.CreateMeetingDto request, String email) {
        User user = userService.findByEmail(email);

        String code = UUID.randomUUID().toString();

        Meeting meeting = Meeting.builder()
                .host(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .inviteCode(code)
                .build();

        return new MeetingResponseDTO.CreateMeetingResponseDto(meetingRepository.save(meeting).getId(), baseUrl + "/meetings/" + code);
    }

    @Transactional
    public MeetingResponseDTO.EditMeetingResponseDto editMeeting(MeetingRequestDTO.EditMeetingDto request) {
        Meeting meeting = findById(request.getMeetingId());

        if (request.getTitle() != null) {
            meeting.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            meeting.setDescription(request.getDescription());
        }

        if (request.getStartTime() != null) {
            meeting.setStartTime(request.getStartTime());
        }

        if (request.getEndTime() != null) {
            meeting.setEndTime(request.getEndTime());
        }

        return new MeetingResponseDTO.EditMeetingResponseDto(meeting.getId());
    }

    @Transactional
    public void deleteMeeting(MeetingRequestDTO.DeleteMeetingDto request) {
        meetingRepository.deleteById(request.getMeetingId());
    }

}
