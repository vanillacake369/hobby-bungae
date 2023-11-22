package com.example.hobbybungae.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, Long> {
    List<ProfileEntity> findAllByOrderByHobbiesDesc();
}
