/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.elements.box.CosmeticElement
 */
package v1_21.morecosmetics.gui.elements.box;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import v1_21.morecosmetics.DrawUtils;

@Environment(value=EnvType.CLIENT)
public class CosmeticElement
extends BoxComponent {
    private static CosmeticElement selected;
    private static Identifier settingsIcon;
    private static Identifier hourglassIcon;
    private CosmeticModel model;
    private CosmeticUser user;
    private Consumer<Boolean> toggleCallback;
    private boolean cosmeticEnabled;
    private boolean mouseOverSettings;
    private boolean extended;
    private int animationIndex;
    private int startRot;
    private float startBounce;

    public CosmeticElement(CosmeticModel model, CosmeticUser user, Consumer<Boolean> toggleCallback) {
        super(model.getName(), true);
        this.model = model;
        this.user = user;
        this.toggleCallback = toggleCallback;
        this.cosmeticEnabled = user.hasCosmetic(model.getId());
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        boolean switching = this.mouseOver;
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        if (selected != this && switching != this.mouseOver) {
            this.startRot = SharedVars.TICKS;
        }
        this.mouseOverSettings = this.hasChildComponents() ? mouseY > y + compHeight - 14 && mouseY < y + compHeight - 10 : false;
        DrawUtils.drawRoundedRect((int)x, (int)y, (int)(x + compWidth), (int)(y + compHeight), (int)(this.cosmeticEnabled ? UIConstants.UI_ACCENT_COLOR : UIConstants.UI_SEPARATION_COLOR), (int)4);
        DrawUtils.drawRoundedRect((int)(x + 1), (int)(y + 1), (int)(x + compWidth - 1), (int)(y + compHeight - 1), (int)UIConstants.UI_COMPONENT_COLOR, (int)3);
        if (this.mouseOver || selected == this) {
            long time;
            int elapsed = SharedVars.TICKS - this.startRot;
            DrawUtils.drawRoundedRect((int)(x + 1), (int)(y + 1), (int)(x + compWidth - 1), (int)(y + compHeight - 1), (int)Utils.toRGB((int)50, (int)50, (int)50, (int)50), (int)3);
            if (ModConfig.getConfig().animatedPreview && elapsed > 15) {
                if (elapsed == 16) {
                    this.startBounce = this.getBounce();
                }
                DrawUtils.renderCosmeticPreview((CosmeticModel)this.model, (int)(x + 38), (int)(y + 20), (float)(this.enabled ? 32.0f : 1.0f), (float)((190 + elapsed - 15) % 360), (float)(this.getBounce() - this.startBounce - 4.0f), (float)0.0f, (!this.enabled ? 1 : 0) != 0);
            } else {
                DrawUtils.renderCosmeticPreview((CosmeticModel)this.model, (int)(x + 38), (int)(y + 20), (float)(this.enabled ? 32.0f : 1.0f), (float)190.0f, (float)-4.0f, (float)0.0f, (!this.enabled ? 1 : 0) != 0);
            }
            if (this.model.hasConfig()) {
                MinecraftClient.getInstance().getTextureManager().bindTexture(settingsIcon);
                DrawUtils.drawTexture((float)(x + 2), (float)(y + 2), (float)256.0f, (float)256.0f, (float)8.0f, (float)8.0f, (float)1.0f, (int)UIConstants.UI_DISABLED_COLOR, (Identifier)settingsIcon);
            }
            if (ModelGui.getInstance().isOnline() && (time = MoreCosmetics.getInstance().getUserHandler().getOnlineCosmetics().getOrDefault(this.model.getId(), -1L).longValue()) != -1L) {
                MinecraftClient.getInstance().getTextureManager().bindTexture(hourglassIcon);
                DrawUtils.drawTexture((float)(x + compWidth - 10), (float)(y + 2), (float)256.0f, (float)256.0f, (float)8.0f, (float)8.0f, (float)1.0f, (int)UIConstants.EXIT_NEUTRAL_COLOR, (Identifier)hourglassIcon);
                if (mouseX >= x + compWidth - 10 && mouseX <= x + compWidth && mouseY >= y + 2 && mouseY <= y + 10) {
                    String until = LanguageHandler.get((String)"until") + " " + MoreCosmetics.DATE_FORMAT.format(time * 1000L);
                    DrawUtils.drawToolTip((String)until, (int)mouseX, (int)mouseY);
                }
            }
        } else {
            DrawUtils.renderCosmeticPreview((CosmeticModel)this.model, (int)(x + 38), (int)(y + 20), (float)(this.enabled ? 32.0f : 1.0f), (float)190.0f, (float)-4.0f, (float)0.0f, (!this.enabled ? 1 : 0) != 0);
        }
        DrawUtils.drawCenteredString((String)this.model.getName(), (float)(x + compWidth / 2), (float)(y + compHeight - 8), (float)0.7f);
        if (!this.enabled) {
            DrawUtils.drawRoundedRect((int)x, (int)y, (int)(x + compWidth), (int)(y + compHeight), (int)Utils.toRGB((int)0, (int)0, (int)0, (int)100), (int)4);
        }
        ++this.animationIndex;
    }

    private float getBounce() {
        return (float)(50.0 * Math.cos(SharedVars.RENDER_TICKS / 10.0f) / 20.0);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.mouseOver) {
            return;
        }
        if (mouseButton == 0) {
            boolean onSettings;
            boolean bl = onSettings = mouseX > this.x && mouseX < this.x + 10 && mouseY > this.y && mouseY < this.y + 10;
            if (onSettings && this.hasChildComponents()) {
                this.showExtension();
            } else {
                this.parentBoxManager.setExtensionVisible(false);
                this.cosmeticEnabled = !this.cosmeticEnabled;
                this.toggleCallback.accept(this.cosmeticEnabled);
                this.extended = false;
                selected = null;
            }
        } else if (mouseButton == 1 && this.hasChildComponents()) {
            this.showExtension();
        }
    }

    private void showExtension() {
        if (this.extended) {
            this.parentBoxManager.setExtensionVisible(false);
            this.extended = false;
        } else {
            this.extended = true;
            this.parentBoxManager.setExtensionVisible(true, (GuiComponent)this);
            this.cosmeticEnabled = true;
            this.toggleCallback.accept(true);
            selected = this;
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled && selected == this) {
            selected = null;
        }
    }

    public CosmeticModel getModel() {
        return this.model;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    static {
        settingsIcon = Identifier.of((String)"morecosmetics/gui/icons/settings.png");
        hourglassIcon = Identifier.of((String)"morecosmetics/gui/icons/hourglass.png");
    }
}

