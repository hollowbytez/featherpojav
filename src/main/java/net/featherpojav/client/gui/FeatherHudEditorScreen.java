package net.featherpojav.client.gui;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class FeatherHudEditorScreen extends Screen {
    private final Screen parent;
    
    // Bounding Box definitions for HUD elements
    private static class HudElement {
        String name;
        java.util.function.IntSupplier getX;
        java.util.function.IntConsumer setX;
        java.util.function.IntSupplier getY;
        java.util.function.IntConsumer setY;
        java.util.function.BooleanSupplier isEnabled;
        int width;
        int height;

        HudElement(String name, java.util.function.IntSupplier getX, java.util.function.IntConsumer setX,
                   java.util.function.IntSupplier getY, java.util.function.IntConsumer setY,
                   java.util.function.BooleanSupplier isEnabled, int width, int height) {
            this.name = name;
            this.getX = getX;
            this.setX = setX;
            this.getY = getY;
            this.setY = setY;
            this.isEnabled = isEnabled;
            this.width = width;
            this.height = height;
        }
    }
    
    private HudElement[] elements;
    private HudElement draggedElement = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public FeatherHudEditorScreen(Screen parent) {
        super(Text.of("HUD Layout Editor"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        FeatherConfig cfg = FeatherConfig.INSTANCE;
        elements = new HudElement[]{
            new HudElement("Keystrokes", () -> cfg.keystrokesX, (x) -> cfg.keystrokesX = x, () -> cfg.keystrokesY, (y) -> cfg.keystrokesY = y, () -> cfg.keystrokes, 60, 60),
            new HudElement("Armor HUD", () -> cfg.armorHUDX, (x) -> cfg.armorHUDX = x, () -> cfg.armorHUDY, (y) -> cfg.armorHUDY = y, () -> cfg.armorHUD, cfg.armorHUDVertical ? 22 : 90, cfg.armorHUDVertical ? 90 : 22),
            new HudElement("Potion HUD", () -> cfg.potionHUDX, (x) -> cfg.potionHUDX = x, () -> cfg.potionHUDY, (y) -> cfg.potionHUDY = y, () -> cfg.potionHUD, 100, 40),
            new HudElement("Coordinates", () -> cfg.coordHUDX, (x) -> cfg.coordHUDX = x, () -> cfg.coordHUDY, (y) -> cfg.coordHUDY = y, () -> cfg.coordHUD, 120, 26),
            new HudElement("Direction HUD", () -> cfg.directionHUDX, (x) -> cfg.directionHUDX = x, () -> cfg.directionHUDY, (y) -> cfg.directionHUDY = y, () -> cfg.directionHUD, 160, 20),
            new HudElement("FPS HUD", () -> cfg.fpsHUDX, (x) -> cfg.fpsHUDX = x, () -> cfg.fpsHUDY, (y) -> cfg.fpsHUDY = y, () -> cfg.fpsHUD, 50, 14),
            new HudElement("Combo Display", () -> cfg.comboDisplayX, (x) -> cfg.comboDisplayX = x, () -> cfg.comboDisplayY, (y) -> cfg.comboDisplayY = y, () -> cfg.comboDisplay, 65, 14),
            new HudElement("Ping Display", () -> cfg.pingDisplayX, (x) -> cfg.pingDisplayX = x, () -> cfg.pingDisplayY, (y) -> cfg.pingDisplayY = y, () -> cfg.pingDisplay, 60, 14),
            new HudElement("Playtime", () -> cfg.playtimeX, (x) -> cfg.playtimeX = x, () -> cfg.playtimeY, (y) -> cfg.playtimeY = y, () -> cfg.playtime, 100, 14),
            new HudElement("Reach Display", () -> cfg.reachDisplayX, (x) -> cfg.reachDisplayX = x, () -> cfg.reachDisplayY, (y) -> cfg.reachDisplayY = y, () -> cfg.reachDisplay, 70, 14),
            new HudElement("Server Address", () -> cfg.serverAddressX, (x) -> cfg.serverAddressX = x, () -> cfg.serverAddressY, (y) -> cfg.serverAddressY = y, () -> cfg.serverAddress, 110, 14),
            new HudElement("Speed Meter", () -> cfg.speedMeterX, (x) -> cfg.speedMeterX = x, () -> cfg.speedMeterY, (y) -> cfg.speedMeterY = y, () -> cfg.speedMeter, 85, 14),
            new HudElement("Stopwatch", () -> cfg.stopwatchX, (x) -> cfg.stopwatchX = x, () -> cfg.stopwatchY, (y) -> cfg.stopwatchY = y, () -> cfg.stopwatch, 95, 14),
            new HudElement("Item Counter", () -> cfg.itemCounterX, (x) -> cfg.itemCounterX = x, () -> cfg.itemCounterY, (y) -> cfg.itemCounterY = y, () -> cfg.itemCounter, 65, 14),
            new HudElement("Pack Display", () -> cfg.packDisplayX, (x) -> cfg.packDisplayX = x, () -> cfg.packDisplayY, (y) -> cfg.packDisplayY = y, () -> cfg.packDisplay, 110, 14),
            new HudElement("Target HUD", () -> cfg.targetHudX, (x) -> cfg.targetHudX = x, () -> cfg.targetHudY, (y) -> cfg.targetHudY = y, () -> cfg.damageIndicator, 120, 36),
            new HudElement("Totem Counter", () -> cfg.totemCounterX, (x) -> cfg.totemCounterX = x, () -> cfg.totemCounterY, (y) -> cfg.totemCounterY = y, () -> cfg.totemCounter, 65, 14),
            new HudElement("Saturation HUD", () -> cfg.saturationHUDX, (x) -> cfg.saturationHUDX = x, () -> cfg.saturationHUDY, (y) -> cfg.saturationHUDY = y, () -> cfg.saturationHUD, 80, 14)
        };
        
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
        
        // Draw grid and hints
        context.drawCenteredTextWithShadow(this.textRenderer, "HUD Layout Editor", this.width / 2, 10, 0xFF9C27B0);
        context.drawCenteredTextWithShadow(this.textRenderer, "Drag boxes to reposition them. Touch-friendly for PojavLauncher.", this.width / 2, 22, 0xFF888888);
        
        for (HudElement el : elements) {
            if (!el.isEnabled.getAsBoolean()) continue;
            
            int x = el.getX.getAsInt();
            int y = el.getY.getAsInt();
            int w = el.width;
            int h = el.height;
            
            boolean isHovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
            boolean isDragged = el == draggedElement;
            
            // Render bounding box with active status colors (purple for dragging, white/grey for normal)
            int borderColor = isDragged ? 0xFF9C27B0 : (isHovered ? 0xFFBA68C8 : 0xAA777777);
            int boxBgColor = isDragged ? 0x409C27B0 : (isHovered ? 0x20BA68C8 : 0x10FFFFFF);
            
            context.fill(x, y, x + w, y + h, boxBgColor);
            context.drawBorder(x, y, w, h, borderColor);
            
            // Element label inside the box
            context.drawText(this.textRenderer, el.name, x + 3, y + 3, 0xFFFFFFFF, true);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // Do nothing to prevent background blur and allow clear view of the world
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (HudElement el : elements) {
            if (!el.isEnabled.getAsBoolean()) continue;
            
            int x = el.getX.getAsInt();
            int y = el.getY.getAsInt();
            int w = el.width;
            int h = el.height;
            
            if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
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
            FeatherConfig.save();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}
