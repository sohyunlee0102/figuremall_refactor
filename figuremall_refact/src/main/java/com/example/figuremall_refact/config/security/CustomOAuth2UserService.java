package com.example.figuremall_refact.config.security;

import com.example.figuremall_refact.domain.enums.Gender;
import com.example.figuremall_refact.domain.enums.Provider;
import com.example.figuremall_refact.domain.enums.Role;
import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.domain.user.User;
import com.example.figuremall_refact.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        String name = null;
        String email = null;
        String picture = null;

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        System.out.println(provider);

        if (provider.equals("naver")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("response");
            name = (String) response.get("name");
            email = (String) response.get("email");
            picture = (String) response.get("profile_image");
        } else if (provider.equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");
            picture = (String) profile.get("profile_image_url");
        } else if (provider.equals("google")) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            picture = oAuth2User.getAttribute("picture");
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            userRepository.save(
                    User.builder()
                            .email(email)
                            .picture(picture)
                            .username(name)
                            .status(Status.ACTIVE)
                            .point(0)
                            .provider(Provider.valueOf(provider.toUpperCase()))
                            .role(Role.USER)
                            .build()
            );
        }

        attributes.put("name", name);
        attributes.put("email", email);
        attributes.put("picture", picture);
        attributes.put("provider", provider);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                "email"
        );
    }
}
