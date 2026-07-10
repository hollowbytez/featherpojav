/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.loader.api.FabricLoader
 *  net.fabricmc.loader.api.ModContainer
 *  v1_21.morecosmetics.compatibility.fabric.MoreCosmeticsFabric
 */
package v1_21.morecosmetics.compatibility.fabric;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

@Environment(value=EnvType.CLIENT)
public class MoreCosmeticsFabric
implements ClientModInitializer {
    public static final String VERSION = "1.21";

    public void onInitializeClient() {
        MoreCosmetics.log((String)"Fabric Init!");
        CompatibilityManager.setOnFabric((boolean)true);
        CompatibilityManager.VERSION = VERSION;
        CompatibilityManager.INSTALLATION = "Fabric-1.21";
        CompatibilityManager.PLATFORM = "Fabric";
    }

    public static JsonArray getFabricMods() {
        JsonArray array = new JsonArray();
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", mod.getMetadata().getName());
            obj.addProperty("modid", mod.getMetadata().getId());
            obj.addProperty("version", mod.getMetadata().getVersion().getFriendlyString());
            array.add((JsonElement)obj);
        }
        return array;
    }
}

