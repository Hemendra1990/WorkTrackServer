package com.hemendra.worktrackserver.dto;

import com.hemendra.worktrackserver.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserWebsiteActivityDto {
    Long id;
    String userName;
    String macAddress;
    ActivityType activityType;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long duration;
    UUID sessionId;
    String url;
    String activeWindow;
}
