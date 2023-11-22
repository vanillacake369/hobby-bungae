package com.example.hobbybungae.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class User extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_name",nullable = false)
    private String idName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

//    @OneToMany
//    private List<BungaePost> bungaePosts;
//
//    @OneToOne
//    private Profile profile;
}
