package com.example.figuremall_refact.service.userService;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.UserAddressHandler;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.domain.user.UserAddress;
import com.example.figuremall_refact.dto.userDto.UserRequestDTO;
import com.example.figuremall_refact.dto.userDto.UserResponseDTO;
import com.example.figuremall_refact.repository.userRepository.UserAddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserService userService;

    public UserAddress findById(Long id) {
        return userAddressRepository.findById(id).orElseThrow(() -> new UserAddressHandler(ErrorStatus.ADDRESS_NOT_FOUND));
    }

    @Transactional
    public UserResponseDTO.AddAddressResponseDto addAddress(UserRequestDTO.AddAddressDTO request, String email) {
        User user = userService.findByEmail(email);
        UserAddress address = UserAddress.builder()
                .address(request.getAddress())
                .detail(request.getDetail())
                .postalCode(request.getPostalCode())
                .isDefault(request.getIsDefault())
                .user(user)
                .build();
        userAddressRepository.save(address);
        return new UserResponseDTO.AddAddressResponseDto(address.getId(), address.getAddress(), address.getDetail(), address.getPostalCode());
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        userAddressRepository.deleteById(addressId);
    }

    @Transactional
    public List<UserResponseDTO.Address> getAddresses(String email) {
        User user = userService.findByEmail(email);
        List<UserAddress> addresses = userAddressRepository.findAllByUser(user);
        List<UserResponseDTO.Address> addressList = new ArrayList<>();

        for (UserAddress address : addresses) {
            addressList.add(new UserResponseDTO.Address(address.getId(), address.getAddress(), address.getDetail(), address.getPostalCode()));
        }
        return addressList;
    }

}
