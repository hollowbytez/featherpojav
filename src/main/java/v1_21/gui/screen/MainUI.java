/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 *  com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.screen.BaseUI
 *  v1_21.morecosmetics.gui.screen.MainUI
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 */
package v1_21.morecosmetics.gui.screen;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.gui.screen.BaseUI;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;
import v1_21.morecosmetics.gui.screen.UIScreen;

/*
 * Exception performing whole class analysis ignored.
 */
@Environment(value=EnvType.CLIENT)
public class MainUI
extends BaseUI {
    private static ScreenWrapper screen;
    private ModelGui modelGui = new ModelGui((BoxManager.BoxGuiInstance)this);
    private CustomButton onlineButton;
    private CustomButton updateButton;
    private long lastUpdate;
    private long lastToggle;

    private MainUI() {
        this.initBoxGui((CustomBoxGui)this.modelGui);
    }

    public void initUI() {
        this.onlineButton = new CustomButton(6, this.height - 27, 78, 20, this.modelGui.isOnline() ? "\u00a7aONLINE" : "\u00a77OFFLINE").setSize(1.17f);
        this.updateButton = new CustomButton(6, this.height - 50, 78, 20, "\u00a7aUPDATE").setSize(1.17f);
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        boolean switchEnabled = System.currentTimeMillis() - this.lastToggle > 800L;
        this.onlineButton.drawButton(mouseX, mouseY);
        if (this.modelGui.isOnline()) {
            boolean updateEnabled = MoreCosmetics.getInstance().getUserHandler().areSettingsChanged() && System.currentTimeMillis() - this.lastUpdate > 4000L;
            this.updateButton.setEnabled(updateEnabled);
            this.updateButton.setText(updateEnabled ? "\u00a7eUPDATE" : "\u00a77UPDATE");
            this.updateButton.drawButton(mouseX, mouseY);
            this.onlineButton.setText("\u00a7aONLINE");
        } else {
            this.onlineButton.setText("\u00a77OFFLINE");
        }
        this.onlineButton.setEnabled(switchEnabled);
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (this.onlineButton.mousePressed(mouseX, mouseY) && MoreCosmetics.getInstance().getUserHandler().toggleOnlineMode()) {
            this.lastToggle = System.currentTimeMillis();
        }
        if (this.modelGui.isOnline() && this.updateButton.mousePressed(mouseX, mouseY)) {
            this.lastUpdate = System.currentTimeMillis();
            MoreCosmetics.getInstance().getUserHandler().upload();
        }
    }

    public static ScreenWrapper getScreen() {
        return screen != null ? screen : (screen = new ScreenWrapper((UIScreen)new MainUI()));
    }

    public static void displayUI() {
        ScreenWrapper.displayScreen((ScreenWrapper)MainUI.getScreen());
    }
}

