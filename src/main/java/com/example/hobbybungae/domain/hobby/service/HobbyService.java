package com.example.hobbybungae.domain.hobby.service;

import com.example.hobbybungae.domain.hobby.dto.HobbyRequestDto;
import com.example.hobbybungae.domain.hobby.dto.HobbyResponseDto;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.exception.DuplicatedHobbyException;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyRepository hobbyRepository;

    public HobbyResponseDto postHobby(HobbyRequestDto requestDto) {
        String hobbyName = requestDto.getHobbyName();

        //중복 검사
        validateDuplication(hobbyName);

        Hobby hobby = Hobby.builder().hobbyName(hobbyName).build();
        hobbyRepository.save(hobby);
        return new HobbyResponseDto(hobby);
    }

    private void validateDuplication(String hobbyName) {
        Optional<Hobby> checkHobbyName = hobbyRepository.findByHobbyName(hobbyName);
        if (checkHobbyName.isPresent()) {
            throw new DuplicatedHobbyException("hobby's name", hobbyName);
        }
    }

}
