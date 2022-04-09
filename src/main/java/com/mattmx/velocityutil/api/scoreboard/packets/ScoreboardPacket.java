package com.mattmx.velocityutil.api.scoreboard.packets;
import com.velocitypowered.api.util.MessagePosition;
import dev.simplix.protocolize.api.Hand;
import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.packet.AbstractPacket;
import dev.simplix.protocolize.api.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;

import java.util.*;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_17_1;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_LATEST;

public class ScoreboardPacket extends AbstractPacket {

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_17_1, MINECRAFT_LATEST, 0x4C)
    );

    private Byte position;
    private String scoreName;


    @Override
    public void read(ByteBuf byteBuf, PacketDirection packetDirection, int i) {

    }

    @Override
    public void write(ByteBuf byteBuf, PacketDirection packetDirection, int i) {

    }
}
