package net.hollowclient.client.gui;

import net.hollowclient.client.config.HollowConfig;
import net.hollowclient.client.config.HollowHudConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class HollowHudEditorScreen extends Screen {
    private final Screen parent;
    
    // Bounding Box definitions for HUD elements
    // Bounding Box definitions for HUD elements
    private static class HudElement {
        String name;
        java.util.function.IntSupplier getX;
        java.util.function.IntConsumer setX;
        java.util.function.IntSupplier getY;
        java.util.function.IntConsumer setY;
        java.util.function.DoubleSupplier getScale;
        java.util.function.DoubleConsumer setScale;
        java.util.function.DoubleSupplier getOpacity;
        java.util.function.DoubleConsumer setOpacity;
        java.util.function.BooleanSupplier isEnabled;
        java.util.function.Consumer<Boolean> setEnabled;
        int width;
        int height;

        HudElement(String name, java.util.function.IntSupplier getX, java.util.function.IntConsumer setX,
                   java.util.function.IntSupplier getY, java.util.function.IntConsumer setY,
                   java.util.function.DoubleSupplier getScale, java.util.function.DoubleConsumer setScale,
                   java.util.function.DoubleSupplier getOpacity, java.util.function.DoubleConsumer setOpacity,
                   java.util.function.BooleanSupplier isEnabled, java.util.function.Consumer<Boolean> setEnabled,
                   int width, int height) {
            this.name = name;
            this.getX = getX;
            this.setX = setX;
            this.getY = getY;
            this.setY = setY;
            this.getScale = getScale;
            this.setScale = setScale;
            this.getOpacity = getOpacity;
            this.setOpacity = setOpacity;
            this.isEnabled = isEnabled;
            this.setEnabled = setEnabled;
            this.width = width;
            this.height = height;
        }
    }
    
    private HudElement[] elements;
    private HudElement draggedElement = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;
    private boolean isMenuExpanded = false;
    private HudElement selectedElement = null;

    public HollowHudEditorScreen(Screen parent) {
        super(Text.of("HUD Layout Editor"));
        this.parent = parent;
    }
    
    private HudElement createElem(String name, int defWidth, int defHeight) {
        return new HudElement(name,
            () -> (int) HollowHudConfig.get(name).x,
            (x) -> HollowHudConfig.get(name).x = x,
            () -> (int) HollowHudConfig.get(name).y,
            (y) -> HollowHudConfig.get(name).y = y,
            () -> HollowHudConfig.get(name).scale,
            (s) -> HollowHudConfig.get(name).scale = (float) s,
            () -> HollowHudConfig.get(name).opacity,
            (o) -> HollowHudConfig.get(name).opacity = (float) o,
            () -> HollowHudConfig.get(name).enabled,
            (e) -> HollowHudConfig.get(name).enabled = e,
            defWidth, defHeight);
    }

    @Override
    protected void init() {
        elements = new HudElement[]{
            createElem("Keystrokes", 60, 60),
            createElem("Armor HUD", 90, 22),
            createElem("Potion HUD", 100, 40),
            createElem("Coordinates", 120, 26),
            createElem("Direction HUD", 160, 20),
            createElem("FPS HUD", 50, 14),
            createElem("Combo Display", 65, 14),
            createElem("Ping Display", 60, 14),
            createElem("Playtime", 100, 14),
            createElem("Reach Display", 70, 14),
            createElem("Server Address", 110, 14),
            createElem("Speed Meter", 85, 14),
            createElem("Stopwatch", 95, 14),
            createElem("Item Counter", 65, 14),
            createElem("Pack Display", 110, 14),
            createElem("Target HUD", 120, 36),
            createElem("Totem Counter", 65, 14),
            createElem("Saturation HUD", 80, 14),
            createElem("Armor Bar", 100, 20),
            createElem("Armor Status", 80, 60),
            createElem("Boss Bar", 140, 20),
            createElem("Hearts", 100, 20),
            createElem("Scoreboard", 100, 100)
        };
        
        selectedElement = null;

        // Return button
        this.addDrawableChild(ButtonWidget.builder(Text.of("Save & Exit"), button -> {
            if (this.client != null) {
                this.client.setScreen(parent);
            }
        }).dimensions(this.width / 2 - 60, this.height - 30, 120, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Translucent dark background overlay so game is visible underneath
        context.fill(0, 0, this.width, this.height, 0x80000000);
        
        // Render actual HUD elements as a preview!
        net.hollowclient.client.hud.HollowHudRenderer.renderHUD(context, delta);
        
        // Draw grid and hints (slightly lowered if menu is collapsed, or hidden if menu is expanded)
        if (!this.isMenuExpanded) {
            context.drawCenteredTextWithShadow(this.textRenderer, "HUD Layout Editor", this.width / 2, 20, 0xFF9C27B0);
            context.drawCenteredTextWithShadow(this.textRenderer, "Drag boxes to reposition them. Touch-friendly for PojavLauncher.", this.width / 2, 32, 0xFF888888);
        }
        
        for (HudElement el : elements) {
            boolean enabled = el.isEnabled.getAsBoolean();
            if (!enabled) continue;
            
            int x = el.getX.getAsInt();
            int y = el.getY.getAsInt();
            double scale = el.getScale.getAsDouble();
            double opacity = el.getOpacity != null ? el.getOpacity.getAsDouble() : 1.0;
            int w = (int) (el.width * scale);
            int h = (int) (el.height * scale);
            
            boolean isHovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
            boolean isDragged = el == draggedElement;
            boolean isSelected = el == selectedElement;
            
            int alpha = (int)(opacity * 255);
            // Selected border is red/purple, dragged is purple, hovered is light purple, default is gray
            int borderColor = isSelected ? 0xFFFF007F : (isDragged ? 0xFF9C27B0 : (isHovered ? 0xFFBA68C8 : ((alpha << 24) | 0x777777)));
            int boxBgColor = isSelected ? 0x60FF007F : (isDragged ? 0x409C27B0 : (isHovered ? 0x20BA68C8 : (((alpha / 6) << 24) | 0xFFFFFF)));
            
            context.fill(x, y, x + w, y + h, boxBgColor);
            context.drawBorder(x, y, w, h, borderColor);
            
            // Element label inside the box
            context.getMatrices().push();
            context.getMatrices().translate(x + 3, y + 3, 0);
            context.getMatrices().scale((float)Math.min(1.0, scale), (float)Math.min(1.0, scale), 1f);
            context.drawText(this.textRenderer, el.name, 0, 0, 0xFFFFFFFF, true);
            context.getMatrices().pop();
        }
        
        // ==========================================
        // PUBG/BGMI-style Dropdown Control Menu
        // ==========================================
        int menuW = 240;
        int menuH = 120;
        int menuX = (this.width - menuW) / 2;
        int menuY = this.isMenuExpanded ? 10 : -menuH;

        if (this.isMenuExpanded) {
            // Draw panel background
            RenderUtils.drawRoundedRect(context.getMatrices(), menuX, menuY, menuW, menuH, 6, 0xEE1A1A1E);
            RenderUtils.drawRoundedOutline(context.getMatrices(), menuX, menuY, menuW, menuH, 6, 1.0f, 0xFF9C27B0);

            if (selectedElement == null) {
                context.drawCenteredTextWithShadow(this.textRenderer, "HUD Settings Panel", menuX + menuW / 2, menuY + 12, 0xFF9C27B0);
                context.drawCenteredTextWithShadow(this.textRenderer, "Click any HUD box to edit", menuX + menuW / 2, menuY + 50, 0xFF888888);
            } else {
                context.drawCenteredTextWithShadow(this.textRenderer, "Editing: " + selectedElement.name, menuX + menuW / 2, menuY + 10, 0xFF9C27B0);
                
                // --- Row 1: Visibility ---
                boolean enabled = selectedElement.isEnabled.getAsBoolean();
                context.drawText(this.textRenderer, "Visible: " + (enabled ? "ON" : "OFF"), menuX + 15, menuY + 32, 0xFFFFFFFF, true);
                drawMiniButton(context, enabled ? "Disable" : "Enable", menuX + 140, menuY + 28, 80, 14, mouseX, mouseY);

                // --- Row 2: Size (Scale) ---
                double scale = selectedElement.getScale.getAsDouble();
                context.drawText(this.textRenderer, "Scale: " + (int)(scale * 100) + "%", menuX + 15, menuY + 52, 0xFFFFFFFF, true);
                drawMiniButton(context, "-", menuX + 110, menuY + 48, 16, 14, mouseX, mouseY);
                drawMiniButton(context, "+", menuX + 130, menuY + 48, 16, 14, mouseX, mouseY);
                drawMiniButton(context, "Reset", menuX + 155, menuY + 48, 35, 14, mouseX, mouseY);

                // --- Row 3: Opacity ---
                double opacity = selectedElement.getOpacity.getAsDouble();
                context.drawText(this.textRenderer, "Opacity: " + (int)(opacity * 100) + "%", menuX + 15, menuY + 72, 0xFFFFFFFF, true);
                drawMiniButton(context, "-", menuX + 110, menuY + 68, 16, 14, mouseX, mouseY);
                drawMiniButton(context, "+", menuX + 130, menuY + 68, 16, 14, mouseX, mouseY);
                drawMiniButton(context, "Reset", menuX + 155, menuY + 68, 35, 14, mouseX, mouseY);

                // --- Row 4: Reset Position & Global Reset ---
                drawMiniButton(context, "Reset Pos", menuX + 15, menuY + 92, 100, 16, mouseX, mouseY);
                drawMiniButton(context, "Reset All", menuX + 125, menuY + 92, 100, 16, mouseX, mouseY);
            }

            // Draw arrow pull-up tab
            int tabW = 60;
            int tabH = 14;
            int tabX = (this.width - tabW) / 2;
            int tabY = menuY + menuH;
            RenderUtils.drawRoundedRect(context.getMatrices(), tabX, tabY, tabW, tabH, 4, 0xEE1A1A1E);
            RenderUtils.drawRoundedOutline(context.getMatrices(), tabX, tabY, tabW, tabH, 4, 1.0f, 0xFF9C27B0);
            context.drawCenteredTextWithShadow(this.textRenderer, "▲", tabX + tabW / 2, tabY + 2, 0xFFFFFFFF);
        } else {
            // Draw collapsed tab at the top
            int tabW = 90;
            int tabH = 16;
            int tabX = (this.width - tabW) / 2;
            int tabY = 0;
            RenderUtils.drawRoundedRect(context.getMatrices(), tabX, tabY, tabW, tabH, 4, 0xEE1A1A1E);
            RenderUtils.drawRoundedOutline(context.getMatrices(), tabX, tabY, tabW, tabH, 4, 1.0f, 0xFF9C27B0);
            context.drawCenteredTextWithShadow(this.textRenderer, "HUD Settings ▼", tabX + tabW / 2, tabY + 4, 0xFFFFFFFF);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawMiniButton(DrawContext context, String text, int x, int y, int w, int h, int mouseX, int mouseY) {
        boolean hover = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
        int color = hover ? 0xFF9C27B0 : 0xFF2A2B36;
        RenderUtils.drawRoundedRect(context.getMatrices(), x, y, w, h, 2, color);
        RenderUtils.drawRoundedOutline(context.getMatrices(), x, y, w, h, 2, 1.0f, hover ? 0xFFFFFFFF : 0x20FFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, text, x + w / 2, y + (h - 7) / 2, 0xFFFFFFFF);
    }
    
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // Do nothing to prevent background blur and allow clear view of the world
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // --- 1. Dropdown Settings Menu Click Interactions ---
        int menuW = 240;
        int menuH = 120;
        int menuX = (this.width - menuW) / 2;
        int menuY = this.isMenuExpanded ? 10 : -menuH;

        if (this.isMenuExpanded) {
            // Check pull-up arrow tab click
            int tabW = 60;
            int tabH = 14;
            int tabX = (this.width - tabW) / 2;
            int tabY = menuY + menuH;
            if (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= tabY && mouseY <= tabY + tabH) {
                this.isMenuExpanded = false;
                return true;
            }

            // Click within settings panel bounds
            if (mouseX >= menuX && mouseX <= menuX + menuW && mouseY >= menuY && mouseY <= menuY + menuH) {
                if (selectedElement != null) {
                    // Visibility Toggle
                    if (mouseX >= menuX + 140 && mouseX <= menuX + 220 && mouseY >= menuY + 28 && mouseY <= menuY + 42) {
                        selectedElement.setEnabled.accept(!selectedElement.isEnabled.getAsBoolean());
                        HollowHudConfig.save();
                        return true;
                    }
                    // Scale Minus
                    if (mouseX >= menuX + 110 && mouseX <= menuX + 126 && mouseY >= menuY + 48 && mouseY <= menuY + 62) {
                        double s = Math.max(0.5, selectedElement.getScale.getAsDouble() - 0.1);
                        selectedElement.setScale.accept(s);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Scale Plus
                    if (mouseX >= menuX + 130 && mouseX <= menuX + 146 && mouseY >= menuY + 48 && mouseY <= menuY + 62) {
                        double s = Math.min(3.0, selectedElement.getScale.getAsDouble() + 0.1);
                        selectedElement.setScale.accept(s);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Scale Reset
                    if (mouseX >= menuX + 155 && mouseX <= menuX + 190 && mouseY >= menuY + 48 && mouseY <= menuY + 62) {
                        selectedElement.setScale.accept(1.0);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Opacity Minus
                    if (mouseX >= menuX + 110 && mouseX <= menuX + 126 && mouseY >= menuY + 68 && mouseY <= menuY + 82) {
                        double o = Math.max(0.1, selectedElement.getOpacity.getAsDouble() - 0.1);
                        selectedElement.setOpacity.accept(o);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Opacity Plus
                    if (mouseX >= menuX + 130 && mouseX <= menuX + 146 && mouseY >= menuY + 68 && mouseY <= menuY + 82) {
                        double o = Math.min(1.0, selectedElement.getOpacity.getAsDouble() + 0.1);
                        selectedElement.setOpacity.accept(o);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Opacity Reset
                    if (mouseX >= menuX + 155 && mouseX <= menuX + 190 && mouseY >= menuY + 68 && mouseY <= menuY + 82) {
                        selectedElement.setOpacity.accept(1.0);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Reset Position
                    if (mouseX >= menuX + 15 && mouseX <= menuX + 115 && mouseY >= menuY + 92 && mouseY <= menuY + 108) {
                        selectedElement.setX.accept(10);
                        selectedElement.setY.accept(10);
                        HollowHudConfig.save();
                        return true;
                    }
                    // Reset All
                    if (mouseX >= menuX + 125 && mouseX <= menuX + 225 && mouseY >= menuY + 92 && mouseY <= menuY + 108) {
                        for (HudElement el : elements) {
                            el.setX.accept(10);
                            el.setY.accept(10);
                            el.setScale.accept(1.0);
                            el.setOpacity.accept(1.0);
                            el.setEnabled.accept(true);
                        }
                        HollowHudConfig.save();
                        return true;
                    }
                }
                return true; // Consume all clicks inside panel
            }
        } else {
            // Check collapsed tab click
            int tabW = 90;
            int tabH = 16;
            int tabX = (this.width - tabW) / 2;
            if (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= 0 && mouseY <= tabH) {
                this.isMenuExpanded = true;
                return true;
            }
        }

        // --- 2. Element Dragging & Selection Click Interactions ---
        selectedElement = null;
        for (HudElement el : elements) {
            boolean enabled = el.isEnabled.getAsBoolean();
            if (!enabled) continue;
            
            int x = el.getX.getAsInt();
            int y = el.getY.getAsInt();
            double scale = el.getScale.getAsDouble();
            int w = (int) (el.width * scale);
            int h = (int) (el.height * scale);
            
            if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
                selectedElement = el;
                draggedElement = el;
                dragOffsetX = (int) mouseX - x;
                dragOffsetY = (int) mouseY - y;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggedElement != null) {
            int newX = (int) mouseX - dragOffsetX;
            int newY = (int) mouseY - dragOffsetY;
            
            // Screen clamping / boundaries
            int maxW = this.width - draggedElement.width;
            int maxH = this.height - draggedElement.height;
            
            if (newX < 0) newX = 0;
            if (newX > maxW) newX = maxW;
            if (newY < 0) newY = 0;
            if (newY > maxH) newY = maxH;
            
            // Edge snapping (10 pixels snap zone)
            if (newX < 10) newX = 0;
            if (Math.abs(newX - maxW) < 10) newX = maxW;
            if (newY < 10) newY = 0;
            if (Math.abs(newY - maxH) < 10) newY = maxH;
            
            // Center snapping
            int centerX = this.width / 2 - draggedElement.width / 2;
            if (Math.abs(newX - centerX) < 10) newX = centerX;
            
            draggedElement.setX.accept(newX);
            draggedElement.setY.accept(newY);
            
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggedElement != null) {
            draggedElement = null;
            HollowHudConfig.save();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double hAmt, double vAmt) {
        for (HudElement el : elements) {
            boolean enabled = el.isEnabled.getAsBoolean();
            if (!enabled) continue;

            int x = el.getX.getAsInt();
            int y = el.getY.getAsInt();
            double scale = el.getScale.getAsDouble();
            int w = (int) (el.width * scale);
            int h = (int) (el.height * scale);

            if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
                double newScale = scale + (vAmt * 0.1);
                if (newScale < 0.5) newScale = 0.5;
                if (newScale > 3.0) newScale = 3.0;
                el.setScale.accept(newScale);
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, hAmt, vAmt);
    }

    @Override
    public void close() {
        HollowHudConfig.save();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}

