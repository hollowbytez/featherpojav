package net.featherpojav.client.gui;

import net.featherpojav.client.config.FeatherConfig;
import net.featherpojav.client.FeatherPojavModClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class FeatherSettingsScreen extends Screen {
    private final Screen parent;
    private Category currentCategory = Category.ALL;
    private final List<ModCard> cards = new ArrayList<>();
    private TextFieldWidget searchField;
    
    // Full-screen layout geometry
    private int panelX, panelY, panelW, panelH;
    private int headerH = 28;
    private int tabBarH = 20;
    
    // Scroll
    private double scrollY = 0;
    
    public FeatherSettingsScreen(Screen parent) {
        super(Text.of("Feather Mod Menu"));
        this.parent = parent;
    }
    
    enum Category {
        ALL("All"),
        NEW("New"),
        HUD("HUD"),
        PVP("PvP");
        
        final String name;
        Category(String name) { this.name = name; }
    }
    
    private static class ModCard {
        String name;
        String icon;
        java.util.function.BooleanSupplier getter;
        java.util.function.Consumer<Boolean> setter;
        Category category;
        Runnable onConfigure;

        ModCard(String name, String icon, Category category, java.util.function.BooleanSupplier getter, java.util.function.Consumer<Boolean> setter) {
            this.name = name;
            this.icon = icon;
            this.category = category;
            this.getter = getter;
            this.setter = setter;
        }

        ModCard withConfig(Runnable onConfigure) {
            this.onConfigure = onConfigure;
            return this;
        }
    }
    
    @Override
    protected void init() {
        // Full-screen panel with margins
        int margin = 10;
        panelX = margin;
        panelY = margin;
        panelW = this.width - margin * 2;
        panelH = this.height - margin * 2;

        // Search field in header area
        int searchW = Math.min(120, panelW / 4);
        int searchX = panelX + panelW - searchW - 50;
        int searchY = panelY + 8;
        this.searchField = new TextFieldWidget(this.textRenderer, searchX, searchY, searchW, 12, Text.of("Search"));
        this.searchField.setMaxLength(30);
        this.searchField.setDrawsBackground(false);
        this.searchField.setPlaceholder(Text.of("Search"));
        this.searchField.setEditableColor(0xFFFFFFFF);
        this.searchField.setUneditableColor(0xFF888888);
        this.addSelectableChild(this.searchField);

        cards.clear();
        FeatherConfig cfg = FeatherConfig.INSTANCE;
        
        // --- HUD Category ---
        cards.add(new ModCard("Armor Bar", "🛡", Category.HUD, () -> cfg.armorBar, (v) -> cfg.armorBar = v));
        cards.add(new ModCard("Armor HUD", "🛡", Category.HUD, () -> cfg.armorHUD, (v) -> cfg.armorHUD = v));
        cards.add(new ModCard("Armor Status", "🛡", Category.HUD, () -> cfg.armorStatus, (v) -> cfg.armorStatus = v));
        cards.add(new ModCard("Boss Bar", "👿", Category.HUD, () -> cfg.bossBar, (v) -> cfg.bossBar = v));
        cards.add(new ModCard("Combo Display", "⚔", Category.HUD, () -> cfg.comboDisplay, (v) -> cfg.comboDisplay = v));
        cards.add(new ModCard("Coordinates", "📍", Category.HUD, () -> cfg.coordHUD, (v) -> cfg.coordHUD = v));
        cards.add(new ModCard("Damage Indicator", "💔", Category.HUD, () -> cfg.damageIndicator, (v) -> cfg.damageIndicator = v));
        cards.add(new ModCard("Direction HUD", "🧭", Category.HUD, () -> cfg.directionHUD, (v) -> cfg.directionHUD = v));
        cards.add(new ModCard("FPS HUD", "📊", Category.HUD, () -> cfg.fpsHUD, (v) -> cfg.fpsHUD = v));
        cards.add(new ModCard("Hearts", "❤", Category.HUD, () -> cfg.hearts, (v) -> cfg.hearts = v));
        cards.add(new ModCard("Item Counter", "📦", Category.HUD, () -> cfg.itemCounter, (v) -> cfg.itemCounter = v));
        cards.add(new ModCard("Keystrokes", "⌨", Category.HUD, () -> cfg.keystrokes, (v) -> cfg.keystrokes = v));
        cards.add(new ModCard("Pack Display", "🗂", Category.HUD, () -> cfg.packDisplay, (v) -> cfg.packDisplay = v));
        cards.add(new ModCard("Ping Display", "📶", Category.HUD, () -> cfg.pingDisplay, (v) -> cfg.pingDisplay = v));
        cards.add(new ModCard("Playtime", "⏳", Category.HUD, () -> cfg.playtime, (v) -> cfg.playtime = v));
        cards.add(new ModCard("Potion HUD", "🧪", Category.HUD, () -> cfg.potionHUD, (v) -> cfg.potionHUD = v));
        cards.add(new ModCard("Reach Display", "📏", Category.HUD, () -> cfg.reachDisplay, (v) -> cfg.reachDisplay = v));
        cards.add(new ModCard("Saturation HUD", "🥩", Category.HUD, () -> cfg.saturationHUD, (v) -> cfg.saturationHUD = v));
        cards.add(new ModCard("Scoreboard", "📋", Category.HUD, () -> cfg.scoreboard, (v) -> cfg.scoreboard = v));
        cards.add(new ModCard("Server IP", "🌐", Category.HUD, () -> cfg.serverAddress, (v) -> cfg.serverAddress = v));
        cards.add(new ModCard("Speed Meter", "👟", Category.HUD, () -> cfg.speedMeter, (v) -> cfg.speedMeter = v));
        cards.add(new ModCard("Stopwatch", "⏱", Category.HUD, () -> cfg.stopwatch, (v) -> cfg.stopwatch = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherKeybindSettingScreen(this, "Stopwatch", FeatherPojavModClient.stopwatchKey));
            }));
        cards.add(new ModCard("Totem Counter", "🪶", Category.HUD, () -> cfg.totemCounter, (v) -> cfg.totemCounter = v));

        // --- PvP Category ---
        cards.add(new ModCard("AutoGG", "🗣", Category.PVP, () -> cfg.autoGG, (v) -> cfg.autoGG = v));
        cards.add(new ModCard("Crystal Optimizer", "💎", Category.PVP, () -> cfg.crystalOptimizer, (v) -> cfg.crystalOptimizer = v));
        cards.add(new ModCard("Freelook", "👁", Category.PVP, () -> cfg.freelook, (v) -> cfg.freelook = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherKeybindSettingScreen(this, "Freelook", FeatherPojavModClient.freelookKey));
            }));
        cards.add(new ModCard("Hitbox Outlines", "📦", Category.PVP, () -> cfg.hitbox, (v) -> cfg.hitbox = v));
        cards.add(new ModCard("Hurt Cam", "🤕", Category.PVP, () -> cfg.hurtCam, (v) -> cfg.hurtCam = v));
        cards.add(new ModCard("Low Fire", "🔥", Category.PVP, () -> cfg.lowFire, (v) -> cfg.lowFire = v));
        cards.add(new ModCard("ToggleSprint", "🏃", Category.PVP, () -> cfg.toggleSprint, (v) -> cfg.toggleSprint = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherKeybindSettingScreen(this, "ToggleSprint", FeatherPojavModClient.toggleSprintKey));
            }));
        cards.add(new ModCard("Zoom", "🔍", Category.PVP, () -> cfg.zoom, (v) -> cfg.zoom = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherZoomScreen(this));
            }));

        // --- New/Gameplay Category ---
        cards.add(new ModCard("Auto Text", "✍", Category.NEW, () -> cfg.autoText, (v) -> cfg.autoText = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherAutoTextScreen(this));
            }));
        cards.add(new ModCard("Cull Logs", "🧹", Category.NEW, () -> cfg.cullLogs, (v) -> cfg.cullLogs = v));
        cards.add(new ModCard("Custom Crosshair", "⌖", Category.NEW, () -> cfg.customCrosshair, (v) -> cfg.customCrosshair = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherCrosshairScreen(this));
            }));
        cards.add(new ModCard("Drop Prevention", "🔒", Category.NEW, () -> cfg.dropPrevention, (v) -> cfg.dropPrevention = v));
        cards.add(new ModCard("Fullbright", "💡", Category.NEW, () -> cfg.fullbright, (v) -> cfg.fullbright = v));
        cards.add(new ModCard("Item Physics", "🌍", Category.NEW, () -> cfg.itemPhysics, (v) -> cfg.itemPhysics = v));
        cards.add(new ModCard("Nick Hider", "👤", Category.NEW, () -> cfg.nickHider, (v) -> cfg.nickHider = v));
        cards.add(new ModCard("TimeChanger", "☀️", Category.NEW, () -> cfg.timeChanger, (v) -> cfg.timeChanger = v)
            .withConfig(() -> {
                if (this.client != null) this.client.setScreen(new FeatherTimeChangerScreen(this));
            }));
    }

    private List<ModCard> getFilteredCards() {
        String query = this.searchField != null ? this.searchField.getText().toLowerCase().trim() : "";
        List<ModCard> list = new ArrayList<>();
        for (ModCard c : cards) {
            boolean matchesCategory = (currentCategory == Category.ALL || c.category == currentCategory);
            boolean matchesSearch = query.isEmpty() || c.name.toLowerCase().contains(query);
            if (matchesCategory && matchesSearch) {
                list.add(c);
            }
        }
        list.sort(java.util.Comparator.comparing(c -> c.name));
        return list;
    }

    // ===== Number of columns for the card grid =====
    private int getColumns() {
        if (panelW > 500) return 4;
        if (panelW > 350) return 3;
        return 2;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int cardAreaY = panelY + headerH + tabBarH + 4;
        int cardAreaH = panelH - headerH - tabBarH - 8;
        if (mouseX >= panelX && mouseX <= panelX + panelW && mouseY >= cardAreaY && mouseY <= cardAreaY + cardAreaH) {
            int cols = getColumns();
            int cardH = getCardHeight();
            int gap = 4;
            int itemsCount = getFilteredCards().size();
            int rowsCount = (itemsCount + cols - 1) / cols;
            int totalHeight = rowsCount * (cardH + gap);
            double maxScroll = Math.max(0, totalHeight - cardAreaH);
            scrollY -= verticalAmount * 20;
            if (scrollY < 0) scrollY = 0;
            if (scrollY > maxScroll) scrollY = maxScroll;
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private int getCardHeight() {
        return Math.max(60, Math.min(80, (panelH - headerH - tabBarH - 40) / 3));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Darkened game background
        context.fill(0, 0, this.width, this.height, 0x90000000);

        // =========================================================
        // HEADER BAR: Feather logo + "MOD MENU" + search + close
        // =========================================================
        int hY = panelY;
        context.fill(panelX, hY, panelX + panelW, hY + headerH, 0xE0101014);
        // Bottom border of header
        context.fill(panelX, hY + headerH - 1, panelX + panelW, hY + headerH, 0xFF222226);

        // Feather icon (small)
        Identifier iconId = Identifier.of("featherpojav", "icon.png");
        context.drawTexture(iconId, panelX + 6, hY + 4, 0.0f, 0.0f, 20, 20, 500, 500);

        // "MOD MENU" text next to icon
        context.drawText(this.textRenderer, "MOD MENU", panelX + 30, hY + 10, 0xFFFFFFFF, true);

        // "FeatherClient" stylized text branding in header center
        String brandText = "F E A T H E R C L I E N T";
        int brandW = this.textRenderer.getWidth(brandText);
        int brandX = panelX + panelW / 2 - brandW / 2;
        context.drawText(this.textRenderer, brandText, brandX, hY + 10, 0xFF9C27B0, true);

        // Search field background + render
        if (this.searchField != null) {
            int sX = this.searchField.getX();
            int sY = this.searchField.getY();
            int sW = this.searchField.getWidth();
            int sH = this.searchField.getHeight();
            context.fill(sX - 3, sY - 2, sX + sW + 3, sY + sH + 2, 0xFF1A1A1E);
            context.drawBorder(sX - 3, sY - 2, sW + 6, sH + 4, this.searchField.isFocused() ? 0xFF9C27B0 : 0xFF333338);
            this.searchField.render(context, mouseX, mouseY, delta);
        }

        // Close (X) button top-right
        int closeX = panelX + panelW - 18;
        int closeY = hY + 4;
        boolean closeHovered = mouseX >= closeX - 2 && mouseX <= closeX + 14 && mouseY >= closeY && mouseY <= closeY + 20;
        context.fill(closeX - 2, closeY, closeX + 14, closeY + 20, closeHovered ? 0xFFE53935 : 0xFF2A2A30);
        context.drawCenteredTextWithShadow(this.textRenderer, "✕", closeX + 6, closeY + 6, 0xFFFFFFFF);

        // =========================================================
        // GREEN "MOD MENU" pill tab + category tabs
        // =========================================================
        int tabY = hY + headerH + 2;
        context.fill(panelX, tabY - 2, panelX + panelW, tabY + tabBarH, 0xD0101014);
        // Bottom border of tab bar
        context.fill(panelX, tabY + tabBarH - 1, panelX + panelW, tabY + tabBarH, 0xFF1A1A1E);

        // Green "MOD MENU" pill on the left
        int pillW = 70;
        int pillH = 16;
        int pillX = panelX + 8;
        int pillY = tabY + 1;
        context.fill(pillX, pillY, pillX + pillW, pillY + pillH, 0xFF2E7D32);
        context.drawCenteredTextWithShadow(this.textRenderer, "🪶 MOD MENU", pillX + pillW / 2, pillY + 4, 0xFFFFFFFF);

        // Category tabs: All, New, HUD, PvP
        int catTabX = pillX + pillW + 10;
        for (Category cat : Category.values()) {
            boolean active = cat == currentCategory;
            int tw = this.textRenderer.getWidth(cat.name) + 14;
            boolean hovered = mouseX >= catTabX && mouseX <= catTabX + tw && mouseY >= pillY && mouseY <= pillY + pillH;

            if (active) {
                context.fill(catTabX, pillY, catTabX + tw, pillY + pillH, 0xFF9C27B0);
            } else if (hovered) {
                context.fill(catTabX, pillY, catTabX + tw, pillY + pillH, 0xFF2A2A30);
            }
            context.drawCenteredTextWithShadow(this.textRenderer, cat.name, catTabX + tw / 2, pillY + 4, active ? 0xFFFFFFFF : 0xFFAAAAAA);
            catTabX += tw + 4;
        }

        // HUD Layout button (right side of tab bar)
        int hudBtnW = 70;
        int hudBtnX = panelX + panelW - hudBtnW - 8;
        boolean hudHovered = mouseX >= hudBtnX && mouseX <= hudBtnX + hudBtnW && mouseY >= pillY && mouseY <= pillY + pillH;
        context.fill(hudBtnX, pillY, hudBtnX + hudBtnW, pillY + pillH, hudHovered ? 0xFF333338 : 0xFF1E1E22);
        context.drawCenteredTextWithShadow(this.textRenderer, "📐 Layout", hudBtnX + hudBtnW / 2, pillY + 4, hudHovered ? 0xFFE1BEE7 : 0xFF888888);

        // =========================================================
        // CARD GRID (4 columns like reference, large cards)
        // =========================================================
        int cardAreaY = tabY + tabBarH + 4;
        int cardAreaH = panelH - headerH - tabBarH - 8;
        int cols = getColumns();
        int gap = 4;
        int cardW = (panelW - (cols + 1) * gap) / cols;
        int cardH = getCardHeight();

        context.enableScissor(panelX, cardAreaY, panelX + panelW, cardAreaY + cardAreaH);

        List<ModCard> filtered = getFilteredCards();
        int index = 0;
        for (ModCard card : filtered) {
            int col = index % cols;
            int row = index / cols;

            int cX = panelX + gap + col * (cardW + gap);
            int cY = cardAreaY + row * (cardH + gap) - (int) scrollY;

            if (cY + cardH >= cardAreaY && cY <= cardAreaY + cardAreaH) {
                boolean enabled = card.getter.getAsBoolean();
                boolean cardHovered = mouseX >= cX && mouseX <= cX + cardW && mouseY >= cY && mouseY <= cY + cardH;

                // Card background (dark rounded-look box)
                int cardBg = cardHovered ? 0xFF222228 : 0xFF18181C;
                context.fill(cX, cY, cX + cardW, cY + cardH, cardBg);
                context.drawBorder(cX, cY, cardW, cardH, 0xFF2A2A30);

                // "NEW" badge top-left (for NEW category cards)
                if (card.category == Category.NEW) {
                    context.fill(cX + 3, cY + 3, cX + 25, cY + 13, 0xFF2E7D32);
                    context.drawText(this.textRenderer, "NEW", cX + 5, cY + 4, 0xFFFFFFFF, false);
                }

                // Card name at top center
                String displayName = card.name;
                int nameW = this.textRenderer.getWidth(displayName);
                if (nameW > cardW - 8) {
                    // Truncate
                    while (this.textRenderer.getWidth(displayName + "..") > cardW - 8 && displayName.length() > 3) {
                        displayName = displayName.substring(0, displayName.length() - 1);
                    }
                    displayName = displayName + "..";
                }
                context.drawCenteredTextWithShadow(this.textRenderer, displayName, cX + cardW / 2, cY + 4, 0xFFDDDDDD);

                // Settings gear icon top-right (if configurable)
                if (card.onConfigure != null) {
                    boolean gearHovered = mouseX >= cX + cardW - 14 && mouseX <= cX + cardW - 2 && mouseY >= cY + 2 && mouseY <= cY + 14;
                    context.drawText(this.textRenderer, "⚙", cX + cardW - 13, cY + 4, gearHovered ? 0xFFE1BEE7 : 0xFF666666, false);
                }

                // Large centered icon
                int iconY = cY + 16;
                int iconAreaH = cardH - 34;
                context.drawCenteredTextWithShadow(this.textRenderer, card.icon, cX + cardW / 2, iconY + iconAreaH / 2 - 4, 0xFFCCCCCC);

                // Toggle button at bottom center
                int btnW = Math.min(60, cardW - 10);
                int btnH = 14;
                int btnX = cX + cardW / 2 - btnW / 2;
                int btnY = cY + cardH - btnH - 4;
                boolean btnHovered = mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH;

                if (enabled) {
                    int bg = btnHovered ? 0xFF388E3C : 0xFF2E7D32;
                    context.fill(btnX, btnY, btnX + btnW, btnY + btnH, bg);
                    context.drawCenteredTextWithShadow(this.textRenderer, "Enabled", btnX + btnW / 2, btnY + 3, 0xFFFFFFFF);
                } else {
                    int bg = btnHovered ? 0xFF333338 : 0xFF252528;
                    context.fill(btnX, btnY, btnX + btnW, btnY + btnH, bg);
                    context.drawCenteredTextWithShadow(this.textRenderer, "Disabled", btnX + btnW / 2, btnY + 3, 0xFF888888);
                }
            }
            index++;
        }

        context.disableScissor();

        // Scrollbar
        int rowsCount = (filtered.size() + cols - 1) / cols;
        int totalHeight = rowsCount * (cardH + gap);
        if (totalHeight > cardAreaH) {
            int scrollBarH = Math.max(15, (int) (((double) cardAreaH / totalHeight) * cardAreaH));
            int scrollBarY = cardAreaY + (int) ((scrollY / (totalHeight - cardAreaH)) * (cardAreaH - scrollBarH));
            context.fill(panelX + panelW - 4, scrollBarY, panelX + panelW - 1, scrollBarY + scrollBarH, 0xFF444448);
        }

        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int hY = panelY;

        // Close button
        int closeX = panelX + panelW - 18;
        int closeY = hY + 4;
        if (mouseX >= closeX - 2 && mouseX <= closeX + 14 && mouseY >= closeY && mouseY <= closeY + 20) {
            this.close();
            return true;
        }

        // Search field focus
        if (this.searchField != null) {
            boolean clicked = mouseX >= this.searchField.getX() - 3 && mouseX <= this.searchField.getX() + this.searchField.getWidth() + 3
                           && mouseY >= this.searchField.getY() - 2 && mouseY <= this.searchField.getY() + this.searchField.getHeight() + 2;
            this.searchField.setFocused(clicked);
            if (clicked && button == 1) {
                this.searchField.setText("");
            }
        }

        // Category tabs
        int tabY = hY + headerH + 2;
        int pillW = 70;
        int pillH = 16;
        int pillXPos = panelX + 8;
        int pillY = tabY + 1;

        int catTabX = pillXPos + pillW + 10;
        for (Category cat : Category.values()) {
            int tw = this.textRenderer.getWidth(cat.name) + 14;
            if (mouseX >= catTabX && mouseX <= catTabX + tw && mouseY >= pillY && mouseY <= pillY + pillH) {
                currentCategory = cat;
                scrollY = 0;
                return true;
            }
            catTabX += tw + 4;
        }

        // HUD Layout button
        int hudBtnW = 70;
        int hudBtnX = panelX + panelW - hudBtnW - 8;
        if (mouseX >= hudBtnX && mouseX <= hudBtnX + hudBtnW && mouseY >= pillY && mouseY <= pillY + pillH) {
            if (this.client != null) {
                this.client.setScreen(new net.featherpojav.client.gui.FeatherHudEditorScreen(this));
            }
            return true;
        }

        // Card interactions
        int cardAreaY = tabY + tabBarH + 4;
        int cardAreaH = panelH - headerH - tabBarH - 8;
        int cols = getColumns();
        int gap = 4;
        int cardW = (panelW - (cols + 1) * gap) / cols;
        int cardH = getCardHeight();

        List<ModCard> filtered = getFilteredCards();
        int index = 0;
        for (ModCard card : filtered) {
            int col = index % cols;
            int row = index / cols;

            int cX = panelX + gap + col * (cardW + gap);
            int cY = cardAreaY + row * (cardH + gap) - (int) scrollY;

            if (cY + cardH >= cardAreaY && cY <= cardAreaY + cardAreaH) {
                boolean cardClicked = mouseX >= cX && mouseX <= cX + cardW && mouseY >= cY && mouseY <= cY + cardH;

                // Right-click opens config
                if (cardClicked && button == 1 && card.onConfigure != null) {
                    card.onConfigure.run();
                    return true;
                }

                // Gear icon click
                if (card.onConfigure != null) {
                    if (mouseX >= cX + cardW - 14 && mouseX <= cX + cardW - 2 && mouseY >= cY + 2 && mouseY <= cY + 14) {
                        card.onConfigure.run();
                        return true;
                    }
                }

                // Toggle button click
                int btnW = Math.min(60, cardW - 10);
                int btnH = 14;
                int btnX = cX + cardW / 2 - btnW / 2;
                int btnY = cY + cardH - btnH - 4;

                if (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH) {
                    card.setter.accept(!card.getter.getAsBoolean());
                    FeatherConfig.save();
                    return true;
                }

                // Left click anywhere else on card also toggles
                if (cardClicked && button == 0) {
                    card.setter.accept(!card.getter.getAsBoolean());
                    FeatherConfig.save();
                    return true;
                }
            }
            index++;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // Do nothing - render game behind
    }
    
    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.searchField != null && this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
            scrollY = 0;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.searchField != null && this.searchField.charTyped(chr, modifiers)) {
            scrollY = 0;
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

}

// ==========================================
// Sub-Settings Configuration Screen: Auto Text
// ==========================================
class FeatherAutoTextScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget inputField;
    private boolean listening = false;
    private ButtonWidget bindButton;

    protected FeatherAutoTextScreen(Screen parent) {
        super(Text.of("Auto Text Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        inputField = new TextFieldWidget(this.textRenderer, cx - 100, cy - 35, 200, 20, Text.of("Edit Macro Command"));
        inputField.setMaxLength(128);
        inputField.setText(FeatherConfig.INSTANCE.autoTextCommand);
        this.addSelectableChild(inputField);

        bindButton = ButtonWidget.builder(Text.of(getKeyNameText()), button -> {
            listening = true;
            button.setMessage(Text.of("Press any key..."));
        }).dimensions(cx - 100, cy - 5, 200, 20).build();
        this.addDrawableChild(bindButton);

        this.addDrawableChild(ButtonWidget.builder(Text.of("Save & Back"), button -> {
            FeatherConfig.INSTANCE.autoTextCommand = inputField.getText();
            FeatherConfig.save();
            if (this.client != null) this.client.setScreen(parent);
        }).dimensions(cx - 50, cy + 25, 100, 20).build());
    }

    private String getKeyNameText() {
        return "Keybind: " + FeatherPojavModClient.autoTextKey.getBoundKeyTranslationKey().replace("key.keyboard.", "").toUpperCase();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listening) {
            listening = false;
            if (keyCode != GLFW.GLFW_KEY_ESCAPE) {
                FeatherPojavModClient.autoTextKey.setBoundKey(InputUtil.fromKeyCode(keyCode, scanCode));
                if (this.client != null && this.client.options != null) {
                    this.client.options.write();
                }
            }
            bindButton.setMessage(Text.of(getKeyNameText()));
            return true;
        }
        if (inputField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (inputField.charTyped(chr, modifiers)) {
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xD0141416);
        context.drawCenteredTextWithShadow(this.textRenderer, "AUTO TEXT MACRO CONFIG", this.width / 2, this.height / 2 - 65, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Edit the command macro and click the keybind button to customize.", this.width / 2, this.height / 2 - 50, 0xFFBA68C8);
        inputField.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(parent);
    }
}

// ==========================================
// Sub-Settings Configuration Screen: TimeChanger
// ==========================================
class FeatherTimeChangerScreen extends Screen {
    private final Screen parent;

    protected FeatherTimeChangerScreen(Screen parent) {
        super(Text.of("TimeChanger Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int leftX = this.width / 2 - 95;
        int startY = this.height / 2 - 35;

        this.addDrawableChild(ButtonWidget.builder(Text.of("Morning"), button -> setTime(0, 0)).dimensions(leftX, startY, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Day"), button -> setTime(6000, 1)).dimensions(leftX + 100, startY, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Sunset"), button -> setTime(12000, 2)).dimensions(leftX, startY + 25, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Night"), button -> setTime(18000, 3)).dimensions(leftX + 100, startY + 25, 90, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Back"), button -> {
            if (this.client != null) this.client.setScreen(parent);
        }).dimensions(this.width / 2 - 45, startY + 60, 90, 20).build());
    }

    private void setTime(long ticks, int mode) {
        FeatherConfig.INSTANCE.timeChangerTicks = ticks;
        FeatherConfig.INSTANCE.timeChangerMode = mode;
        FeatherConfig.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xD0141416);
        context.drawCenteredTextWithShadow(this.textRenderer, "TIMECHANGER PRESETS", this.width / 2, this.height / 2 - 60, 0xFFFFFFFF);
        
        String modeName = "Day";
        if (FeatherConfig.INSTANCE.timeChangerMode == 0) modeName = "Morning";
        else if (FeatherConfig.INSTANCE.timeChangerMode == 2) modeName = "Sunset";
        else if (FeatherConfig.INSTANCE.timeChangerMode == 3) modeName = "Night";
        context.drawCenteredTextWithShadow(this.textRenderer, "Active: " + modeName + " (" + FeatherConfig.INSTANCE.timeChangerTicks + " ticks)", this.width / 2, this.height / 2 - 48, 0xFFBA68C8);
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(parent);
    }
}

// ==========================================
// Sub-Settings Configuration Screen: Crosshair
// ==========================================
class FeatherCrosshairScreen extends Screen {
    private final Screen parent;

    protected FeatherCrosshairScreen(Screen parent) {
        super(Text.of("Crosshair Settings"));
        this.parent = parent;
    }

    private static final int[] COLORS = {0xFF00FF00, 0xFFFF0000, 0xFF00BFFF, 0xFFFFFF00, 0xFF00FFFF, 0xFFFF00FF, 0xFFFFFFFF, 0xFFFF8C00, 0xFFFF69B4};
    private static final String[] COLOR_NAMES = {"Green", "Red", "Blue", "Yellow", "Cyan", "Magenta", "White", "Orange", "Pink"};

    private String getPresetButtonText() {
        String[] presets = {"Cross", "Dot", "Circle", "Circle/Dot", "T-Shape", "X-Shape", "Square", "Chevron", "Tri-Bar", "Box/Dot"};
        int p = FeatherConfig.INSTANCE.crosshairPreset;
        if (p < 0 || p >= presets.length) p = 0;
        return "Style: " + presets[p];
    }

    private void cyclePreset(ButtonWidget button) {
        FeatherConfig.INSTANCE.crosshairPreset = (FeatherConfig.INSTANCE.crosshairPreset + 1) % 10;
        FeatherConfig.save();
        button.setMessage(Text.of(getPresetButtonText()));
    }

    private String getColorButtonText() {
        int color = FeatherConfig.INSTANCE.crosshairColor;
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i] == color) {
                return "Color: " + COLOR_NAMES[i];
            }
        }
        return "Color: Custom";
    }

    private void cycleColor(ButtonWidget button) {
        int color = FeatherConfig.INSTANCE.crosshairColor;
        int nextIndex = 0;
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i] == color) {
                nextIndex = (i + 1) % COLORS.length;
                break;
            }
        }
        FeatherConfig.INSTANCE.crosshairColor = COLORS[nextIndex];
        FeatherConfig.save();
        button.setMessage(Text.of(getColorButtonText()));
    }

    @Override
    protected void init() {
        int leftX = this.width / 2 - 95;
        int startY = this.height / 2 - 40;

        this.addDrawableChild(ButtonWidget.builder(Text.of("Size +"), button -> adjustSize(0.5f)).dimensions(leftX, startY, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Size -"), button -> adjustSize(-0.5f)).dimensions(leftX + 100, startY, 90, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Gap +"), button -> adjustGap(0.5f)).dimensions(leftX, startY + 25, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Gap -"), button -> adjustGap(-0.5f)).dimensions(leftX + 100, startY + 25, 90, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Thickness +"), button -> adjustTh(0.5f)).dimensions(leftX, startY + 50, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Thickness -"), button -> adjustTh(-0.5f)).dimensions(leftX + 100, startY + 50, 90, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of(getPresetButtonText()), button -> cyclePreset(button)).dimensions(leftX, startY + 75, 90, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of(getColorButtonText()), button -> cycleColor(button)).dimensions(leftX + 100, startY + 75, 90, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Back"), button -> {
            if (this.client != null) this.client.setScreen(parent);
        }).dimensions(this.width / 2 - 45, startY + 105, 90, 20).build());
    }

    private void adjustSize(float val) {
        FeatherConfig.INSTANCE.crosshairSize = Math.max(1.0f, FeatherConfig.INSTANCE.crosshairSize + val);
        FeatherConfig.save();
    }

    private void adjustGap(float val) {
        FeatherConfig.INSTANCE.crosshairGap = Math.max(0.0f, FeatherConfig.INSTANCE.crosshairGap + val);
        FeatherConfig.save();
    }

    private void adjustTh(float val) {
        FeatherConfig.INSTANCE.crosshairThickness = Math.max(0.5f, FeatherConfig.INSTANCE.crosshairThickness + val);
        FeatherConfig.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xD0141416);
        context.drawCenteredTextWithShadow(this.textRenderer, "CUSTOM CROSSHAIR CONFIGURATION", this.width / 2, this.height / 2 - 75, 0xFFFFFFFF);
        
        FeatherConfig cfg = FeatherConfig.INSTANCE;
        String desc = String.format("Size: %.1f | Gap: %.1f | Thickness: %.1f", cfg.crosshairSize, cfg.crosshairGap, cfg.crosshairThickness);
        context.drawCenteredTextWithShadow(this.textRenderer, desc, this.width / 2, this.height / 2 - 60, 0xFFBA68C8);
        
        int cx = this.width / 2;
        int cy = this.height / 2 - 100;
        float gap = cfg.crosshairGap;
        float size = cfg.crosshairSize;
        float th = cfg.crosshairThickness;
        int color = cfg.crosshairColor;
        int preset = cfg.crosshairPreset;

        int t = Math.max(1, (int) th);
        int h1 = t / 2;
        int h2 = t / 2 + (t % 2);

        switch (preset) {
            case 1:
                context.fill(cx - h1, cy - h1, cx + h2, cy + h2, color);
                break;
            case 2:
            case 3:
                int radius = (int)(gap + size);
                for (int angle = 0; angle < 360; angle += 10) {
                    double rad = Math.toRadians(angle);
                    int px = (int) Math.round(cx + Math.cos(rad) * radius);
                    int py = (int) Math.round(cy + Math.sin(rad) * radius);
                    context.fill(px - h1, py - h1, px + h2, py + h2, color);
                }
                if (preset == 3) {
                    context.fill(cx - h1, cy - h1, cx + h2, cy + h2, color);
                }
                break;
            case 4:
                context.fill((int)(cx - gap - size), cy - h1, (int)(cx - gap), cy + h2, color);
                context.fill((int)(cx + gap), cy - h1, (int)(cx + gap + size), cy + h2, color);
                context.fill(cx - h1, (int)(cy + gap), cx + h2, (int)(cy + gap + size), color);
                break;
            case 5:
                for (int idx = 0; idx < (int) size; idx++) {
                    int f = (int)(gap + idx);
                    context.fill(cx - f - h1, cy - f - h1, cx - f + h2, cy - f + h2, color);
                    context.fill(cx + f - h1, cy - f - h1, cx + f + h2, cy - f + h2, color);
                    context.fill(cx - f - h1, cy + f - h1, cx - f + h2, cy + f + h2, color);
                    context.fill(cx + f - h1, cy + f - h1, cx + f + h2, cy + f + h2, color);
                }
                break;
            case 6:
            case 9:
                int r = (int)(gap + size);
                context.fill(cx - r, cy - r - h1, cx + r, cy - r + h2, color);
                context.fill(cx - r, cy + r - h1, cx + r, cy + r + h2, color);
                context.fill(cx - r - h1, cy - r, cx - r + h2, cy + r, color);
                context.fill(cx + r - h1, cy - r, cx + r + h2, cy + r, color);
                if (preset == 9) {
                    context.fill(cx - h1, cy - h1, cx + h2, cy + h2, color);
                }
                break;
            case 7:
                for (int idx = 0; idx < (int) size; idx++) {
                    context.fill(cx - idx - h1, (int)(cy - gap + idx) - h1, cx - idx + h2, (int)(cy - gap + idx) + h2, color);
                    context.fill(cx + idx - h1, (int)(cy - gap + idx) - h1, cx + idx + h2, (int)(cy - gap + idx) + h2, color);
                }
                break;
            case 8:
                context.fill((int)(cx - gap - size), cy - h1, (int)(cx - gap), cy + h2, color);
                context.fill((int)(cx + gap), cy - h1, (int)(cx + gap + size), cy + h2, color);
                context.fill(cx - h1, (int)(cy - gap - size), cx + h2, (int)(cy - gap), color);
                break;
            default:
                context.fill((int)(cx - gap - size), cy - h1, (int)(cx - gap), cy + h2, color);
                context.fill((int)(cx + gap), cy - h1, (int)(cx + gap + size), cy + h2, color);
                context.fill(cx - h1, (int)(cy - gap - size), cx + h2, (int)(cy - gap), color);
                context.fill(cx - h1, (int)(cy + gap), cx + h2, (int)(cy + gap + size), color);
                break;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(parent);
    }
}
