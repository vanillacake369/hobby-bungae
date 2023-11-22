package com.example.hobbybungae.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "si",nullable = false)
    private String siName;

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", siName='" + siName + '\'' +
                '}';
    }
}
