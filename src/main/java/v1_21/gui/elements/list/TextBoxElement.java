/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.components.CustomTextBox
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.TextBoxElement
 */
package v1_21.morecosmetics.gui.elements.list;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.components.CustomTextBox;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class TextBoxElement
extends BaseElement {
    public static final int TEXT_FIELD_WIDTH = 70;
    private final Consumer<String> confirmCallback;
    private final Consumer<String> delayedCallback;
    private final Consumer<String> liveCallback;
    private final Consumer<String> focusCallback;
    private boolean focusState;
    private long lastTyped = -1L;
    protected int delayTime = 1500;
    private CustomTextBox textField;

    public TextBoxElement(String title, String currentText, Consumer<String> confirmCallback, Consumer<String> delayedCallback, Consumer<String> liveCallback, Consumer<String> focusCallback, int maxLength) {
        super(title);
        this.confirmCallback = confirmCallback;
        this.delayedCallback = delayedCallback;
        this.liveCallback = liveCallback;
        this.focusCallback = focusCallback;
        this.textField = new CustomTextBox(0, 0, 70, 16);
        this.textField.setText(currentText == null ? "" : currentText);
        this.textField.setFocused(false);
        this.textField.setMaxLength(maxLength);
        if (currentText != null) {
            this.textField.setCursorPosition(currentText.length());
        }
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.textField.xPosition = x + compWidth - 70 - 1;
        this.textField.yPosition = y + 2;
        this.textField.drawTextBox(mouseX, mouseY);
        String text = this.textField.getText();
        if (this.delayedCallback != null && text != null && text.length() >= 3 && this.lastTyped != -1L && System.currentTimeMillis() - this.lastTyped > (long)this.delayTime) {
            this.delayedCallback.accept(text);
            this.lastTyped = -1L;
        }
        if (this.focusCallback != null && this.focusState != this.textField.isFocused()) {
            this.focusState = this.textField.isFocused();
            if (!this.focusState) {
                this.focusCallback.accept(text);
            }
        }
    }

    public void keyTyped(char charCode, int keyCode) {
        String cache = this.textField.getText();
        this.textField.textBoxKeyTyped(charCode, keyCode);
        if (this.textField.isFocused()) {
            if (this.confirmCallback != null && keyCode == 257) {
                this.confirmCallback.accept(this.textField.getText());
            }
            if (this.liveCallback != null && !cache.equals(this.textField.getText())) {
                this.liveCallback.accept(this.textField.getText());
            }
            if (this.delayedCallback != null) {
                this.lastTyped = System.currentTimeMillis();
            }
        }
        super.keyTyped(charCode, keyCode);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void update(Object value) {
        this.textField.setText(value == null ? "" : (String)value);
    }

    public int getControlWidth() {
        return 72;
    }
}

