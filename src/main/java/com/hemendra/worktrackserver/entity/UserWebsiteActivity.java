package com.hemendra.worktrackserver.entity;

import com.hemendra.worktrackserver.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_website_activity")
@Data
public class UserWebsiteActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String userName;
    String macAddress;

    @Column(columnDefinition = "TEXT", nullable = true)
    @Enumerated(EnumType.STRING)
    ActivityType activityType;

    LocalDateTime startTime;
    LocalDateTime endTime;
    Long duration;
    UUID sessionId;
    @Column(columnDefinition = "TEXT", nullable = true)
    String url;
    @Column(columnDefinition = "TEXT", nullable = true)
    String activeWindow;
}
