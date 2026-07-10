/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.components.CustomTextBox
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.NumberBoxElement
 */
package v1_21.morecosmetics.gui.elements.list;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.components.CustomTextBox;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class NumberBoxElement
extends BaseElement {
    public static final int TEXT_FIELD_HEIGHT = 16;
    public static final int TEXT_FIELD_WIDTH = 40;
    private int min;
    private int max;
    private int current;
    private int lastMouseX;
    private boolean dragging;
    private CustomTextBox textField;
    private Consumer<Integer> callback;

    public NumberBoxElement(String title, int min, int max, int current, Consumer<Integer> callback) {
        super(title);
        this.min = min;
        this.max = max;
        this.current = current;
        this.callback = callback;
        this.validateCurrent();
        this.textField = new CustomTextBox(0, 0, 40, 16);
        this.textField.setText("" + current);
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        int cacheLastMouse = this.lastMouseX / 10;
        int cacheCurrentMouse = mouseX / 10;
        if (this.dragging && cacheCurrentMouse != cacheLastMouse) {
            this.current = cacheLastMouse < cacheCurrentMouse ? ++this.current : --this.current;
            this.lastMouseX = mouseX;
            this.validateCurrent();
            this.textField.setText("" + this.current);
        }
        this.textField.xPosition = x + compWidth - 41;
        this.textField.yPosition = y + 2;
        this.textField.drawTextBox(mouseX, mouseY);
    }

    public void keyTyped(char charCode, int keyCode) {
        if (!this.textField.isFocused()) {
            return;
        }
        if (keyCode == 14) {
            this.textField.textBoxKeyTyped(charCode, keyCode);
            return;
        }
        try {
            this.current = Integer.parseInt(this.textField.getText() + charCode);
        }
        catch (NumberFormatException e) {
            return;
        }
        this.validateCurrent();
        this.textField.setText("" + this.current);
        this.callback.accept(this.current);
        super.keyTyped(charCode, keyCode);
    }

    private void validateCurrent() {
        this.current = this.current < this.min ? this.min : this.current;
        this.current = this.current > this.max ? this.max : this.current;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= this.textField.xPosition && mouseX <= this.textField.xPosition + 40 && mouseY >= this.textField.yPosition && mouseY <= this.textField.yPosition + 16) {
            this.dragging = true;
            this.lastMouseX = mouseX;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseRelease(int mouseX, int mouseY, int state) {
        if (this.dragging) {
            this.dragging = false;
        }
        super.mouseRelease(mouseX, mouseY, state);
    }
}

