package com.hemendra.worktrackserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_activity")
@Data
public class AppActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String userName;
    String macAddress;

    LocalDateTime startTime;
    LocalDateTime endTime;
    Long duration;

    @Column(columnDefinition = "TEXT", nullable = true)
    String activeWindow;

    String appCategory;
    String appName;
}
