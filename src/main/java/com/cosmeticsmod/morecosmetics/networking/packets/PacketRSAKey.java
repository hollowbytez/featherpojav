/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketRSAKey
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;

public class PacketRSAKey
implements Packet {
    private byte[] publicKey;

    public PacketRSAKey() {
    }

    public PacketRSAKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public void read(PacketBuf buf) throws IOException {
        this.publicKey = new byte[buf.readVarInt()];
        buf.readBytes(this.publicKey);
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeVarInt(this.publicKey.length);
        buf.writeBytes(this.publicKey);
    }

    public byte[] getPublicKey() {
        return this.publicKey;
    }
}

