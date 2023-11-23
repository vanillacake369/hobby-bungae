package com.example.hobbybungae.profile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileJpaRepository profileJpaRepository;

    public ProfileResponseDto editProfile(ProfileEditRequestDto requestDto) {
        ProfileEntity profileEntity = new ProfileEntity(requestDto);
        ProfileEntity editProfile = profileJpaRepository.save(profileEntity);
        return new ProfileResponseDto(editProfile);
    }

    public ProfileResponseDto getProfile(Long profileId) {
        ProfileEntity profileEntity = getProfileEntity(profileId);
        return new ProfileResponseDto(profileEntity);
    }
// Exception
    @Transactional
    public ProfileResponseDto updateProfile(Long profileId, ProfileUpdateRequestDto requestDto) {
        ProfileEntity profileEntity = getProfileEntity(profileId);
        verifyPassword(profileEntity, requestDto.getPassword());
        profileEntity.update(requestDto);
        return new ProfileResponseDto(profileEntity);
    }

    @Transactional(readOnly = true)
    private ProfileEntity getProfileEntity(Long profileId) {
        return profileJpaRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("해당 프로필을 찾을 수 없습니다."));
    }

    private static void verifyPassword(ProfileEntity profileEntity, String password) {
        if (!profileEntity.passwordMatches(password)) {
            throw new AuthorizeEsception("비밀번호가 일치하지 않습니다.");
        }
    }
}

