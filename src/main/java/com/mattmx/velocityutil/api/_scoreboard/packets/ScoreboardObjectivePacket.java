package com.mattmx.velocityutil.api._scoreboard.packets;

import com.mattmx.velocityutil.api._scoreboard.packets.enums.OptionalVarInt;
import com.mattmx.velocityutil.api._scoreboard.packets.enums.ScoreboardMode;
import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.packet.AbstractPacket;
import dev.simplix.protocolize.api.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;

import java.util.List;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_17_1;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_LATEST;

public class ScoreboardObjectivePacket extends AbstractPacket {

    public ScoreboardObjectivePacket(String name, ScoreboardMode mode) {
        this(name, mode, null, null);
    }

    public ScoreboardObjectivePacket(String name, ScoreboardMode mode, String objectValue, OptionalVarInt type) {
        this.objectiveName = name;
        this.mode = mode;
        this.objectValue = objectValue;
        this.type = type;
    }

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_17_1, MINECRAFT_LATEST, 0x53)
    );

    private String objectiveName;
    private ScoreboardMode mode;
    private String objectValue;
    private OptionalVarInt type;

    @Override
    public void read(ByteBuf byteBuf, PacketDirection packetDirection, int i) {
        this.objectiveName = ProtocolUtil.readString(byteBuf);
        this.mode = ScoreboardMode.modeByProtocolId(ProtocolUtil.readVarInt(byteBuf));
        if (mode == ScoreboardMode.CREATE || mode == ScoreboardMode.UPDATE) {
            this.objectValue = ProtocolUtil.readString(byteBuf);
            this.type = OptionalVarInt.varByProtocolId(ProtocolUtil.readVarInt(byteBuf));
        }
    }

    @Override
    public void write(ByteBuf byteBuf, PacketDirection packetDirection, int i) {
        ProtocolUtil.writeString(byteBuf, objectiveName);
        ProtocolUtil.writeVarInt(byteBuf, mode.protocolId());
        if (mode == ScoreboardMode.CREATE || mode == ScoreboardMode.UPDATE) {
            ProtocolUtil.writeString(byteBuf, objectValue);
            ProtocolUtil.writeVarInt(byteBuf, type.protocolId());
        }
    }
}
