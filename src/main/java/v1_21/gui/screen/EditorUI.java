/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.EditGui
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 *  com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.screen.BaseUI
 *  v1_21.morecosmetics.gui.screen.EditorUI
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 */
package v1_21.morecosmetics.gui.screen;

import com.cosmeticsmod.morecosmetics.gui.EditGui;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
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
public class EditorUI
extends BaseUI {
    private static ScreenWrapper screen;
    private EditGui editGui = new EditGui((BoxManager.BoxGuiInstance)this);
    private CustomButton saveButton;
    private CustomButton cancelButton;

    private EditorUI() {
        this.initBoxGui((CustomBoxGui)this.editGui);
    }

    public void initUI() {
        this.saveButton = new CustomButton(6, this.height - 50, 78, 20, "\u00a7aSAVE").setSize(1.17f);
        this.cancelButton = new CustomButton(6, this.height - 27, 78, 20, "\u00a77CANCEL").setSize(1.17f);
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.saveButton.drawButton(mouseX, mouseY);
        this.cancelButton.drawButton(mouseX, mouseY);
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (this.saveButton.mousePressed(mouseX, mouseY)) {
            this.editGui.save();
        }
        if (this.cancelButton.mousePressed(mouseX, mouseY)) {
            this.editGui.cancel();
        }
    }

    public void onCustomAction(String ... cmd) {
        if (cmd.length == 2 && cmd[0].equals("flush")) {
            this.ctm.remove(cmd[1]);
        }
    }

    public static ScreenWrapper getScreen() {
        return screen != null ? screen : (screen = new ScreenWrapper((UIScreen)new EditorUI()));
    }

    public static void displayUI(CosmeticModel model) {
        ScreenWrapper screen = EditorUI.getScreen();
        EditGui.updateModel((CosmeticModel)model);
        ScreenWrapper.displayScreen((ScreenWrapper)screen);
    }
}

