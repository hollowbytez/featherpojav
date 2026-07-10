/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 */
package com.cosmeticsmod.morecosmetics.models.renderer;

import com.cosmeticsmod.morecosmetics.user.CosmeticUser;

public interface RenderCallback {
    default public boolean onPreRender(CosmeticUser user) {
        return true;
    }

    default public void onPostRender(CosmeticUser user) {
    }
}

