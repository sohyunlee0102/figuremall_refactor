package com.example.figuremall_refact.repository.userRepository;

import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.domain.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    UserAddress findByUserAndIsDefault(User user, boolean isDefault);
    List<UserAddress> findAllByUser(User user);
}
