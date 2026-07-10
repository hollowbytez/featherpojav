/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.text.Text
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.DrawContext
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 *  v1_21.morecosmetics.models.renderer.StackHolder
 */
package v1_21.morecosmetics.gui.screen;

import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.screen.UIScreen;
import v1_21.morecosmetics.models.renderer.StackHolder;

/*
 * Exception performing whole class analysis ignored.
 */
@Environment(value=EnvType.CLIENT)
public class ScreenWrapper
extends Screen {
    public static final int DEFAULT_FRONT_BUFFER = 200;
    private static ScreenWrapper lastScreen;
    private final UIScreen screen;
    private UIScreen overlay;
    private int frontBuffer;
    private int mouseX;
    private int mouseY;
    private float guiScale;
    private int lastKeyCode;

    public ScreenWrapper(UIScreen screen) {
        this(screen, 0);
    }

    public ScreenWrapper(UIScreen screen, int frontBuffer) {
        super((Text)Text.literal((String)screen.getClass().getSimpleName()));
        this.screen = screen;
        this.frontBuffer = frontBuffer;
        this.guiScale = 0.5f + (float)ModConfig.getConfig().guiScale / 20.0f;
    }

    protected void init() {
        int w = (int)Math.ceil((float)this.width / this.getXFactor());
        int h = (int)Math.ceil((float)this.height / this.getYFactor());
        this.screen.init(w, h, this);
        if (this.overlay != null) {
            this.overlay.init(w, h, this);
        }
    }

    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MatrixStack matrices = context.getMatrices();
        StackHolder.update((MatrixStack)matrices);
        if (matrices != null) {
            float xFactor = this.getXFactor();
            float yFactor = this.getYFactor();
            this.mouseX = (int)Math.ceil((float)mouseX / xFactor);
            this.mouseY = (int)Math.ceil((float)mouseY / yFactor);
            matrices.push();
            matrices.scale(xFactor, yFactor, 1.0f);
            this.screen.drawScreen(this.mouseX, this.mouseY, delta);
            if (this.frontBuffer > 0) {
                matrices.translate(0.0f, 0.0f, (float)this.frontBuffer);
            }
            if (this.overlay != null) {
                DrawUtils.drawDarkOverlay((int)this.screen.width, (int)this.screen.height);
                this.overlay.drawScreen(this.mouseX, this.mouseY, delta);
            }
            matrices.pop();
        }
        super.render(context, mouseX, mouseY, delta);
    }

    public void removed() {
        this.screen.onGuiClosed();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.overlay != null) {
            this.overlay.mouseClicked(this.mouseX, this.mouseY, button);
        } else {
            this.screen.mouseClicked(this.mouseX, this.mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.overlay != null) {
            this.overlay.mouseReleased(this.mouseX, this.mouseY, button);
        } else {
            this.screen.mouseReleased(this.mouseX, this.mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void mouseMoved(double mouseX, double mouseY) {
        if (this.overlay != null) {
            this.overlay.handleMouseInput();
        } else {
            this.screen.handleMouseInput();
        }
        super.mouseMoved(mouseX, mouseY);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        SharedVars.SCROLL_AMOUNT = (int)verticalAmount;
        if (this.overlay != null) {
            this.overlay.onScroll(verticalAmount);
        } else {
            this.screen.onScroll(verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private Screen parentScreen;

    public void setParent(Screen parent) {
        this.parentScreen = parent;
    }

    public Screen getParentScreen() {
        return this.parentScreen;
    }

    @Override
    public void close() {
        if (this.client != null) {
            if (this.parentScreen != null) {
                this.client.setScreen(this.parentScreen);
            } else {
                this.client.setScreen(null);
            }
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC key
            if (this.overlay != null) {
                ScreenWrapper.closeOverlay();
                return true;
            }
        }
        if (this.overlay != null) {
            this.lastKeyCode = keyCode;
            this.overlay.keyTyped('\u0001', this.lastKeyCode);
        } else {
            this.lastKeyCode = keyCode;
            this.screen.keyTyped('\u0001', this.lastKeyCode);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char chr, int modifiers) {
        if (this.overlay != null) {
            this.overlay.keyTyped(chr, this.lastKeyCode);
        } else {
            this.screen.keyTyped(chr, this.lastKeyCode);
        }
        return super.charTyped(chr, modifiers);
    }

    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        int w = (int)Math.ceil((float)width / this.getXFactor());
        int h = (int)Math.ceil((float)height / this.getYFactor());
        this.screen.init(w, h, this);
        if (this.overlay != null) {
            this.overlay.init(w, h, this);
        }
    }

    public boolean shouldPause() {
        return false;
    }

    public void setGuiScale(float guiScale) {
        this.guiScale = guiScale;
        int w = (int)Math.ceil((float)this.width / this.getXFactor());
        int h = (int)Math.ceil((float)this.height / this.getYFactor());
        this.screen.init(w, h, this);
        if (this.overlay != null) {
            this.overlay.init(w, h, this);
        }
    }

    public float getXFactor() {
        this.guiScale = 0.5f + (float)com.cosmeticsmod.morecosmetics.utils.ModConfig.getConfig().guiScale / 20.0f;
        float factor = (float)this.width / 640.0f * this.guiScale;
        if (factor < 0.05f) {
            factor = 0.05f;
        }
        return factor;
    }

    public float getYFactor() {
        return this.getXFactor();
    }

    public UIScreen getUI() {
        return this.screen;
    }

    public static void displayOverlay(UIScreen overlay) {
        ScreenWrapper.displayOverlay((UIScreen)overlay, (int)200);
    }

    public static void displayOverlay(UIScreen overlay, int frontBuffer) {
        if (MinecraftClient.getInstance().currentScreen instanceof ScreenWrapper) {
            ScreenWrapper wrapper = (ScreenWrapper)MinecraftClient.getInstance().currentScreen;
            if (overlay == null && wrapper.overlay != null) {
                wrapper.overlay.onGuiClosed();
            }
            wrapper.overlay = overlay;
            wrapper.frontBuffer = frontBuffer;
            if (overlay != null) {
                int w = (int)Math.ceil((float)wrapper.width / wrapper.getXFactor());
                int h = (int)Math.ceil((float)wrapper.height / wrapper.getYFactor());
                overlay.init(w, h, wrapper);
            }
        } else if (lastScreen != null) {
            ScreenWrapper.displayScreen((ScreenWrapper)lastScreen);
            ScreenWrapper.displayOverlay((UIScreen)overlay, (int)frontBuffer);
        }
    }

    public static void closeOverlay() {
        ScreenWrapper.displayOverlay(null, (int)0);
    }

    public static void displayScreen(ScreenWrapper screen) {
        MinecraftClient.getInstance().setScreen((Screen)screen);
        screen.getUI().refreshGui();
        lastScreen = screen;
    }

    public static void displayLastScreen() {
        if (lastScreen != null) {
            MinecraftClient.getInstance().setScreen((Screen)lastScreen);
            lastScreen.getUI().refreshGui();
        }
    }
}

