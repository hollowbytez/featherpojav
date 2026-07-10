/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.google.gson.JsonArray
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.compatibility.CompatibilityCheck
 *  v1_21.morecosmetics.compatibility.fabric.MoreCosmeticsFabric
 */
package v1_21.morecosmetics.compatibility;

import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.google.gson.JsonArray;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.compatibility.fabric.MoreCosmeticsFabric;

@Environment(value=EnvType.CLIENT)
public class CompatibilityCheck {
    public static void check() {
        if (CompatibilityManager.isOnFabric()) {
            CompatibilityManager.setModList((JsonArray)MoreCosmeticsFabric.getFabricMods());
        }
    }
}

