/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.editor.LocalServer
 *  com.cosmeticsmod.morecosmetics.nametags.NametagHandler
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontHandler
 *  com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.InfoPacketHandler
 *  com.cosmeticsmod.morecosmetics.networking.NettyClient
 *  com.cosmeticsmod.morecosmetics.pachtes.PatchLoader
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.GeckoBridge
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.ModInfo
 *  com.cosmeticsmod.morecosmetics.utils.OpenMode
 *  com.cosmeticsmod.morecosmetics.utils.RainbowHandler
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.Updater
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.cosmeticsmod.morecosmetics.utils.VersionAdapter
 *  com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole
 *  com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.cosmeticsmod.morecosmetics;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder;
import com.cosmeticsmod.morecosmetics.models.ModelHandler;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.editor.LocalServer;
import com.cosmeticsmod.morecosmetics.nametags.NametagHandler;
import com.cosmeticsmod.morecosmetics.nametags.font.FontHandler;
import com.cosmeticsmod.morecosmetics.networking.ClientPacketHandler;
import com.cosmeticsmod.morecosmetics.networking.InfoPacketHandler;
import com.cosmeticsmod.morecosmetics.networking.NettyClient;
import com.cosmeticsmod.morecosmetics.pachtes.PatchLoader;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.GeckoBridge;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.ModInfo;
import com.cosmeticsmod.morecosmetics.utils.OpenMode;
import com.cosmeticsmod.morecosmetics.utils.RainbowHandler;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.Updater;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.cosmeticsmod.morecosmetics.utils.VersionAdapter;
import com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole;
import com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exception performing whole class analysis ignored.
 */
public class MoreCosmetics {
    public static final int VERSION = 1201;
    public static final String VERSION_STR = "1.2";
    public static final String USER_AGENT = "MoreCosmetics-1.2";
    public static final String INFO_URL = "http://dl.cosmeticsmod.com/morecosmetics/mod.json";
    public static final String BACKUP_URL = "http://dl.cosmeticsmod.de/morecosmetics/mod.json";
    public static final Gson GSON = new Gson();
    public static final JsonParser PARSER = new JsonParser();
    public static final Random RANDOM = new Random();
    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    public static DateFormat DATE_FORMAT = DateFormat.getDateInstance(2, Locale.getDefault());
    public static final Logger LOGGER = LogManager.getLogger((String)"MoreCosmetics");
    public static final File ROOT_DIR = new File("MoreCosmetics");
    public static final File DATA_DIR = new File("MoreCosmetics/data");
    public static final File CONFIG_DIR = new File("MoreCosmetics/data/config");
    public static final int PORT = 6970;
    private static MoreCosmetics instance;
    private ModelLoader modelLoader;
    private VersionAdapter versionAdapter;
    private NametagHandler nametagHandler;
    private NotificationHandler notificationHandler;
    private ListElementBuilder listElementBuilder;
    private BoxElementBuilder boxElementBuilder;
    private FontHandler fontHandler;
    private ModelHandler modelHandler;
    private UserHandler userHandler;
    private NettyClient connection;
    private boolean postInit;
    private boolean openGui;
    private ModInfo info;
    private final ArrayList<ITickListener> tickListeners = new ArrayList();
    private final ArrayList<Runnable> delayedTasks = new ArrayList();

    private MoreCosmetics() {
    }

    private void init(int clientId) {
        Utils.removeLookups();
        CompatibilityManager.detect();
        MoreCosmetics.log((String)"MoreCosmetics v1.2 (1201) loading...");
        MoreCosmetics.log((String)("Version = " + CompatibilityManager.VERSION));
        MoreCosmetics.log((String)("Installation = " + CompatibilityManager.INSTALLATION));
        MoreCosmetics.log((String)("Platform = " + CompatibilityManager.PLATFORM));
        try {
            this.fontHandler = new FontHandler();
            EXECUTOR.execute(() -> this.downloadInfo());
            this.fontHandler.init();
            ModConfig config = ModConfig.loadConfig();
            this.initConfig(config);
            GeckoBridge.init();
            this.versionAdapter = this.initVersionHandler();
            this.registerTickListener((ITickListener)this.versionAdapter);
            UUID uuid = this.versionAdapter.getUuid(false);
            this.userHandler = new UserHandler(uuid);
            this.registerTickListener((ITickListener)this.userHandler);
            this.registerTickListener((ITickListener)new RainbowHandler());
            this.nametagHandler = this.versionAdapter.getNametagHandler();
            this.modelLoader = this.versionAdapter.getModelLoader();
            this.modelHandler = this.versionAdapter.getModelHandler();
            this.notificationHandler = this.versionAdapter.getNotificationHandler();
            this.listElementBuilder = this.versionAdapter.getListElementBuilder();
            this.boxElementBuilder = this.versionAdapter.getBoxElementBuilder();
            this.registerTickListener((ITickListener)this.fontHandler);
            if (!CompatibilityManager.isOnForge()) {
                CompatibilityManager.check();
            }
            PatchLoader patchLoader = new PatchLoader();
            this.registerTickListener((ITickListener)patchLoader);
            EXECUTOR.execute(() -> ((PatchLoader)patchLoader).load());
            if (uuid.version() == 4) {
                String name = this.versionAdapter.getPlayerName();
                this.connection = new NettyClient("server.cosmeticsmod.com", 6970, name, uuid, 1201, clientId, true);
                this.connection.setPacketHandler((ClientPacketHandler)new InfoPacketHandler());
                this.registerTickListener((ITickListener)this.connection);
            } else {
                this.connection = new NettyClient();
                SharedVars.OFFLINE_MODE = true;
                MoreCosmetics.log((String)"Playing in offline mode!");
            }
            config.runConfigCheck();
            MoreCosmetics.log((String)("Open Mode = " + config.openMode + " | Key = " + config.key));
            this.fontHandler.loadResourceFonts();
            this.fontHandler.setCustomFont(config.customFont);
            MoreCosmetics.log((String)"Mod loaded successfully!");
        }
        catch (Exception e) {
            MoreCosmetics.log((String)("An exception occurred on init: " + e));
            MoreCosmetics.catchThrowable((Throwable)e);
        }
        catch (Error e) {
            MoreCosmetics.log((String)("An error occurred on init: " + e));
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    private void initConfig(ModConfig config) {
        if (config.consoleOnStartUp) {
            DebugConsole.open();
        }
        if (config.editorMode) {
            LocalServer.toggle((boolean)true);
        }
    }

    private void downloadInfo() {
        JsonObject versions;
        String json = Utils.readURL((String)"http://dl.cosmeticsmod.com/morecosmetics/mod.json", (String)"http://dl.cosmeticsmod.de/morecosmetics/mod.json");
        if (json == null) {
            return;
        }
        JsonObject jsonInfo = PARSER.parse(json).getAsJsonObject();
        this.info = (ModInfo)GSON.fromJson((JsonElement)jsonInfo, ModInfo.class);
        String cPlatform = CompatibilityManager.PLATFORM;
        String cVersion = CompatibilityManager.VERSION;
        if (jsonInfo.has("versions") && (versions = jsonInfo.get("versions").getAsJsonObject()).has(cPlatform) && versions.get(cPlatform).getAsJsonObject().has(cVersion)) {
            this.info.version = versions.get(cPlatform).getAsJsonObject().get(cVersion).getAsInt();
        }
        if (this.info.version > 1201) {
            MoreCosmetics.log((String)("Update found: " + this.info.version));
            JsonObject downloads = jsonInfo.get("downloads").getAsJsonObject();
            String dlURL = downloads.get(cPlatform).getAsJsonObject().get(cVersion).getAsString();
            Runtime.getRuntime().addShutdownHook((Thread)new Updater(dlURL));
        }
        if (this.info.trustedUrls != null) {
            TextureCategoryBuilder.addTrustedUrl((String[])this.info.trustedUrls);
        }
        if (this.info.allowedUrls != null) {
            TextureCategoryBuilder.addAllowedUrl((String[])this.info.allowedUrls);
        }
        this.fontHandler.setFontDownloads(this.info.fonts);
        this.fontHandler.downloadFonts();
    }

    private VersionAdapter initVersionHandler() {
        try {
            String version = CompatibilityManager.VERSION.replace(".", "_");
            return (VersionAdapter)Class.forName("v" + version + ".morecosmetics.VersionImpl").newInstance();
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return null;
        }
    }

    protected void onTick() {
        try {
            if (!this.postInit) {
                this.postInit = true;
                if (!CompatibilityManager.isOnFabric()) {
                    this.modelHandler.registerLayer();
                }
            }
            for (ITickListener listener : this.tickListeners) {
                listener.updateTick(SharedVars.TICKS);
            }
            ++SharedVars.TICKS;
            if (this.openGui || this.versionAdapter.isCurrentScreenNull() && this.versionAdapter.isKeyDown(ModConfig.getConfig().key) && !OpenMode.openOnlyOn((OpenMode)OpenMode.BUTTON)) {
                this.openGui = false;
                if (this.userHandler.isLoaded()) {
                    this.versionAdapter.showGuiScreen();
                } else {
                    NotificationHandler.sendError((String)LanguageHandler.get((String)"loading"));
                }
            }
            if (!this.delayedTasks.isEmpty()) {
                this.delayedTasks.forEach(Runnable::run);
                this.delayedTasks.clear();
            }
            if (ModConfig.getConfig().accentColor == 1) {
                UIConstants.UI_ACCENT_COLOR = RainbowHandler.RAINBOW_VALUE;
            }
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    @Deprecated
    public void openGui() {
        this.openGui = true;
    }

    public void openUI(boolean queued) {
        if (queued) {
            this.openGui = true;
        } else if (this.versionAdapter.isInGame()) {
            this.versionAdapter.showGuiScreen();
        }
    }

    public void registerTickListener(ITickListener listener) {
        this.tickListeners.add(listener);
    }

    public void runDelayed(Runnable task) {
        this.delayedTasks.add(task);
    }

    public void runAsync(Runnable task) {
        EXECUTOR.execute(task);
    }

    public ModelLoader getModelLoader() {
        return this.modelLoader;
    }

    public NotificationHandler getNotificationHandler() {
        return this.notificationHandler;
    }

    public static MoreCosmetics initId(int clientId) {
        if (instance == null) {
            instance = new MoreCosmetics();
            instance.init(clientId);
        } else {
            instance.getConnection().registerClientId(clientId);
        }
        return instance;
    }

    public static MoreCosmetics getInstance() {
        if (instance == null) {
            instance = new MoreCosmetics();
            instance.init(1);
        }
        return instance;
    }

    public static void debug(String msg) {
        DebugConsole.print((String)msg, (EnumDebugState)EnumDebugState.DEBUG);
    }

    public static void debugThrowable(Throwable throwable) {
        DebugConsole.print((String)("[Debug]: " + Utils.throwableToString((Throwable)throwable)), (EnumDebugState)EnumDebugState.ERROR);
    }

    public static void log(String msg) {
        LOGGER.info("[MoreCosmetics] " + msg);
        DebugConsole.print((String)msg, (EnumDebugState)EnumDebugState.LOG);
    }

    public static void catchThrowable(Throwable throwable) {
        LOGGER.catching(throwable);
        DebugConsole.print((String)Utils.throwableToString((Throwable)throwable), (EnumDebugState)EnumDebugState.ERROR);
    }

    public VersionAdapter getVersionAdapter() {
        return this.versionAdapter;
    }

    public NametagHandler getNametagHandler() {
        return this.nametagHandler;
    }

    public ListElementBuilder getListElementBuilder() {
        return this.listElementBuilder;
    }

    public BoxElementBuilder getBoxElementBuilder() {
        return this.boxElementBuilder;
    }

    public FontHandler getFontHandler() {
        return this.fontHandler;
    }

    public UserHandler getUserHandler() {
        return this.userHandler;
    }

    public ModelHandler getModelHandler() {
        return this.modelHandler;
    }

    public NettyClient getConnection() {
        return this.connection;
    }

    public ModInfo getInfo() {
        return this.info;
    }
}

