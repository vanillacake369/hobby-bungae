package com.example.hobbybungae.domain.userProfile.repository;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {

	Optional<UserHobby> findUserHobbyByHobby(Hobby hobby);

	Optional<UserHobby> findUserHobbyByUser(User user);

    List<UserHobby> findAllByUserId(Long id);
}
