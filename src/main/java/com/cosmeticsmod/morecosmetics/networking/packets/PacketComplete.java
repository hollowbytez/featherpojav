/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import java.io.IOException;
import java.util.HashMap;

public class PacketComplete
implements Packet {
    private String loginKey;
    private String textureToken;
    private int loginStreak;
    private long firstJoin;
    private HashMap<Integer, Long> cosmetics = new HashMap();

    public void read(PacketBuf buf) throws IOException {
        this.loginKey = buf.readString();
        this.loginStreak = buf.readVarInt();
        int mapLength = buf.readVarInt();
        for (int i = 0; i < mapLength; ++i) {
            this.cosmetics.put(buf.readVarInt(), buf.readLong());
        }
        this.firstJoin = buf.readLong();
        this.textureToken = buf.readString();
    }

    public String getLoginKey() {
        return this.loginKey;
    }

    public String getTextureToken() {
        return this.textureToken;
    }

    public int getLoginStreak() {
        return this.loginStreak;
    }

    public HashMap<Integer, Long> getCosmetics() {
        return this.cosmetics;
    }

    public long getFirstJoin() {
        return this.firstJoin;
    }
}

