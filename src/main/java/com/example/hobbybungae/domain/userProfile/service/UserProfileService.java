package com.example.hobbybungae.domain.userProfile.service;

import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileResponseDto;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingIdException;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingPasswordException;
import com.example.hobbybungae.domain.userProfile.exception.ProfileUserNotFoundException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private static void validateId(Long inputId, Long signedInUserId) {
		if (!Objects.equals(inputId, signedInUserId)) {
			throw new NotMatchingIdException();
		}
	}

	// 사용자 프로필 조회
	public UserProfileResponseDto getUser(Long id, User inputUser) {
		validateId(id, inputUser.getId());
		User user = getUserEntity(id);
		return UserProfileResponseDto.of(user);
	}

	/**
	 * 패스워드 수정값을 포함한 사용자 프로필 수정
	 *
	 * @param id         사용자 PK id
	 * @param requestDto 프로필 수정요청 DTO
	 * @param signInUser 로그인한 프로필 주인 사용자
	 * @return 수정된 사용자 프로필 데이터
	 * <p>
	 * Validation은 따로 분리하는 게 좋아보임/
	 * @author 임지훈
	 */
	// Update Password
	@Transactional
	public UserProfileResponseDto updateUser(Long id, UserProfileUpdateRequestDto requestDto,
		User signInUser) {
		/* VALIDATION */
		/* !! 분리요망 !! */
		validateId(id, signInUser.getId());
		if (!requestDto.password().isEmpty()) {
			String inputPassword = requestDto.passwordReconfirm();
			if (!passwordEncoder.matches(inputPassword, signInUser.getPassword())) {
				throw new NotMatchingPasswordException();
			}
			signInUser.updatePassword(passwordEncoder.encode(requestDto.password())); // 지훈님 User에 만들어야함
		}

		signInUser.update(requestDto);

		return UserProfileResponseDto.of(signInUser);
	}

	@Transactional(readOnly = true)
	public User getUserEntity(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ProfileUserNotFoundException("user_id", userId.toString()));
	}
}
