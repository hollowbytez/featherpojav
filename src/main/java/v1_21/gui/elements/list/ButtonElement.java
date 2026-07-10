/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.ButtonElement
 */
package v1_21.morecosmetics.gui.elements.list;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class ButtonElement
extends BaseElement {
    private CustomButton btn;
    private Runnable callback;

    public ButtonElement(String title, Runnable callback) {
        this(title, "Click", callback);
    }

    public ButtonElement(String title, String buttonText, Runnable callback) {
        super(title);
        this.callback = callback;
        this.btn = new CustomButton(0, 0, 40, 18, buttonText);
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.btn.xPosition = x + compWidth - 40;
        this.btn.yPosition = y + 1;
        this.btn.drawButton(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.btn.mousePressed(mouseX, mouseY)) {
            this.callback.run();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}

