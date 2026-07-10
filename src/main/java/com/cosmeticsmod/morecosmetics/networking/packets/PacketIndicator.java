/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketIndicator
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;
import java.util.HashMap;

public class PacketIndicator
implements Packet {
    private HashMap<Long, Integer> indicated;
    private Long[] transmit;
    private int currentPlayers;

    public PacketIndicator() {
    }

    public PacketIndicator(Long[] transmit) {
        this.transmit = transmit;
    }

    public void handle(NettyClient client) {
        client.updateIndicator(this.indicated, this.currentPlayers);
    }

    public void read(PacketBuf buf) throws IOException {
        this.currentPlayers = buf.readVarInt();
        int amount = buf.readVarInt();
        this.indicated = new HashMap();
        for (int i = 0; i < amount; ++i) {
            this.indicated.put(buf.readLong(), buf.readVarInt());
        }
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeVarInt(this.transmit.length);
        for (Long l : this.transmit) {
            buf.writeLong(l);
        }
    }
}

