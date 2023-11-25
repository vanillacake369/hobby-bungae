package com.example.hobbybungae.domain.userProfile.service;

import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileResponseDto;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingPasswordException;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingIdException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 프로필 조회
    public UserProfileResponseDto getUser(Long userId , User inputUser) {
        if (!inputUser.getIdName().equals(userId)) {
            throw new NotMatchingIdException();
        }
        User user = getUserEntity(userId);
        return new UserProfileResponseDto(user);
    }

    // Update Password
    @Transactional
    public UserProfileResponseDto updateUser(Long userId, UserProfileUpdateRequestDto requestDto, User inputUser) {
        if (!inputUser.getIdName().equals(userId)) {
            throw new NotMatchingIdException();
        }
        if (!requestDto.getPassword().isEmpty()) {
            String inputPassword = requestDto.getPasswordReconfirm();
            if(!passwordEncoder.matches(inputPassword, inputUser.getPassword())) {
                throw new NotMatchingPasswordException();
            }
            inputUser.updatePassword(requestDto); // 지훈님 User에 만들어야함
        }
        inputUser.update(requestDto); // 지훈님 User에 만들어야함
        return new UserProfileResponseDto(inputUser);
    }

    @Transactional(readOnly = true)
    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 프로필을 찾을 수 없습니다."));
    }
}
