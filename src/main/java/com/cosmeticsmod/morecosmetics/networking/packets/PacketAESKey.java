/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketAESKey
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;

public class PacketAESKey
implements Packet {
    private byte[] key;
    private byte[] iv;

    public void read(PacketBuf buf) throws IOException {
        this.key = new byte[buf.readVarInt()];
        buf.readBytes(this.key);
        this.iv = new byte[buf.readVarInt()];
        buf.readBytes(this.iv);
    }

    public byte[] getKey() {
        return this.key;
    }

    public byte[] getIv() {
        return this.iv;
    }
}

