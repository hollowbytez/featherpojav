/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;
import java.util.UUID;

public class PacketCosmeticUpdate
implements Packet {
    private byte[] data;
    private UUID[] nearbyPlayers;

    public PacketCosmeticUpdate() {
    }

    public PacketCosmeticUpdate(byte[] data, UUID[] nearbyPlayers) {
        this.data = data;
        this.nearbyPlayers = nearbyPlayers;
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeVarInt(this.data.length);
        buf.writeBytes(this.data);
        buf.writeVarInt(this.nearbyPlayers.length);
        for (int i = 0; i < this.nearbyPlayers.length; ++i) {
            buf.writeUUID(this.nearbyPlayers[i]);
        }
    }
}

