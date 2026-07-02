package net.featherpojav.client;

import java.io.File;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.featherpojav.client.config.FeatherConfig;
import net.featherpojav.client.gui.FeatherSettingsScreen;
import net.featherpojav.client.hud.FeatherHudRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FeatherPojavModClient implements ClientModInitializer {
    public static KeyBinding openSettingsKey;
    public static KeyBinding openHudEditorKey;
    public static KeyBinding zoomKey;
    public static KeyBinding freelookKey;
    public static KeyBinding stopwatchKey;
    public static double originalGamma = -1.0;

    // Zoom state
    public static boolean isZooming = false;
    public static float zoomLevel = 1.0f;
    public static float targetZoomLevel = 1.0f;

    // Freelook state
    public static boolean isFreelooking = false;
    public static float freelookYaw = 0.0f;
    public static float freelookPitch = 0.0f;

    @Override
    public void onInitializeClient() {
        // Load configurations
        FeatherConfig.load();

        // Cull Logs on Startup
        if (FeatherConfig.INSTANCE.cullLogs) {
            try {
                File logsDir = new File(MinecraftClient.getInstance().runDirectory, "logs");
                if (logsDir.exists() && logsDir.isDirectory()) {
                    File[] files = logsDir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.getName().endsWith(".log") || file.getName().endsWith(".log.gz")) {
                                file.delete();
                            }
                        }
                    }
                }
            } catch (Exception ignored) {}
        }

        // Keybindings Registration
        openSettingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.featherpojav.settings",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.featherpojav"
        ));

        openHudEditorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.featherpojav.hud_editor",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                "category.featherpojav"
        ));

        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.featherpojav.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.featherpojav"
        ));

        freelookKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.featherpojav.freelook",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.featherpojav"
        ));

        stopwatchKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.featherpojav.stopwatch",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.featherpojav"
        ));

        // Register HUD Render Callback
        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            FeatherHudRenderer.renderHUD(context, tickCounter.getTickDelta(false));
        });

        // Register Attack Entity Callback (for Combo & Reach tracker)
        net.fabricmc.fabric.api.event.player.AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient() && player == MinecraftClient.getInstance().player) {
                FeatherHudRenderer.onHit();
                FeatherHudRenderer.onAttackEntity(entity);
            }
            return net.minecraft.util.ActionResult.PASS;
        });

        // Client tick event handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Handle Hitbox outlines toggle in real-time
            if (client.getEntityRenderDispatcher() != null) {
                client.getEntityRenderDispatcher().setRenderHitboxes(FeatherConfig.INSTANCE.hitbox);
            }

            // Handle Menu keybind
            if (openSettingsKey.wasPressed()) {
                client.setScreen(new FeatherSettingsScreen(null));
            }

            // Handle HUD Editor keybind
            if (openHudEditorKey.wasPressed()) {
                client.setScreen(new net.featherpojav.client.gui.FeatherHudEditorScreen(null));
            }

            // Handle Stopwatch keybind
            if (stopwatchKey.wasPressed() && FeatherConfig.INSTANCE.stopwatch) {
                FeatherHudRenderer.toggleStopwatch();
            }

            // Handle Zoom status
            if (FeatherConfig.INSTANCE.zoom) {
                isZooming = zoomKey.isPressed();
            } else {
                isZooming = false;
            }

            // Handle Freelook status
            if (FeatherConfig.INSTANCE.freelook) {
                if (freelookKey.isPressed()) {
                    if (!isFreelooking) {
                        isFreelooking = true;
                        freelookYaw = client.player.getYaw();
                        freelookPitch = client.player.getPitch();
                    }
                } else {
                    isFreelooking = false;
                }
            } else {
                isFreelooking = false;
            }

            // Handle Fullbright status (gamma option override)
            if (client.options != null && client.options.getGamma() != null) {
                if (FeatherConfig.INSTANCE.fullbright) {
                    if (originalGamma == -1.0) {
                        originalGamma = client.options.getGamma().getValue();
                    }
                    client.options.getGamma().setValue(15.0);
                } else {
                    if (originalGamma != -1.0) {
                        client.options.getGamma().setValue(originalGamma);
                        originalGamma = -1.0;
                    }
                }
            }
        });

        // Register client command /feather for mobile players on PojavLauncher
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("feather")
                .executes(context -> {
                    context.getSource().getClient().execute(() -> {
                        context.getSource().getClient().setScreen(new FeatherSettingsScreen(null));
                    });
                    context.getSource().sendFeedback(Text.of("Opening Feather Settings..."));
                    return 1;
                })
            );
        });
    }
}
