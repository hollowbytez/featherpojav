/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 *  com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListManager
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.math.MatrixStack
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.PopupManager
 *  v1_21.morecosmetics.gui.screen.BaseUI
 *  v1_21.morecosmetics.gui.screen.UIScreen
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.gui.screen;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListManager;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.PopupManager;
import v1_21.morecosmetics.gui.screen.UIScreen;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
public abstract class BaseUI
extends UIScreen
implements BoxManager.BoxGuiInstance {
    private CustomBoxGui boxGui;
    protected BoxManager boxManager = new BoxManager(this.width, this.height, (BoxManager.BoxGuiInstance)this);
    protected CustomTextureManager ctm = CustomTextureManager.getGlobalInstance();
    private boolean previewDragging;
    private boolean refresh;
    private boolean leftPressing;
    private int mouseX;
    private int mouseY;
    private int lastMouseX;
    private int lastMouseY;
    private int previewDragX = -10;
    private int previewDragY = 1;

    protected BaseUI() {
    }

    public void initBoxGui(CustomBoxGui boxGui) {
        this.boxGui = boxGui;
    }

    public abstract void initUI();

    public abstract void draw(int var1, int var2, float var3);

    public abstract void onClick(int var1, int var2, int var3);

    public void initGui() {
        if (MinecraftClient.getInstance().player != null) {
            if (this.boxManager.getCategories().isEmpty()) {
                this.boxGui.fillGui((List)this.boxManager.getCategories());
            }
            this.boxManager.updateResolution(this.width, this.height);
            this.initUI();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.lastMouseX = this.mouseX;
        this.lastMouseY = this.mouseY;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        boolean enabled = MinecraftClient.getInstance().player != null;
        DrawUtils.drawRect((int)0, (int)0, (int)this.width, (int)this.height, (int)Utils.toRGB((int)25, (int)26, (int)31, (int)240));
        DrawUtils.drawRect((int)0, (int)0, (int)100, (int)this.height, (int)UIConstants.UI_COMPONENT_COLOR);
        DrawUtils.drawRoundedRect((int)90, (int)0, (int)this.boxManager.baseBoxEndX(), (int)this.height, (int)UIConstants.UI_SEPARATION_COLOR, (int)8);
        DrawUtils.drawRoundedRect((int)91, (int)1, (int)(this.boxManager.baseBoxEndX() - 1), (int)(this.height - 1), (int)UIConstants.UI_BACKGROUND_COLOR, (int)8);
        DrawUtils.drawRect((int)9, (int)21, (int)(this.boxManager.baseBoxSplitX() - 13), (int)22, (int)UIConstants.UI_SEPARATION_COLOR);
        DrawUtils.drawRect((int)90, (int)21, (int)this.boxManager.baseBoxEndX(), (int)22, (int)UIConstants.UI_SEPARATION_COLOR);
        DrawUtils.drawString((String)"MoreCosmetics", (float)9.0f, (float)9.0f, (int)UIConstants.UI_ACCENT_COLOR);
        DrawUtils.drawCenteredString((String)("\u00a7f" + this.boxManager.getCurrentTitle()), (float)((this.boxManager.baseBoxSplitX() + this.boxManager.baseBoxEndX()) / 2), (float)9.0f, (int)UIConstants.UI_ACCENT_COLOR);
        if (enabled) {
            if (this.refresh) {
                this.refresh = false;
                this.boxManager.setExtensionVisible(false);
                this.boxGui.fillGui((List)this.boxManager.getCategories());
                this.boxManager.updateResolution(this.width, this.height);
                this.boxManager.refreshList();
            }
            if (ModConfig.getConfig().showPreview) {
                this.drawPreview(this.width, this.height);
            }
            this.draw(mouseX, mouseY, partialTicks);
        } else {
            DrawUtils.drawCenteredString((String)"Preview only ingame available!", (float)(this.width / 2), (float)(this.height / 2 - 7));
            DrawUtils.drawCenteredString((String)"Press ESC to exit.", (float)(this.width / 2), (float)(this.height / 2 + 7));
        }
        this.boxManager.draw(null, mouseX, mouseY, enabled);
        MoreCosmetics.getInstance().getNotificationHandler().draw(null, this.width);
        PopupManager.draw((int)this.width, (int)this.height, (int)mouseX, (int)mouseY);
    }

    private void drawPreview(int width, int height) {
        if (this.leftPressing && this.mouseX > width / 4 * 3) {
            this.previewDragging = true;
        } else if (this.previewDragging && !this.leftPressing) {
            this.previewDragging = false;
        }
        if (this.previewDragging) {
            this.previewDragX += this.mouseX - this.lastMouseX;
            this.previewDragY += this.mouseY - this.lastMouseY;
            this.previewDragX %= 360;
            this.previewDragY %= 360;
            this.previewDragY = this.previewDragY == 0 ? 1 : this.previewDragY;
        }
        DrawUtils.drawEntityOnScreen((int)(width / 4 * 3 + 20), (int)(height / 2 + 40), (int)(height / 6), (float)0.0f, (float)0.0f, (int)this.previewDragX, (int)this.previewDragY, (int)0, (LivingEntity)MinecraftClient.getInstance().player);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean bl = this.leftPressing = mouseButton == 0 && mouseX > this.width / 4 * 3;
        if (this.boxManager.mouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }
        this.onClick(mouseX, mouseY, mouseButton);
        PopupManager.click((int)mouseX, (int)mouseY);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.leftPressing = false;
        this.boxManager.mouseReleased(this.mouseX, this.mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void onScroll(double amount) {
        this.boxManager.handleMouseInput(this.mouseX, this.mouseY);
        super.onScroll(amount);
    }

    protected void keyTyped(char typedChar, int keyCode) {
        this.boxManager.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    public void onGuiClosed() {
        this.leftPressing = false;
        this.boxGui.onGuiClosed();
    }

    public void drawCategory(int y, boolean selected, boolean first, BoxCategory category) {
        boolean hover;
        if (category == BoxManager.SEPARATION_LINE) {
            DrawUtils.drawRect((int)9, (int)((y += 10) - 1), (int)(this.boxManager.baseBoxSplitX() - 13), (int)y, (int)UIConstants.UI_SEPARATION_COLOR);
            return;
        }
        int left = this.boxManager.baseBoxStartX();
        int top = y - 4;
        int right = this.boxManager.baseBoxSplitX() - 3;
        int bottom = y + 13 + 4;
        boolean bl = hover = this.mouseX >= left && this.mouseX <= right && this.mouseY >= top && this.mouseY <= bottom;
        if (hover || selected) {
            DrawUtils.drawRect((int)left, (int)top, (int)right, (int)bottom, (int)(selected ? UIConstants.UI_SEPARATION_COLOR : UIConstants.UI_COMPONENT_HOVER));
        }
        Identifier icon = null;
        icon = category.getIconPath().startsWith("http") ? this.ctm.getTexture(category.getName(), category.getIconPath()) : this.ctm.getResource(category.getName(), "morecosmetics/gui/icons/" + category.getIconPath() + ".png");
        if (icon != null) {
            DrawUtils.drawString((String)category.getName(), (float)(this.boxManager.baseBoxStartX() + 13 + 13), (float)(y + 3), (int)(selected ? UIConstants.UI_TEXT_COLOR : UIConstants.UI_DISABLED_COLOR));
            MinecraftClient.getInstance().getTextureManager().bindTexture(icon);
            DrawUtils.drawTexture((float)(this.boxManager.baseBoxStartX() + 8), (float)y, (float)256.0f, (float)256.0f, (float)13.0f, (float)13.0f, (float)1.0f, (int)(selected ? UIConstants.TEXURE_COLOR : UIConstants.UI_DISABLED_COLOR), (Identifier)icon);
        }
    }

    public void drawScrollbar() {
        DrawUtils.drawRoundedRect((int)this.boxManager.baseBoxEndX(), (int)this.boxManager.baseBoxStartY(), (int)(this.boxManager.baseBoxEndX() + 3), (int)(this.boxManager.baseBoxEndY() - 7), (int)UIConstants.UI_BACKGROUND_COLOR, (int)1);
        DrawUtils.drawRoundedRect((int)this.boxManager.baseBoxEndX(), (int)this.boxManager.getBarStart(), (int)(this.boxManager.baseBoxEndX() + 3), (int)(this.boxManager.getBarEnd() - 7), (int)UIConstants.UI_COMPONENT_COLOR, (int)1);
    }

    public void drawListManagerScrollbar(ListManager listManager) {
        DrawUtils.drawRoundedRect((int)(listManager.getScrollDrawX() + 6), (int)listManager.getStartY(), (int)(listManager.getScrollDrawX() + 6 + 3), (int)(listManager.getEndY() - 7), (int)UIConstants.UI_BACKGROUND_COLOR, (int)1);
        DrawUtils.drawRoundedRect((int)(listManager.getScrollDrawX() + 6), (int)listManager.getBarStart(), (int)(listManager.getScrollDrawX() + 6 + 3), (int)(listManager.getBarEnd() - 7), (int)UIConstants.UI_COMPONENT_COLOR, (int)1);
    }

    public void refreshGui() {
        this.refresh = true;
    }

    public void drawRoundedRect(int left, int top, int right, int bottom, int color, int rad) {
        DrawUtils.drawRoundedRect((int)left, (int)top, (int)right, (int)bottom, (int)color, (int)rad);
    }

    private MatrixStack activeStack;

    public void translateUI(boolean toFront, float z) {
        if (MinecraftClient.getInstance().currentScreen == this.getWrapper()) {
            if (toFront) {
                this.activeStack = StackHolder.STACK;
                if (this.activeStack != null) {
                    this.activeStack.push();
                    this.activeStack.translate(0.0f, 0.0f, z);
                }
            } else {
                if (this.activeStack != null) {
                    this.activeStack.pop();
                    this.activeStack = null;
                }
            }
        }
    }
}

