package com.example.hobbybungae.domain.user.service;

import com.example.hobbybungae.domain.user.dto.request.UserRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.DuplicatedUserException;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String DUPLICATED_USER_ERROR_MESSAGE = "중복되지 않는 아이디를 확인해주시길 바랍니다.";
    //    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean hasDuplicatedUser(String idName) {
        return userRepository.findByIdName(idName).isPresent();
    }

    @Transactional
    public ResponseEntity<UserResponseDto> signUp(UserRequestDto requestDto) throws DuplicatedUserException {
        verifyDuplicatedUser(requestDto);

        User newUser = User.builder()
                .idName(requestDto.idName())
                .name(requestDto.name())
                .password(passwordEncoder.encode(requestDto.password()))
                .build();
        userRepository.save(newUser);

//        profileService.createNewUserProfile(newUser);

        return new ResponseEntity<>(UserResponseDto.successResponseOf(newUser), HttpStatus.OK);
    }

    private void verifyDuplicatedUser(UserRequestDto requestDto) throws DuplicatedUserException {
        if (hasDuplicatedUser(requestDto.idName())) {
            throw new DuplicatedUserException("id_name", requestDto.idName(), DUPLICATED_USER_ERROR_MESSAGE);
        }
    }
}
