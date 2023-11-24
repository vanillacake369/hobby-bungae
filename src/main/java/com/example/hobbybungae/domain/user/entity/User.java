package com.example.hobbybungae.domain.user.entity;

import com.example.hobbybungae.domain.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String password;

//    @OneToMany
//    List<BungaePost> bungaePosts;

//    @OneToOne
//    Profile profile;


    @Builder
    public User(Long id, String idName, String name, String password) {
        this.id = id;
        this.idName = idName;
        this.name = name;
        this.password = password;
    }
}
