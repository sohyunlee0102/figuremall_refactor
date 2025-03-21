package com.example.figuremall_refact.repository.reportRepostiory;

import com.example.figuremall_refact.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
