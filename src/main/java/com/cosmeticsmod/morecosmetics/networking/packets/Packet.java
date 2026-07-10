/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import java.io.IOException;

public interface Packet {
    default public void handle(NettyClient client) {
    }

    default public void read(PacketBuf buf) throws IOException {
    }

    default public void write(PacketBuf buf) throws IOException {
    }

    default public String getContent() {
        return null;
    }
}

