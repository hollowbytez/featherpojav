/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.models.config.ColorCodeDeserializer
 *  com.cosmeticsmod.morecosmetics.models.config.ConfigAccessor
 *  com.cosmeticsmod.morecosmetics.models.config.DataAdapter
 *  com.cosmeticsmod.morecosmetics.models.config.ModelConfig
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.nametags.Nametag
 *  com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo
 *  com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.user.UserUpdateCallback
 *  com.cosmeticsmod.morecosmetics.user.cloaks.Cloak
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.cosmeticsmod.morecosmetics.utils.VersionAdapter
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 */
package com.cosmeticsmod.morecosmetics.user;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.misc.PopupFetcher;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder;
import com.cosmeticsmod.morecosmetics.models.config.ColorCodeDeserializer;
import com.cosmeticsmod.morecosmetics.models.config.ConfigAccessor;
import com.cosmeticsmod.morecosmetics.models.config.DataAdapter;
import com.cosmeticsmod.morecosmetics.models.config.ModelConfig;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.config.SettingOverlay;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.nametags.Nametag;
import com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo;
import com.cosmeticsmod.morecosmetics.networking.handler.PacketBuf;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketComplete;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketCosmeticUpdate;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.user.UserUpdateCallback;
import com.cosmeticsmod.morecosmetics.user.cloaks.Cloak;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.cosmeticsmod.morecosmetics.utils.VersionAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class UserHandler
implements ITickListener {
    public static final Integer NAMETAG_ID = -1;
    public static final Gson DATA_GSON = new GsonBuilder().registerTypeAdapter(ModelData.class, (Object)new DataAdapter()).registerTypeAdapter(String.class, (Object)new ColorCodeDeserializer()).create();
    private HashMap<UUID, CosmeticUser> users = new HashMap();
    private HashMap<CosmeticModel, ModelData> localData = new HashMap();
    private HashSet<Long> whitelist = new HashSet();
    private HashMap<Integer, Long> onlineCosmetics = new HashMap();
    private HashMap<Integer, ModelData> currentOnlineData = new HashMap();
    private List<Integer> viewedPopups = new ArrayList();
    private List<UserUpdateCallback> userUpdateCallbacks = new ArrayList();
    private MoreCosmetics mod;
    private CosmeticUser currentUser;
    private final Cloak offlineCloak = new Cloak();
    private final Cloak onlineCloak = new Cloak();
    private File userData;
    private String loginKey;
    private String textureToken;
    private int loginStreak;
    private long firstJoin;
    private Set<Integer> offlineStorage = new HashSet();
    private boolean onlineMode;
    private boolean loaded;
    private boolean settingsChanged;

    public UserHandler(UUID uuid) {
        this.mod = MoreCosmetics.getInstance();
        this.onlineMode = false;
        this.initUser(uuid);
        this.loadWhitelist(null);
    }

    private void loadUserConfig() {
        try {
            this.userData = new File(MoreCosmetics.CONFIG_DIR, this.currentUser.getUuid().toString() + ".json");
            if (!this.userData.exists()) {
                this.userData.createNewFile();
                return;
            }
            String content = FileUtils.readFileToString((File)this.userData);
            if (content.isEmpty()) {
                return;
            }
            JsonObject json = MoreCosmetics.PARSER.parse(content).getAsJsonObject();
            JsonObject compareMap;
            Gson gson = MoreCosmetics.GSON;
            ArrayList cosList = new ArrayList();
            if (json.has("cos")) {
                List cos = (List)gson.fromJson(json.get("cos"), List.class);
                cos.forEach(val -> cosList.add(((Number)val).intValue()));
            }
            for (CosmeticModel model : this.mod.getModelLoader().getCosmetics().values()) {
                if (model == null) continue;
                this.toggleCosmetic(cosList.contains(model.getId()), model);
            }
            this.onlineMode = false; // Always offline
            if (json.has("tag")) {
                this.currentUser.setNametag((Nametag)gson.fromJson(json.get("tag"), Nametag.class));
            }
            if (json.has("cloak") && json.get("cloak").isJsonObject()) {
                Cloak cloak = (Cloak)gson.fromJson(json.get("cloak"), Cloak.class);
                this.currentUser.setCloak(cloak);
                this.offlineCloak.sync(cloak);
            }
            if (json.has("popups")) {
                List popups = (List)gson.fromJson(json.get("popups"), List.class);
                popups.forEach(val -> this.viewedPopups.add(((Number)val).intValue()));
            }
        }
        catch (Exception e) {
            MoreCosmetics.log((String)("Failed to read the config: " + e));
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public void saveUserConfig() {
        try {
            Set cos = this.getActiveCosmetics().keySet();
            Gson gson = MoreCosmetics.GSON;
            JsonObject obj = new JsonObject();
            if (!cos.isEmpty()) {
                obj.add("cos", gson.toJsonTree((Object)cos));
            }
            if (this.currentUser.hasNametag()) {
                obj.add("tag", gson.toJsonTree((Object)this.currentUser.getNametag()));
            }
            obj.add("cloak", gson.toJsonTree((Object)this.currentUser.getCloak()));
            obj.addProperty("online", Boolean.valueOf(false));
            if (!this.viewedPopups.isEmpty()) {
                obj.add("popups", gson.toJsonTree((Object)this.viewedPopups));
            }
            FileUtils.writeStringToFile((File)this.userData, (String)obj.toString());
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    private CosmeticUser initUser(UUID uuid) {
        this.currentUser = new CosmeticUser(uuid);
        this.users.put(uuid, this.currentUser);
        return this.currentUser;
    }

    private void loadWhitelist(Runnable callback) {
        if (callback != null) {
            callback.run();
        }
    }

    public CosmeticUser getCurrentUser() {
        return this.currentUser;
    }

    public CosmeticUser getSafeUser(UUID uuid) {
        return this.users.computeIfAbsent(uuid, u -> new CosmeticUser(uuid));
    }

    public CosmeticUser getUser(UUID uuid) {
        CosmeticUser user = (CosmeticUser)this.users.get(uuid);
        if (user == null) {
            user = new CosmeticUser(uuid);
            this.loadUserData(user, null, false, false);
            this.users.put(uuid, user);
            return user;
        }
        return user;
    }

    public boolean isWhitelisted(UUID uuid) {
        return true;
    }

    public void updateUserWhitelist() {
    }

    public void loadUserData(CosmeticUser user, Runnable callback, boolean sync, boolean updateCallback) {
        if (callback != null) {
            callback.run();
        }
        if (updateCallback) {
            this.userUpdateCallbacks.forEach(cb -> cb.onUserUpdate(user.getUuid()));
        }
    }

    public void applyJsonToUser(CosmeticUser user, JsonObject obj, boolean sync) {
        this.applyJsonToUser(user, obj, sync, true, true, true);
    }

    public void applyJsonToUser(CosmeticUser user, JsonObject obj, boolean sync, boolean cosmetics, boolean cloak, boolean nametag) {
        if (nametag && obj.has("tag")) {
            String logoUrl;
            JsonObject tagObj = obj.get("tag").getAsJsonObject();
            Nametag tag = (Nametag)DATA_GSON.fromJson((JsonElement)tagObj, Nametag.class);
            if (tagObj.has("sc")) {
                tag.setScaleNum(tagObj.get("sc").getAsInt());
            }
            if (tagObj.has("l") && tagObj.has("lc")) {
                String logo = tagObj.get("l").getAsString().replace("1", user.getUuid().toString());
                EnumLogo urlLogo = EnumLogo.getById((int)tagObj.get("lc").getAsInt());
                if (urlLogo != null && !logo.isEmpty()) {
                    tag.setLogoURL(urlLogo.format(logo));
                }
            } else if (!(!tagObj.has("logo") || TextureCategoryBuilder.isAllowedTextureUrl((String)(logoUrl = tagObj.get("logo").getAsString())) && logoUrl.startsWith(EnumLogo.EMOJI.getUrl().replace("%s", "")))) {
                tag.setLogoURL("");
            }
            user.setNametag(tag);
        }
        if (cosmetics && obj.has("cos")) {
            Map userCosmetics = user.getCosmetics();
            if (sync) {
                userCosmetics.values().forEach(data -> ((com.cosmeticsmod.morecosmetics.models.config.ModelData)data).setActive(false));
            }
            for (JsonElement e : obj.get("cos").getAsJsonArray()) {
                JsonObject cos = e.getAsJsonObject();
                Integer id = cos.get("i").getAsInt();
                CosmeticModel model = this.mod.getModelLoader().getModel(id);
                if (model == null) continue;
                ModelData data2 = null;
                if (cos.has("d")) {
                    data2 = (ModelData)MoreCosmetics.GSON.fromJson(cos.get("d"), ModelData.class);
                    if (data2.getVersion() != model.getVersion()) {
                        data2 = this.loadNewData(model, false);
                    } else {
                        this.filterOverlay(data2.texture, user.getUuid().toString());
                    }
                } else {
                    data2 = this.loadNewData(model, false);
                }
                data2.setActive(true);
                userCosmetics.put(id, data2);
            }
        }
        if (cloak && obj.has("cloak") && obj.get("cloak").isJsonPrimitive()) {
            String url = obj.get("cloak").getAsString();
            if (url.startsWith("http")) {
                if (TextureCategoryBuilder.isAllowedTextureUrl((String)url)) {
                    user.getCloak().update(url);
                }
            } else if (!this.onlineMode && (url.contains("/") || url.contains("\\")) && new File(url).exists()) {
                user.getCloak().update(url);
            }
        }
    }

    private void filterOverlay(SettingOverlay[] overlays, String user) {
        if (overlays != null) {
            for (SettingOverlay overlay : overlays) {
                if (overlay.url == null || TextureCategoryBuilder.isAllowedTextureUrl((String)overlay.url)) continue;
                overlay.url = "";
                MoreCosmetics.log((String)("Removed overlay url for " + user));
            }
        }
    }

    public JsonObject getJsonFromUser(CosmeticUser user, boolean nametag) {
        JsonObject cosmeticObj = new JsonObject();
        if (user.getCloak().isActive()) {
            cosmeticObj.addProperty("cloak", user.getCloak().getUrl());
        }
        if (user.hasNametag() && nametag) {
            cosmeticObj.add("tag", MoreCosmetics.GSON.toJsonTree((Object)user.getNametag()));
        }
        JsonArray cosmeticArray = new JsonArray();
        for (Integer id : user.getCosmetics().keySet()) {
            ModelData data = (ModelData)user.getCosmetics().get(id);
            if (!data.isActive()) continue;
            JsonObject obj = new JsonObject();
            obj.addProperty("i", (Number)id);
            if (this.mod.getModelLoader().getModel(id).hasConfig()) {
                obj.add("d", DATA_GSON.toJsonTree(user.getCosmetics().get(id)));
            }
            cosmeticArray.add((JsonElement)obj);
        }
        cosmeticObj.add("cos", (JsonElement)cosmeticArray);
        return cosmeticObj;
    }

    public void toggleCosmetic(boolean enabled, CosmeticModel model) {
        ModelData data = (ModelData)this.currentUser.getCosmetics().get(model.getId());
        if (data != null) {
            if (data.isActive() != enabled) {
                data.setActive(enabled);
                this.checkSettingsChanged();
            }
        } else {
            data = this.loadData(model, true);
            data.setActive(enabled);
            this.currentUser.getCosmetics().put(model.getId(), data);
            this.checkSettingsChanged();
        }
        ModConfig.getConfig().cosmetics = true;
        this.saveUserConfig();
    }

    public ModelData loadData(CosmeticModel model, boolean local) {
        return this.localData.getOrDefault(model, this.localData.computeIfAbsent(model, data -> this.loadNewData(model, local)));
    }

    private ModelData loadNewData(CosmeticModel model, boolean local) {
        ModelData data = null;
        if (local && (data = this.loadConfigData(model.getId().intValue())) != null && model.getVersion() == data.getVersion()) {
            return data;
        }
        data = new ModelData();
        data.setVersion(model.getVersion());
        data.setSubModels(this.initOverlay(model.getSubModels().length));
        if (model.hasTextureModels()) {
            data.setTextureModels(this.initOverlay(model.getTextureModels().size()));
        }
        if (model.hasItemModels()) {
            data.setItemModels(this.initOverlay(model.getItemModels().size()));
        }
        if (model.hasConfig()) {
            for (ModelConfig config : model.getConfig()) {
                Object val = config.value;
                if (val == null) continue;
                if (config.mult != 1.0f && val instanceof Number) {
                    val = Float.valueOf(((Number)val).floatValue() / config.mult);
                }
                ConfigAccessor.set((ModelConfig)config, (ModelData)data, (Object)val);
            }
        }
        return data;
    }

    private SettingOverlay[] initOverlay(int size) {
        SettingOverlay[] overlay = new SettingOverlay[size];
        for (int i = 0; i < overlay.length; ++i) {
            overlay[i] = new SettingOverlay();
        }
        return overlay;
    }

    public void saveData(int id, ModelData data) {
        if (this.onlineMode) {
            this.checkSettingsChanged();
        }
        String json = DATA_GSON.toJson((Object)data);
        try {
            FileUtils.writeStringToFile((File)new File(MoreCosmetics.CONFIG_DIR, id + ".json"), (String)json, (String)"UTF-8");
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    private ModelData loadConfigData(int id) {
        File file = new File(MoreCosmetics.CONFIG_DIR, id + ".json");
        if (!file.exists()) {
            return null;
        }
        try {
            return (ModelData)MoreCosmetics.GSON.fromJson(FileUtils.readFileToString((File)file, (String)"UTF-8"), ModelData.class);
        }
        catch (JsonSyntaxException | IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return null;
        }
    }

    public void clearConfig(int id) {
        CosmeticModel model;
        File file = new File(MoreCosmetics.CONFIG_DIR, id + ".json");
        if (file.exists()) {
            file.delete();
        }
        if ((model = this.mod.getModelLoader().getModel(Integer.valueOf(id))) != null) {
            this.localData.remove(model);
        }
        boolean hasCosmetic = this.currentUser.hasCosmetic(Integer.valueOf(id));
        this.currentUser.getCosmetics().remove(id);
        if (hasCosmetic) {
            this.toggleCosmetic(true, model);
        }
    }

    public HashMap<UUID, CosmeticUser> getUsers() {
        return this.users;
    }

    public void reload(Runnable callback) {
        this.mod.getNametagHandler().resetNametags();
        this.users.clear();
        this.users.put(this.currentUser.getUuid(), this.currentUser);
        this.whitelist.clear();
        this.loadWhitelist(() -> {
            if (this.onlineMode && this.isWhitelisted(this.currentUser.getUuid())) {
                this.loadUserData(this.currentUser, callback, true, true);
            } else {
                callback.run();
            }
        });
    }

    public void sync(Runnable callback) {
        this.currentUser.setNametag(null);
        this.currentUser.getCloak().update(null);
        if (this.isWhitelisted(this.currentUser.getUuid())) {
            this.loadUserData(this.currentUser, callback, true, true);
        } else {
            this.currentUser.getCosmetics().clear();
            callback.run();
        }
    }

    public void upload() {
        if (!this.onlineMode) {
            return;
        }
        JsonObject cosmeticObj = this.getJsonFromUser(this.currentUser, false);
        byte[] data = cosmeticObj.toString().getBytes(StandardCharsets.UTF_8);
        this.mod.getConnection().sendAsync((Packet)new PacketCosmeticUpdate(data, this.getNearbyUsers()));
        this.updateCurrentData();
    }

    public void updateTick(int tick) {
        if (this.userData == null && this.mod.getModelLoader().isLoaded()) {
            MoreCosmetics.log((String)"Init Config!");
            this.loadUserConfig();
            PopupFetcher.init();
            this.loaded = true;
        }
        if (this.currentUser != null) {
            VersionAdapter adapter = this.mod.getVersionAdapter();
            UUID uuid = adapter.getUuid(true);
            if (!this.currentUser.getUuid().equals(uuid)) {
                if (uuid.equals(adapter.getUuid(false))) {
                    SharedVars.OFFLINE_MODE = false;
                    MoreCosmetics.log((String)"Account got changed!");
                    this.switchAcc(uuid);
                } else {
                    SharedVars.OFFLINE_MODE = true;
                    MoreCosmetics.log((String)"Playing in non premium mode!");
                    CosmeticUser user = new CosmeticUser(uuid);
                    user.setNametag(this.currentUser.getNametag());
                    user.getCosmetics().putAll(this.currentUser.getCosmetics());
                    user.setCloak(this.currentUser.getCloak());
                    this.users.put(uuid, user);
                    this.currentUser = user;
                }
            }
        }
    }

    public UUID[] getNearbyUsers() {
        UUID[] players = this.mod.getVersionAdapter().getPlayersInWorld();
        ArrayList<UUID> closeAndIndicated = new ArrayList<UUID>();
        for (UUID uuid : players) {
            if (!this.mod.getConnection().isUsing(uuid.getMostSignificantBits()) || uuid.equals(this.currentUser.getUuid())) continue;
            closeAndIndicated.add(uuid);
        }
        return closeAndIndicated.toArray(new UUID[closeAndIndicated.size()]);
    }

    public void setLoginData(PacketComplete packet) {
        this.loginKey = packet.getLoginKey();
        this.textureToken = packet.getTextureToken();
        this.loginStreak = packet.getLoginStreak();
        this.firstJoin = packet.getFirstJoin();
        this.onlineCosmetics = packet.getCosmetics();
    }

    private void updateCurrentData() {
        this.currentOnlineData.clear();
        for (Map.Entry en : this.currentUser.getCosmetics().entrySet()) {
            if (!((ModelData)en.getValue()).isActive()) continue;
            this.currentOnlineData.put((Integer)en.getKey(), MoreCosmetics.GSON.fromJson(DATA_GSON.toJsonTree(en.getValue()), ModelData.class));
        }
        this.onlineCloak.sync(this.currentUser.getCloak());
        this.checkSettingsChanged();
    }

    public void checkSettingsChanged() {
        HashMap compareMap;
        this.settingsChanged = this.onlineMode ? !this.currentOnlineData.equals(compareMap = this.getActiveCosmetics()) || !this.currentUser.getCloak().equals((Object)this.onlineCloak) : false;
    }

    public boolean areSettingsChanged() {
        return this.settingsChanged;
    }

    public String getLoginKey() {
        return this.loginKey == null ? "" : this.loginKey;
    }

    public String getTextureToken() {
        return this.textureToken == null ? "" : this.textureToken;
    }

    public int getLoginStreak() {
        return this.loginStreak;
    }

    public long getFirstJoin() {
        return this.firstJoin == 0L ? System.currentTimeMillis() : this.firstJoin * 1000L;
    }

    public boolean hasOnlineNametag() {
        return this.onlineCosmetics.containsKey(NAMETAG_ID);
    }

    public HashMap<Integer, Long> getOnlineCosmetics() {
        return this.onlineCosmetics;
    }

    public void addViewedPopup(int id) {
        if (!this.viewedPopups.contains(id)) {
            this.viewedPopups.add(id);
            this.saveUserConfig();
        }
    }

    private HashMap<Integer, ModelData> getActiveCosmetics() {
        HashMap<Integer, ModelData> current = new HashMap<Integer, ModelData>(this.currentUser.getCosmetics());
        current.entrySet().removeIf(e -> !((ModelData)e.getValue()).isActive());
        return current;
    }

    private void restoreOfflineStorage() {
        this.offlineStorage.forEach(i -> this.toggleCosmetic(true, MoreCosmetics.getInstance().getModelLoader().getModel(i)));
        this.currentUser.getCloak().sync(this.offlineCloak);
    }

    private void saveOfflineStorage() {
        this.offlineStorage.clear();
        this.offlineStorage.addAll(this.getActiveCosmetics().keySet());
        this.offlineCloak.sync(this.currentUser.getCloak());
    }

    public boolean toggleOnlineMode() {
        if (!this.onlineMode && this.checkOffline()) {
            NotificationHandler.sendError((String)LanguageHandler.get((String)"noconnection"));
            return false;
        }
        boolean bl = this.onlineMode = !this.onlineMode;
        if (!this.onlineMode) {
            this.restoreOfflineStorage();
            ModelGui.refreshGui();
        } else {
            this.saveOfflineStorage();
            this.sync(ModelGui::refreshGui);
        }
        this.saveUserConfig();
        return true;
    }

    public void switchAcc(UUID uuid) {
        this.offlineStorage.clear();
        this.onlineCosmetics.clear();
        this.initUser(uuid);
        this.loadUserConfig();
        this.mod.getConnection().reconnectNewAccount(this.mod.getVersionAdapter().getPlayerName(), uuid);
        if (this.isWhitelisted(this.currentUser.getUuid())) {
            this.loadUserData(this.currentUser, ModelGui::refreshGui, this.onlineMode, false);
        } else {
            ModelGui.refreshGui();
        }
    }

    public int getOnlineCount() {
        int count = this.onlineCosmetics.size();
        return this.hasOnlineNametag() ? count - 1 : count;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void addUpdateCallback(UserUpdateCallback callback) {
        this.userUpdateCallbacks.add(callback);
    }

    public List<Integer> getViewedPopups() {
        return this.viewedPopups;
    }

    private boolean checkOffline() {
        return SharedVars.OFFLINE_MODE || !this.mod.getConnection().isConnected();
    }

    public boolean isOnlineMode() {
        if (this.onlineMode && this.checkOffline()) {
            this.restoreOfflineStorage();
            this.onlineMode = false;
        }
        return this.onlineMode;
    }

    public void resetData() {
        this.offlineStorage.clear();
        this.currentUser.getCloak().update(null);
        this.currentUser.getCosmetics().clear();
        this.currentUser.setNametag(null);
        this.onlineMode = false;
        this.localData.clear();
        try {
            FileUtils.deleteDirectory((File)MoreCosmetics.CONFIG_DIR);
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
        MoreCosmetics.CONFIG_DIR.mkdirs();
        this.saveUserConfig();
        ModConfig.saveConfig();
    }

    public String getUploadUrl() {
        String url = this.mod.getInfo().uploadUrl;
        if (url == null) {
            return null;
        }
        return url.replace("{uuid}", this.currentUser.getUuid().toString()).replace("{token}", this.textureToken);
    }
}

