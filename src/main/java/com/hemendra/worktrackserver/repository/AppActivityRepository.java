package com.hemendra.worktrackserver.repository;

import com.hemendra.worktrackserver.entity.AppActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppActivityRepository extends JpaRepository<AppActivity, Long> {

    Optional<AppActivity> findByUserName(String userName);
}
