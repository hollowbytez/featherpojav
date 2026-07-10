/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketDataContainer
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 */
package com.cosmeticsmod.morecosmetics.networking;

import com.cosmeticsmod.morecosmetics.networking.packets.PacketDataContainer;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo;

public interface ClientPacketHandler {
    default public void handle(PacketDataContainer packet) {
    }

    default public void handle(PacketInfo info) {
    }
}

