/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.ClickableIcon
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.screen.ConfirmUI
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 */
package v1_21.morecosmetics.gui.screen;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.components.ClickableIcon;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;
import v1_21.morecosmetics.gui.screen.UIScreen;

@Environment(value=EnvType.CLIENT)
public class ConfirmUI
extends UIScreen {
    public static int LINE_LENGTH = 180;
    public static int LINE_HEIGHT = 12;
    private final ClickableIcon exitIcon;
    private CustomButton yesButton;
    private CustomButton noButton;
    private final String title;
    private final String text;
    private final Consumer<Boolean> callback;
    private final ArrayList<String> textLines;

    public ConfirmUI(String title, String text, Consumer<Boolean> callback) {
        this.title = title;
        this.text = text;
        this.callback = callback;
        this.textLines = new ArrayList();
        this.exitIcon = new ClickableIcon(8, 8, "morecosmetics/gui/icons/close.png", false);
        this.exitIcon.setHoverColor(UIConstants.DISABLED_COLOR);
        LINE_LENGTH = 180;
        this.textLines.clear();
        StringBuilder builder = new StringBuilder();
        for (String word : text.split(" ")) {
            String current = builder.toString();
            if (DrawUtils.getStringWidth((String)(current + " " + word)) >= LINE_LENGTH) {
                this.textLines.add(current);
                builder = new StringBuilder(word);
                continue;
            }
            if (builder.length() != 0) {
                builder.append(" ");
            }
            builder.append(word);
        }
        if (builder.length() != 0) {
            this.textLines.add(builder.toString());
        }
    }

    public void initGui() {
        this.yesButton = new CustomButton(this.width / 2 - 42, this.height / 2 + 5, 40, 18, "\u00a7a" + LanguageHandler.get((String)"yes"));
        this.noButton = new CustomButton(this.width / 2 + 2, this.height / 2 + 5, 40, 18, "\u00a7c" + LanguageHandler.get((String)"no"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int h = LINE_HEIGHT * this.textLines.size();
        int ll = LINE_LENGTH / 2;
        DrawUtils.drawRoundedWindowRect((int)(this.width / 2 - ll - 2 - 1), (int)(this.height / 2 - 30 - h - 1), (int)(this.width / 2 + ll + 2 + 1), (int)(this.height / 2 + 30 + 1), (int)UIConstants.UI_SEPARATION_COLOR);
        DrawUtils.drawRoundedWindowRect((int)(this.width / 2 - ll - 2), (int)(this.height / 2 - 30 - h), (int)(this.width / 2 + ll + 2), (int)(this.height / 2 + 30), (int)UIConstants.UI_BACKGROUND_COLOR);
        DrawUtils.drawCenteredString((String)this.title, (float)(this.width / 2), (float)(this.height / 2 - 25 - h), (int)-1);
        this.exitIcon.drawIcon(this.width / 2 + ll - 10, this.height / 2 - 25 - h, mouseX, mouseY);
        for (String line : this.textLines) {
            DrawUtils.drawCenteredString((String)line, (float)(this.width / 2), (float)(this.height / 2 - h), (int)-1);
            h -= LINE_HEIGHT;
        }
        this.yesButton.drawButton(mouseX, mouseY);
        this.noButton.drawButton(mouseX, mouseY);
    }

    private boolean handled = false;

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.yesButton.mousePressed(mouseX, mouseY)) {
            this.handled = true;
            ScreenWrapper.closeOverlay();
            this.callback.accept(true);
        } else if (this.noButton.mousePressed(mouseX, mouseY) || this.exitIcon.isHovered()) {
            this.handled = true;
            ScreenWrapper.closeOverlay();
            this.callback.accept(false);
        }
    }

    @Override
    public void onGuiClosed() {
        if (!this.handled) {
            this.handled = true;
            this.callback.accept(false);
        }
    }
}

