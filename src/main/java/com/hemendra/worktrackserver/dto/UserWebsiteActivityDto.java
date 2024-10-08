package com.hemendra.worktrackserver.dto;

import com.hemendra.worktrackserver.enums.ActivityType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserWebsiteActivityDto implements Serializable {

    private static final long serialVersionUID = 12221121121L;

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
