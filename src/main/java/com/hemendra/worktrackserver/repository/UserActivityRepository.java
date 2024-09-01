package com.hemendra.worktrackserver.repository;

import com.hemendra.worktrackserver.dto.UserActivityDto;
import com.hemendra.worktrackserver.entity.UserActivity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    Optional<UserActivity> findBySessionId(UUID sessionId);

    @Query(value = "select user_name, mac_address, activity_type, start_time from user_activity", nativeQuery = true)
    List<Tuple> findAllByCustom();

    default List<UserActivityDto> findAllTupleCustom() {
        List<Tuple> allByCustom = this.findAllByCustom();
        return allByCustom.stream().map(tuple -> {
            UserActivityDto userActivityDto = new UserActivityDto();
            userActivityDto.setUserName(tuple.get(0, String.class));
            userActivityDto.setMacAddress(tuple.get(1, String.class));
            return userActivityDto;
        }).toList();
    }
}
