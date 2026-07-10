/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.models.config.ConfigAccessor
 *  com.cosmeticsmod.morecosmetics.models.config.ModelConfig
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 */
package com.cosmeticsmod.morecosmetics.models.config;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.models.config.ModelConfig;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.config.SettingOverlay;

public class ConfigAccessor {
    public static Object get(ModelConfig config, ModelData data) {
        try {
            String[] key = config.key.split("\\.");
            if (key.length == 1) {
                return SettingOverlay.class.getDeclaredField(config.key).get(data);
            }
            if (key.length == 3) {
                Object[] childs = (Object[])ModelData.class.getDeclaredField(key[0]).get(data);
                Object child = childs[Integer.valueOf(key[1])];
                return SettingOverlay.class.getDeclaredField(key[2]).get(child);
            }
        }
        catch (NullPointerException e) {
            MoreCosmetics.log((String)("Error while getting config value: path not found on " + config.key + " (" + config.name + ")"));
        }
        catch (Exception e) {
            MoreCosmetics.log((String)("Error while getting config value: " + config.key + " (" + config.name + ")"));
            MoreCosmetics.catchThrowable((Throwable)e);
        }
        return null;
    }

    public static void set(ModelConfig config, ModelData data, Object obj) {
        try {
            String[] key = config.key.split("\\.");
            if (key.length == 1) {
                SettingOverlay.class.getDeclaredField(config.key).set(data, obj);
            } else if (key.length == 3) {
                Object[] childs = (Object[])ModelData.class.getDeclaredField(key[0]).get(data);
                Object child = childs[Integer.valueOf(key[1])];
                SettingOverlay.class.getDeclaredField(key[2]).set(child, obj);
            }
        }
        catch (NullPointerException e) {
            MoreCosmetics.log((String)("Error while setting config value: path not found on " + config.key + " (" + config.name + ")"));
        }
        catch (Exception e) {
            MoreCosmetics.log((String)("Error while setting config value: " + config.key + " (" + config.name + ")"));
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }
}

