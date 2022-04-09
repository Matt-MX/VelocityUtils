package com.mattmx.velocityutil.api._scoreboard.packets;
import com.mattmx.velocityutil.api._scoreboard.packets.enums.ScoreboardPosition;
import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.packet.AbstractPacket;
import dev.simplix.protocolize.api.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;

import java.util.*;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_17_1;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_LATEST;

public class DisplayScoreboardPacket extends AbstractPacket {

    public DisplayScoreboardPacket(ScoreboardPosition pos, String name) {
        this.position = pos;
        this.scoreName = name;
    }

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_17_1, MINECRAFT_LATEST, 0x4C)
    );

    private ScoreboardPosition position;
    private String scoreName;

    @Override
    public void read(ByteBuf byteBuf, PacketDirection packetDirection, int i) {
        this.position = ScoreboardPosition.positionFromProtocolId(ProtocolUtil.readVarInt(byteBuf));
        this.scoreName = ProtocolUtil.readString(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, PacketDirection packetDirection, int i) {
        ProtocolUtil.write21BitVarInt(byteBuf, position.protocolId());
        ProtocolUtil.writeString(byteBuf, scoreName);
    }
}
