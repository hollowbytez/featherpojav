/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.Notification
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.InfoPacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.InfoPacketHandler$1
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 */
package com.cosmeticsmod.morecosmetics.networking;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.notification.Notification;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler;
import com.cosmeticsmod.morecosmetics.networking.InfoPacketHandler;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;

public class InfoPacketHandler
implements ClientPacketHandler {
    public void handle(PacketInfo info) {
        MoreCosmetics mod = MoreCosmetics.getInstance();
        UserHandler userHandler = mod.getUserHandler();
        switch (info.getInfo()) {
            case NAMETAG_UPDATE: {
                mod.getConnection().send((Packet)new PacketCosmeticUpdate(new byte[]{-1}, userHandler.getNearbyUsers()));
                userHandler.loadUserData(userHandler.getCurrentUser(), () -> NotificationHandler.sendSuccess((String)"Nametag Update", (String)LanguageHandler.get((String)"updatenametag")), false, true);
                break;
            }
            case COSMETIC_UPDATE_ACCEPTED: {
                userHandler.updateUserWhitelist();
                userHandler.loadUserData(userHandler.getCurrentUser(), () -> NotificationHandler.sendSuccess((String)"Cosmetic Update", (String)LanguageHandler.get((String)"updatesuccess")), true, true);
                break;
            }
            case COSMETIC_UPDATE_REJECTED: {
                NotificationHandler.sendNotification((Notification)new Notification(LanguageHandler.get((String)"error"), "morecosmetics/gui/icons/reject.png", UIConstants.DISABLED_COLOR, LanguageHandler.get((String)"updaterejected")));
                userHandler.loadUserData(userHandler.getCurrentUser(), ModelGui::refreshGui, true, true);
                break;
            }
        }
    }
}

