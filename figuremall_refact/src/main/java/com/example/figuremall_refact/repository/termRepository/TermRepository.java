package com.example.figuremall_refact.repository.termRepository;

import com.example.figuremall_refact.domain.term.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
}
