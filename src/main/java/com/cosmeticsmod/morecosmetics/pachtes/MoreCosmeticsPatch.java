/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.pachtes.MoreCosmeticsPatch
 *  com.cosmeticsmod.morecosmetics.pachtes.PatchInfo
 */
package com.cosmeticsmod.morecosmetics.pachtes;

import com.cosmeticsmod.morecosmetics.pachtes.PatchInfo;

public abstract class MoreCosmeticsPatch {
    protected PatchInfo patchInfo;

    protected abstract void onInit();

    public PatchInfo getPatchInfo() {
        return this.patchInfo;
    }
}

