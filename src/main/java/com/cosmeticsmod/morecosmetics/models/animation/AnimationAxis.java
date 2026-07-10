/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis
 */
package com.cosmeticsmod.morecosmetics.models.animation;

/*
 * Exception performing whole class analysis ignored.
 */
public enum AnimationAxis {
    X(1, 1, 0, 0),
    Y(2, 0, 1, 0),
    Z(3, 0, 0, 1);

    private final int id;
    public final int x;
    public final int y;
    public final int z;

    private AnimationAxis(int id, int x, int y, int z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return this.id;
    }

    public static AnimationAxis getById(int id) {
        for (AnimationAxis axis : AnimationAxis.values()) {
            if (axis.getId() != id) continue;
            return axis;
        }
        return null;
    }
}

