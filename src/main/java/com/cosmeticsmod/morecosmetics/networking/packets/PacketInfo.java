/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo;
import java.io.IOException;

public class PacketInfo
implements Packet {
    private EnumInfo info;

    public PacketInfo(EnumInfo info) {
        this.info = info;
    }

    public PacketInfo() {
    }

    public void read(PacketBuf buf) throws IOException {
        this.info = EnumInfo.getById((int)buf.readVarInt());
    }

    public void write(PacketBuf buf) throws IOException {
        buf.writeVarInt(this.info.getId());
    }

    public String getContent() {
        return this.info.name();
    }

    public EnumInfo getInfo() {
        return this.info;
    }
}

