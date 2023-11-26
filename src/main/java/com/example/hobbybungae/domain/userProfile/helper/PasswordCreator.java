package com.example.hobbybungae.domain.userProfile.helper;

import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingIdException;
import com.example.hobbybungae.domain.userProfile.exception.NotMatchingPasswordException;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordCreator {

	/**
	 * @param user            로그인된 유저
	 * @param requestDto      유저프로필수정요청dto
	 * @param passwordEncoder injected passwordEncoder
	 * @return 인코드된패스워드 혹은 null :: Optional<String>
	 */
	public static Optional<String> createEncodePasswordOrNull(User user, UserProfileUpdateRequestDto requestDto,
		PasswordEncoder passwordEncoder) throws NotMatchingIdException {
		String requestPassword = requestDto.password();
		if (!requestPassword.isEmpty()) {
			if (!passwordEncoder.matches(requestPassword, user.getPassword())) {
				throw new NotMatchingPasswordException();
			}
			return Optional.of(passwordEncoder.encode(requestPassword));
		}
		return Optional.empty();
	}
}
