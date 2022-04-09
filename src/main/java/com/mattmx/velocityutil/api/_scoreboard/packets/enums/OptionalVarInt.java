package com.mattmx.velocityutil.api._scoreboard.packets.enums;

public enum OptionalVarInt {
    INTEGER(0, "integer"),
    HEARTS(1, "hearts");
    private int protocolId;
    private String name;

    private OptionalVarInt(int protocolId, String name) {
        this.protocolId = protocolId;
        this.name = name;
    }

    public static OptionalVarInt varByProtocolId(int protocolId) {
        OptionalVarInt[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            OptionalVarInt varInt = var1[var3];
            if (varInt.protocolId() == protocolId) {
                return varInt;
            }
        }

        return null;
    }

    public int protocolId() {
        return this.protocolId;
    }
}
