package com.example.hobbybungae.domain.user.entity;

import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idName;

    @Column(nullable = false)
    private String name;

    @Column
    private String Introduction;

    @Column(nullable = false)
    private String password;

    @Column
    List<Hobby> hobbyList;

//    @OneToMany
//    List<BungaePost> bungaePosts;

//    @OneToOne
//    Profile profile;


    @Builder
    public User(Long id, String idName, String name, String introduction, String password, List<Hobby> hobbyList) {
        this.id = id;
        this.idName = idName;
        this.name = name;
        this.Introduction = introduction;
        this.password = password;
        this.hobbyList = hobbyList;
    }
}
