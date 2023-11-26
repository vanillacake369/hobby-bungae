package com.example.hobbybungae.domain.userProfile.service;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileResponseDto;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingIdException;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingPasswordException;
import com.example.hobbybungae.domain.userProfile.exception.ProfileUserNotFoundException;
import com.example.hobbybungae.domain.userProfile.repository.UserHobbyRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

	private final UserRepository userRepository;

	private final UserHobbyRepository userHobbyRepository;

	private final PasswordEncoder passwordEncoder;

	private static void validateId(Long inputId, Long signedInUserId) {
		if (!Objects.equals(inputId, signedInUserId)) {
			throw new NotMatchingIdException();
		}
	}

	// 사용자 프로필 조회
	@Transactional(readOnly = true)
	public UserProfileResponseDto getUser(Long id, User inputUser) {
		validateId(id, inputUser.getId());
		User user = getUserEntity(id);
		return UserProfileResponseDto.of(user);
	}

	/**
	 * 패스워드 수정값을 포함한 사용자 프로필 수정
	 *
	 * @param id
	 * @param requestDto
	 * @param inputUser
	 * @return 수정된 사용자 프로필 데이터
	 * <p>
	 * Validation은 따로 분리하는 게 좋아보임/
	 * @author 임지훈
	 */
	public UserProfileResponseDto updateUser(Long id, UserProfileUpdateRequestDto requestDto, User inputUser) {
		validateId(id, inputUser.getId());
		if (!requestDto.password().isEmpty()) {
			String inputPassword = requestDto.passwordReconfirm();
			if (!passwordEncoder.matches(inputPassword, inputUser.getPassword())) {
				throw new NotMatchingPasswordException();
			}
			//inputUser.updatePassword(passwordEncoder.encode(requestDto.getPassword())); // 지훈님 User에 만들어야함
		}
		// inputUser.update(requestDto);

		//현재 유저의 취미를 모두 삭제하고 새로 등록
		List<UserHobby> userHobbyList = userHobbyRepository.findAllByUserId(id);
		userHobbyRepository.deleteAll(userHobbyList);
		List<Hobby> newHobbyList = requestDto.hobbyList();
		for (Hobby hobby : newHobbyList) {
			inputUser.addHobby(hobby);
		}

		return UserProfileResponseDto.of(inputUser);
	}

	@Transactional(readOnly = true)
	public User getUserEntity(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ProfileUserNotFoundException("user_id", userId.toString()));
	}
}
