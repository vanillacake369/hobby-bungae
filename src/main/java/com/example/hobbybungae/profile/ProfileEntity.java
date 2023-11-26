package com.example.hobbybungae.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "profiles")
@NoArgsConstructor
public class ProfileEntity {
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long profileId;
    @Column(nullable = false,length = 10)
    private Long userId;
//    @Column(nullable = false)
//    private String password;
    @Column(nullable = false,length = 10)
    private String name;
    @Column(nullable = false,length = 100)
    private String introduction;

    // 다대다 관계 매핑
    @ManyToMany
    @JoinTable(
            name = "user_hobby",
            joinColumns = @JoinColumn(name = "hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Hobby> hobbies = new ArrayList<>();

    // 수정 내용
    public void update(ProfileUpdateRequestDto requestDto) {
        this.userId = getUserId();
        this.name = getName();
        this.introduction = getIntroduction();
        this.hobbies = getHobbies();
    }

    // 비밀번호 확인
    public boolean passwordMatches(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
