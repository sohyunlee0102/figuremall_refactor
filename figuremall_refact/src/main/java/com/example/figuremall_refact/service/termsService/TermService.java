package com.example.figuremall_refact.service.termsService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.TermHandler;
import com.example.figuremall_refact.domain.term.Term;
import com.example.figuremall_refact.dto.termDto.TermRequestDTO;
import com.example.figuremall_refact.dto.termDto.TermResponseDTO;
import com.example.figuremall_refact.repository.termRepository.TermRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;

    public Term findById(Long id){
        return termRepository.findById(id).orElseThrow(() -> new TermHandler(ErrorStatus.TERM_NOT_FOUND));
    }

    @Transactional
    public TermResponseDTO.CreateTermResponseDto createTerm(TermRequestDTO.CreateTermDto request) {
        Term term = Term.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .version(request.getVersion())
                .isOptional(request.getIsOptional())
                .build();

        return new TermResponseDTO.CreateTermResponseDto(termRepository.save(term).getId());
    }

    @Transactional
    public TermResponseDTO.CreateTermResponseDto updateTerm(TermRequestDTO.UpdateTermDto request) {
        Term term = findById(request.getTermId());

        if (request.getTitle() != null) {
            term.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            term.setContent(request.getContent());
        }

        if (request.getVersion() != null) {
            term.setVersion(request.getVersion());
        }

        if (request.getIsOptional() != null) {
            term.setIsOptional(request.getIsOptional());
        }

        return new TermResponseDTO.CreateTermResponseDto(term.getId());
    }

    @Transactional
    public void deleteTerm(TermRequestDTO.DeleteTermDto request) {
        termRepository.delete(findById(request.getTermId()));
    }

}
