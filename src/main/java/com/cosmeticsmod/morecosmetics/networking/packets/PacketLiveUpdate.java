/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketLiveUpdate
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import java.io.IOException;
import java.util.UUID;

public class PacketLiveUpdate
implements Packet {
    private UUID uuid;

    public void handle(NettyClient client) {
        CosmeticUser refreshUser = MoreCosmetics.getInstance().getUserHandler().getSafeUser(this.uuid);
        MoreCosmetics.getInstance().getUserHandler().loadUserData(refreshUser, () -> MoreCosmetics.log((String)("Successfully refreshed userdata of " + this.uuid)), true, true);
    }

    public void read(PacketBuf buf) throws IOException {
        this.uuid = buf.readUUID();
    }
}

