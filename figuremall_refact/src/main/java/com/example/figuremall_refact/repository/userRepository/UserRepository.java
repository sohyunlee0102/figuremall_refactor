package com.example.figuremall_refact.repository.userRepository;

import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByStatusAndInactiveDateBefore(Status status, LocalDate inactiveDate);

}
