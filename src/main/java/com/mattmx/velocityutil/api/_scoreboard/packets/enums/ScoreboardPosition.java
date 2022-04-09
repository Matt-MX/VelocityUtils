package com.mattmx.velocityutil.api._scoreboard.packets.enums;

public enum ScoreboardPosition {
    LIST(0),
    SIDEBAR(1),
    BELOW_NAME(2),
    TEAM_SPECIFIC(3);
    private int id;
    private ScoreboardPosition(int id) {
        this.id = id;
    }

    public static ScoreboardPosition positionFromProtocolId(int id) {
        for (ScoreboardPosition val : values()) {
            if (val.id == id) {
                return val;
            }
        }
        if (id <= 18 && id >= 3) {
            return TEAM_SPECIFIC;
        }
        return null;
    }

    public int protocolId() {
        return id;
    }
}
