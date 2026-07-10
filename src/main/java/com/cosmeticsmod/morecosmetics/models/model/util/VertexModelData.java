/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.util.VertexModelData
 */
package com.cosmeticsmod.morecosmetics.models.model.util;

public class VertexModelData {
    private int[] vertexData;
    private int x;
    private int y;
    private int z;

    public VertexModelData(int[] vertexData, int x, int y, int z) {
        this.vertexData = vertexData;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] getVertexData() {
        return this.vertexData;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
}

