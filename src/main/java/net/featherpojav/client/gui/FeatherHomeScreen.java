package net.featherpojav.client.gui;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FeatherHomeScreen extends Screen {
    private final List<MenuButton> buttons = new ArrayList<>();
    private final int sidebarWidth = 140;

    public FeatherHomeScreen() {
        super(Text.of("Feather Home Screen"));
    }

    private static class MenuButton {
        String label;
        Runnable onClick;

        MenuButton(String label, Runnable onClick) {
            this.label = label;
            this.onClick = onClick;
        }
    }

    @Override
    protected void init() {
        buttons.clear();
        buttons.add(new MenuButton("Singleplayer", () -> {
            if (this.client != null) this.client.setScreen(new SelectWorldScreen(this));
        }));
        buttons.add(new MenuButton("Multiplayer", () -> {
            if (this.client != null) this.client.setScreen(new MultiplayerScreen(this));
        }));
        buttons.add(new MenuButton("Feather Mods", () -> {
            if (this.client != null) this.client.setScreen(new FeatherSettingsScreen(this));
        }));
        buttons.add(new MenuButton("Settings", () -> {
            if (this.client != null) this.client.setScreen(new OptionsScreen(this, this.client.options));
        }));
        buttons.add(new MenuButton("Quit Game", () -> {
            if (this.client != null) this.client.scheduleStop();
        }));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw main body background gradient (Dark violet to black)
        context.fillGradient(sidebarWidth, 0, this.width, this.height, 0xFF120E1C, 0xFF050508);
        
        // Draw Sidebar background (Very dark purple)
        context.fill(0, 0, sidebarWidth, this.height, 0xFF0B0712);
        context.fill(sidebarWidth, 0, sidebarWidth + 1, this.height, 0xFF1D142D); // Border line

        // Draw Title / Brand logo
        context.drawText(this.textRenderer, "FEATHER", 15, 20, 0xFF9C27B0, true);
        context.drawText(this.textRenderer, "CLIENT", 15, 32, 0xFFFFFFFF, true);
        
        // Draw buttons in the sidebar
        int buttonY = 70;
        int buttonHeight = 22;
        int buttonWidth = sidebarWidth - 20;
        
        for (MenuButton btn : buttons) {
            boolean hovered = mouseX >= 10 && mouseX <= 10 + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + buttonHeight;
            
            // Hover background indicator
            if (hovered) {
                context.fill(10, buttonY, 10 + buttonWidth, buttonY + buttonHeight, 0xFF2C1E40);
                context.drawBorder(10, buttonY, buttonWidth, buttonHeight, 0xFF9C27B0);
            } else {
                context.fill(10, buttonY, 10 + buttonWidth, buttonY + buttonHeight, 0xFF140D21);
            }
            
            // Button label
            context.drawText(this.textRenderer, btn.label, 20, buttonY + (buttonHeight - 8) / 2, hovered ? 0xFFFFFFFF : 0xFFCCCCCC, false);
            buttonY += 28;
        }

        // Draw Profile information card at the bottom of the sidebar
        if (this.client != null && this.client.getSession() != null) {
            int profileY = this.height - 40;
            context.fill(8, profileY - 5, sidebarWidth - 8, this.height - 5, 0xFF140D21);
            context.drawBorder(8, profileY - 5, sidebarWidth - 16, 40, 0xFF2A1C47);
            
            // Draw skin head
            try {
                SkinTextures skinTextures = this.client.getSkinProvider().getSkinTextures(this.client.getGameProfile());
                Identifier skinId = skinTextures.texture();
                
                // Draw 8x8 pixel section of skin face scaled to 24x24
                context.drawTexture(skinId, 15, profileY, 24, 24, 8.0f, 8.0f, 8, 8, 64, 64);
                // Draw outer layer (hat/accessory) if present
                context.drawTexture(skinId, 15, profileY, 24, 24, 40.0f, 8.0f, 8, 8, 64, 64);
            } catch (Exception e) {
                // Fallback: draw grey square
                context.fill(15, profileY, 39, profileY + 24, 0xFF555555);
            }
            
            // Draw Username and Account type
            String username = this.client.getSession().getUsername();
            context.drawText(this.textRenderer, username, 45, profileY + 3, 0xFFFFFFFF, false);
            context.drawText(this.textRenderer, "Offline Mode", 45, profileY + 13, 0xFF888888, false);
        }

        // Render Dashboard Content (Right panel)
        int panelLeft = sidebarWidth + 30;
        int panelWidth = this.width - panelLeft - 30;
        
        // News Banner
        int newsY = 20;
        int newsHeight = 100;
        context.fill(panelLeft, newsY, panelLeft + panelWidth, newsY + newsHeight, 0xFF140E26);
        context.drawBorder(panelLeft, newsY, panelWidth, newsHeight, 0xFF352454);
        
        context.drawText(this.textRenderer, "Feather Pojav - v1.0.0 Update", panelLeft + 15, newsY + 15, 0xFFBA68C8, true);
        context.drawText(this.textRenderer, "Successfully loaded client environment on PojavLauncher.", panelLeft + 15, newsY + 35, 0xFFDDDDDD, false);
        context.drawText(this.textRenderer, "All mods are running offline without authentication checks.", panelLeft + 15, newsY + 50, 0xFFDDDDDD, false);
        context.drawText(this.textRenderer, "Press [Right Shift] or type /feather in-game to configure mods.", panelLeft + 15, newsY + 65, 0xFF999999, false);

        // System optimization status panel
        int optY = newsY + newsHeight + 20;
        int optHeight = this.height - optY - 30;
        context.fill(panelLeft, optY, panelLeft + panelWidth, optY + optHeight, 0xFF0E0E12);
        context.drawBorder(panelLeft, optY, panelWidth, optHeight, 0xFF24242A);
        
        context.drawText(this.textRenderer, "Performance & Diagnostics", panelLeft + 15, optY + 15, 0xFFFFFFFF, true);
        
        context.drawText(this.textRenderer, "Device Compatibility: POJAVLAUNCHER (GL4ES)", panelLeft + 15, optY + 40, 0xFF88FF88, false);
        context.drawText(this.textRenderer, "Active Render System: Minecraft Java 1.21.1", panelLeft + 15, optY + 55, 0xFFCCCCCC, false);
        
        context.drawText(this.textRenderer, "Tips for Pojav Launcher Performance:", panelLeft + 15, optY + 80, 0xFFBA68C8, true);
        context.drawText(this.textRenderer, "1. Set Render Distance to 6-8 chunks.", panelLeft + 15, optY + 100, 0xFFCCCCCC, false);
        context.drawText(this.textRenderer, "2. Allocate 1.5GB - 2.5GB of RAM in Pojav Launcher settings.", panelLeft + 15, optY + 115, 0xFFCCCCCC, false);
        context.drawText(this.textRenderer, "3. Turn off fancy leaves and heavy particles if lag occurs.", panelLeft + 15, optY + 130, 0xFFCCCCCC, false);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int buttonY = 70;
        int buttonHeight = 22;
        int buttonWidth = sidebarWidth - 20;
        
        for (MenuButton btn : buttons) {
            if (mouseX >= 10 && mouseX <= 10 + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
                btn.onClick.run();
                return true;
            }
            buttonY += 28;
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
