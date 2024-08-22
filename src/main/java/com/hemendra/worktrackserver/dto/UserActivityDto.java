package com.hemendra.worktrackserver.dto;

import com.hemendra.worktrackserver.enums.ActivityState;
import com.hemendra.worktrackserver.enums.ActivityType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserActivityDto implements Serializable {
    Long id;
    String userName;
    String macAddress;
    ActivityType activityType;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long duration;
    UUID sessionId;
    ActivityState state;

    public UserActivityDto() {
    }

    public UserActivityDto(String userName, String macAddress, ActivityType activityType, LocalDateTime startTime) {
        this.userName = userName;
        this.macAddress = macAddress;
        this.activityType = activityType;
        this.startTime = startTime;
    }
}
