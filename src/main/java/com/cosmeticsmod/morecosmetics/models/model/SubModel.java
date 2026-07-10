/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.SubModel
 *  com.cosmeticsmod.morecosmetics.models.model.TextureModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 */
package com.cosmeticsmod.morecosmetics.models.model;

import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.TextureModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;
import java.util.ArrayList;
import java.util.List;

public class SubModel
extends PositionModel {
    private final String name;
    private final int color;
    private boolean visible;
    private transient List<TextureModel> textures;

    public SubModel(String name, int color, boolean visible) {
        super(ModelPosition.FREE);
        this.name = name;
        this.color = color;
        this.visible = visible;
    }

    public String getName() {
        return this.name;
    }

    public int getColor() {
        return this.color;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<TextureModel> getTextures() {
        if (this.textures == null) {
            this.textures = new ArrayList();
        }
        return this.textures;
    }

    public boolean hasTextures() {
        return this.textures != null && !this.textures.isEmpty();
    }
}

