/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.components.CustomButton;

@Environment(value=EnvType.CLIENT)
public class BaseElement
extends ListComponent {
    private static Identifier settingsIcon = Identifier.of((String)"morecosmetics/gui/icons/settings.png");
    private CustomButton btn;

    public BaseElement(String title, boolean tileEnabled) {
        super(title, tileEnabled);
    }

    public BaseElement(String title) {
        super(title);
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        if (this.lineVisible) {
            int w = x + (compWidth == 150 ? 8 : 5) - 3;
            int h = y + compHeight / 2 - DrawUtils.getFontHeight() / 2;
            DrawUtils.drawRect((int)w, (int)h, (int)(w + 1), (int)(h + DrawUtils.getFontHeight() - 1), (int)UIConstants.UI_ACCENT_COLOR);
        }
        if (this.hasChildComponents()) {
            if (this.btn == null) {
                this.btn = new CustomButton(0, 0, 18, 18, "");
            }
            this.btn.xPosition = x + compWidth - 20 - this.getControlWidth();
            this.btn.yPosition = y + 1;
            this.btn.drawButton(mouseX, mouseY);
            MinecraftClient.getInstance().getTextureManager().bindTexture(settingsIcon);
            DrawUtils.drawTexture((float)(this.btn.xPosition + 4), (float)(this.btn.yPosition + 4), (float)256.0f, (float)256.0f, (float)10.0f, (float)10.0f, (float)1.0f, (int)UIConstants.UI_TEXT_COLOR, (Identifier)settingsIcon);
        }
        DrawUtils.drawString((String)this.title, (float)(x + (compWidth == 150 ? 8 : 5)), (float)(y + compHeight / 2 - DrawUtils.getFontHeight() / 2));
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.parentBoxManager != null && this.btn != null && this.btn.mousePressed(mouseX, mouseY)) {
            this.parentBoxManager.setExtensionVisible(true, (GuiComponent)this);
        }
    }
}

