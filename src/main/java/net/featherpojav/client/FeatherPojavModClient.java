package net.featherpojav.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.featherpojav.client.config.FeatherConfig;
import net.featherpojav.client.gui.FeatherSettingsScreen;
import net.featherpojav.client.hud.FeatherHudRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FeatherPojavModClient implements ClientModInitializer {
    public static KeyBinding openSettingsKey;
    public static KeyBinding openHudEditorKey;
    public static KeyBinding zoomKey;
    public static KeyBinding freelookKey;

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

        // Register HUD Render Callback
        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            FeatherHudRenderer.renderHUD(context, tickCounter.getTickDelta(false));
        });

        // Client tick event handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Handle Menu keybind
            if (openSettingsKey.wasPressed()) {
                client.setScreen(new FeatherSettingsScreen(null));
            }

            // Handle HUD Editor keybind
            if (openHudEditorKey.wasPressed()) {
                client.setScreen(new net.featherpojav.client.gui.FeatherHudEditorScreen(null));
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
                    client.options.getGamma().setValue(15.0);
                } else {
                    if (client.options.getGamma().getValue() > 1.0) {
                        client.options.getGamma().setValue(1.0);
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
