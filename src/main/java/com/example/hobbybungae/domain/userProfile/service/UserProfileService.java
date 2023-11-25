package com.example.hobbybungae.domain.userProfile.service;

import static com.example.hobbybungae.global_exception.ErrorCode.NOT_FOUND_PROFILE_USER;

import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileResponseDto;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingPasswordException;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingIdException;
import com.example.hobbybungae.domain.userProfile.exception.ProfileUserNotFoundException;
import com.example.hobbybungae.global_exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return UserProfileResponseDto.of(user);
    }

    // Update Password
    @Transactional
    public UserProfileResponseDto updateUser(Long userId, UserProfileUpdateRequestDto requestDto, User inputUser) {
        if (!inputUser.getIdName().equals(userId)) {
            throw new NotMatchingIdException();
        }
//        if (!requestDto.getPassword().isEmpty()) {
//            String inputPassword = requestDto.getPasswordReconfirm();
//            if(!passwordEncoder.matches(inputPassword, inputUser.getPassword())) {
//                throw new NotMatchingPasswordException();
//            }
//            //inputUser.updatePassword(requestDto); // 지훈님 User에 만들어야함
//        }
        //inputUser.update(requestDto); // 지훈님 User에 만들어야함
        return UserProfileResponseDto.of(inputUser);
    }

    @Transactional(readOnly = true)
    public User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ProfileUserNotFoundException("user_id", userId.toString()));
    }
}
