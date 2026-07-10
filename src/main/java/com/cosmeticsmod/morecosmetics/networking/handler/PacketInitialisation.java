/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketInitialisation
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketAESKey
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketClientInfo
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticPurchase
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketDataContainer
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketHello
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketIndicator
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketKick
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketLiveUpdate
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketRSAKey
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketServerHash
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketTextureUpdate
 */
package com.cosmeticsmod.morecosmetics.networking.handler;

import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketAESKey;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketClientInfo;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticPurchase;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketDataContainer;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketHello;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketIndicator;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketKick;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketLiveUpdate;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketRSAKey;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketServerHash;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketTextureUpdate;
import java.util.HashMap;
import java.util.Map;

public class PacketInitialisation {
    private static final int NO_CLIENT_VERIFY_ID_DURATION = 10;
    private Map<Integer, Class<? extends Packet>> packets = new HashMap();
    private static PacketInitialisation instance;

    private PacketInitialisation() {
        this.register(0, PacketHello.class);
        this.register(1, PacketRSAKey.class);
        this.register(2, PacketAESKey.class);
        this.register(3, PacketInfo.class);
        this.register(4, PacketServerHash.class);
        this.register(5, PacketKick.class);
        this.register(6, PacketComplete.class);
        this.register(11, PacketIndicator.class);
        this.register(12, PacketDataContainer.class);
        this.register(13, PacketCosmeticUpdate.class);
        this.register(14, PacketLiveUpdate.class);
        this.register(15, PacketCosmeticPurchase.class);
        this.register(16, PacketTextureUpdate.class);
        this.register(17, PacketClientInfo.class);
    }

    private void register(int id, Class<? extends Packet> clazz) {
        try {
            clazz.newInstance();
            this.packets.put(id, clazz);
        }
        catch (Exception e) {
            NettyClient.log((String)("Class " + clazz.getSimpleName() + " doesn't have a default constructor"));
        }
    }

    public boolean doesNeedVerify(Packet p) {
        return this.getPacketId(p) > 10;
    }

    public Packet getPacket(int id) throws InstantiationException, IllegalAccessException {
        if (!this.packets.containsKey(id)) {
            throw new NullPointerException("[PacketInitialisation] Packet " + id + " is not registered");
        }
        return (Packet)((Class)this.packets.get(id)).newInstance();
    }

    public int getPacketId(Packet packet) {
        for (Map.Entry entry : this.packets.entrySet()) {
            if (!((Class)entry.getValue()).isInstance(packet)) continue;
            return (Integer)entry.getKey();
        }
        throw new NullPointerException("[PacketInitialisation] Packet " + packet.getClass().getSimpleName() + " is not registered");
    }

    public Map<Integer, Class<? extends Packet>> getPackets() {
        return this.packets;
    }

    public static PacketInitialisation getInstance() {
        return instance == null ? (instance = new PacketInitialisation()) : instance;
    }
}

