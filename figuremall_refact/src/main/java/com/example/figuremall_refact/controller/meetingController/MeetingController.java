package com.example.figuremall_refact.controller.meetingController;

import com.example.figuremall_refact.apiPayload.ApiResponse;
import com.example.figuremall_refact.dto.meetingDto.MeetingRequestDTO;
import com.example.figuremall_refact.dto.meetingDto.MeetingResponseDTO;
import com.example.figuremall_refact.service.meetingService.MeetingParticipantService;
import com.example.figuremall_refact.service.meetingService.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final MeetingParticipantService meetingParticipantService;

    @PostMapping
    public ApiResponse<MeetingResponseDTO.CreateMeetingResponseDto> createMeeting(@Valid @RequestBody MeetingRequestDTO.CreateMeetingDto request,
                                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(meetingService.createMeeting(request, userDetails.getUsername()));
    }

    @PutMapping
    public ApiResponse<MeetingResponseDTO.EditMeetingResponseDto> editMeeting(@Valid @RequestBody MeetingRequestDTO.EditMeetingDto request) {
        return ApiResponse.onSuccess(meetingService.editMeeting(request));
    }

    @DeleteMapping
    public ApiResponse<String> deleteMeeting(@Valid @RequestBody MeetingRequestDTO.DeleteMeetingDto request) {
        meetingService.deleteMeeting(request);
        return ApiResponse.onSuccess("모임이 삭제되었습니다.");
    }

    @PostMapping("/participants")
    public ApiResponse<MeetingResponseDTO.CreateParticipantResponseDto> createParticipant(
            @Valid @RequestBody MeetingRequestDTO.CreateParticipantDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(meetingParticipantService.createParticipant(request, userDetails.getUsername()));
    }

    @PutMapping("/participants")
    public ApiResponse<MeetingResponseDTO.CreateParticipantResponseDto> updateParticipant(
            @Valid @RequestBody MeetingRequestDTO.UpdateParticipantDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.onSuccess(meetingParticipantService.updateParticipant(request, userDetails.getUsername()));
    }

}
