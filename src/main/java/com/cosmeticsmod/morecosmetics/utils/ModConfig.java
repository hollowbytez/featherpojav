/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.KeyMappings
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  org.apache.commons.io.FileUtils
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.KeyMappings;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/*
 * Exception performing whole class analysis ignored.
 */
public class ModConfig {
    private static File configFile;
    private static ModConfig config;
    private transient boolean firstInit;
    public boolean showPreview = true;
    public boolean showName = true;
    public boolean cosmetics = true;
    public boolean cloaks = true;
    public boolean nametags = true;
    public boolean armorMode = false;
    public boolean customFont = true;
    public boolean animatedPreview = true;
    public boolean editorMode;
    public boolean consoleOnStartUp;
    public boolean nsfwTextures;
    public boolean cloakCompatibility;
    public boolean replaceShield;
    public boolean damageTint;
    public int guiScale = 10;
    public int rainbowSpeed = 5;
    public int key = 50;
    public int openMode = 0;
    public int accentColor = UIConstants.UI_ACCENT_COLOR;
    public int lastVersion;

    private ModConfig() {
    }

    public static void saveConfig() {
        try {
            FileUtils.writeStringToFile((File)configFile, (String)MoreCosmetics.GSON.toJson((Object)config), (String)"UTF-8");
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public static ModConfig loadConfig() {
        MoreCosmetics.CONFIG_DIR.mkdirs();
        configFile = new File(MoreCosmetics.CONFIG_DIR, "MoreCosmetics.json");
        if (configFile.exists()) {
            try {
                config = (ModConfig)MoreCosmetics.GSON.fromJson(FileUtils.readFileToString((File)configFile, (String)"UTF-8"), ModConfig.class);
            }
            catch (Exception e) {
                MoreCosmetics.catchThrowable((Throwable)e);
                config = new ModConfig();
            }
            return config;
        }
        config = new ModConfig();
        ModConfig.config.firstInit = true;
        return config;
    }

    public void runConfigCheck() {
        int currentVersion = CompatibilityManager.getVersionAsNumber();
        KeyMappings.setOldKeys((currentVersion < 113 ? 1 : 0) != 0);
        if (this.firstInit) {
            this.guiScale = MoreCosmetics.getInstance().getVersionAdapter().getMinecraftGuiScale() > 1 ? 7 : 10;
            this.key = KeyMappings.KEY_M.getKey();
            this.lastVersion = currentVersion;
            ModConfig.saveConfig();
        } else if (currentVersion != this.lastVersion) {
            if (currentVersion >= 113 && this.lastVersion < 113) {
                this.key = KeyMappings.getNewKey((int)this.key);
            }
            if (ModConfig.config.lastVersion >= 113 && currentVersion < 113) {
                this.key = KeyMappings.getOldKey((int)this.key);
            }
            this.lastVersion = currentVersion;
            ModConfig.saveConfig();
        }
    }

    public boolean isFirstInit() {
        return this.firstInit;
    }

    public static ModConfig getConfig() {
        return config;
    }
}

