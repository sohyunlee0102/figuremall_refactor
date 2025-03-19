package com.example.figuremall_refact.repository.userRepository;

import com.example.figuremall_refact.domain.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
