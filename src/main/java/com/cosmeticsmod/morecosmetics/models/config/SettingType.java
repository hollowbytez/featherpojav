/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.config.SettingType
 */
package com.cosmeticsmod.morecosmetics.models.config;

/*
 * Exception performing whole class analysis ignored.
 */
public enum SettingType {
    SWITCH(1),
    SLIDER(2),
    COLOR(3),
    TEXTURE(4),
    NUMBERBOX(5),
    TEXTBOX(6),
    ITEM(7);

    private int id;

    private SettingType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SettingType byId(int id) {
        for (SettingType type : SettingType.values()) {
            if (type.id != id) continue;
            return type;
        }
        return null;
    }
}

