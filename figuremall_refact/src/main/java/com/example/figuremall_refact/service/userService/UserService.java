package com.example.figuremall_refact.service.userService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.UserAddressHandler;
import com.example.figuremall_refact.apiPayload.exception.handler.UserHandler;
import com.example.figuremall_refact.config.jwt.JwtTokenUtil;
import com.example.figuremall_refact.domain.cart.Cart;
import com.example.figuremall_refact.domain.enums.Role;
import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.domain.listener.ListenerUtil;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.domain.user.UserAddress;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.repository.userRepository.UserAddressRepository;
import com.example.figuremall_refact.repository.userRepository.UserRepository;
import com.example.figuremall_refact.service.cartService.CartService;
import com.example.figuremall_refact.service.s3Service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;
    private final S3Service s3Service;
    private final UserAddressRepository userAddressRepository;
    private final CartService cartService;
    private final ValueOperations<String, String> redisOps;
    private final JwtTokenUtil jwtTokenUtil;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Transactional
    public UserResponseDTO.JoinResultDTO joinUser(UserRequestDTO.JoinDto request) {
        ListenerUtil.disableListener();

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (user.getStatus() == Status.INACTIVE) {
                user.setStatus(Status.ACTIVE);
                user.setInactiveDate(null);
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsername(request.getUsername());
                userRepository.save(user);
                ListenerUtil.enableListener();
                return new UserResponseDTO.JoinResultDTO(user.getEmail(), user.getUsername());
            } else {
                throw new UserHandler(ErrorStatus.EMAIL_ALREADY_EXISTS);
            }
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .point(0)
                .status(Status.ACTIVE)
                .role(Role.USER)
                .build();

        newUser.encodePassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(newUser);

        Cart cart = cartService.createCart(newUser);
        newUser.setCart(cart);

        ListenerUtil.enableListener();

        return new UserResponseDTO.JoinResultDTO(newUser.getEmail(), newUser.getUsername());
    }

    @Transactional
    public void deleteUser(String email, String accessToken) {
        User user = findByEmail(email);

        String refreshToken = redisOps.get(email);
        if (refreshToken != null) {
            redisOps.getOperations().delete(email);

            long expiration = jwtTokenUtil.getExpiration(refreshToken);
            redisOps.getOperations().opsForValue().set("BLACKLIST" + refreshToken, "logout", expiration, TimeUnit.SECONDS);
        }

        if (accessToken != null) {
            long expiration = jwtTokenUtil.getExpiration(accessToken);
            redisOps.getOperations().opsForValue().set("BLACKLIST" + accessToken, "logout", expiration, TimeUnit.SECONDS);
        }

        user.setStatus(Status.INACTIVE);
        user.setInactiveDate(LocalDate.now());
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDTO.UpdateResponseDto updateUser(UserRequestDTO.UpdateDTO request, String email, MultipartFile image) {
        User user = findByEmail(email);

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getPhoneNum() != null) {
            user.setPhoneNum(request.getPhoneNum());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadFile(image);
                user.setPicture(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 중 오류 발생", e);
            }
        }

        userRepository.save(user);

        if (request.getAddresses() != null) {
            for (UserRequestDTO.UserAddressDTO updatedAddress : request.getAddresses()) {
                UserAddress address = userAddressRepository.findById(updatedAddress.getId())
                        .orElseThrow(() -> new UserAddressHandler(ErrorStatus.ADDRESS_NOT_FOUND));
                address.setAddress(updatedAddress.getAddress());
                address.setDetail(updatedAddress.getDetail());
                address.setPostalCode(updatedAddress.getPostalCode());

                userAddressRepository.save(address);
            }
        }

        return new UserResponseDTO.UpdateResponseDto(user.getId());
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteInactiveUsers() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<User> usersToDelete = userRepository.findByStatusAndInactiveDateBefore(Status.INACTIVE, thirtyDaysAgo);

        userRepository.deleteAll(usersToDelete);
    }

}
