package com.mattmx.velocityutil.api._scoreboard.packets.enums;

public enum ScoreboardMode {
    CREATE(0),
    REMOVE(1),
    UPDATE(2);
    private final int protocolId;
    private ScoreboardMode(int protocolId) {
        this.protocolId = protocolId;
    }

    public static ScoreboardMode modeByProtocolId(int protocolId) {
        ScoreboardMode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ScoreboardMode mode = var1[var3];
            if (mode.protocolId() == protocolId) {
                return mode;
            }
        }

        return null;
    }

    public int protocolId() {
        return this.protocolId;
    }

}
