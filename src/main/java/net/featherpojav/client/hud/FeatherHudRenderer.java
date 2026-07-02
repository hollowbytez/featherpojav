package net.featherpojav.client.hud;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeatherHudRenderer {
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();
    
    private static boolean lastLMB = false;
    private static boolean lastRMB = false;

    // PvP / Stats Tracking fields
    public static int comboCount = 0;
    public static long lastHitTime = 0;
    public static double lastReach = 0.0;
    
    private static final long sessionStartTime = System.currentTimeMillis();

    // Stopwatch states
    public static long stopwatchStart = 0;
    public static boolean stopwatchRunning = false;
    public static long stopwatchElapsed = 0;

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

    public static void onHit() {
        long now = System.currentTimeMillis();
        if (now - lastHitTime < 2000) {
            comboCount++;
        } else {
            comboCount = 1;
        }
        lastHitTime = now;
    }

    public static void onAttackEntity(net.minecraft.entity.Entity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && entity != null) {
            lastReach = client.player.distanceTo(entity);
        }
    }

    public static String getPlaytime() {
        long duration = System.currentTimeMillis() - sessionStartTime;
        long seconds = (duration / 1000) % 60;
        long minutes = (duration / (1000 * 60)) % 60;
        long hours = (duration / (1000 * 60 * 60));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void toggleStopwatch() {
        if (stopwatchRunning) {
            stopwatchElapsed += System.currentTimeMillis() - stopwatchStart;
            stopwatchRunning = false;
        } else {
            stopwatchStart = System.currentTimeMillis();
            stopwatchRunning = true;
        }
    }

    public static String getStopwatchTime() {
        long elapsed = stopwatchElapsed;
        if (stopwatchRunning) {
            elapsed += System.currentTimeMillis() - stopwatchStart;
        }
        long seconds = (elapsed / 1000) % 60;
        long minutes = (elapsed / (1000 * 60)) % 60;
        long millis = (elapsed % 1000) / 10;
        return String.format("%02d:%02d.%02d", minutes, seconds, millis);
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
            
            // Draw N, S, E, W
            String[] dirs = {"S", "W", "N", "E"};
            for (int j = 0; j < 4; j++) {
                float angle = j * 90;
                float diff = angle - playerYaw;
                diff = ((diff + 180) % 360 + 360) % 360 - 180;
                
                if (Math.abs(diff) <= 60) {
                    int posX = dx + width / 2 + (int) (diff * 1.3f);
                    context.drawCenteredTextWithShadow(tr, dirs[j], posX, dy + 2, cfg.themeColor);
                }
            }
            
            // Draw degree markings every 15 degrees
            for (int angle = 0; angle < 360; angle += 15) {
                if (angle % 90 == 0) continue;
                float diff = angle - playerYaw;
                diff = ((diff + 180) % 360 + 360) % 360 - 180;
                
                if (Math.abs(diff) <= 60) {
                    int posX = dx + width / 2 + (int) (diff * 1.3f);
                    context.drawCenteredTextWithShadow(tr, String.valueOf(angle), posX, dy + 4, 0xFF888888);
                }
            }
        }

        // 5. Armor HUD & Armor Status
        if (cfg.armorHUD) {
            int ax = cfg.armorHUDX;
            int ay = cfg.armorHUDY;
            
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
                
                context.drawItem(stack, itemX, itemY);
                context.drawItemInSlot(tr, stack, itemX, itemY);
                
                if (cfg.armorStatus && stack.isDamageable()) {
                    int maxDmg = stack.getMaxDamage();
                    int curDmg = maxDmg - stack.getDamage();
                    String durText = curDmg + "/" + maxDmg;
                    context.drawText(tr, durText, itemX + 18, itemY + 4, 0xFF88FF88, true);
                }
                
                offset += cfg.armorHUDVertical ? 18 : 36;
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

        // 6a. Armor Bar
        if (cfg.armorBar) {
            int armorValue = client.player.getArmor();
            if (armorValue > 0) {
                int barX = client.getWindow().getScaledWidth() / 2 - 91;
                int barY = client.getWindow().getScaledHeight() - 49;
                context.drawText(tr, "🛡 " + armorValue, barX, barY, 0xFF5C6BC0, true);
            }
        }

        // 6b. Hearts Multiplier
        if (cfg.hearts) {
            int hp = (int) client.player.getHealth();
            int maxHp = (int) client.player.getMaxHealth();
            int abs = (int) client.player.getAbsorptionAmount();
            String hpText = "❤ " + hp + "/" + maxHp;
            if (abs > 0) hpText += " + " + abs + "★";
            int barX = client.getWindow().getScaledWidth() / 2 - 91;
            int barY = client.getWindow().getScaledHeight() - 40;
            context.drawText(tr, hpText, barX, barY, 0xFFE57373, true);
        }

        // 6c. Pack Display
        if (cfg.packDisplay) {
            int px = cfg.packDisplayX;
            int py = cfg.packDisplayY;
            String packName = "Default";
            if (client.getResourcePackManager() != null) {
                var enabledPacks = client.getResourcePackManager().getEnabledProfiles();
                if (!enabledPacks.isEmpty()) {
                    packName = enabledPacks.iterator().next().getDisplayName().getString();
                }
            }
            context.fill(px, py, px + 110, py + 14, 0x80000000);
            context.fill(px, py, px + 2, py + 14, cfg.themeColor);
            context.drawText(tr, "Pack: " + packName, px + 6, py + 3, 0xFFFFFFFF, false);
        }

        // 7. Combo Display
        if (cfg.comboDisplay) {
            int cx = cfg.comboDisplayX;
            int cy = cfg.comboDisplayY;
            context.fill(cx, cy, cx + 65, cy + 14, 0x80000000);
            context.fill(cx, cy, cx + 2, cy + 14, cfg.themeColor);
            context.drawText(tr, "Combo: " + comboCount, cx + 6, cy + 3, 0xFFFFFFFF, false);
        }

        // 8. Ping Display
        if (cfg.pingDisplay) {
            int px = cfg.pingDisplayX;
            int py = cfg.pingDisplayY;
            int ping = 0;
            if (client.getNetworkHandler() != null && client.player != null) {
                var entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
                if (entry != null) {
                    ping = entry.getLatency();
                }
            }
            context.fill(px, py, px + 60, py + 14, 0x80000000);
            context.fill(px, py, px + 2, py + 14, cfg.themeColor);
            context.drawText(tr, "Ping: " + ping + "ms", px + 6, py + 3, 0xFFFFFFFF, false);
        }

        // 9. Playtime
        if (cfg.playtime) {
            int px = cfg.playtimeX;
            int py = cfg.playtimeY;
            context.fill(px, py, px + 100, py + 14, 0x80000000);
            context.fill(px, py, px + 2, py + 14, cfg.themeColor);
            context.drawText(tr, "Time: " + getPlaytime(), px + 6, py + 3, 0xFFFFFFFF, false);
        }

        // 10. Reach Display
        if (cfg.reachDisplay) {
            int rx = cfg.reachDisplayX;
            int ry = cfg.reachDisplayY;
            context.fill(rx, ry, rx + 70, ry + 14, 0x80000000);
            context.fill(rx, ry, rx + 2, ry + 14, cfg.themeColor);
            context.drawText(tr, String.format("Reach: %.2fm", lastReach), rx + 6, ry + 3, 0xFFFFFFFF, false);
        }

        // 11. Server Address
        if (cfg.serverAddress) {
            int sx = cfg.serverAddressX;
            int sy = cfg.serverAddressY;
            String addr = "Singleplayer";
            if (client.getCurrentServerEntry() != null) {
                addr = client.getCurrentServerEntry().address;
            }
            context.fill(sx, sy, sx + 110, sy + 14, 0x80000000);
            context.fill(sx, sy, sx + 2, sy + 14, cfg.themeColor);
            context.drawText(tr, "IP: " + addr, sx + 6, sy + 3, 0xFFFFFFFF, false);
        }

        // 12. Speed Meter
        if (cfg.speedMeter) {
            int sx = cfg.speedMeterX;
            int sy = cfg.speedMeterY;
            double xDiff = client.player.getX() - client.player.prevX;
            double zDiff = client.player.getZ() - client.player.prevZ;
            double speed = Math.sqrt(xDiff * xDiff + zDiff * zDiff) * 20.0;
            context.fill(sx, sy, sx + 85, sy + 14, 0x80000000);
            context.fill(sx, sy, sx + 2, sy + 14, cfg.themeColor);
            context.drawText(tr, String.format("Speed: %.2f m/s", speed), sx + 6, sy + 3, 0xFFFFFFFF, false);
        }

        // 13. Stopwatch
        if (cfg.stopwatch) {
            int sx = cfg.stopwatchX;
            int sy = cfg.stopwatchY;
            context.fill(sx, sy, sx + 95, sy + 14, 0x80000000);
            context.fill(sx, sy, sx + 2, sy + 14, cfg.themeColor);
            context.drawText(tr, "Timer: " + getStopwatchTime(), sx + 6, sy + 3, 0xFFFFFFFF, false);
        }

        // 14. Item Counter
        if (cfg.itemCounter) {
            int ix = cfg.itemCounterX;
            int iy = cfg.itemCounterY;
            int count = 0;
            ItemStack held = client.player.getMainHandStack();
            if (!held.isEmpty()) {
                for (int i = 0; i < client.player.getInventory().size(); i++) {
                    ItemStack stack = client.player.getInventory().getStack(i);
                    if (stack.getItem() == held.getItem()) {
                        count += stack.getCount();
                    }
                }
            }
            context.fill(ix, iy, ix + 65, iy + 14, 0x80000000);
            context.fill(ix, iy, ix + 2, iy + 14, cfg.themeColor);
            context.drawText(tr, "Held: " + count, ix + 6, iy + 3, 0xFFFFFFFF, false);
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
