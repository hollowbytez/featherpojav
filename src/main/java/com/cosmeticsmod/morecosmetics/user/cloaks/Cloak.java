/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.user.cloaks.Cloak
 */
package com.cosmeticsmod.morecosmetics.user.cloaks;

public class Cloak {
    public static final String DEFAULT_CLOAK = "https://cl.cosmeticsmod.com/textures/cloaks/cosmeticsmod.png";
    private boolean enabled;
    private String url;

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isActive() {
        return this.enabled && this.url != null;
    }

    public void sync(Cloak cloak) {
        this.url = cloak.url;
        this.enabled = cloak.enabled;
    }

    public void update(String url) {
        this.url = url;
        this.enabled = url != null;
    }

    public void toggle(Boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            this.url = this.url == null ? DEFAULT_CLOAK : this.url;
        }
    }

    public String getUrl() {
        return this.url;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.enabled ? 1231 : 1237);
        result = 31 * result + (this.url == null ? 0 : this.url.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Cloak other = (Cloak)obj;
        if (this.enabled != other.enabled) {
            return false;
        }
        return !(this.url == null ? other.url != null : !this.url.equals(other.url));
    }

    public String toString() {
        return "Cloak [enabled=" + this.enabled + ", url=" + this.url + "]";
    }
}

