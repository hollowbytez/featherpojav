/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.models.config.SettingType
 *  com.cosmeticsmod.morecosmetics.utils.RainbowHandler
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.resource.Resource
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.ColorPickerElement
 *  v1_21.morecosmetics.gui.elements.list.ColorPickerElement$ColorWheelGui
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.models.config.SettingType;
import com.cosmeticsmod.morecosmetics.utils.RainbowHandler;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.elements.list.BaseElement;
import v1_21.morecosmetics.gui.elements.list.ColorPickerElement;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;
import v1_21.morecosmetics.gui.screen.UIScreen;

@Environment(value=EnvType.CLIENT)
public class ColorPickerElement
extends BaseElement {
    public static final Identifier COLOR_PALETTE = Identifier.of((String)"morecosmetics/gui/components/wheel.png");
    public static final int WIDTH = 18;
    public static final int HEIGHT = 18;
    private static BufferedImage colorPaletteImage;
    private int latestX;
    private int latestY;
    private int tileWidth;
    private int selectedColor;
    private int defaultColor;
    private boolean openedSelector;
    private boolean advanced;
    private boolean rainbowEnabled;
    private Consumer<Integer> callback;

    public ColorPickerElement(String title, int selectedColor, int defaultColor, boolean rainbowEnabled, Consumer<Integer> callback) {
        super(title);
        this.selectedColor = selectedColor;
        this.defaultColor = defaultColor;
        this.rainbowEnabled = rainbowEnabled;
        this.callback = callback;
        if (colorPaletteImage == null) {
            try {
                colorPaletteImage = ImageIO.read(((Resource)MinecraftClient.getInstance().getResourceManager().getResource(COLOR_PALETTE).get()).getInputStream());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Object value) {
        this.selectedColor = (Integer)value;
    }

    public int getType() {
        return SettingType.COLOR.getId();
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.latestX = x;
        this.latestY = y;
        this.tileWidth = x + compWidth;
        DrawUtils.drawRoundedRect((int)(this.tileWidth - 18 - 1), (int)(y + 1), (int)(this.tileWidth - 1), (int)(y + 18 + 1), (int)UIConstants.UI_SEPARATION_COLOR, (int)3);
        DrawUtils.drawRoundedRect((int)(this.tileWidth - 18), (int)(y + 2), (int)(this.tileWidth - 2), (int)(y + 18), (int)(this.selectedColor == 0 ? this.defaultColor : (this.selectedColor == 1 ? RainbowHandler.RAINBOW_VALUE : this.selectedColor)));
        if (this.openedSelector && !this.advanced) {
            this.advanced = true;
            ScreenWrapper.displayOverlay((UIScreen)new ColorWheelGui(this, this));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOverColorField(mouseX, mouseY)) {
            this.openedSelector = !this.openedSelector;
            return;
        }
        this.openedSelector = false;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private boolean isMouseOverColorField(int mouseX, int mouseY) {
        return mouseX > this.tileWidth - 18 - 1 && mouseX < this.tileWidth && mouseY > this.latestY && mouseY < this.latestY + 18;
    }

    private void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getControlWidth() {
        return 18;
    }

    static /* synthetic */ String access$000(ColorPickerElement x0) {
        return x0.title;
    }

    public class ColorWheelGui extends v1_21.morecosmetics.gui.screen.UIScreen {
        public ColorWheelGui(ColorPickerElement a, ColorPickerElement b) {}
        @Override public void initGui() {}
        @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
        @Override public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    }
}

