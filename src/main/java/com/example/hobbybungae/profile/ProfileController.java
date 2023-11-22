package com.example.hobbybungae.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hobby-bungae/v1/users/profile")
public class ProfileController {
    private final ProfileService profileService;

    // 프로필 작성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponseDto editProfile(
            @RequestBody ProfileEditRequestDto requestDto
    ) {
        ProfileResponseDto responseDto = profileService.editProfile(requestDto);
        return responseDto;
    }

    // 프로필 조회
    @GetMapping("/{$user-id}")
    public ProfileResponseDto getProfile(
            @PathVariable Long profileId;
    ) {
        return profileService.getProfile(profileId);
    }
    @GetMapping
    public ResponseEntity<List<ProfileResponseDto>> getPosts() {
        List<ProfileResponseDto> responseDto = profileService.getProfile();
        return ResponseEntity.ok(responseDto);
    }

    // 프로필 수정
    @PatchMapping("/{$user-id}")
    public ResponseEntity<ProfileResponseDto> updateProfile(
            @PathVariable Long profileId,
            @RequestBody ProfileUpdateRequestDto requestDto);
    )
}
