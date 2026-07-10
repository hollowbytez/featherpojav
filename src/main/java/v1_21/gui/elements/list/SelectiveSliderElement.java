/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.KeyMappings
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.elements.list.SelectiveSliderElement
 *  v1_21.morecosmetics.gui.elements.list.SliderElement
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.utils.KeyMappings;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import java.awt.Color;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.elements.list.SliderElement;

@Environment(value=EnvType.CLIENT)
public class SelectiveSliderElement
extends SliderElement {
    private boolean selected;
    private boolean edited;
    private String cacheValue;

    public SelectiveSliderElement(String title, int min, int max, int current, Consumer<Integer> callback, int width, boolean directCallback) {
        super(title, min, max, current, callback, width, directCallback);
        this.textOverwritten = true;
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        int i = DrawUtils.drawCenteredString((String)String.valueOf(this.selected ? this.cacheValue : Integer.valueOf(this.current)), (float)(this.xPosition + this.width - 10), (float)(this.yPosition + this.height / 2 - DrawUtils.getFontHeight() / 2));
        if (this.selected && System.currentTimeMillis() % 800L > 400L) {
            DrawUtils.drawRect((int)(i - 1), (int)(this.yPosition + this.height / 4 + 1), (int)i, (int)(this.yPosition + this.height / 4 + DrawUtils.getFontHeight()), (int)Color.GRAY.getRGB());
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.hoversSlider(mouseX, mouseY)) {
            this.update(mouseX);
            this.dragging = true;
        }
        if (this.hoversText(mouseX, mouseY)) {
            if (mouseButton == 0) {
                if (this.selected) {
                    this.current = this.isInvalidCache() ? this.min : MathUtils.clampInt((int)Integer.parseInt(this.cacheValue), (int)this.min, (int)this.max);
                    this.callback.accept(this.current);
                } else {
                    this.cacheValue = "" + this.current;
                }
                this.selected = !this.selected;
                this.edited = false;
            }
        } else if (this.selected) {
            this.selected = false;
            this.current = this.isInvalidCache() ? this.min : MathUtils.clampInt((int)Integer.parseInt(this.cacheValue), (int)this.min, (int)this.max);
            this.callback.accept(this.current);
        }
    }

    public void keyTyped(char charCode, int keyCode) {
        if (this.selected) {
            if (!this.edited) {
                this.edited = true;
            }
            if (keyCode == KeyMappings.KEY_BACK.getKey() && this.cacheValue.length() > 0) {
                this.cacheValue = this.cacheValue.substring(0, this.cacheValue.length() - 1);
            }
            if (Character.isDigit(charCode)) {
                this.cacheValue = "" + MathUtils.clampInt((int)Integer.parseInt(this.cacheValue + charCode), (int)this.min, (int)this.max);
            }
            if (keyCode == KeyMappings.KEY_UP.getKey()) {
                this.cacheValue = "" + MathUtils.clampInt((int)(Integer.parseInt(this.cacheValue) + 1), (int)this.min, (int)this.max);
            } else if (keyCode == KeyMappings.KEY_DOWN.getKey()) {
                this.cacheValue = "" + MathUtils.clampInt((int)(Integer.parseInt(this.cacheValue) - 1), (int)this.min, (int)this.max);
            } else if (keyCode == KeyMappings.KEY_MINUS.getKey() || keyCode == KeyMappings.KEY_SUBTRACT.getKey()) {
                this.cacheValue = this.cacheValue.startsWith("-") ? this.cacheValue.substring(1) : "-" + this.cacheValue;
                try {
                    this.cacheValue = "" + MathUtils.clampInt((int)Integer.parseInt(this.cacheValue), (int)this.min, (int)this.max);
                }
                catch (NumberFormatException e) {
                    MoreCosmetics.debug((String)("Could not parse " + this.cacheValue + " to int"));
                }
            } else if (keyCode == KeyMappings.KEY_RETURN.getKey()) {
                this.selected = false;
                this.current = this.isInvalidCache() ? this.min : MathUtils.clampInt((int)Integer.parseInt(this.cacheValue), (int)this.min, (int)this.max);
                this.callback.accept(this.current);
            }
            this.sliderValue = this.normalizeValue(this.isInvalidCache() ? (float)this.min : (float)Integer.parseInt(this.cacheValue));
        }
        super.keyTyped(charCode, keyCode);
    }

    private boolean isInvalidCache() {
        return this.cacheValue.isEmpty() || this.cacheValue.startsWith("-") && this.cacheValue.length() == 1;
    }

    private boolean hoversSlider(int mouseX, int mouseY) {
        return mouseX >= this.xPosition + 4 && mouseX <= this.xPosition + this.width - 20 && mouseY >= this.yPosition && mouseY <= this.yPosition + this.height;
    }

    private boolean hoversText(int mouseX, int mouseY) {
        return mouseX >= this.xPosition + this.width - 20 && mouseX <= this.xPosition + this.width && mouseY >= this.yPosition && mouseY <= this.yPosition + this.height;
    }
}

