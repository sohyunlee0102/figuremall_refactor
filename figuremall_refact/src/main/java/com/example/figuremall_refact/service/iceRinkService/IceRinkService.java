package com.example.figuremall_refact.service.iceRinkService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.IceRinkHandler;
import com.example.figuremall_refact.domain.iceRink.IceRink;
import com.example.figuremall_refact.dto.iceRinkDto.IceRinkRequestDTO;
import com.example.figuremall_refact.dto.iceRinkDto.IceRinkResponseDTO;
import com.example.figuremall_refact.repository.iceRinkRepository.IceRinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IceRinkService {

    private final IceRinkRepository iceRinkRepository;

    public IceRink findById(Long id) {
        return iceRinkRepository.findById(id).orElseThrow(() -> new IceRinkHandler(ErrorStatus.ICE_RINK_NOT_FOUND));
    }

    @Transactional
    public IceRinkResponseDTO createIceRink(IceRinkRequestDTO.AddIceRinkDto request) {
        IceRink iceRink = IceRink.builder()
                .name(request.getName())
                .city(request.getCity())
                .district(request.getDistrict())
                .detailedAddress(request.getDetailedAddress())
                .phone(request.getPhone())
                .build();

        return new IceRinkResponseDTO(iceRinkRepository.save(iceRink).getId());
    }

    @Transactional
    public IceRinkResponseDTO updateIceRink(IceRinkRequestDTO.UpdateIceRinkDto request) {
        IceRink iceRink = findById(request.getIceRinkId());

        if (request.getName() != null) {
            iceRink.setName(request.getName());
        }

        if (request.getCity() != null) {
            iceRink.setCity(request.getCity());
        }

        if (request.getDistrict() != null) {
            iceRink.setDistrict(request.getDistrict());
        }

        if (request.getDetailedAddress() != null) {
            iceRink.setDetailedAddress(request.getDetailedAddress());
        }

        if (request.getPhone() != null) {
            iceRink.setPhone(request.getPhone());
        }

        return new IceRinkResponseDTO(iceRink.getId());
    }

    @Transactional
    public void deleteIceRink(IceRinkRequestDTO.DeleteIceRinkDto request) {
        iceRinkRepository.deleteById(request.getIceRinkId());
    }

}
