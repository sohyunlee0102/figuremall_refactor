package com.example.figuremall_refact.service.meetingService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.MeetingHandler;
import com.example.figuremall_refact.domain.meeting.AvailableTime;
import com.example.figuremall_refact.domain.meeting.MeetingParticipant;
import com.example.figuremall_refact.dto.meetingDto.MeetingRequestDTO;
import com.example.figuremall_refact.dto.meetingDto.MeetingResponseDTO;
import com.example.figuremall_refact.repository.meetingRepository.AvailableTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableTimeService {

    private final AvailableTimeRepository availableTimeRepository;

    public AvailableTime findById(Long id) {
        return availableTimeRepository.findById(id).orElseThrow(() -> new MeetingHandler(ErrorStatus.AVAILABLE_TIME_NOT_FOUND));
    }

    @Transactional
    public void saveAvailableTimes(List<MeetingRequestDTO.AddAvailableTimeDto> requestList, MeetingParticipant meetingParticipant) {
        List<AvailableTime> availableTimes = new ArrayList<>();

        for (MeetingRequestDTO.AddAvailableTimeDto availableTimeDto : requestList) {
            AvailableTime availableTime = AvailableTime.builder()
                    .participant(meetingParticipant)
                    .startTime(availableTimeDto.getStartTime())
                    .endTime(availableTimeDto.getEndTime())
                    .build();

            availableTimes.add(availableTime);
        }

        availableTimeRepository.saveAll(availableTimes);
    }

    @Transactional
    public void deleteAll(MeetingParticipant meetingParticipant) {
        availableTimeRepository.deleteAllByParticipant(meetingParticipant);
    }

}
