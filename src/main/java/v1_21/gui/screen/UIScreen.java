/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 */
package v1_21.morecosmetics.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;

@Environment(value=EnvType.CLIENT)
public abstract class UIScreen {
    private ScreenWrapper wrapper;
    protected int width;
    protected int height;

    protected void init(int width, int height, ScreenWrapper wrapper) {
        this.width = width;
        this.height = height;
        this.wrapper = wrapper;
        this.initGui();
    }

    public abstract void drawScreen(int var1, int var2, float var3);

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public void handleMouseInput() {
    }

    public void onScroll(double amount) {
    }

    public void initGui() {
    }

    public void updateScreen() {
    }

    public void refreshGui() {
    }

    protected void keyTyped(char typedChar, int keyCode) {
    }

    public void onGuiClosed() {
    }

    public ScreenWrapper getWrapper() {
        return this.wrapper;
    }
}

