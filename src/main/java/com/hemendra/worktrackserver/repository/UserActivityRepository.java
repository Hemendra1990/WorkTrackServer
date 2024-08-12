package com.hemendra.worktrackserver.repository;

import com.hemendra.worktrackserver.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    Optional<UserActivity> findBySessionId(UUID sessionId);
}
