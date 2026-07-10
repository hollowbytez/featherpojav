/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer
 */
package com.cosmeticsmod.morecosmetics.nametags.font;

public interface CustomFontRenderer {
    public int drawString(String var1, float var2, float var3, int var4, boolean var5);

    public int renderString(String var1, float var2, float var3, int var4, boolean var5);

    public int getStringWidth(String var1);

    public int getFontHeight();

    public String trimStringToWidth(String var1, int var2, boolean var3);
}

