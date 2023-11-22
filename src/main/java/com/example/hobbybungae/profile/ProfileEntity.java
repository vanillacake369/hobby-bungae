package com.example.hobbybungae.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "profiles")
@NoArgsConstructor
public class ProfileEntity {
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long profile_Id;
    @Column(nullable = false,length = 10)
    private Long user_id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false,length = 10)
    private String name;
    @Column(nullable = false,length = 100)
    private String introduction;
    @Column
    private String state;
    @ManyToMany
    @JoinTable(
            name = "user_hobby",
            joinColumns = @JoinColumn(name = "hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Hobby> hobbies = new ArrayList<>();

    // 프로필 내용
    public ProfileEntity(ProfileEditRequestDto requestDto) {
        this.user_id = requestDto.getUser_id();
        this.name = requestDto.getName();
        this.password = requestDto.getPassword();
        this.introduction = requestDto.getIntroduction();
        this.state = requestDto.getState();
        this.hobbies = requestDto.getHobbies();
    }

    // 수정 내용
    public void update(ProfileUpdateRequestDto requestDto) {
        this.user_id = getUser_id();
        this.name = getName();
        this.introduction = getIntroduction();
        this.state = getState();
        this.hobbies = getHobbies();
    }

    // 비밀번호 확인
    public boolean passwordMatches(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
