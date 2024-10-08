package com.hemendra.worktrackserver.enums;

public enum ActivityType {
    ACTIVE("ACTIVE"),
    IDLE("IDLE"),
    BROWSING("BROWSING"),
    SHUTDOWN("SHUTDOWN"),
    LOCK("LOCK"),
    SLEEP("SLEEP");;

    private String type;

    private ActivityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
