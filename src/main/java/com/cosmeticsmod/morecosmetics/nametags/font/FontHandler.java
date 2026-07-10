/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontData
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontHandler
 *  com.cosmeticsmod.morecosmetics.nametags.font.Glyph
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  org.apache.commons.io.FileUtils
 */
package com.cosmeticsmod.morecosmetics.nametags.font;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer;
import com.cosmeticsmod.morecosmetics.nametags.font.FontData;
import com.cosmeticsmod.morecosmetics.nametags.font.Glyph;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

public class FontHandler
implements ITickListener {
    public static final FontData DEFAULT = new FontData();
    public static final int LOADER_VERSION = 1;
    public static final int UI_FONT_ID = 100;
    private HashMap<Integer, FontData> fonts = new HashMap();
    private LinkedList<FontData> fontQueue = new LinkedList();
    private File fontDir;
    private boolean customFont;
    private boolean initialized;
    private int fontDownloads;

    public void init() {
        FontHandler.DEFAULT.name = "-";
        this.fonts.put(0, DEFAULT);
        this.fontDir = new File(MoreCosmetics.DATA_DIR, "fonts");
        this.fontDir.mkdirs();
    }

    public void loadResourceFonts() {
        this.loadResourceFont(100, "montserrat.cmf");
        this.loadResourceFont(1000, "arial.cmf");
        this.loadResourceFont(1001, "couriernew.cmf");
    }

    private void loadResourceFont(int id, String font) {
        this.loadFont(Integer.valueOf(id), Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/minecraft/morecosmetics/gui/fonts/" + font), true, true);
    }

    public void downloadFonts() {
        for (int i = 1; i <= this.fontDownloads; ++i) {
            try {
                String name = i + ".cmf";
                File file = new File(this.fontDir, name);
                if (!file.exists()) {
                    Utils.downloadFile((String)("http://dl.cosmeticsmod.com/textures/fonts/" + name), (File)file, (int)5000);
                }
                this.loadFont(Integer.valueOf(i), (InputStream)FileUtils.openInputStream((File)file), true, false);
                continue;
            }
            catch (IOException e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
    }

    private void loadFont(Integer id, InputStream stream, boolean register, boolean resourceFont) {
        FontData fontData = this.loadFont(id.intValue(), stream);
        if (fontData.delete && !resourceFont) {
            if (new File(this.fontDir, fontData.id + ".cmf").delete()) {
                MoreCosmetics.log((String)("Deleted outdated font: " + fontData.id));
            } else {
                MoreCosmetics.log((String)("Failed to delete outdated font: " + fontData.id));
            }
            return;
        }
        fontData.register = register;
        fontData.resourceFont = resourceFont;
        this.fontQueue.add(fontData);
    }

    private FontData loadFont(int id, InputStream stream) {
        MoreCosmetics.debug((String)("Loading font " + id));
        FontData fontData = new FontData();
        try (BufferedInputStream bis = new BufferedInputStream(stream);){
            int offset;
            int fontSize;
            int fontHeight;
            String name;
            int version = bis.read();
            if (version != 1) {
                MoreCosmetics.debug((String)("[WARNING] Loading font v" + version + " with loader v" + 1));
                fontData.delete = 1 > version;
            }
            int nameLength = bis.read();
            byte[] nameBuffer = new byte[nameLength];
            bis.read(nameBuffer);
            fontData.name = name = new String(nameBuffer, StandardCharsets.UTF_8);
            int fontId = this.readVarInt((InputStream)bis);
            if (fontId == id) {
                fontData.id = fontId;
            } else {
                MoreCosmetics.debug((String)("[WARNING] Id not matching! Using: " + id));
                fontData.id = id;
            }
            fontData.height = fontHeight = this.readVarInt((InputStream)bis);
            fontData.size = fontSize = this.readVarInt((InputStream)bis);
            fontData.offset = offset = this.readVarInt((InputStream)bis);
            int characters = this.readVarInt((InputStream)bis);
            HashMap<Character, Glyph> fontGlyphs = new HashMap<Character, Glyph>();
            for (int i = 0; i < characters; ++i) {
                char ch = (char)this.readVarInt((InputStream)bis);
                Glyph glyph = new Glyph();
                glyph.x = this.readVarInt((InputStream)bis);
                glyph.y = this.readVarInt((InputStream)bis);
                glyph.width = this.readVarInt((InputStream)bis);
                glyph.height = fontHeight;
                fontGlyphs.put(Character.valueOf(ch), glyph);
            }
            fontData.glyphs = fontGlyphs;
            BufferedImage img = ImageIO.read(bis);
            if (img == null) {
                throw new IllegalStateException("Failed to load font image!");
            }
            fontData.image = img;
            MoreCosmetics.debug((String)("Successfully loaded font " + name));
        }
        catch (Exception e) {
            fontData.delete = true;
            MoreCosmetics.log((String)("Failed to load font " + id));
            MoreCosmetics.catchThrowable((Throwable)e);
        }
        return fontData;
    }

    public int readVarInt(InputStream is) throws IOException {
        byte part;
        int out = 0;
        int bytes = 0;
        do {
            part = (byte)is.read();
            out |= (part & 0x7F) << bytes++ * 7;
            if (bytes <= 5) continue;
            throw new IllegalArgumentException(String.format("Varint is too long (%d > 5)", bytes));
        } while ((part & 0x80) == 128);
        return out;
    }

    public HashMap<Integer, FontData> getFonts() {
        return this.fonts;
    }

    public FontData getFont(Integer id) {
        return this.fonts.getOrDefault(id, DEFAULT);
    }

    public int getId(String name) {
        for (FontData font : this.fonts.values()) {
            if (!font.name.equals(name)) continue;
            return font.id;
        }
        return 0;
    }

    public void setCustomFont(boolean customFont) {
        this.customFont = customFont;
        if (this.initialized) {
            MoreCosmetics mc = MoreCosmetics.getInstance();
            mc.getVersionAdapter().setCustomFontRenderer(customFont ? mc.getNametagHandler().getFontRenderer(Integer.valueOf(100)) : null);
        }
    }

    public int getFontDownloads() {
        return this.fontDownloads;
    }

    public void setFontDownloads(int fontDownloads) {
        this.fontDownloads = fontDownloads;
    }

    public void updateTick(int tick) {
        MoreCosmetics mc = MoreCosmetics.getInstance();
        while (!this.fontQueue.isEmpty()) {
            FontData fontData = (FontData)this.fontQueue.pop();
            mc.getNametagHandler().addFont(Integer.valueOf(fontData.id), fontData);
            if (!fontData.register) continue;
            this.fonts.put(fontData.id, fontData);
        }
        if (!this.initialized) {
            this.initialized = true;
            if (this.customFont) {
                mc.getVersionAdapter().setCustomFontRenderer((CustomFontRenderer)mc.getNametagHandler().getFontRenderer(Integer.valueOf(100)));
            }
        }
    }
}

