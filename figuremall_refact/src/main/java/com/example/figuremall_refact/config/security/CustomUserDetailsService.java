package com.example.figuremall_refact.config.security;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.AuthHandler;
import com.example.figuremall_refact.apiPayload.exception.handler.UserHandler;
import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.figuremall_refact.domain.user.User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    return new AuthHandler(ErrorStatus.AUTHENTICATION_FAILED);
                });

        if (user.getStatus() == Status.INACTIVE) {
            throw new UserHandler(ErrorStatus.INACTIVE_USER);
        }

        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}
