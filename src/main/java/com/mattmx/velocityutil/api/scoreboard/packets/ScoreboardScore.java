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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ScoreboardScore extends AbstractPacket {

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_17_1, MINECRAFT_LATEST, 0x56)
    );

    private String itemName;
    /**
     * 0 = create / update, 1 = remove.
     */
    private byte action;
    private String scoreName;
    private int value;

    @Override
    public void read(ByteBuf buf, PacketDirection direction, int i) {
        itemName = ProtocolUtil.readString(buf);
        action = buf.readByte();
        scoreName = ProtocolUtil.readString(buf);
        if (action != 1) {
            value = ProtocolUtil.readVarInt(buf);
        }
    }

    @Override
    public void write(ByteBuf buf, PacketDirection direction, int i) {
        ProtocolUtil.writeString(buf, itemName);
        buf.writeByte(action);
        ProtocolUtil.writeString(buf, scoreName);
        if (action != 1) {
            ProtocolUtil.writeVarInt(buf, value);
        }
    }
}
