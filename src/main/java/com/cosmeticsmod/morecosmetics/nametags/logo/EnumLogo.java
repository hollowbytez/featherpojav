/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo
 */
package com.cosmeticsmod.morecosmetics.nametags.logo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * Exception performing whole class analysis ignored.
 */
public enum EnumLogo {
    EMOJI(1, "https://emojicdn.elk.sh/%s"),
    USER(2, "http://logo.cosmeticsmod.com/user/%s.png"),
    SOCIALMEDIA(3, "http://logo.cosmeticsmod.com/sm/%s.png"),
    SERVER(4, "http://logo.cosmeticsmod.com/server/%s.png"),
    ICON(5, "https://cl.cosmeticsmod.com/icon/%s.png"),
    CLOAK(6, "http://dl.cosmeticsmod.com/morecosmetics/cloaks/%s");

    private int id;
    private String url;

    private EnumLogo(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public int getID() {
        return this.id;
    }

    public String format(String texture) {
        try {
            return String.format(this.url, URLEncoder.encode(texture, "UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static EnumLogo getById(int id) {
        for (EnumLogo logo : EnumLogo.values()) {
            if (logo.getID() != id) continue;
            return logo;
        }
        return null;
    }
}

