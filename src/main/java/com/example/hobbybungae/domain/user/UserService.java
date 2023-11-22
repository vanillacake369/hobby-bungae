package com.example.hobbybungae.domain.user;

import com.example.hobbybungae.domain.dto.CommonResponseDto;
import com.example.hobbybungae.domain.dto.ErrorResponseDto;
import com.example.hobbybungae.domain.dto.SuccessResponseDto;
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

    private static final String SIGNUP_ERROR_MESSAGE = "중복되는 회원 아이디가 존재합니다. 다른 아이디로 시도해주세요.";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    boolean hasDuplicatedUser(String idName) {
        return userRepository.findByIdName(idName).isPresent();
    }

    @Transactional
    public ResponseEntity<CommonResponseDto> signUp(UserRequestDto requestDto) {
        if (hasDuplicatedUser(requestDto.idName())) {
            return new ResponseEntity<>(new ErrorResponseDto(SIGNUP_ERROR_MESSAGE),
                    HttpStatus.CONFLICT);
        }

        User newUser = User.builder()
                .idName(requestDto.idName())
                .name(requestDto.name())
                .password(passwordEncoder.encode(requestDto.password()))
                .build();
        userRepository.save(newUser);

        return new ResponseEntity<>(new SuccessResponseDto(newUser), HttpStatus.OK);
    }
}
