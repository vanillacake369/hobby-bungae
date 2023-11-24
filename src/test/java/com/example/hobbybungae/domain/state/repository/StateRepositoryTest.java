package com.example.hobbybungae.domain.state.repository;

import com.example.hobbybungae.domain.state.entity.State;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StateRepositoryTest {
    @Autowired
    StateRepository stateRepository;

    @Test
    @DisplayName("저장한 시들을 확인합니다.")
    public void 도시데이터확인() throws Exception {
        // GIVEN
        List<State> all = stateRepository.findAll();
        // WHEN
        for (State s : all) {
            System.out.println(s.toString());
        }
        // THEN
    }
}