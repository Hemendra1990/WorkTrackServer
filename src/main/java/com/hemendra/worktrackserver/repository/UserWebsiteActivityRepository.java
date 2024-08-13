package com.hemendra.worktrackserver.repository;

import com.hemendra.worktrackserver.entity.UserWebsiteActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserWebsiteActivityRepository extends JpaRepository<UserWebsiteActivity, Long> {
    Optional<UserWebsiteActivity> findBySessionId(UUID sessionId);
}
