/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.nametags.Nametag
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.user.cloaks.Cloak
 */
package com.cosmeticsmod.morecosmetics.user;

import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.nametags.Nametag;
import com.cosmeticsmod.morecosmetics.user.cloaks.Cloak;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CosmeticUser {
    private UUID uuid;
    private Map<Integer, ModelData> cosmetics;
    private Nametag nametag;
    private Cloak cloak = new Cloak();
    private float nametagHeight = 0.0f;

    public CosmeticUser(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean hasNametag() {
        return this.nametag != null;
    }

    public Nametag getNametag() {
        return this.nametag;
    }

    public void setNametag(Nametag nametag) {
        this.nametag = nametag;
    }

    public boolean hasCosmetics() {
        return this.cosmetics != null && !this.cosmetics.isEmpty();
    }

    public Map<Integer, ModelData> getCosmetics() {
        if (this.cosmetics == null) {
            this.cosmetics = new ConcurrentHashMap();
        }
        return this.cosmetics;
    }

    public boolean hasCosmetic(Integer id) {
        return this.getCosmetics().containsKey(id) && ((ModelData)this.getCosmetics().get(id)).isActive();
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void resetNametagHeight() {
        this.nametagHeight = 0.0f;
    }

    public void setNametagHeight(float nameHeight) {
        this.nametagHeight = nameHeight;
    }

    public float getNametagHeight() {
        return this.nametagHeight;
    }

    public boolean hasCloak() {
        return this.cloak.isActive();
    }

    public void setCloak(Cloak cloak) {
        this.cloak = cloak;
    }

    public Cloak getCloak() {
        return this.cloak;
    }
}

