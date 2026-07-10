/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 */
package com.cosmeticsmod.morecosmetics.models.model.util;

/*
 * Exception performing whole class analysis ignored.
 */
public enum ModelPosition {
    FREE(0),
    HEAD(1, 3),
    BODY(2, 2),
    ABOVE_HEAD(3),
    RIGHT_ARM(4, 2),
    LEFT_ARM(5, 2),
    RIGHT_LEG(6, 1),
    LEFT_LEG(7, 1);

    public static final ModelPosition[] VALUES;
    private int id;
    private int armorSlot;

    private ModelPosition(int id) {
        this(id, -1);
    }

    private ModelPosition(int id, int armorSlot) {
        this.id = id;
        this.armorSlot = armorSlot;
    }

    public int getId() {
        return this.id;
    }

    public int getArmorSlot() {
        return this.armorSlot;
    }

    public static ModelPosition getById(int id) {
        for (ModelPosition pos : VALUES) {
            if (pos.getId() != id) continue;
            return pos;
        }
        return FREE;
    }

    public String toString() {
        return this.name().replace("_", " ");
    }

    static {
        VALUES = ModelPosition.values();
    }
}

