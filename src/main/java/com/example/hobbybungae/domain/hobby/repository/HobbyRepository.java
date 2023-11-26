package com.example.hobbybungae.domain.hobby.repository;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

	List<Hobby> findByHobbyName(String hobbyName);
}
