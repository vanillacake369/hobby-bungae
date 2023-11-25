package com.example.hobbybungae.domain.userProfile.entity;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_hobby")
@NoArgsConstructor
public class UserHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hobby_id", nullable = false)
    private Hobby hobby;

    public UserHobby(User user, Hobby hobby) {
        this.user = user;
        this.hobby = hobby;
    }
}