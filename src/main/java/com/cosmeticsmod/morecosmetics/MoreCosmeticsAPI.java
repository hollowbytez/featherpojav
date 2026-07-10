/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiListener
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback
 *  com.cosmeticsmod.morecosmetics.nametags.NametagHandler
 *  com.cosmeticsmod.morecosmetics.user.UserUpdateCallback
 */
package com.cosmeticsmod.morecosmetics;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.GuiListener;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.models.ModelHandler;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback;
import com.cosmeticsmod.morecosmetics.nametags.NametagHandler;
import com.cosmeticsmod.morecosmetics.user.UserUpdateCallback;

public class MoreCosmeticsAPI {
    public static MoreCosmetics init(int id) {
        return MoreCosmetics.initId((int)id);
    }

    public static void onTick() {
        MoreCosmetics.getInstance().onTick();
    }

    public static void addRenderCallback(RenderCallback callback) {
        MoreCosmetics.getInstance().getModelHandler().addRenderCallback(callback);
    }

    public static void addUserUpdateCallback(UserUpdateCallback callback) {
        MoreCosmetics.getInstance().getUserHandler().addUpdateCallback(callback);
    }

    public static void setGuiListener(GuiListener listener) {
        BoxManager.setGuiListener((GuiListener)listener);
    }

    public static void setCloakEnabled(boolean enabled) {
        ModelHandler.setCloakEnabled((boolean)enabled);
    }

    public static boolean isCloakEnabled() {
        return ModelHandler.isCloakEnabled();
    }

    public static void setNametagEnabled(boolean enabled) {
        NametagHandler.setNametagEnabled((boolean)enabled);
    }

    public static boolean isNametagEnabled() {
        return NametagHandler.isNametagEnabled();
    }

    public static void onRenderName(Object stack, Object entity, double x, double y, double z) {
        MoreCosmetics.getInstance().getNametagHandler().renderNametag(stack, entity, x, y, z);
    }

    public static void showGuiScreen() {
        MoreCosmetics.getInstance().getVersionAdapter().showGuiScreen();
    }

    public static void openUI(boolean queued) {
        MoreCosmetics.getInstance().openUI(queued);
    }

    public static MoreCosmetics getMoreCosmetics() {
        return MoreCosmetics.getInstance();
    }
}

