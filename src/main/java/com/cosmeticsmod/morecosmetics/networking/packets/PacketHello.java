/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketHello
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;
import java.util.UUID;

public class PacketHello
implements Packet {
    private String pName;
    private UUID pUUID;
    private int version;
    private int clientID;
    private int nettyVersion;

    public PacketHello() {
    }

    public PacketHello(String pName, UUID pUUID, int version, int clientID, int nettyVersion) {
        this.pName = pName;
        this.pUUID = pUUID;
        this.version = version;
        this.clientID = clientID;
        this.nettyVersion = nettyVersion;
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeString(this.pName);
        buf.writeUUID(this.pUUID);
        buf.writeVarInt(this.version);
        buf.writeVarInt(this.clientID);
        buf.writeVarInt(this.nettyVersion);
    }
}

