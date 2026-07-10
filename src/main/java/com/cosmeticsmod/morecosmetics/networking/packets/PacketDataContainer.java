/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketDataContainer
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;

public class PacketDataContainer
implements Packet {
    private String key;
    private byte[] data;

    public PacketDataContainer() {
    }

    public PacketDataContainer(String key, byte[] data) {
        this.key = key;
        this.data = data;
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeString(this.key);
        buf.writeVarInt(this.data.length);
        buf.writeBytes(this.data);
    }

    public void read(PacketBuf buf) throws IOException {
        this.key = buf.readString();
        this.data = new byte[buf.readVarInt()];
        buf.readBytes(this.data);
    }

    public void handle(NettyClient client) {
        if (client.getPacketHandler() != null) {
            client.getPacketHandler().handle(this);
        }
    }

    public String getKey() {
        return this.key;
    }

    public byte[] getData() {
        return this.data;
    }
}

