package io.fairyproject.mc.map.packet;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO: replace it as official PacketEvents map data wrapper when my PR merged.
 */
@Deprecated
public class WrapperPlayServerMapData extends PacketWrapper<WrapperPlayServerMapData> {

    private int data;
    private int scale;
    private Collection<Icon> icons;
    private byte[] buffer;
    private int column;
    private int rows;
    private int x;
    private int z;

    public WrapperPlayServerMapData(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerMapData(int data, int scale, Collection<Icon> icons, byte[] buffer, int x, int z, int column, int rows) {
        super(PacketType.Play.Server.MAP_DATA);
        this.data = data;
        this.scale = scale;
        this.icons = icons;
        this.buffer = buffer;
        this.column = column;
        this.rows = rows;
        this.x = x;
        this.z = z;
    }

    @Override
    public void read() {
        this.data = this.readVarInt();
        this.scale = this.readByte();

        int iconCount = this.readVarInt();
        this.icons = new ArrayList<>(iconCount);
        for (int i = 0; i < iconCount; i++) {
            this.icons.add(new Icon(this));
        }

        this.column = this.readUnsignedByte();
        if (this.column > 0) {
            this.rows = this.readUnsignedByte();
            this.x = this.readUnsignedByte();
            this.z = this.readUnsignedByte();
            this.buffer = this.readByteArray();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.data);
        this.writeByte(this.scale);

        this.writeVarInt(this.icons.size());
        this.icons.forEach(icon -> {
            this.writeByte((icon.getType() & 15) << 4 | icon.getRotation() & 15);
            this.writeByte(icon.getX());
            this.writeByte(icon.getY());
        });

        this.writeByte(this.column);
        if (this.column > 0) {
            this.writeByte(this.rows);
            this.writeByte(this.x);
            this.writeByte(this.z);
            this.writeByteArray(this.buffer);
        }
    }

    @Override
    public void copy(WrapperPlayServerMapData wrapper) {
        super.copy(wrapper);
        this.data = wrapper.data;
        this.scale = wrapper.scale;
        this.icons = new ArrayList<>(wrapper.icons.size());
        wrapper.icons.forEach(icon -> this.icons.add(icon.clone()));
        this.buffer = wrapper.buffer.clone();
        this.column = wrapper.column;
        this.rows = wrapper.rows;
        this.x = wrapper.x;
        this.z = wrapper.z;
    }

    @RequiredArgsConstructor
    @Data
    public static class Icon implements Cloneable {

        private final byte type;
        private final byte x;
        private final byte y;
        private final byte rotation;

        public Icon(PacketWrapper<WrapperPlayServerMapData> packetWrapper) {
            short s = packetWrapper.readByte();

            this.type = (byte)(s >> 4 & 15);
            this.x = packetWrapper.readByte();
            this.y = packetWrapper.readByte();
            this.rotation = (byte) (s & 15);
        }

        @Override
        public Icon clone() {
            try {
                return (Icon) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
