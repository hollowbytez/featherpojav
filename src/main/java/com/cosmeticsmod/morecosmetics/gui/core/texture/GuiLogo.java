/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.GuiLogo
 *  com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo
 */
package com.cosmeticsmod.morecosmetics.gui.core.texture;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo;

public class GuiLogo {
    private String logo;
    private String icon;
    private String name;
    private String link;
    private Runnable callback;
    private boolean hovered;

    public GuiLogo(String name, String logo, String link) {
        this.name = name;
        this.logo = logo;
        this.link = link;
    }

    public GuiLogo(String name, String logo, Runnable clickCallback) {
        this.name = name;
        this.logo = logo;
        this.callback = clickCallback;
    }

    public String getLogo() {
        if (this.logo == null && this.icon != null) {
            this.logo = EnumLogo.ICON.format(this.icon);
        }
        return this.logo;
    }

    public String getName() {
        if (this.name == null && this.icon != null) {
            this.name = this.icon;
        }
        return this.name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return this.link;
    }

    public Runnable getCallback() {
        return this.callback;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public void onClick() {
        if (this.callback != null) {
            this.callback.run();
        } else if (this.link != null) {
            MoreCosmetics.getInstance().getVersionAdapter().openBrowser(this.link);
        }
    }
}

