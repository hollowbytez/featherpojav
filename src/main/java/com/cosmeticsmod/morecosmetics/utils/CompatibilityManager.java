/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.JsonArray
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonArray;

/*
 * Exception performing whole class analysis ignored.
 */
public class CompatibilityManager {
    private static boolean onForge;
    private static boolean onFabric;
    private static boolean noRenderEvent;
    private static boolean cmInstalled;
    private static JsonArray modList;
    public static final String PLATFORM_VANILLA = "Vanilla";
    public static final String PLATFORM_FABRIC = "Fabric";
    public static final String PLATFORM_FORGE = "Forge";
    public static final int MC_VERSION_113 = 113;
    public static final int MC_VERSION_1202 = 1202;
    public static String VERSION;
    public static String INSTALLATION;
    public static String PLATFORM;
    private static boolean showName;

    public static int getVersionAsNumber() {
        if (VERSION != null) {
            try {
                return Integer.parseInt(VERSION.replace(".", ""));
            }
            catch (NumberFormatException e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
        return 0;
    }

    public static boolean isVersionOrHigher(int version) {
        return CompatibilityManager.getVersionAsNumber() >= version;
    }

    public static void check() {
        noRenderEvent = Utils.isClassPresent((String)"BytecodeMethods");
        cmInstalled = Utils.isClassPresent((String)"de.leonardox.cosmeticsmod.Main");
        MoreCosmetics.getInstance().getVersionAdapter().checkCompatiblity();
    }

    public static void detect() {
        if (PLATFORM == null) {
            if (Utils.isClassPresent((String)"net.minecraftforge.common.MinecraftForge")) {
                PLATFORM = "Forge";
                CompatibilityManager.setOnForge((boolean)true);
            } else if (Utils.isClassPresent((String)"net.fabricmc.api.ModInitializer")) {
                PLATFORM = "Fabric";
                CompatibilityManager.setOnFabric((boolean)true);
            } else {
                PLATFORM = "Vanilla";
            }
        }
        if (VERSION == null) {
            for (String version : new String[]{"1_8", "1_16", "1_17", "1_18", "1_19", "1_19_3", "1_19_4", "1_20", "1_20_2"}) {
                if (!Utils.isClassPresent((String)("v" + version + ".morecosmetics.VersionImpl"))) continue;
                VERSION = version.replace("_", ".");
                break;
            }
        }
        if (INSTALLATION == null) {
            INSTALLATION = "Unknown";
        }
    }

    public static void setModList(JsonArray modList) {
        CompatibilityManager.modList = modList;
    }

    public static JsonArray getModList() {
        return modList;
    }

    public static void setOnForge(boolean onForge) {
        CompatibilityManager.onForge = onForge;
    }

    public static boolean isOnForge() {
        return onForge;
    }

    public static void setOnFabric(boolean onFabric) {
        CompatibilityManager.onFabric = onFabric;
    }

    public static boolean isOnFabric() {
        return onFabric;
    }

    public static void setNoRenderEvent(boolean noRenderEvent) {
        CompatibilityManager.noRenderEvent = noRenderEvent;
    }

    public static boolean isNoRenderEvent() {
        return noRenderEvent;
    }

    public static void setShowName(boolean showName) {
        CompatibilityManager.showName = showName;
    }

    public static boolean shouldShowName() {
        return showName;
    }

    public static boolean isCmInstalled() {
        return cmInstalled;
    }

    static {
        showName = true;
    }
}

