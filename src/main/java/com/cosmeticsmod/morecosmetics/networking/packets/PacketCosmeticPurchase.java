/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticPurchase
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 */
package com.cosmeticsmod.morecosmetics.networking.packets;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import java.io.IOException;
import java.util.HashMap;

public class PacketCosmeticPurchase
implements Packet {
    private HashMap<Integer, Long> cosmetics = new HashMap();

    public void read(PacketBuf buf) throws IOException {
        int size = buf.readVarInt();
        for (int i = 0; i < size; ++i) {
            this.cosmetics.put(buf.readVarInt(), buf.readLong());
        }
    }

    public void handle(NettyClient client) {
        UserHandler handler = MoreCosmetics.getInstance().getUserHandler();
        handler.getOnlineCosmetics().clear();
        handler.getOnlineCosmetics().putAll(this.cosmetics);
        handler.updateUserWhitelist();
        client.send((Packet)new PacketCosmeticUpdate(new byte[]{-1}, handler.getNearbyUsers()));
        handler.loadUserData(handler.getCurrentUser(), () -> {
            ModelGui.refreshGui();
            NotificationHandler.sendSuccess((String)"Purchase", (String)LanguageHandler.get((String)"updatesuccess"));
        }, false, true);
    }

    public HashMap<Integer, Long> getCosmetics() {
        return this.cosmetics;
    }
}

