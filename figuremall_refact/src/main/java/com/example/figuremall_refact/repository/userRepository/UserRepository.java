package com.example.figuremall_refact.repository.userRepository;

import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStatusAndInactiveDateBefore(Status status, LocalDate inactiveDate);

}
