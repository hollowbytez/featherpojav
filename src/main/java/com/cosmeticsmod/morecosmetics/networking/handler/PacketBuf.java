/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.handler.codec.DecoderException
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketBuf {
    public ByteBuf buf;

    public PacketBuf() {
        this.buf = Unpooled.buffer();
    }

    public PacketBuf(ByteBuf buf) {
        this.buf = buf;
    }

    public PacketBuf(byte[] input) {
        this.buf = Unpooled.buffer((int)input.length);
        this.buf.writeBytes(input);
    }

    public UUID readUUID() {
        return new UUID(this.buf.readLong(), this.buf.readLong());
    }

    public void writeUUID(UUID uuid) {
        this.buf.writeLong(uuid.getMostSignificantBits());
        this.buf.writeLong(uuid.getLeastSignificantBits());
    }

    public void writeString(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        this.writeVarInt(bytes.length);
        this.writeBytes(bytes);
    }

    public String readString() {
        byte[] bytes = new byte[this.readVarInt()];
        this.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static int getVarIntSize(int value) {
        int byteCount = 0;
        do {
            byte part = (byte)(value & 0x7F);
            if ((value >>>= 7) != 0) {
                part = (byte)(part | 0x80);
            }
            ++byteCount;
        } while (value != 0);
        return byteCount;
    }

    public void writeVarInt(int value) {
        do {
            byte part = (byte)(value & 0x7F);
            if ((value >>>= 7) != 0) {
                part = (byte)(part | 0x80);
            }
            this.buf.writeByte((int)part);
        } while (value != 0);
    }

    public int readVarInt() {
        byte part;
        int out = 0;
        int bytes = 0;
        do {
            part = this.buf.readByte();
            out |= (part & 0x7F) << bytes++ * 7;
            if (bytes <= 5) continue;
            throw new DecoderException(String.format("Varint is too long (%d > 5)", bytes));
        } while ((part & 0x80) == 128);
        return out;
    }

    public void ensureWritable(int length) {
        this.buf.ensureWritable(length);
    }

    public int readableBytes() {
        return this.buf.readableBytes();
    }

    public byte readByte() {
        return this.buf.readByte();
    }

    public byte[] readBytes(byte[] input) {
        this.buf.readBytes(input);
        return input;
    }

    public void writeBytes(byte[] input) {
        this.buf.writeBytes(input);
    }

    public void writeBytes(ByteBuf input) {
        this.buf.writeBytes(input);
    }

    public void writeLong(Long l) {
        this.buf.writeLong(l.longValue());
    }

    public Long readLong() {
        return this.buf.readLong();
    }

    public ByteBuf copy() {
        return this.buf.copy();
    }

    public byte[] array() {
        return this.buf.array();
    }
}

