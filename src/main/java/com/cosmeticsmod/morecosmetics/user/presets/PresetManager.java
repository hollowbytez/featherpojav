/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.user.presets.PresetManager
 *  com.cosmeticsmod.morecosmetics.user.presets.PresetManager$SortMode
 *  com.cosmeticsmod.morecosmetics.user.presets.UserPreset
 *  org.apache.commons.io.FileUtils
 */
package com.cosmeticsmod.morecosmetics.user.presets;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.cosmeticsmod.morecosmetics.user.presets.PresetManager;
import com.cosmeticsmod.morecosmetics.user.presets.UserPreset;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import org.apache.commons.io.FileUtils;

public class PresetManager {
    private final ArrayList<UserPreset> presets = new ArrayList();
    private File presetsDir;
    private SortMode mode = SortMode.NAME;

    public void loadPresets() {
        this.presetsDir = new File(MoreCosmetics.ROOT_DIR, "presets");
        this.presetsDir.mkdirs();
        this.presets.clear();
        File[] files = this.presetsDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.getName().length() != 41 || !file.getName().endsWith(".json")) continue;
            try {
                String json = FileUtils.readFileToString((File)file, (String)"UTF-8");
                this.presets.add(MoreCosmetics.GSON.fromJson(json, UserPreset.class));
            }
            catch (Exception e) {
                MoreCosmetics.log((String)("Failed to load preset " + file.getName() + " " + e));
            }
        }
        this.sortPresets();
    }

    public void applyPreset(CosmeticUser user, UserPreset preset) {
        if (preset.cloak) {
            user.getCloak().toggle(Boolean.valueOf(false));
        }
        if (preset.nametag) {
            user.setNametag(null);
        }
        UserHandler handler = MoreCosmetics.getInstance().getUserHandler();
        handler.applyJsonToUser(user, preset.getData(), true, preset.cosmetics, preset.cloak, preset.nametag);
        if (preset.isOnline()) {
            user.getCosmetics().keySet().removeIf(i -> !handler.getOnlineCosmetics().containsKey(i));
            handler.checkSettingsChanged();
        }
    }

    public void saveToProfile(String name, CosmeticUser user, boolean online) {
        String uuid = new UUID(user.getUuid().getMostSignificantBits(), System.nanoTime()).toString();
        UserPreset preset = new UserPreset(uuid, name, MoreCosmetics.getInstance().getUserHandler().getJsonFromUser(user, true), online, System.currentTimeMillis() / 1000L);
        this.presets.add(preset);
        this.sortPresets();
        try {
            FileUtils.writeStringToFile((File)new File(this.presetsDir, uuid + ".json"), (String)MoreCosmetics.GSON.toJson((Object)preset), (String)"UTF-8");
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public void deletePreset(UserPreset preset) {
        this.presets.remove(preset);
        if (preset == null || new File(this.presetsDir, preset.getUuid() + ".json").delete()) {
            MoreCosmetics.log((String)"Failed to delete preset!");
        }
    }

    public void updateName(UserPreset preset, String name) {
        preset.setName(name);
        this.savePreset(preset);
        this.sortPresets();
    }

    public void updatePreset(UserPreset preset, CosmeticUser user) {
        preset.setData(MoreCosmetics.getInstance().getUserHandler().getJsonFromUser(user, true));
        this.savePreset(preset);
    }

    public void updateApplies(UserPreset preset, boolean cosmetics, boolean cloak, boolean nametag) {
        preset.cosmetics = cosmetics;
        preset.cloak = cloak;
        preset.nametag = nametag;
        this.savePreset(preset);
    }

    private void savePreset(UserPreset preset) {
        try {
            FileUtils.writeStringToFile((File)new File(this.presetsDir, preset.getUuid() + ".json"), (String)MoreCosmetics.GSON.toJson((Object)preset), (String)"UTF-8");
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public void sortPresets() {
        Collections.sort(this.presets, this.mode.getComparator());
    }

    public ArrayList<UserPreset> getPresets() {
        return this.presets;
    }

    public File getPresetsDir() {
        return this.presetsDir;
    }

    public void setMode(SortMode mode) {
        this.mode = mode;
    }

    public SortMode getMode() {
        return this.mode;
    }

    public enum SortMode {
        NAME, DATE, TYPE;
        public java.util.Comparator<com.cosmeticsmod.morecosmetics.user.presets.UserPreset> getComparator() {
            return (a, b) -> 0;
        }
    }
}

