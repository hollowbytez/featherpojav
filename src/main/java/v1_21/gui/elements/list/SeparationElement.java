/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.utils.RainbowHandler
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.elements.list.SeparationElement
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import com.cosmeticsmod.morecosmetics.utils.RainbowHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;

@Environment(value=EnvType.CLIENT)
public class SeparationElement
extends ListComponent {
    private int lineColor;

    public SeparationElement(String title, int lineColor) {
        super(title);
        this.lineColor = lineColor;
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        int tileWidth = x + compWidth;
        int midY = y + compHeight / 2 - DrawUtils.getFontHeight() / 2;
        int midX = compWidth / 2 + x;
        int w = DrawUtils.drawCenteredString((String)this.title, (float)midX, (float)midY) - midX;
        if (this.lineColor != 0) {
            if (this.lineColor == 1) {
                this.lineColor = RainbowHandler.RAINBOW_VALUE;
            }
            int offset = 5;
            DrawUtils.drawRect((int)(x + offset), (int)(midY + 3), (int)(midX - w - offset), (int)(midY + 4), (int)this.lineColor);
            DrawUtils.drawRect((int)(midX + w + offset), (int)(midY + 3), (int)(tileWidth - offset), (int)(midY + 4), (int)this.lineColor);
        }
    }
}

