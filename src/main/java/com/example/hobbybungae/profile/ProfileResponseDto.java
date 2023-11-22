package com.example.hobbybungae.profile;

import lombok.Getter;

@Getter
public record ProfileResponseDto (
        Long profileId,
        Long user_id,
        String password,
        String name,
        String introduction //,
        // List<hobby> hobbies
) {
    public static ProfileResponseDto fromProfile(ProfileEntity editProfile) {
        return new ProfileResponseDto(
                editProfile.getProfile_Id(),
                editProfile.getUser_id(),
                editProfile.getPassword(),
                editProfile.getName(),
                editProfile.getIntroduction() //,
               // saveProfile.getHobbies()
        );
    }
}

