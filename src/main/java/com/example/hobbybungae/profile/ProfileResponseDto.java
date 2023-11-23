package com.example.hobbybungae.profile;

import lombok.Getter;
import org.springframework.context.annotation.Profile;

@Getter
public record ProfileResponseDto (
        Long profileId,
        Long user_id,
        String password,
        String name,
        String introduction //,
        // List<hobby> hobbies
) {
    public static ProfileResponseDto fromProfile(ProfileEntity updateProfile) {
        return new ProfileResponseDto(
                updateProfile.getProfileId(),
                updateProfile.getUserID(),
                updateProfile.getName(),
                updateProfile.getIntroduction() //,
               // saveProfile.getHobbies()
        );
    }
}

