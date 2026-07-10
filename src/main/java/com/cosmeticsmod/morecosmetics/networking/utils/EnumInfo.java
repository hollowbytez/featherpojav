/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo
 */
package com.cosmeticsmod.morecosmetics.networking.utils;

/*
 * Exception performing whole class analysis ignored.
 */
public enum EnumInfo {
    VERIFY_HASHJOIN_REQUEST(0),
    ACCEPTED_HASHJOIN(1),
    ACCEPTED_AESKEY(2),
    KEEP_ALIVE(3),
    NAMETAG_UPDATE(4),
    COSMETIC_UPDATE_ACCEPTED(5),
    COSMETIC_UPDATE_REJECTED(6),
    RESET_DATA(7),
    TEXTURE_LOGIN(8);

    public static final EnumInfo[] VALUES;
    private int id;

    private EnumInfo(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EnumInfo getById(int id) {
        for (EnumInfo info : VALUES) {
            if (info.id != id) continue;
            return info;
        }
        return null;
    }

    static {
        VALUES = EnumInfo.values();
    }
}

