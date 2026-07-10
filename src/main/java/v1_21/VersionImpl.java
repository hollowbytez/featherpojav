/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  com.cosmeticsmod.morecosmetics.nametags.NametagHandler
 *  com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.VersionAdapter
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.util.Util
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.session.Session
 *  net.minecraft.sound.SoundEvent
 *  net.minecraft.sound.SoundEvents
 *  net.minecraft.sound.SoundCategory
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.network.PlayerListEntry
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  org.lwjgl.glfw.GLFW
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.VersionImpl
 *  v1_21.morecosmetics.compatibility.CompatibilityCheck
 *  v1_21.morecosmetics.compatibility.java.NativeFileChooser
 *  v1_21.morecosmetics.gui.NotificationManager
 *  v1_21.morecosmetics.gui.builder.BoxElementContainer
 *  v1_21.morecosmetics.gui.builder.ListElementContainer
 *  v1_21.morecosmetics.gui.screen.ConfirmUI
 *  v1_21.morecosmetics.gui.screen.EditorUI
 *  v1_21.morecosmetics.gui.screen.MainUI
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 *  v1_21.morecosmetics.models.ModelCosmeticLoader
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.nametags.NametagRenderer
 */
package v1_21.morecosmetics;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.models.ModelHandler;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import com.cosmeticsmod.morecosmetics.nametags.NametagHandler;
import com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.VersionAdapter;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.lwjgl.glfw.GLFW;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.compatibility.CompatibilityCheck;
import v1_21.morecosmetics.compatibility.java.NativeFileChooser;
import v1_21.morecosmetics.gui.NotificationManager;
import v1_21.morecosmetics.gui.builder.BoxElementContainer;
import v1_21.morecosmetics.gui.builder.ListElementContainer;
import v1_21.morecosmetics.gui.screen.ConfirmUI;
import v1_21.morecosmetics.gui.screen.EditorUI;
import v1_21.morecosmetics.gui.screen.MainUI;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;
import v1_21.morecosmetics.gui.screen.UIScreen;
import v1_21.morecosmetics.models.ModelCosmeticLoader;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.nametags.NametagRenderer;

@Environment(value=EnvType.CLIENT)
public class VersionImpl
implements VersionAdapter {
    public void updateTick(int tick) {
        MinecraftClient mc = MinecraftClient.getInstance();
        NettyClient client = MoreCosmetics.getInstance().getConnection();
        if (tick % 200 == 0 && client.isConnected() && mc.player != null) {
            Collection<PlayerListEntry> players = mc.getNetworkHandler().getPlayerList();
            ArrayList<Long> transmit = new ArrayList<Long>();
            for (PlayerListEntry playerEntry : players) {
                if (transmit.size() >= 300) break;
                Long key = playerEntry.getProfile().getId().getMostSignificantBits();
                if (client.getIndicated().containsKey(key)) continue;
                client.getIndicated().put(key, null);
                transmit.add(key);
            }
            if (!transmit.isEmpty()) {
                client.sendIndication(transmit.toArray(new Long[transmit.size()]));
            }
        }
    }

    public void bindTexture(int texture) {
        RenderSystem.bindTexture((int)texture);
    }

    public void renderPreview(Object stack, int x, int y, int mouseX, int mouseY, int size, int rotation) {
        if (MinecraftClient.getInstance().player != null) {
            DrawUtils.drawEntityOnScreen((int)x, (int)y, (int)size, (float)mouseX, (float)mouseY, (int)rotation, (int)0, (int)0, (LivingEntity)MinecraftClient.getInstance().player);
        }
    }

    public void showGuiScreen() {
        ScreenWrapper editorScreen = v1_21.morecosmetics.gui.screen.EditorUI.getScreen();
        net.minecraft.client.gui.screen.Screen parent = editorScreen.getParentScreen();
        if (parent != null) {
            MinecraftClient.getInstance().setScreen(parent);
            editorScreen.setParent(null);
        } else {
            MainUI.displayUI();
        }
    }

    public void showEditorScreen(CosmeticModel model) {
        EditorUI.displayUI((CosmeticModel)model);
    }

    public void setGuiScale(float scale) {
        MainUI.getScreen().setGuiScale(scale);
    }

    public void playButtonSound() {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.world.playSound(null, mc.player.getBlockPos(), (SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 1.0f, 1.0f);
    }

    public void setCustomFontRenderer(CustomFontRenderer renderer) {
        DrawUtils.setCustomFontRenderer((CustomFontRenderer)renderer);
    }

    public boolean isOnScreen(String name) {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        return screen != null && screen.getClass().getSimpleName().equals(name);
    }

    public boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton((long)MinecraftClient.getInstance().getWindow().getHandle(), (int)button) == 1;
    }

    public boolean isKeyDown(int key) {
        return GLFW.glfwGetKey((long)MinecraftClient.getInstance().getWindow().getHandle(), (int)key) == 1;
    }

    public int getMouseDWheel() {
        return SharedVars.SCROLL_AMOUNT;
    }

    public int getMinecraftGuiScale() {
        int guiScale = (Integer)MinecraftClient.getInstance().options.getGuiScale().getValue();
        return guiScale == 0 ? 0 : 4 - guiScale;
    }

    public UUID getUuid(boolean ingame) {
        UUID uuid = null;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (ingame && mc.player != null) {
            uuid = mc.player.getUuid();
        } else if (mc.getSession() != null && mc.getSession().getUuidOrNull() != null) {
            uuid = mc.getSession().getUuidOrNull();
        }
        return uuid != null ? uuid : UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public String getPlayerName() {
        return MinecraftClient.getInstance().getSession().getUsername();
    }

    public boolean isInGame() {
        return MinecraftClient.getInstance().world != null;
    }

    public boolean authenticate(String serverId) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Session session = mc.getSession();
        if (session == null) {
            return false;
        }
        try {
            mc.getSessionService().joinServer(session.getUuidOrNull(), session.getAccessToken(), serverId);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public NametagHandler getNametagHandler() {
        return new NametagRenderer();
    }

    public ModelLoader getModelLoader() {
        return new ModelCosmeticLoader();
    }

    public ModelHandler getModelHandler() {
        return new ModelCosmeticRenderer();
    }

    public NotificationHandler getNotificationHandler() {
        return new NotificationManager();
    }

    public ListElementBuilder getListElementBuilder() {
        return new ListElementContainer();
    }

    public BoxElementBuilder getBoxElementBuilder() {
        return new BoxElementContainer();
    }

    public UUID[] getPlayersInWorld() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world != null) {
            List players = mc.world.getPlayers();
            UUID[] uuids = new UUID[players.size()];
            for (int i = 0; i < uuids.length; ++i) {
                uuids[i] = ((AbstractClientPlayerEntity)players.get(i)).getUuid();
            }
            return uuids;
        }
        return new UUID[0];
    }

    public boolean isCurrentScreenNull() {
        return MinecraftClient.getInstance().currentScreen == null;
    }

    public void showConfirmDialog(String title, String msg, Consumer<Boolean> callback) {
        ScreenWrapper.displayOverlay((UIScreen)new ConfirmUI(title, msg, callback));
    }

    public void openFileChooser(String title, File path, Consumer<File> callback, String filterDescription, String ... extensions) {
        NativeFileChooser.openNativeFileChooser((String)title, (File)path, callback, (String)filterDescription, (String[])extensions);
    }

    public void openBrowser(String url) {
        Util.getOperatingSystem().open(url);
    }

    public void openFile(File file) {
        Util.getOperatingSystem().open(file);
    }

    public void copyToClipboard(String content) {
        MinecraftClient.getInstance().keyboard.setClipboard(content);
    }

    public RenderStack getRenderStack() {
        return StackHolder.getInstance();
    }

    public void checkCompatiblity() {
        CompatibilityCheck.check();
    }
}

