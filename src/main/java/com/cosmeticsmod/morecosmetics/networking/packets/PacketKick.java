/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketKick
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumDisconnection
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.utils.EnumDisconnection;
import java.io.IOException;

public class PacketKick
implements Packet {
    private EnumDisconnection disconnect;

    public PacketKick(EnumDisconnection dis) {
        this.disconnect = dis;
    }

    public PacketKick() {
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeVarInt(this.disconnect.getId());
    }

    public void read(PacketBuf buf) throws IOException {
        this.disconnect = EnumDisconnection.getById((int)buf.readVarInt());
    }

    public String getContent() {
        return this.disconnect.name();
    }

    public EnumDisconnection getDisconnect() {
        return this.disconnect;
    }
}

