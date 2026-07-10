/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.SliderElement
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.elements.list.BaseElement;

@Environment(value=EnvType.CLIENT)
public class SliderElement
extends BaseElement {
    protected boolean textOverwritten;
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;
    protected int min;
    protected int max;
    protected int current;
    protected Consumer<Integer> callback;
    protected float sliderValue;
    public boolean dragging;
    public boolean directCallback;

    public SliderElement(String title, int min, int max, int current, Consumer<Integer> callback, int width, boolean directCallback) {
        super(title);
        this.min = min;
        this.max = max;
        this.callback = callback;
        this.width = width;
        this.current = MathUtils.clampInt((int)current, (int)min, (int)max);
        this.sliderValue = this.normalizeValue((float)this.current);
        this.directCallback = directCallback;
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.xPosition = x + compWidth - this.width;
        this.yPosition = y;
        this.height = compHeight;
        DrawUtils.drawRoundedRect((int)this.xPosition, (int)(this.yPosition + 1), (int)(this.xPosition + this.width), (int)(this.yPosition + this.height - 1), (int)UIConstants.UI_SEPARATION_COLOR);
        DrawUtils.drawRoundedRect((int)(this.xPosition + 1), (int)(this.yPosition + 2), (int)(this.xPosition + this.width - 1), (int)(this.yPosition + this.height - 2), (int)UIConstants.UI_COMPONENT_COLOR);
        DrawUtils.drawRect((int)(this.xPosition + 4), (int)(this.yPosition + this.height / 2 - 1), (int)(this.xPosition + this.width - 20), (int)(this.yPosition + this.height / 2 + 1), (int)UIConstants.UI_SEPARATION_COLOR);
        if (this.isHovered(mouseX, mouseY, 0)) {
            DrawUtils.drawRoundedRect((int)(this.xPosition + 1), (int)(this.yPosition + 2), (int)(this.xPosition + this.width - 1), (int)(this.yPosition + this.height - 2), (int)UIConstants.UI_COMPONENT_HOVER);
        }
        if (!this.textOverwritten) {
            DrawUtils.drawCenteredString((String)("" + this.current), (float)(this.xPosition + this.width - 10), (float)(this.yPosition + this.height / 2 - DrawUtils.getFontHeight() / 2));
        }
        if (this.dragging) {
            this.update(mouseX);
        }
        int xV = this.xPosition + (int)(MathUtils.clampFloat((float)this.sliderValue, (float)0.05f, (float)0.95f) * (float)(this.width - 20));
        int yV = this.yPosition + this.height / 2;
        DrawUtils.drawRoundedRect((int)xV, (int)(yV - 5), (int)(xV + 3), (int)(yV + 5), (int)Utils.toRGB((int)230, (int)230, (int)230, (int)255), (int)1);
    }

    private boolean isHovered(int mouseX, int mouseY, int tolerance) {
        return mouseX >= this.xPosition - tolerance && mouseY >= this.yPosition - tolerance && mouseX < this.xPosition + this.width + tolerance && mouseY < this.yPosition + this.height + tolerance;
    }

    public float normalizeValue(float value) {
        return MathUtils.clampFloat((float)((this.snapToStepClamp(value) - (float)this.min) / (float)(this.max - this.min)), (float)0.0f, (float)1.0f);
    }

    public float denormalizeValue(float value) {
        return this.snapToStepClamp((float)this.min + (float)(this.max - this.min) * MathUtils.clampFloat((float)value, (float)0.0f, (float)1.0f));
    }

    public float snapToStepClamp(float value) {
        return MathUtils.clampFloat((float)value, (float)this.min, (float)this.max);
    }

    public void update(int mouseX) {
        this.sliderValue = (float)(mouseX - (this.xPosition + 2)) / (float)(this.width - 22);
        this.sliderValue = MathUtils.clampFloat((float)this.sliderValue, (float)0.0f, (float)1.0f);
        int val = Math.round(this.denormalizeValue(this.sliderValue));
        if (this.current != val) {
            this.current = val;
            if (this.directCallback) {
                this.callback.accept(this.current);
            }
        }
    }

    public void update(Object value) {
        int current = (Integer)value;
        this.current = MathUtils.clampInt((int)current, (int)this.min, (int)this.max);
        this.sliderValue = this.normalizeValue((float)this.current);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isHovered(mouseX, mouseY, 0) && !this.textOverwritten) {
            this.update(mouseX);
            this.dragging = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseRelease(int mouseX, int mouseY, int state) {
        if (this.dragging) {
            this.dragging = false;
            if (!this.directCallback) {
                this.callback.accept(this.current);
            }
        }
        super.mouseRelease(mouseX, mouseY, state);
    }

    public void keyTyped(char charCode, int keyCode) {
        super.keyTyped(charCode, keyCode);
    }

    public int getControlWidth() {
        return this.width;
    }
}

