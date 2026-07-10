/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.ItemModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 */
package com.cosmeticsmod.morecosmetics.models.model;

import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;

public class ItemModel
extends PositionModel {
    private int itemId;

    public ItemModel(int itemId) {
        super(ModelPosition.FREE);
        this.itemId = itemId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}

