/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.SwitchElement
 */
package v1_21.morecosmetics.gui.elements.list;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class SwitchElement
extends BaseElement {
    private final CustomButton btn;
    private boolean currState;
    private boolean hoverElement;
    private final Consumer<Boolean> callback;

    public SwitchElement(String title, boolean state, boolean tileEnabled, Consumer<Boolean> callback) {
        super(title, tileEnabled);
        this.currState = state;
        this.callback = callback;
        this.btn = new CustomButton(0, 0, 40, 18, this.currState ? "\u00a7aON" : "\u00a7cOFF");
        this.setTileEnabled(this.currState);
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.btn.xPosition = x + compWidth - 40;
        this.btn.yPosition = y + 1;
        this.btn.drawButton(mouseX, mouseY);
        this.hoverElement = mouseX >= x && mouseX <= x + compWidth && mouseY >= y && mouseY <= y + compHeight;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.btn.mousePressed(mouseX, mouseY)) {
            this.currState = !this.currState;
            this.setTileEnabled(this.currState);
            this.btn.setText(this.currState ? "\u00a7aON" : "\u00a7cOFF");
            this.callback.accept(this.currState);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void update(Object value) {
        this.currState = (Boolean)value;
        this.btn.setText(this.currState ? "\u00a7aON" : "\u00a7cOFF");
    }
}

