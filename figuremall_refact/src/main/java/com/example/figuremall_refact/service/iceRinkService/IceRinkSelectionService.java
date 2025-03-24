package com.example.figuremall_refact.service.iceRinkService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.IceRinkHandler;
import com.example.figuremall_refact.domain.iceRink.IceRink;
import com.example.figuremall_refact.domain.iceRink.IceRinkSelection;
import com.example.figuremall_refact.domain.meeting.MeetingParticipant;
import com.example.figuremall_refact.dto.iceRinkDto.IceRinkRequestDTO;
import com.example.figuremall_refact.repository.iceRinkRepository.IceRinkSelectionRepository;
import com.example.figuremall_refact.service.meetingService.MeetingParticipantService;
import com.example.figuremall_refact.service.userService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IceRinkSelectionService {

    private final IceRinkSelectionRepository iceRinkSelectionRepository;
    private final IceRinkService iceRinkService;
    private final MeetingParticipantService meetingParticipantService;
    private final UserService userService;

    public IceRinkSelection findById(Long id) {
        return iceRinkSelectionRepository.findById(id).orElseThrow(() -> new IceRinkHandler(ErrorStatus.ICE_RINK_SELECTION_NOT_FOUND));
    }

    @Transactional
    public void addIceRinkSelection(IceRinkRequestDTO.AddIceRinkSelectionDto requestList, String email) {
        List<IceRinkSelection> selectionList = new ArrayList<>();

        MeetingParticipant participant = meetingParticipantService.findById(userService.findByEmail(email).getId());

        for (Long iceRinkId : requestList.getIceRinkIds()) {
            IceRink iceRink = iceRinkService.findById(iceRinkId);

            IceRinkSelection iceRinkSelection = IceRinkSelection.builder()
                    .participant(participant)
                    .iceRink(iceRink)
                    .build();

            selectionList.add(iceRinkSelection);
        }

        iceRinkSelectionRepository.saveAll(selectionList);
    }

    @Transactional
    public void updateIceRinkSelection(IceRinkRequestDTO.AddIceRinkSelectionDto requestList, String email) {
        List<IceRinkSelection> selectionList = new ArrayList<>();

        MeetingParticipant participant = meetingParticipantService.findById(userService.findByEmail(email).getId());

        iceRinkSelectionRepository.deleteAllByParticipant(participant);

        for (Long iceRinkId : requestList.getIceRinkIds()) {
            IceRink iceRink = iceRinkService.findById(iceRinkId);

            IceRinkSelection iceRinkSelection = IceRinkSelection.builder()
                    .participant(participant)
                    .iceRink(iceRink)
                    .build();

            selectionList.add(iceRinkSelection);
        }

        iceRinkSelectionRepository.saveAll(selectionList);
    }

}
