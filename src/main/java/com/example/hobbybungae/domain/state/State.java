package com.example.hobbybungae.domain.state;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
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
