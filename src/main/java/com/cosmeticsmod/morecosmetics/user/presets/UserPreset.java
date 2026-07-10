/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.user.presets.UserPreset
 *  com.google.gson.JsonObject
 */
package com.cosmeticsmod.morecosmetics.user.presets;

import com.google.gson.JsonObject;

public class UserPreset {
    private final String uuid;
    private String name;
    private JsonObject data;
    private long date;
    private boolean online;
    public boolean cloak = true;
    public boolean cosmetics = true;
    public boolean nametag = true;

    public UserPreset(String uuid, String name, JsonObject data, boolean online, long date) {
        this.uuid = uuid;
        this.name = name;
        this.data = data;
        this.online = online;
        this.date = date;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject getData() {
        return this.data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public boolean isOnline() {
        return this.online;
    }

    public long getDate() {
        return this.date;
    }
}

