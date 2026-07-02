package net.featherpojav.client.hud;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeatherHudRenderer {
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();
    
    private static boolean lastLMB = false;
    private static boolean lastRMB = false;

    public static void addLeftClick() {
        leftClicks.add(System.currentTimeMillis());
    }

    public static void addRightClick() {
        rightClicks.add(System.currentTimeMillis());
    }

    public static int getLeftCPS() {
        long now = System.currentTimeMillis();
        leftClicks.removeIf(time -> now - time > 1000);
        return leftClicks.size();
    }

    public static int getRightCPS() {
        long now = System.currentTimeMillis();
        rightClicks.removeIf(time -> now - time > 1000);
        return rightClicks.size();
    }

    public static void updateCPS(MinecraftClient client) {
        boolean currentLMB = client.options.attackKey.isPressed();
        boolean currentRMB = client.options.useKey.isPressed();
        
        if (currentLMB && !lastLMB) {
            addLeftClick();
        }
        if (currentRMB && !lastRMB) {
            addRightClick();
        }
        
        lastLMB = currentLMB;
        lastRMB = currentRMB;
    }

    public static void renderHUD(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;
        
        TextRenderer tr = client.textRenderer;
        FeatherConfig cfg = FeatherConfig.INSTANCE;
        
        updateCPS(client);

        // 1. Keystrokes & CPS
        if (cfg.keystrokes) {
            int kx = cfg.keystrokesX;
            int ky = cfg.keystrokesY;
            
            boolean w = client.options.forwardKey.isPressed();
            boolean a = client.options.leftKey.isPressed();
            boolean s = client.options.backKey.isPressed();
            boolean d = client.options.rightKey.isPressed();
            boolean space = client.options.jumpKey.isPressed();
            boolean lmb = client.options.attackKey.isPressed();
            boolean rmb = client.options.useKey.isPressed();

            // WASD
            drawKey(context, tr, "W", kx + 20, ky, 18, 18, w);
            drawKey(context, tr, "A", kx, ky + 20, 18, 18, a);
            drawKey(context, tr, "S", kx + 20, ky + 20, 18, 18, s);
            drawKey(context, tr, "D", kx + 40, ky + 20, 18, 18, d);
            
            // Mouse Clicks / CPS
            drawKey(context, tr, getLeftCPS() + " CPS", kx, ky + 40, 29, 18, lmb);
            drawKey(context, tr, getRightCPS() + " CPS", kx + 31, ky + 40, 29, 18, rmb);
            
            // Spacebar
            drawKey(context, tr, "------", kx, ky + 60, 60, 10, space);
        }

        // 2. Coordinates HUD
        if (cfg.coordHUD) {
            int cx = cfg.coordHUDX;
            int cy = cfg.coordHUDY;
            
            context.fill(cx, cy, cx + 120, cy + 26, 0x80000000);
            context.fill(cx, cy, cx + 2, cy + 26, cfg.themeColor);
            
            String coords = String.format("XYZ: %.1f / %.0f / %.1f", client.player.getX(), client.player.getY(), client.player.getZ());
            context.drawText(tr, coords, cx + 6, cy + 3, 0xFFFFFFFF, false);
            
            // Direction facing
            float yaw = client.player.getYaw();
            yaw = (yaw % 360 + 360) % 360;
            String direction = "S";
            if (yaw >= 315 || yaw < 45) direction = "S (+Z)";
            else if (yaw >= 45 && yaw < 135) direction = "W (-X)";
            else if (yaw >= 135 && yaw < 225) direction = "N (-Z)";
            else if (yaw >= 225 && yaw < 315) direction = "E (+X)";
            
            context.drawText(tr, "Facing: " + direction, cx + 6, cy + 14, 0xFF888888, false);
        }

        // 3. FPS HUD
        if (cfg.fpsHUD) {
            int fx = cfg.fpsHUDX;
            int fy = cfg.fpsHUDY;
            
            context.fill(fx, fy, fx + 50, fy + 14, 0x80000000);
            context.fill(fx, fy, fx + 2, fy + 14, cfg.themeColor);
            context.drawText(tr, client.getCurrentFps() + " FPS", fx + 6, fy + 3, 0xFFFFFFFF, false);
        }

        // 4. Direction HUD / Compass Scale
        if (cfg.directionHUD) {
            int dx = cfg.directionHUDX;
            int dy = cfg.directionHUDY;
            int width = 160;
            int height = 18;
            
            context.fill(dx, dy, dx + width, dy + height, 0x80000000);
            context.drawBorder(dx, dy, width, height, 0x20FFFFFF);
            
            // Draw center marker line
            context.fill(dx + width / 2 - 1, dy, dx + width / 2 + 1, dy + height, cfg.themeColor);
            
            float playerYaw = client.player.getYaw();
            
            // Draw scale markings
            for (int i = -60; i <= 60; i += 5) {
                float angle = playerYaw + i;
                float normalizedAngle = (angle % 360 + 360) % 360;
                
                // Position on screen relative to center
                int posX = dx + width / 2 + (int) (i * 1.5f);
                if (posX < dx + 3 || posX > dx + width - 3) continue;
                
                if (normalizedAngle % 90 == 0) {
                    // Main directions: N, S, E, W
                    String dir = "N";
                    if (Math.abs(normalizedAngle - 0) < 1) dir = "S";
                    else if (Math.abs(normalizedAngle - 90) < 1) dir = "W";
                    else if (Math.abs(normalizedAngle - 180) < 1) dir = "N";
                    else if (Math.abs(normalizedAngle - 270) < 1) dir = "E";
                    
                    context.drawCenteredTextWithShadow(tr, dir, posX, dy + 2, 0xFFFFFFFF);
                } else if (normalizedAngle % 45 == 0) {
                    // Sub-directions: NE, SE, SW, NW
                    String dir = "SW";
                    if (Math.abs(normalizedAngle - 45) < 1) dir = "SW";
                    else if (Math.abs(normalizedAngle - 135) < 1) dir = "NW";
                    else if (Math.abs(normalizedAngle - 225) < 1) dir = "NE";
                    else if (Math.abs(normalizedAngle - 315) < 1) dir = "SE";
                    
                    context.drawCenteredTextWithShadow(tr, dir, posX, dy + 3, 0xFFCCCCCC);
                } else if (normalizedAngle % 15 == 0) {
                    // Degree markers
                    String degrees = String.valueOf((int) normalizedAngle / 10);
                    context.drawCenteredTextWithShadow(tr, degrees, posX, dy + 4, 0xFF888888);
                }
            }
        }

        // 5. Armor HUD
        if (cfg.armorHUD) {
            int ax = cfg.armorHUDX;
            int ay = cfg.armorHUDY;
            
            // Gather all items: main hand, helmet, chestplate, leggings, boots, offhand
            List<ItemStack> items = new ArrayList<>();
            items.add(client.player.getMainHandStack());
            for (int i = 3; i >= 0; i--) {
                items.add(client.player.getInventory().armor.get(i));
            }
            items.add(client.player.getOffHandStack());
            
            int offset = 0;
            for (ItemStack stack : items) {
                if (stack == null || stack.isEmpty()) continue;
                
                int itemX = ax + (cfg.armorHUDVertical ? 0 : offset);
                int itemY = ay + (cfg.armorHUDVertical ? offset : 0);
                
                context.drawItemInSlot(tr, stack, itemX, itemY);
                
                offset += cfg.armorHUDVertical ? 18 : 22;
            }
        }

        // 6. Potion HUD
        if (cfg.potionHUD) {
            int px = cfg.potionHUDX;
            int py = cfg.potionHUDY;
            
            Collection<StatusEffectInstance> effects = client.player.getStatusEffects();
            int offsetY = 0;
            
            for (StatusEffectInstance effect : effects) {
                String effectName = Text.translatable(effect.getEffectType().value().getTranslationKey()).getString();
                int amplifier = effect.getAmplifier();
                if (amplifier > 0) {
                    effectName += " " + (amplifier + 1);
                }
                
                // Format duration (MM:SS)
                int duration = effect.getDuration();
                String durationStr = "Permanent";
                if (!effect.isInfinite()) {
                    int seconds = (duration / 20) % 60;
                    int minutes = (duration / 1200);
                    durationStr = String.format("%d:%02d", minutes, seconds);
                }
                
                String fullText = effectName + " (" + durationStr + ")";
                
                context.fill(px, py + offsetY, px + 110, py + offsetY + 14, 0x80000000);
                context.fill(px, py + offsetY, px + 2, py + offsetY + 14, cfg.themeColor);
                
                context.drawText(tr, fullText, px + 6, py + offsetY + 3, 0xFFFFFFFF, false);
                offsetY += 16;
            }
        }
    }

    private static void drawKey(DrawContext context, TextRenderer tr, String label, int x, int y, int w, int h, boolean pressed) {
        int bg = pressed ? FeatherConfig.INSTANCE.themeColor : 0x80000000;
        int textCol = pressed ? 0xFFFFFFFF : 0xFFCCCCCC;
        
        context.fill(x, y, x + w, y + h, bg);
        context.drawBorder(x, y, w, h, pressed ? 0xFFBA68C8 : 0x40FFFFFF);
        context.drawCenteredTextWithShadow(tr, label, x + w / 2, y + (h - 8) / 2, textCol);
    }
}
