/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.Notification
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 */
package com.cosmeticsmod.morecosmetics.gui.core.notification;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.notification.Notification;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import java.util.ArrayList;

/*
 * Exception performing whole class analysis ignored.
 */
public abstract class NotificationHandler {
    public static final int NOTIFICATION_SHOW_DURATION = 4000;
    public static final int NOTIFICATION_BOX_PUFFER = 4;
    public static final int MAX_LINE_LENGTH = 150;
    public static final int WIDTH_BETWEEN_LINE = 3;
    public static final int BOX_MARGIN = 7;
    public static final int LOGO_TEXT_SPACE = 7;
    public static final int TITLE_TEXT_SPACE = 5;
    public static final int LOGO_BOUNCE = 16;
    protected static ArrayList<Notification> notifications = new ArrayList();

    public abstract void draw(Object var1, int var2);

    public abstract int getStringWidth(String var1);

    public abstract int getWholeHeight(int var1);

    public int getWholeWidth() {
        return 187;
    }

    public static void sendSuccess(String title, String message) {
        NotificationHandler.sendNotification((Notification)new Notification(title, "morecosmetics/gui/icons/accept.png", UIConstants.ENABLED_COLOR, message));
    }

    public static void sendInfo(String title, String message) {
        NotificationHandler.sendNotification((Notification)new Notification(title, "morecosmetics/gui/icons/info.png", UIConstants.ENABLED_COLOR, message));
    }

    public static void sendError(String message) {
        NotificationHandler.sendNotification((Notification)new Notification(LanguageHandler.get((String)"error"), "morecosmetics/gui/icons/warning.png", UIConstants.DISABLED_COLOR, message));
    }

    public static void sendWarning(String message) {
        NotificationHandler.sendNotification((Notification)new Notification(LanguageHandler.get((String)"warning"), "morecosmetics/gui/icons/warning.png", UIConstants.WARNING_COLOR, message));
    }

    public static void sendNotification(Notification notification) {
        if (!notifications.contains(notification)) {
            notifications.add(notification);
        }
    }
}

