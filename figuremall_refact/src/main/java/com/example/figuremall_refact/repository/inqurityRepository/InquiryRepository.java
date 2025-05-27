package com.example.figuremall_refact.repository.inqurityRepository;

import com.example.figuremall_refact.domain.inquiry.Inquiry;
import com.example.figuremall_refact.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Page<Inquiry> findByUser(User user, Pageable pageable);
}
