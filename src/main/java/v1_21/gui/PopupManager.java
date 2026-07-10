/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher
 *  com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher$Popup
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.math.MatrixStack
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.PopupManager
 *  v1_21.morecosmetics.gui.components.ClickableIcon
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomImage
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.gui;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher;
import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.components.ClickableIcon;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomImage;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
public class PopupManager {
    public static final String CLOSE_ACTION = "X";
    private static CustomTextureManager textureManager = CustomTextureManager.getGlobalInstance();
    private static ArrayList<CustomButton> buttons = new ArrayList();
    private static PopupFetcher.Popup popup;
    private static boolean popupHover;
    private static ClickableIcon exitIcon;

    public static boolean draw(int width, int height, int mouseX, int mouseY) {
        if (PopupFetcher.getPopups().isEmpty()) {
            return false;
        }
        popup = (PopupFetcher.Popup)PopupFetcher.getPopups().get(0);
        CustomImage img = textureManager.getImage("popup-" + PopupManager.popup.id, PopupManager.popup.img, null);
        if (img == null) {
            return false;
        }
        int h = PopupManager.popup.size / 3;
        MatrixStack stack = StackHolder.STACK;
        int w = (int)((float)h * img.getFactor());
        stack.push();
        stack.translate(0.0f, 0.0f, 200.0f);
        int x = width / 2 - w / 2;
        int y = (int)((float)(height / 2) - (float)h / 1.8f);
        DrawUtils.drawDarkOverlay((int)width, (int)height);
        MinecraftClient.getInstance().getTextureManager().bindTexture(img.getLocation());
        DrawUtils.drawTexture((float)x, (float)y, (float)256.0f, (float)256.0f, (float)w, (float)h, (float)0.99f, (int)-1, (Identifier)img.getLocation());
        popupHover = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
        int hovercolor = PopupManager.popup.hovercolor == 0 ? UIConstants.DISABLED_COLOR : PopupManager.popup.hovercolor;
        int exitcolor = PopupManager.popup.color == 0 ? -1 : PopupManager.popup.color;
        exitIcon.setColor(exitcolor);
        exitIcon.setHoverColor(hovercolor);
        exitIcon.drawIcon(x + w - 11, y + 1, mouseX, mouseY);
        stack.pop();
        return true;
    }

    public static void click(int mouseX, int mouseY) {
        if (popup == null) {
            return;
        }
        boolean exit = exitIcon.isHovered();
        if (!exit && popupHover && PopupManager.popup.url != null) {
            MoreCosmetics.getInstance().getVersionAdapter().openBrowser(PopupManager.popup.url);
            exit = true;
        }
        if (exit) {
            MoreCosmetics.getInstance().getUserHandler().addViewedPopup(PopupManager.popup.id);
            PopupFetcher.getPopups().remove(0);
            popup = null;
        }
    }

    static {
        exitIcon = new ClickableIcon(10, 10, "morecosmetics/gui/icons/close.png", false);
    }
}

