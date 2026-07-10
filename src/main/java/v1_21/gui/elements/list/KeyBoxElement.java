/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.glfw.GLFW
 *  v1_21.morecosmetics.gui.components.CustomTextBox
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.KeyBoxElement
 */
package v1_21.morecosmetics.gui.elements.list;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;
import v1_21.morecosmetics.gui.components.CustomTextBox;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class KeyBoxElement
extends BaseElement {
    public static final int TEXT_FIELD_HEIGHT = 16;
    public static final int TEXT_FIELD_WIDTH = 38;
    private Consumer<Integer> callback;
    private CustomTextBox textBox;
    private String key;

    public KeyBoxElement(String title, int currentKey, Consumer<Integer> callback) {
        super(title);
        this.callback = callback;
        this.textBox = new CustomTextBox(0, 0, 38, 16);
        this.textBox.setMaxLength(1);
        String keyName = GLFW.glfwGetKeyName((int)currentKey, (int)-1);
        this.key = keyName != null ? keyName.toUpperCase() : "-";
        this.textBox.setText(this.key);
        this.textBox.setFocused(false);
        this.textBox.setCentered(true);
        this.textBox.setCursorDisplayEnabled(false);
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.textBox.xPosition = x + compWidth - 38 - 1;
        this.textBox.yPosition = y + 2;
        this.textBox.drawTextBox(mouseX, mouseY);
        if (this.textBox.isFocused()) {
            this.textBox.setMaxLength(4 + this.key.length());
            this.textBox.setText("> " + this.key + " <");
        }
    }

    public void keyTyped(char charCode, int keyCode) {
        super.keyTyped(charCode, keyCode);
        if (this.textBox.isFocused() && keyCode != 1 && charCode != '\u0001') {
            this.callback.accept(keyCode);
            this.textBox.setFocused(false);
            this.key = "" + Character.toUpperCase(charCode);
            this.textBox.setMaxLength(this.key.length());
            this.textBox.setText(this.key);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.textBox.mouseClicked(mouseX, mouseY, mouseButton) && this.key != null) {
            this.textBox.setMaxLength(this.key.length());
            this.textBox.setText(this.key);
        }
    }
}

