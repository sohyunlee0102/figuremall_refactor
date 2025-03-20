package com.example.figuremall_refact.repository.termRepository;

import com.example.figuremall_refact.domain.term.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {
}
