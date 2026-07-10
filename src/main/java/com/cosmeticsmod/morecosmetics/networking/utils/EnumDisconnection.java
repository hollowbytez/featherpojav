/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumDisconnection
 */
package com.cosmeticsmod.morecosmetics.networking.utils;

/*
 * Exception performing whole class analysis ignored.
 */
public enum EnumDisconnection {
    INACTIVE(0, "Inactive"),
    ILLEGAL_ACCESS(1, "potential attack"),
    ALREADY_ONLINE(2, "already online"),
    INVALID_AES_KEY(3, "Client provided invalid key data"),
    CRACKED_PLAYER(4, "Client is using cracked mc"),
    EXCEPTION(5, "An expected exception apperad"),
    SPAM_ATTACK(6, "Client tried to spam packets"),
    SHUTDOWN(7, "Server shutdown");

    public static final EnumDisconnection[] VALS;
    private int id;
    private String message;

    private EnumDisconnection(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public static EnumDisconnection getById(int id) {
        for (EnumDisconnection dis : VALS) {
            if (dis.id != id) continue;
            return dis;
        }
        return null;
    }

    static {
        VALS = EnumDisconnection.values();
    }
}

