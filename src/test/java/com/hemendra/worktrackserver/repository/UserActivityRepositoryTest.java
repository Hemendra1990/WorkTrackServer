package com.hemendra.worktrackserver.repository;

import com.hemendra.worktrackserver.dto.UserActivityDto;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class UserActivityRepositoryTest {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Test
    void checkUserNative() {
        List<UserActivityDto> allTupleCustom = userActivityRepository.findAllTupleCustom();
        log.info("{}", allTupleCustom.size());
    }

}