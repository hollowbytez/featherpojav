/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher
 *  com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher$Popup
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.JsonElement
 */
package com.cosmeticsmod.morecosmetics.gui.core.misc;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonElement;
import java.util.ArrayList;

public class PopupFetcher {
    public static final String URL = "https://dl.cosmeticsmod.com/morecosmetics/popups.json";
    private static final ArrayList<Popup> popups = new ArrayList();

    public static void init() {
        MoreCosmetics.EXECUTOR.execute(() -> {
            JsonElement element = Utils.readJsonFromUrl((String)URL, (boolean)false, (Object[])new Object[0]);
            if (element == null) {
                return;
            }
            MoreCosmetics mc = MoreCosmetics.getInstance();
            for (JsonElement obj : element.getAsJsonArray()) {
                Popup popup = (Popup)MoreCosmetics.GSON.fromJson(obj, Popup.class);
                if (popup.disabled || popup.online && (SharedVars.OFFLINE_MODE || !mc.getConnection().isConnected()) || mc.getUserHandler().getViewedPopups().contains(popup.id)) continue;
                popups.add(popup);
            }
        });
    }

    public static ArrayList<Popup> getPopups() {
        return popups;
    }

    public static class Popup {
        public int id;
        public String title;
        public String description;
        public String img;
        public String url;
        public int size;
        public int color;
        public int hovercolor;
        public boolean disabled;
        public boolean online;
        public Runnable action;
    }
}

