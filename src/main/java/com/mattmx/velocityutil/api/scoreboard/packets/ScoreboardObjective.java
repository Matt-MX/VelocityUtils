package com.mattmx.velocityutil.api.scoreboard.packets;

import dev.simplix.protocolize.api.PacketDirection;
import dev.simplix.protocolize.api.mapping.AbstractProtocolMapping;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.packet.AbstractPacket;
import dev.simplix.protocolize.api.util.ProtocolUtil;
import dev.simplix.protocolize.api.util.ProtocolVersions;
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
public class ScoreboardObjective extends AbstractPacket {

    public static final List<ProtocolIdMapping> MAPPINGS = List.of(
            AbstractProtocolMapping.rangedIdMapping(MINECRAFT_1_17_1, MINECRAFT_LATEST, 0x53)
    );

    private String name;
    private String value;
    private HealthDisplay type;
    /**
     * 0 to create, 1 to remove, 2 to update display text.
     */
    private byte action;

    @Override
    public void read(ByteBuf byteBuf, PacketDirection packetDirection, int i) {
        name = ProtocolUtil.readString(byteBuf);
        action = byteBuf.readByte();
        if (action == 0 || action == 2) {
            value = ProtocolUtil.readString(byteBuf);
            if (i >= ProtocolVersions.MINECRAFT_1_13) {
                type = HealthDisplay.values()[ProtocolUtil.readVarInt(byteBuf)];
            } else {
                type = HealthDisplay.fromString(ProtocolUtil.readString(byteBuf));
            }
        }
    }

    @Override
    public void write(ByteBuf byteBuf, PacketDirection packetDirection, int i) {
        ProtocolUtil.writeString(byteBuf, name);
        byteBuf.writeByte(action);
        if (action == 0 || action == 2) {
            ProtocolUtil.writeString(byteBuf, value);
            if (i >= ProtocolVersions.MINECRAFT_1_13) {
                ProtocolUtil.writeVarInt(byteBuf, type.ordinal());
            } else {
                ProtocolUtil.writeString(byteBuf, type.toString());
            }
        }
    }

    public enum HealthDisplay {

        INTEGER, HEARTS;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        public static HealthDisplay fromString(String s) {
            return valueOf(s.toUpperCase());
        }
    }
}
