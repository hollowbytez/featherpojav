/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.animated.AnimationEventType
 */
package com.cosmeticsmod.morecosmetics.models.animated;

/*
 * Exception performing whole class analysis ignored.
 */
public enum AnimationEventType {
    IDLE("idle", 0),
    MOVING("moving", 1),
    SNEAKING("sneaking", 2);

    public static final AnimationEventType[] VALUES;
    private String name;
    private int priority;

    private AnimationEventType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }

    static {
        VALUES = AnimationEventType.values();
    }
}

