/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.cosmeticsmod.morecosmetics.utils.debug.DebugInfo
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.lwjgl.opengl.GL11
 */
package com.cosmeticsmod.morecosmetics.utils.debug;

import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import org.lwjgl.opengl.GL11;

public class DebugInfo {
    public static String getClientInfo() {
        JsonObject debug = new JsonObject();
        debug.addProperty("os-name", System.getProperty("os.name"));
        debug.addProperty("os-version", System.getProperty("os.version"));
        debug.addProperty("opengl-version", GL11.glGetString((int)7938));
        debug.addProperty("mc-version", CompatibilityManager.VERSION);
        debug.addProperty("mc-path", new File("").getAbsolutePath());
        debug.addProperty("mcm-version", (Number)1201);
        debug.addProperty("platform", CompatibilityManager.PLATFORM);
        debug.addProperty("installation", CompatibilityManager.INSTALLATION);
        JsonArray mods = CompatibilityManager.getModList();
        if (mods != null) {
            debug.add("mods", (JsonElement)mods);
        }
        return Utils.PRETTY_GSON.toJson((JsonElement)debug);
    }
}

