package com.hemendra.worktrackserver.entity;

import com.hemendra.worktrackserver.enums.ActivityState;
import com.hemendra.worktrackserver.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_activity")
@Data
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    //userName, macAddress, activityType, startTime, endTime, duration
    String userName;
    String macAddress;

    @Column(columnDefinition = "TEXT", nullable = true)
    @Enumerated(EnumType.STRING)
    ActivityType activityType;

    LocalDateTime startTime;
    LocalDateTime endTime;
    Long duration;
    UUID sessionId;
    @Enumerated(EnumType.STRING)
    ActivityState state;

    String reason;
    String activity;
}
