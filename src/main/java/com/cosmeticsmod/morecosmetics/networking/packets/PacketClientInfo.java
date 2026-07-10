/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketClientInfo
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;
import java.util.HashSet;

public class PacketClientInfo
implements Packet {
    private HashSet<Integer> clientIds;
    private String version;
    private String installation;
    private String platform;

    public PacketClientInfo() {
    }

    public PacketClientInfo(HashSet<Integer> clientIds, String version, String installation, String platform) {
        this.clientIds = clientIds;
        this.version = version;
        this.installation = installation;
        this.platform = platform;
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeVarInt(this.clientIds.size());
        for (Integer id : this.clientIds) {
            buf.writeVarInt(id.intValue());
        }
        buf.writeString(this.version);
        buf.writeString(this.installation);
        buf.writeString(this.platform);
    }
}

