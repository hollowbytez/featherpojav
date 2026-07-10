/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback
 *  com.cosmeticsmod.morecosmetics.models.textures.ImageTransformer
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 */
package com.cosmeticsmod.morecosmetics.models;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback;
import com.cosmeticsmod.morecosmetics.models.textures.ImageTransformer;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ModelHandler {
    private static boolean cloakEnabled = true;
    protected ModelLoader loader;
    protected UserHandler userHandler;
    protected HashMap<String, List<?>> renderLayers = new HashMap();
    protected ArrayList<RenderCallback> callbacks = new ArrayList();

    public ModelHandler() {
        this.userHandler = MoreCosmetics.getInstance().getUserHandler();
        this.loader = MoreCosmetics.getInstance().getModelLoader();
        MoreCosmetics.log((String)"[MODEL] ModelHandler initialized!");
    }

    public abstract void registerLayer();

    public HashMap<String, List<?>> getRenderLayers() {
        return this.renderLayers;
    }

    public void addRenderCallback(RenderCallback callback) {
        this.callbacks.add(callback);
    }

    public static void setCloakEnabled(boolean enabled) {
        cloakEnabled = enabled;
    }

    public static boolean isCloakEnabled() {
        return cloakEnabled;
    }

    public static ImageTransformer getCloakTransformer() {
        return ModConfig.getConfig().cloakCompatibility ? ImageTransformer.MOJANG_CLOAK_TRANSFORMER : ImageTransformer.NO_TRANSFORM;
    }
}

