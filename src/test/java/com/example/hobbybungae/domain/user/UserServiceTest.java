package com.example.hobbybungae.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.TimeStamp;
import com.example.hobbybungae.domain.dto.CommonResponseDto;
import com.example.hobbybungae.domain.dto.ErrorResponseDto;
import com.example.hobbybungae.domain.dto.SuccessResponseDto;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({UserService.class, BCryptPasswordEncoder.class, TimeStamp.class, User.class})
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public static Stream<Arguments> getDuplicatedUser() {
        return Stream.of(
                Arguments.of(
                        User.builder()
                                .idName("jihoon9898")
                                .name("jihoon")
                                .password("1234")
                                .build()
                )
        );
    }

    public static Stream<Arguments> createNewUserSuccess() {
        return Stream.of(
                Arguments.of(
                        UserRequestDto.builder()
                                .idName("sparta1234")
                                .name("happyhappy")
                                .password("1234qwer")
                                .build()
                )
        );
    }

    public static Stream<Arguments> createNewUserDuplicated() {
        return Stream.of(
                Arguments.of(
                        UserRequestDto.builder()
                                .idName("jihoon9898")
                                .name("jihoon")
                                .password("1234")
                                .build()
                )
        );
    }

    @BeforeEach
    @Transactional
    void setUp() {
        User user = User.builder()
                .idName("jihoon9898")
                .name("jihoon")
                .password("1234")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @DisplayName("중복회원을 검증합니다.")
    @MethodSource("getDuplicatedUser")
    public void 중복회원_검증(User user) {
        // WHEN
        String idName = user.getIdName();
        boolean hasDuplicatedUser = userService.hasDuplicatedUser(idName);

        // THEN
        assertTrue(hasDuplicatedUser);
    }

    @ParameterizedTest
    @DisplayName("새로운 회원이 회원가입에 성공합니다.")
    @MethodSource("createNewUserSuccess")
    public void 회원가입_해피케이스(UserRequestDto requestDto) {
        // WHEN
        ResponseEntity<CommonResponseDto> commonResponseDtoResponseEntity = userService.signUp(requestDto);

        // THEN
        assertEquals(commonResponseDtoResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(commonResponseDtoResponseEntity.getBody()).getClass(),
                SuccessResponseDto.class);
    }

    @ParameterizedTest
    @DisplayName("중복된 회원은 회원가입에 실패합니다.")
    @MethodSource("createNewUserDuplicated")
    public void 회원가입_언해피케이스_중복회원(UserRequestDto requestDto) {
        // WHEN
        ResponseEntity<CommonResponseDto> commonResponseDtoResponseEntity = userService.signUp(requestDto);

        // THEN
        assertEquals(commonResponseDtoResponseEntity.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(Objects.requireNonNull(commonResponseDtoResponseEntity.getBody()).getClass(),
                ErrorResponseDto.class);
    }
}