package com.example.hobbybungae.domain.hobby.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "hobbies")
@NoArgsConstructor
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hobbyId;

    @Column
    private String hobbyName;

    @Builder
    public Hobby(String hobbyName) {
        this.hobbyName = hobbyName;
    }

//    @OneToMany(mappedBy = "hobby")
//    private List<UserHobby> userHobbyList = new ArrayList<>();
}
