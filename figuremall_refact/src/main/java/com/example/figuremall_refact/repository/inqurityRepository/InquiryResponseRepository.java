package com.example.figuremall_refact.repository.inqurityRepository;

import com.example.figuremall_refact.domain.inquiry.Inquiry;
import com.example.figuremall_refact.domain.inquiry.InquiryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryResponseRepository extends JpaRepository<InquiryResponse, Long> {
    InquiryResponse findByInquiryId(Long inquiryId);
}
