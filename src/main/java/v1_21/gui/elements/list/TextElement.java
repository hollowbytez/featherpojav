/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.TextElement
 */
package v1_21.morecosmetics.gui.elements.list;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class TextElement
extends BaseElement {
    private String text;
    private int textWidth;

    public TextElement(String title, String text) {
        super(title);
        this.text = text;
        this.textWidth = DrawUtils.getStringWidth((String)text) + 4;
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        int tileWidth = x + compWidth;
        int midY = y + compHeight / 2 - DrawUtils.getFontHeight() / 2;
        DrawUtils.drawString((String)this.text, (float)(tileWidth - this.textWidth), (float)midY);
    }

    public void update(Object value) {
        String text;
        this.text = text = (String)value;
        this.textWidth = DrawUtils.getStringWidth((String)text) + 4;
    }
}

