/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 */
package com.cosmeticsmod.morecosmetics.gui.core.box;

import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import java.util.function.Consumer;

public abstract class BoxElementBuilder {
    public abstract BoxComponent getCosmeticElement(CosmeticModel var1, CosmeticUser var2, Consumer<Boolean> var3);
}

