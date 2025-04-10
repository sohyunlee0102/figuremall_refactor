package com.example.figuremall_refact.repository.inqurityRepository;

import com.example.figuremall_refact.domain.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
