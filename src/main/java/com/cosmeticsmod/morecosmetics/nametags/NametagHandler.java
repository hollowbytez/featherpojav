/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.nametags.Nametag
 *  com.cosmeticsmod.morecosmetics.nametags.NametagHandler
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontData
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 */
package com.cosmeticsmod.morecosmetics.nametags;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.nametags.Nametag;
import com.cosmeticsmod.morecosmetics.nametags.font.FontData;
import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import java.util.HashMap;

public abstract class NametagHandler {
    private static boolean nametagEnabled = true;
    protected UserHandler userHandler;
    protected HashMap<Integer, FontImage> fontRendererMap = new HashMap();
    protected int[] colorCode;

    public NametagHandler() {
        this.userHandler = MoreCosmetics.getInstance().getUserHandler();
        this.colorCode = new int[32];
        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i2 = (i >> 0 & 1) * 170 + j;
            if (i == 6) {
                k += 85;
            }
            if (i >= 16) {
                k /= 4;
                l /= 4;
                i2 /= 4;
            }
            this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i2 & 0xFF;
        }
    }

    public void resetNametags() {
        for (CosmeticUser user : this.userHandler.getUsers().values()) {
            Nametag tag;
            if (!user.hasNametag() || !(tag = user.getNametag()).hasLogo() || !tag.getLogoURL().contains("user")) continue;
            this.resetLogo(tag.getLogoURL());
        }
    }

    public static void setNametagEnabled(boolean nametagEnabled) {
        NametagHandler.nametagEnabled = nametagEnabled;
    }

    public static boolean isNametagEnabled() {
        return nametagEnabled;
    }

    public abstract FontImage addFont(Integer var1, FontData var2);

    public abstract void resetLogo(String var1);

    public abstract void renderNametag(Object var1, Object var2, double var3, double var5, double var7);

    public HashMap<Integer, FontImage> getFontRendererMap() {
        return this.fontRendererMap;
    }

    public FontImage getFontRenderer(Integer id) {
        return (FontImage)this.fontRendererMap.get(id);
    }
}

