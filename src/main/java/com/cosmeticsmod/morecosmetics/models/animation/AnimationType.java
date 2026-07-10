/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationType
 */
package com.cosmeticsmod.morecosmetics.models.animation;

import com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis;

/*
 * Exception performing whole class analysis ignored.
 */
public enum AnimationType {
    BOUNCE(1, AnimationAxis.Y, "Bounce Effect", ""),
    ROTATE(2, AnimationAxis.Y, "Rotation", ""),
    FORWARD(3, AnimationAxis.X, "Movement Forward", ""),
    BACKWARD(4, AnimationAxis.Z, "Movement Backward", ""),
    RESIZE(5, null, "Resize Effect", ""),
    MOTION(6, AnimationAxis.Z, "Random Motion", "");

    public static final AnimationType[] VALUES;
    private final int id;
    private final AnimationAxis axis;
    private final String name;
    private final String description;

    private AnimationType(int id, AnimationAxis axis, String name, String description) {
        this.id = id;
        this.axis = axis;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public static AnimationType getById(int id) {
        for (AnimationType type : AnimationType.values()) {
            if (type.getId() != id) continue;
            return type;
        }
        return null;
    }

    public AnimationAxis getDefaultAxis() {
        return this.axis;
    }

    static {
        VALUES = AnimationType.values();
    }
}

