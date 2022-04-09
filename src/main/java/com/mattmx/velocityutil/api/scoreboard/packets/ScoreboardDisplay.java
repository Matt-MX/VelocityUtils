package com.mattmx.velocityutil.api.scoreboard.packets;

import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.packet.AbstractPacket;
import dev.simplix.protocolize.api.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_17_1;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_LATEST;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
public class ScoreboardDisplay extends AbstractPacket {

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_17_1, MINECRAFT_LATEST, 0x4C)
    );

    /**
     * 0 = list, 1 = side, 2 = below.
     */
    private byte position;
    private String name;

    @Override
    public void read(ByteBuf buf, PacketDirection direction, int i) {
        position = buf.readByte();
        name = ProtocolUtil.readString(buf);
    }

    @Override
    public void write(ByteBuf buf, PacketDirection direction, int i) {
        buf.writeByte(position);
        ProtocolUtil.writeString(buf, name);
    }
}
