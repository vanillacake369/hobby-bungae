package com.example.hobbybungae.domain.hobby.repository;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

	Optional<Hobby> findByHobbyName(String hobbyName);
}
