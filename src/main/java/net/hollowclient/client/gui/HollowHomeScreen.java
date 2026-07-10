package net.hollowclient.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

import java.io.File;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HollowHomeScreen extends Screen {
    private static final int TOTAL_FRAMES = 88;
    private static final Identifier[] ANIMATION_FRAMES = new Identifier[TOTAL_FRAMES];
    static {
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            ANIMATION_FRAMES[i] = Identifier.of("hollowclient", "textures/gui/animated_menu/" + (i + 1) + ".png");
        }
    }

    private int currentFrame = 0;
    private long lastFrameTime = 0;

    private final List<MenuButton> centerButtons = new ArrayList<>();
    private final List<IconWidget> topIcons = new ArrayList<>();
    private final List<ServerShortcut> serverShortcuts = new ArrayList<>();
    private final List<FloatingParticle> particles = new ArrayList<>();

    public HollowHomeScreen() {
        super(Text.of("Hollow Home Screen"));
    }

    private static class FloatingParticle {
        float x, y, speed, size, alpha;
        FloatingParticle(float width, float height) {
            x = (float) (Math.random() * width);
            y = (float) (Math.random() * height);
            speed = 0.1f + (float) (Math.random() * 0.4f);
            size = 1.0f + (float) (Math.random() * 1.5f);
            alpha = 0.1f + (float) (Math.random() * 0.4f);
        }
        void tick(float width, float height) {
            y -= speed;
            x += (float) Math.sin(y * 0.02) * 0.2f; // Soft horizontal sway
            if (y < -10) {
                y = height + 10;
                x = (float) (Math.random() * width);
            }
        }
    }

    private static class MenuButton {
        String label;
        Runnable onClick;
        MenuButton(String label, Runnable onClick) {
            this.label = label;
            this.onClick = onClick;
        }
    }

    private static class IconWidget {
        String icon;
        Runnable onClick;
        IconWidget(String icon, Runnable onClick) { this.icon = icon; this.onClick = onClick; }
    }

    private static class ServerShortcut {
        String ip;
        String name;
        String initial;
        ServerShortcut(String name, String ip, String initial) { this.name = name; this.ip = ip; this.initial = initial; }
    }

    @Override
    protected void init() {
        centerButtons.clear();
        topIcons.clear();
        serverShortcuts.clear();
        particles.clear();

        for (int i = 0; i < 75; i++) {
            particles.add(new FloatingParticle(this.width, this.height));
        }

        // Minimalist Central Buttons
        centerButtons.add(new MenuButton("Singleplayer", () -> { if (this.client != null) this.client.setScreen(new SelectWorldScreen(this)); }));
        centerButtons.add(new MenuButton("Multiplayer", () -> { if (this.client != null) this.client.setScreen(new MultiplayerScreen(this)); }));
        
        if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("modmenu")) {
            centerButtons.add(new MenuButton("Mods", () -> {
                try {
                    Class<?> screenClass = Class.forName("com.terraformersmc.modmenu.gui.ModsScreen");
                    Screen modsScreen = (Screen) screenClass.getConstructor(Screen.class).newInstance(this);
                    if (this.client != null) this.client.setScreen(modsScreen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }

        centerButtons.add(new MenuButton("Screenshots", () -> { 
            if (this.client != null) {
                File dir = new File(this.client.runDirectory, "screenshots");
                if (!dir.exists()) dir.mkdirs();
                Util.getOperatingSystem().open(dir);
            }
        }));
        centerButtons.add(new MenuButton("Hollow Settings", () -> { if (this.client != null) this.client.setScreen(new HollowSettingsScreen(this)); }));

        // Top Right Actions
        topIcons.add(new IconWidget("⚙", () -> { if (this.client != null) this.client.setScreen(new OptionsScreen(this, this.client.options)); }));
        topIcons.add(new IconWidget("👕", () -> { if (this.client != null) this.client.setScreen(new SkinOptionsScreen(this, this.client.options)); }));
    }

    private static int staticCurrentFrame = 0;
    private static long staticLastFrameTime = 0;

    public static void renderEyeBackground(DrawContext context, int width, int height) {
        long currentTime = Util.getMeasuringTimeMs();
        if (currentTime - staticLastFrameTime > 40) { // 25 fps
            staticCurrentFrame = (staticCurrentFrame + 1) % TOTAL_FRAMES;
            staticLastFrameTime = currentTime;
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        context.drawTexture(ANIMATION_FRAMES[staticCurrentFrame], 0, 0, 0, 0, width, height, width, height);
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.fillGradient(0, 0, width, height, 0xA01A0515, 0xA02E0B16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // --- 1. Background Layer (Animated Frames) ---
        renderEyeBackground(context, this.width, this.height);

        // Render soft drifting particles
        for (FloatingParticle p : particles) {
            p.tick(this.width, this.height);
            int a = (int)(p.alpha * 255) << 24;
            // Draw tiny glowing red/pink particle
            context.fill((int)p.x, (int)p.y, (int)(p.x + p.size), (int)(p.y + p.size), a | 0x00FF3366);
        }

        // --- 2. Title (Anime Glow / Aberration Effect) ---
        int centerY = this.height / 2 - 90;
        context.getMatrices().push();
        context.getMatrices().translate(this.width / 2f, centerY - 40, 0);
        context.getMatrices().scale(2.5f, 2.5f, 1.0f);
        Text title = Text.literal("HOLLOW CLIENT").setStyle(Style.EMPTY.withFont(Identifier.of("hollowclient", "eternalo")));
        int tw = this.textRenderer.getWidth(title);
        
        // Chromatic split / glow (Pink & Red)
        context.drawText(this.textRenderer, title, -tw / 2 + 1, 1, 0x80FF1A55, false); // Neon Pink shadow
        context.drawText(this.textRenderer, title, -tw / 2 - 1, -1, 0x809D0022, false); // Dark Red shadow
        context.drawText(this.textRenderer, title, -tw / 2, 0, 0xFFFFFFFF, false); // Pure white core
        context.getMatrices().pop();

        // --- 3. Main Center Buttons ---
        int buttonY = centerY + 15;
        int buttonWidth = 220;
        int buttonHeight = 32;
        int leftX = this.width / 2 - buttonWidth / 2;

        for (MenuButton btn : centerButtons) {
            boolean hovered = mouseX >= leftX && mouseX <= leftX + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + buttonHeight;
            
            // Neon red/pink accent styling
            int bg = hovered ? 0x80330A1A : 0x50110408;
            int border = hovered ? 0x90FF1A55 : 0x30FFFFFF; // Pink outline on hover
            
            RenderUtils.drawRoundedRect(context.getMatrices(), leftX, buttonY, buttonWidth, buttonHeight, 5, bg);
            RenderUtils.drawRoundedOutline(context.getMatrices(), leftX, buttonY, buttonWidth, buttonHeight, 5, hovered ? 1.5f : 1.0f, border);
            
            int textY = buttonY + (buttonHeight - this.textRenderer.fontHeight) / 2 + 1;
            int textColor = hovered ? 0xFFFF6688 : 0xFFDDDDDD; // Pink text on hover
            context.drawCenteredTextWithShadow(this.textRenderer, btn.label, this.width / 2, textY, textColor);
            
            buttonY += 42;
        }

        // --- 4. Quit Game Button (Bottom) ---
        int quitY = this.height - 25;
        boolean quitHovered = mouseX >= leftX && mouseX <= leftX + buttonWidth && mouseY >= quitY && mouseY <= quitY + 15;
        context.drawCenteredTextWithShadow(this.textRenderer, "Quit Game", this.width / 2, quitY, quitHovered ? 0xFFFF3366 : 0xFF888888);

        // --- 5. Top Right Player Info (Sleek pill) ---
        if (this.client != null && this.client.getSession() != null) {
            int rightX = this.width - 150;
            int topY = 20;

            RenderUtils.drawRoundedRect(context.getMatrices(), rightX, topY, 130, 32, 16, 0x60110408);
            RenderUtils.drawRoundedOutline(context.getMatrices(), rightX, topY, 130, 32, 16, 1.0f, 0x30FFFFFF);
            
            try {
                SkinTextures skinTextures = this.client.getSkinProvider().getSkinTextures(this.client.getGameProfile());
                Identifier skinId = skinTextures.texture();
                context.drawTexture(skinId, rightX + 4, topY + 4, 24, 24, 8.0f, 8.0f, 8, 8, 64, 64);
                context.drawTexture(skinId, rightX + 4, topY + 4, 24, 24, 40.0f, 8.0f, 8, 8, 64, 64);
            } catch (Exception ignored) {}

            String username = this.client.getSession().getUsername();
            if (username.length() > 12) username = username.substring(0, 11) + "..";
            context.drawText(this.textRenderer, username, rightX + 34, topY + 12, 0xFFFFFFFF, false);

            // Action Icons (Circular)
            int iconX = rightX - 85;
            for (IconWidget iconBtn : topIcons) {
                boolean hovered = mouseX >= iconX && mouseX <= iconX + 32 && mouseY >= topY && mouseY <= topY + 32;
                RenderUtils.drawRoundedRect(context.getMatrices(), iconX, topY, 32, 32, 16, hovered ? 0x80330A1A : 0x50110408);
                RenderUtils.drawRoundedOutline(context.getMatrices(), iconX, topY, 32, 32, 16, 1.0f, hovered ? 0x90FF1A55 : 0x30FFFFFF);
                context.drawCenteredTextWithShadow(this.textRenderer, iconBtn.icon, iconX + 16, topY + 12, hovered ? 0xFFFF6688 : 0xFFFFFFFF);
                iconX += 40;
            }
        }

        // --- 6. Left Sidebar Servers ---
        int sideY = this.height / 2 - (serverShortcuts.size() * 46) / 2;
        int sideX = 20;
        for (ServerShortcut sc : serverShortcuts) {
            boolean hov = mouseX >= sideX && mouseX <= sideX + 36 && mouseY >= sideY && mouseY <= sideY + 36;
            RenderUtils.drawRoundedRect(context.getMatrices(), sideX, sideY, 36, 36, 10, hov ? 0x80330A1A : 0x50110408);
            RenderUtils.drawRoundedOutline(context.getMatrices(), sideX, sideY, 36, 36, 10, 1.0f, hov ? 0x90FF1A55 : 0x30FFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, sc.initial, sideX + 18, sideY + 14, hov ? 0xFFFF6688 : 0xFFFFFFFF);
            
            // Pink/Red online dot instead of standard green/cyan
            RenderUtils.drawRoundedRect(context.getMatrices(), sideX + 28, sideY + 28, 6, 6, 3, 0xFFFF1A55);
            
            if (hov) {
                context.drawText(this.textRenderer, sc.name, sideX + 46, sideY + 14, 0xFFFFFFFF, true);
            }
            sideY += 46;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int buttonWidth = 220;
        int leftX = this.width / 2 - buttonWidth / 2;
        
        // Quit Button
        int quitY = this.height - 25;
        if (mouseX >= leftX && mouseX <= leftX + buttonWidth && mouseY >= quitY && mouseY <= quitY + 15) {
            if (this.client != null) this.client.scheduleStop();
            return true;
        }

        // Center Buttons
        int centerY = this.height / 2 - 90;
        int buttonY = centerY + 15;
        for (MenuButton btn : centerButtons) {
            if (mouseX >= leftX && mouseX <= leftX + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + 32) {
                btn.onClick.run();
                return true;
            }
            buttonY += 42;
        }

        // Top Right Icons
        if (this.client != null && this.client.getSession() != null) {
            int rightX = this.width - 150;
            int topY = 20;
            int iconX = rightX - 85;
            for (IconWidget iconBtn : topIcons) {
                if (mouseX >= iconX && mouseX <= iconX + 32 && mouseY >= topY && mouseY <= topY + 32) {
                    iconBtn.onClick.run();
                    return true;
                }
                iconX += 40;
            }
        }

        // Left Sidebar Servers
        int sideY = this.height / 2 - (serverShortcuts.size() * 46) / 2;
        int sideX = 20;
        for (ServerShortcut sc : serverShortcuts) {
            if (mouseX >= sideX && mouseX <= sideX + 36 && mouseY >= sideY && mouseY <= sideY + 36) {
                ServerInfo info = new ServerInfo(sc.name, sc.ip, ServerInfo.ServerType.OTHER);
                ConnectScreen.connect(this, this.client, ServerAddress.parse(info.address), info, false, null);
                return true;
            }
            sideY += 46;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
