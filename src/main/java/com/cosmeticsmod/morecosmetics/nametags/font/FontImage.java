/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontData
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  com.cosmeticsmod.morecosmetics.nametags.font.Glyph
 */
package com.cosmeticsmod.morecosmetics.nametags.font;

import com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer;
import com.cosmeticsmod.morecosmetics.nametags.font.FontData;
import com.cosmeticsmod.morecosmetics.nametags.font.Glyph;

public abstract class FontImage
implements CustomFontRenderer {
    public static final int MINECRAFT_FONT_HEIGHT = 9;
    public static final int FONT_CUTOFF = 4;
    protected FontData fontData;
    protected int imageSize;
    protected float scale;
    protected int[] colorCode;
    protected boolean randomStyle;
    protected boolean boldStyle;
    protected boolean italicStyle;
    protected boolean underlineStyle;
    protected boolean strikethroughStyle;

    public FontImage(FontData fontData, int[] colorCode) {
        this.fontData = fontData;
        this.colorCode = colorCode;
        this.imageSize = fontData.image.getWidth();
        this.scale = 9.0f / (float)fontData.size;
    }

    public Glyph getGlyph(char ch) {
        return (Glyph)this.fontData.glyphs.get(Character.valueOf(ch));
    }

    public String getName() {
        return this.fontData.name;
    }

    public int getId() {
        return this.fontData.id;
    }

    public int getWidth(char ch) {
        Glyph g = this.getGlyph(ch);
        return g != null ? g.width - this.fontData.offset : this.getDefaultWidth(ch);
    }

    public int getImageSize() {
        return this.imageSize;
    }

    protected void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        int i;
        this.resetStyles();
        if (dropShadow) {
            i = this.renderString(text, x + 1.0f, y + 1.0f, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        } else {
            i = this.renderString(text, x, y, color, false);
        }
        return i;
    }

    public int getFontHeight() {
        return (int)((float)this.fontData.height * this.scale);
    }

    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        int size = text.length();
        boolean skip = false;
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                skip = true;
                continue;
            }
            if (skip && character >= '0' && character <= 'r') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                } else if (colorIndex == 17) {
                    this.boldStyle = true;
                } else if (colorIndex == 20) {
                    this.italicStyle = true;
                } else if (colorIndex == 21) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                skip = false;
                continue;
            }
            character = text.charAt(i);
            width += this.getWidth(character);
        }
        return (int)((float)(width += this.fontData.offset) * this.scale);
    }

    public String trimStringToWidth(String text, int maxWidth, boolean reverse) {
        maxWidth = (int)((float)maxWidth / this.scale);
        StringBuilder stringbuilder = new StringBuilder();
        boolean skip = false;
        int j = reverse ? text.length() - 1 : 0;
        int k = reverse ? -1 : 1;
        int width = 0;
        for (int i = j; i >= 0 && i < text.length() && i < maxWidth; i += k) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                skip = true;
            } else if (skip && character >= '0' && character <= 'r') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                } else if (colorIndex == 17) {
                    this.boldStyle = true;
                } else if (colorIndex == 20) {
                    this.italicStyle = true;
                } else if (colorIndex == 21) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                skip = false;
            } else {
                character = text.charAt(i);
                width += this.getWidth(character);
            }
            if (width > maxWidth) break;
            if (reverse) {
                stringbuilder.insert(0, character);
                continue;
            }
            stringbuilder.append(character);
        }
        return stringbuilder.toString();
    }

    public abstract int getDefaultWidth(char var1);
}

