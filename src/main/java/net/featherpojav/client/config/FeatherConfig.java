package net.featherpojav.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FeatherConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "featherpojav.json");
    
    public static FeatherConfig INSTANCE = new FeatherConfig();

    // General Client Mod Toggles
    public boolean toggleSprint = true;
    public boolean keystrokes = true;
    public boolean armorHUD = true;
    public boolean potionHUD = true;
    public boolean directionHUD = true;
    public boolean coordHUD = true;
    public boolean fpsHUD = true;
    public boolean fullbright = false;
    public boolean zoom = true;
    public boolean freelook = true;
    public boolean customCrosshair = false;
    public boolean timeChanger = false;
    public boolean itemPhysics = true;
    public boolean autoGG = true;
    
    // HUD Settings (Coordinates and Styles)
    public int keystrokesX = 10;
    public int keystrokesY = 100;
    
    public int armorHUDX = 10;
    public int armorHUDY = 240;
    public boolean armorHUDVertical = true;
    
    public int potionHUDX = 10;
    public int potionHUDY = 320;
    
    public int directionHUDX = 200;
    public int directionHUDY = 10;
    
    public int coordHUDX = 10;
    public int coordHUDY = 10;
    
    public int fpsHUDX = 10;
    public int fpsHUDY = 50;
    
    // Module Customizations
    public int crosshairColor = 0xFF00FF00; // Green
    public float crosshairSize = 5.0f;
    public float crosshairThickness = 1.0f;
    public float crosshairGap = 2.0f;
    
    public long timeChangerTicks = 6000; // Day
    public float particleMultiplier = 1.0f;
    
    // Style settings
    public int themeColor = 0xFF9C27B0; // Feather Purple Accent
    public int backgroundColor = 0x80000000; // Dark overlay translucent
    public int textColor = 0xFFFFFFFF;
    
    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, FeatherConfig.class);
                if (INSTANCE == null) {
                    INSTANCE = new FeatherConfig();
                }
            } catch (Exception e) {
                System.err.println("[FeatherPojav] Failed to load config: " + e.getMessage());
                INSTANCE = new FeatherConfig();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            System.err.println("[FeatherPojav] Failed to save config: " + e.getMessage());
        }
    }
}
