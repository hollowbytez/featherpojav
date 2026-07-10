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
 *  com.cosmeticsmod.morecosmetics.utils.FileChooser
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.VersionAdapter
 */
package com.cosmeticsmod.morecosmetics.utils;

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
import com.cosmeticsmod.morecosmetics.utils.FileChooser;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.function.Consumer;

public interface VersionAdapter
extends ITickListener {
    public void bindTexture(int var1);

    public void renderPreview(Object var1, int var2, int var3, int var4, int var5, int var6, int var7);

    public void showGuiScreen();

    public void showEditorScreen(CosmeticModel var1);

    public void setGuiScale(float var1);

    public void playButtonSound();

    public void setCustomFontRenderer(CustomFontRenderer var1);

    public boolean isOnScreen(String var1);

    public boolean isMouseButtonDown(int var1);

    public int getMouseDWheel();

    public int getMinecraftGuiScale();

    public UUID getUuid(boolean var1);

    public String getPlayerName();

    public boolean isInGame();

    public boolean authenticate(String var1);

    public NametagHandler getNametagHandler();

    public ModelLoader getModelLoader();

    public ModelHandler getModelHandler();

    public NotificationHandler getNotificationHandler();

    public ListElementBuilder getListElementBuilder();

    public BoxElementBuilder getBoxElementBuilder();

    public UUID[] getPlayersInWorld();

    public boolean isCurrentScreenNull();

    default public void showConfirmDialog(String title, String msg, Runnable callback) {
        this.showConfirmDialog(title, msg, b -> {
            if (b.booleanValue()) {
                callback.run();
            }
        });
    }

    public void showConfirmDialog(String var1, String var2, Consumer<Boolean> var3);

    public RenderStack getRenderStack();

    public boolean isKeyDown(int var1);

    default public void openFileChooser(String title, File path, Consumer<File> callback, String filterDescription, String ... extensions) {
        FileChooser.openFileDialog((String)title, (File)path, callback, (String)filterDescription, (String[])extensions);
    }

    default public void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    default public void openBrowser(String url) {
        if (Desktop.isDesktopSupported() && url != null) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            }
            catch (IOException | URISyntaxException e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
    }

    default public void copyToClipboard(String content) {
        StringSelection selection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    default public void checkCompatiblity() {
    }
}

