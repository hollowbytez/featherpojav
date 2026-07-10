/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontData
 *  com.cosmeticsmod.morecosmetics.nametags.font.Glyph
 */
package com.cosmeticsmod.morecosmetics.nametags.font;

import com.cosmeticsmod.morecosmetics.nametags.font.Glyph;
import java.awt.image.BufferedImage;
import java.util.Map;

public class FontData {
    public int id;
    public String name;
    public int size;
    public int height;
    public int offset;
    public Map<Character, Glyph> glyphs;
    public BufferedImage image;
    public boolean delete;
    public boolean register;
    public boolean resourceFont;
}

