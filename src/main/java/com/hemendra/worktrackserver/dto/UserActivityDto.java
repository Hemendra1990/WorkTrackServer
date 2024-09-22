package com.hemendra.worktrackserver.dto;

import com.hemendra.worktrackserver.enums.ActivityState;
import com.hemendra.worktrackserver.enums.ActivityType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserActivityDto implements Serializable {

    private static final long serialVersionUID = 2024082402250L;

    Long id;
    String userName;
    String macAddress;
    ActivityType activityType;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long duration;
    UUID sessionId;
    ActivityState state;
    String reason;
    String activity;

    public UserActivityDto() {
    }

    public UserActivityDto(String userName, String macAddress, ActivityType activityType, LocalDateTime startTime) {
        this.userName = userName;
        this.macAddress = macAddress;
        this.activityType = activityType;
        this.startTime = startTime;
    }
}
