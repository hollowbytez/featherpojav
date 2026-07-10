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
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketTextureUpdate
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

public class PacketTextureUpdate
implements Packet {
    private String type;
    private String url;

    public void read(PacketBuf buf) throws IOException {
        this.type = buf.readString();
        this.url = buf.readString();
    }

    public void handle(NettyClient client) {
        UserHandler handler = MoreCosmetics.getInstance().getUserHandler();
        client.send((Packet)new PacketCosmeticUpdate(new byte[]{-1}, handler.getNearbyUsers()));
        handler.loadUserData(handler.getCurrentUser(), () -> {
            ModelGui.refreshGui();
            NotificationHandler.sendSuccess((String)"Texture Update", (String)LanguageHandler.get((String)"updatesuccess"));
        }, false, true);
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }
}

