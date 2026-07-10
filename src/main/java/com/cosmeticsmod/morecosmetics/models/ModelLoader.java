/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.animation.Animation
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationType
 *  com.cosmeticsmod.morecosmetics.models.config.ModelConfig
 *  com.cosmeticsmod.morecosmetics.models.model.AnimationModel
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.model.ItemModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.SubModel
 *  com.cosmeticsmod.morecosmetics.models.model.TextureModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 *  com.cosmeticsmod.morecosmetics.utils.GeckoBridge
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.apache.commons.io.FileUtils
 */
package com.cosmeticsmod.morecosmetics.models;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;
import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder;
import com.cosmeticsmod.morecosmetics.models.animation.Animation;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationType;
import com.cosmeticsmod.morecosmetics.models.config.ModelConfig;
import com.cosmeticsmod.morecosmetics.models.model.AnimationModel;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.model.ItemModel;
import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.SubModel;
import com.cosmeticsmod.morecosmetics.models.model.TextureModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;
import com.cosmeticsmod.morecosmetics.utils.GeckoBridge;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

public abstract class ModelLoader {
    public static final int INTERNAL_VERSION = 2;
    public static final float[] SIDE_TRANSFORM = new float[]{-0.05f, 0.05f, -0.4f};
    private int onlineCount;
    protected HashMap<Integer, CosmeticModel> cosmetics = new HashMap();
    private File cosmeticsDir;
    private File localCosmeticsDir;
    private boolean loaded;

    public ModelLoader() {
        MoreCosmetics.log((String)"Init Modelloader!");
        this.localCosmeticsDir = new File(MoreCosmetics.ROOT_DIR, "cosmetics");
        this.localCosmeticsDir.mkdirs();
        this.loadOfflineModels();
        this.loadCustomModels();
    }

    private void loadOfflineModels() {
        try {
            java.io.InputStream listStream = ModelLoader.class.getResourceAsStream("/assets/morecosmetics/models/cosmetics.json");
            if (listStream == null) {
                MoreCosmetics.log("Failed to load cosmetics.json from resources!");
                return;
            }
            JsonElement cosData = MoreCosmetics.PARSER.parse(new java.io.InputStreamReader(listStream, java.nio.charset.Charset.forName("UTF-8")));
            for (JsonElement e : cosData.getAsJsonArray()) {
                JsonObject obj = e.getAsJsonObject();
                int id = obj.get("id").getAsInt();
                String name = obj.get("name").getAsString();
                java.io.InputStream modelStream = ModelLoader.class.getResourceAsStream("/assets/morecosmetics/models/" + id + ".json");
                if (modelStream != null) {
                    this.loadModel(modelStream, false);
                    MoreCosmetics.log("Loaded offline model: " + name);
                } else {
                    MoreCosmetics.log("Failed to find model stream for: " + name);
                }
            }
            this.loaded = true;
        } catch (Exception e) {
            MoreCosmetics.catchThrowable(e);
        }
    }

    public void loadModel(java.io.InputStream stream, boolean custom) {
        try (java.io.InputStreamReader reader = new java.io.InputStreamReader(new java.io.BufferedInputStream(stream), java.nio.charset.Charset.forName("UTF-8"));) {
            int minVersion;
            JsonObject obj = MoreCosmetics.PARSER.parse((Reader)reader).getAsJsonObject();
            if (!obj.has("name")) {
                throw new IllegalArgumentException("Missing name");
            }
            if (!obj.has("model")) {
                throw new IllegalArgumentException("Missing model");
            }
            String name = obj.get("name").getAsString();
            if (obj.has("minVersion") && (minVersion = obj.get("minVersion").getAsInt()) > 1201) {
                throw new IllegalStateException("Model requieres version " + minVersion);
            }
            int id = !custom && obj.has("id") ? obj.get("id").getAsInt() : name.hashCode();
            int category = obj.has("category") ? obj.get("category").getAsInt() : 0;
            int version = obj.has("version") ? obj.get("version").getAsInt() : 0;
            String textureName = null;
            if (obj.has("texture")) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(obj.get("texture").getAsString()));){
                    textureName = name.replace(" ", "").toLowerCase() + id;
                    this.loadTexture(textureName, ImageIO.read(bis));
                }
                catch (IOException e) {
                    MoreCosmetics.log((String)("Failed to load texture from " + name));
                }
            }
            ModelConfig[] configEntries = null;
            if (obj.has("config")) {
                JsonArray jsonConfig = obj.get("config").getAsJsonArray();
                configEntries = new ModelConfig[jsonConfig.size()];
                for (int i = 0; i < jsonConfig.size(); ++i) {
                    String val;
                    configEntries[i] = (ModelConfig)MoreCosmetics.GSON.fromJson(jsonConfig.get(i), ModelConfig.class);
                    JsonObject entry = jsonConfig.get(i).getAsJsonObject();
                    if (!entry.has("value")) continue;
                    if (configEntries[i].value.getClass().equals(Double.class) && !(val = entry.get("value").getAsString()).contains(".")) {
                        configEntries[i].value = entry.get("value").getAsInt();
                    }
                    if (configEntries[i].value.getClass().equals(String.class) && (val = entry.get("value").getAsString()).startsWith("http") && !TextureCategoryBuilder.isAllowedTextureUrl((String)val)) {
                        MoreCosmetics.log((String)("Removed url: " + val));
                        configEntries[i].value = "";
                    }
                    if (configEntries[i].src == null || TextureCategoryBuilder.isAllowedTextureUrl((String)configEntries[i].src)) continue;
                    MoreCosmetics.log((String)("Removed mask url: " + configEntries[i].src));
                    configEntries[i].src = null;
                }
            }
            GeoModel geoModel = GeckoBridge.MODEL_LOADER.loadModel(name, obj.get("model").getAsJsonObject().toString());
            SubModel[] subModels = new SubModel[]{};
            if (obj.has("models")) {
                JsonArray models = obj.get("models").getAsJsonArray();
                subModels = new SubModel[models.size()];
                for (int i = 0; i < models.size(); ++i) {
                    JsonObject subModelObj = models.get(i).getAsJsonObject();
                    String modelName = subModelObj.has("name") ? subModelObj.get("name").getAsString() : "ModelComponent " + (i + 1);
                    int color = subModelObj.has("color") ? 0xFF000000 | subModelObj.get("color").getAsInt() : -1;
                    subModels[i] = new SubModel(modelName, color, true);
                    this.loadModelData(subModelObj, (PositionModel)subModels[i]);
                }
            }
            for (int i = 0; i < subModels.length; ++i) {
                this.linkModel(geoModel.topLevelBones, subModels[i], i);
            }
            CosmeticModel model = new CosmeticModel(name, Integer.valueOf(id), category, version, custom, textureName, geoModel, subModels, configEntries, null);
            if (obj.has("animation")) {
                model.setAnimationFile(GeckoBridge.ANIMATION_LOADER.loadAllAnimations(GeckoBridge.MOLANG_PARSER, obj.get("animation").getAsJsonObject()));
            }
            if (obj.has("items")) {
                JsonArray items = obj.get("items").getAsJsonArray();
                for (int i = 0; i < items.size(); ++i) {
                    JsonObject item = items.get(i).getAsJsonObject();
                    ItemModel itemModel = new ItemModel(item.get("item").getAsInt());
                    this.loadModelData(item, (PositionModel)itemModel);
                    model.getItemModels().add(itemModel);
                }
            }
            if (obj.has("textures")) {
                JsonArray textures = obj.get("textures").getAsJsonArray();
                for (int i = 0; i < textures.size(); ++i) {
                    JsonObject texture = textures.get(i).getAsJsonObject();
                    String url = texture.get("url").getAsString();
                    if (url != null && !url.isEmpty() && !TextureCategoryBuilder.isAllowedTextureUrl((String)url)) {
                        MoreCosmetics.log((String)("Removed texture url: " + url));
                        url = "";
                    }
                    TextureModel textureModel = new TextureModel(url, i);
                    if (texture.has("opacity")) {
                        textureModel.setAlpha(texture.get("opacity").getAsFloat());
                    }
                    if (texture.has("color")) {
                        textureModel.setColor(texture.get("color").getAsInt());
                    }
                    if (texture.has("bound")) {
                        textureModel.setBound(texture.get("bound").getAsInt());
                        if (textureModel.getBound() >= 0) {
                            model.getSubModels()[textureModel.getBound()].getTextures().add(textureModel);
                        }
                    }
                    if (texture.has("mask")) {
                        String mask = texture.get("mask").getAsString();
                        this.loadMask(textureModel, name, mask, null);
                    }
                    this.loadModelData(texture, (PositionModel)textureModel);
                }
            }
            this.cosmetics.put(id, model);
        } catch (Exception e) {
            MoreCosmetics.catchThrowable(e);
        }
    }

    private void downloadFiles() {
        try {
            HashMap<String, Long> dlList = new HashMap<String, Long>();
            JsonElement cosData = Utils.readJsonFromUrl((String)"http://dl.cosmeticsmod.com/morecosmetics/cosmetics.json", (boolean)false, (Object[])new Object[0]);
            if (cosData == null) {
                MoreCosmetics.log((String)"Failed download models!");
                return;
            }
            for (JsonElement e : cosData.getAsJsonArray()) {
                JsonObject obj = e.getAsJsonObject();
                String id = obj.get("id").getAsString();
                dlList.put(id, obj.get("len").getAsLong());
                ++this.onlineCount;
            }
            for (File file : this.cosmeticsDir.listFiles()) {
                if (!file.getName().toLowerCase().endsWith(".json")) continue;
                String id = file.getName().substring(0, file.getName().length() - 5);
                if (!dlList.containsKey(id)) {
                    MoreCosmetics.log((String)("Deleting: " + id));
                    file.delete();
                    continue;
                }
                if (((Long)dlList.get(id)).longValue() != file.length()) {
                    MoreCosmetics.log((String)("Updating: " + id + " (" + dlList.get(id) + " / " + file.length() + ")"));
                    file.delete();
                    continue;
                }
                dlList.remove(id);
            }
            for (String id : dlList.keySet()) {
                File file = new File(this.cosmeticsDir.getPath(), id + ".json");
                MoreCosmetics.log((String)("Downloading: " + id));
                Utils.gzipToFile((String)("http://dl.cosmeticsmod.com/morecosmetics/cosmetics/" + id + ".cos"), (File)file, (int)5000);
            }
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public void loadCustomModels() {
        for (File file : this.localCosmeticsDir.listFiles()) {
            if (!file.getName().toLowerCase().endsWith(".json")) continue;
            this.loadModel(file, true);
        }
    }

    protected void loadDownloadedModels() {
        for (File file : this.cosmeticsDir.listFiles()) {
            if (!file.getName().toLowerCase().endsWith(".json")) continue;
            this.loadModel(file, false);
        }
        this.loaded = true;
    }

    public void loadModel(File file, boolean custom) {
        if (file == null) {
            return;
        }
        try (InputStreamReader reader = new InputStreamReader((InputStream)new BufferedInputStream(new FileInputStream(file)), Charset.forName("UTF-8"));){
            int minVersion;
            JsonObject obj = MoreCosmetics.PARSER.parse((Reader)reader).getAsJsonObject();
            if (!obj.has("name")) {
                throw new IllegalArgumentException("Missing name");
            }
            if (!obj.has("model")) {
                throw new IllegalArgumentException("Missing model");
            }
            String name = obj.get("name").getAsString();
            if (obj.has("minVersion") && (minVersion = obj.get("minVersion").getAsInt()) > 1201) {
                throw new IllegalStateException("Model requieres version " + minVersion);
            }
            int id = !custom && obj.has("id") ? obj.get("id").getAsInt() : name.hashCode();
            int category = obj.has("category") ? obj.get("category").getAsInt() : 0;
            int version = obj.has("version") ? obj.get("version").getAsInt() : 0;
            String textureName = null;
            if (obj.has("texture")) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(obj.get("texture").getAsString()));){
                    textureName = name.replace(" ", "").toLowerCase() + id;
                    this.loadTexture(textureName, ImageIO.read(bis));
                }
                catch (IOException e) {
                    MoreCosmetics.log((String)("Failed to load texture from " + name));
                }
            }
            ModelConfig[] configEntries = null;
            if (obj.has("config")) {
                JsonArray jsonConfig = obj.get("config").getAsJsonArray();
                configEntries = new ModelConfig[jsonConfig.size()];
                for (int i = 0; i < jsonConfig.size(); ++i) {
                    String val;
                    configEntries[i] = (ModelConfig)MoreCosmetics.GSON.fromJson(jsonConfig.get(i), ModelConfig.class);
                    JsonObject entry = jsonConfig.get(i).getAsJsonObject();
                    if (!entry.has("value")) continue;
                    if (configEntries[i].value.getClass().equals(Double.class) && !(val = entry.get("value").getAsString()).contains(".")) {
                        configEntries[i].value = entry.get("value").getAsInt();
                    }
                    if (configEntries[i].value.getClass().equals(String.class) && (val = entry.get("value").getAsString()).startsWith("http") && !TextureCategoryBuilder.isAllowedTextureUrl((String)val)) {
                        MoreCosmetics.log((String)("Removed url: " + val));
                        configEntries[i].value = "";
                    }
                    if (configEntries[i].src == null || TextureCategoryBuilder.isAllowedTextureUrl((String)configEntries[i].src)) continue;
                    MoreCosmetics.log((String)("Removed mask url: " + configEntries[i].src));
                    configEntries[i].src = null;
                }
            }
            GeoModel geoModel = GeckoBridge.MODEL_LOADER.loadModel(name, obj.get("model").getAsJsonObject().toString());
            SubModel[] subModels = new SubModel[]{};
            if (obj.has("models")) {
                JsonArray models = obj.get("models").getAsJsonArray();
                subModels = new SubModel[models.size()];
                for (int i = 0; i < models.size(); ++i) {
                    JsonObject subModelObj = models.get(i).getAsJsonObject();
                    String modelName = subModelObj.has("name") ? subModelObj.get("name").getAsString() : "ModelComponent " + (i + 1);
                    int color = subModelObj.has("color") ? 0xFF000000 | subModelObj.get("color").getAsInt() : -1;
                    subModels[i] = new SubModel(modelName, color, true);
                    this.loadModelData(subModelObj, (PositionModel)subModels[i]);
                }
            }
            for (int i = 0; i < subModels.length; ++i) {
                this.linkModel(geoModel.topLevelBones, subModels[i], i);
            }
            CosmeticModel model = new CosmeticModel(name, Integer.valueOf(id), category, version, custom, textureName, geoModel, subModels, configEntries, file);
            if (obj.has("animation")) {
                model.setAnimationFile(GeckoBridge.ANIMATION_LOADER.loadAllAnimations(GeckoBridge.MOLANG_PARSER, obj.get("animation").getAsJsonObject()));
            }
            if (obj.has("items")) {
                JsonArray items = obj.get("items").getAsJsonArray();
                for (int i = 0; i < items.size(); ++i) {
                    JsonObject item = items.get(i).getAsJsonObject();
                    ItemModel itemModel = new ItemModel(item.get("item").getAsInt());
                    this.loadModelData(item, (PositionModel)itemModel);
                    model.getItemModels().add(itemModel);
                }
            }
            if (obj.has("textures")) {
                JsonArray textures = obj.get("textures").getAsJsonArray();
                for (int i = 0; i < textures.size(); ++i) {
                    JsonObject texture = textures.get(i).getAsJsonObject();
                    String url = texture.get("url").getAsString();
                    if (url != null && !url.isEmpty() && !TextureCategoryBuilder.isAllowedTextureUrl((String)url)) {
                        MoreCosmetics.log((String)("Removed texture url: " + url));
                        url = "";
                    }
                    TextureModel textureModel = new TextureModel(url, i);
                    if (texture.has("opacity")) {
                        textureModel.setAlpha(texture.get("opacity").getAsFloat());
                    }
                    if (texture.has("color")) {
                        textureModel.setColor(texture.get("color").getAsInt());
                    }
                    if (texture.has("bound")) {
                        textureModel.setBound(texture.get("bound").getAsInt());
                        if (textureModel.getBound() >= 0) {
                            model.getSubModels()[textureModel.getBound()].getTextures().add(textureModel);
                        }
                    }
                    if (texture.has("mask")) {
                        String mask = texture.get("mask").getAsString();
                        this.loadMask(textureModel, name, mask, null);
                    }
                    this.loadModelData(texture, (PositionModel)textureModel);
                    model.getTextureModels().add(textureModel);
                }
            }
            if (obj.has("height")) {
                model.setHeight(obj.get("height").getAsFloat());
            }
            if (obj.has("resize")) {
                model.setResizeVal(obj.get("resize").getAsFloat());
            }
            if (obj.has("previewScale")) {
                model.setPreviewScale(obj.get("previewScale").getAsFloat());
            }
            if (obj.has("previewY")) {
                model.setPreviewY(obj.get("previewY").getAsFloat());
            }
            if (obj.has("previewRot")) {
                model.setPreviewRot((float[])MoreCosmetics.GSON.fromJson(obj.get("previewRot"), float[].class));
            }
            if (obj.has("adjustment")) {
                model.setAdjustment((float[])MoreCosmetics.GSON.fromJson(obj.get("adjustment"), float[].class));
            }
            if (obj.has("sideTransform")) {
                model.setSideTranform((float[])MoreCosmetics.GSON.fromJson(obj.get("sideTransform"), float[].class));
            }
            this.loadModelData(obj, (PositionModel)model);
            this.cosmetics.put(id, model);
            MoreCosmetics.log((String)("Loaded cosmetic: " + name));
        }
        catch (Exception e) {
            MoreCosmetics.log((String)("Failed to load " + file.getName() + ": " + e.getMessage()));
        }
    }

    private void linkModel(List<GeoBone> bones, SubModel model, int index) {
        for (GeoBone bone : bones) {
            if (bone.getName().toLowerCase().startsWith(model.getName().toLowerCase())) {
                bone.subModelIndex = index;
                continue;
            }
            this.linkModel(bone.childBones, model, index);
        }
    }

    public void loadMask(TextureModel textureModel, String name, String mask, Consumer<Boolean> callback) {
        Optional<Consumer<Boolean>> cc = Optional.ofNullable(callback);
        if (mask.startsWith("http")) {
            if (TextureCategoryBuilder.isAllowedTextureUrl((String)mask)) {
                MoreCosmetics.EXECUTOR.execute(() -> {
                    try (InputStream is = Utils.getInputStream((String)mask, (int)5000, (Object[])new Object[0]);){
                        textureModel.setMask(ImageIO.read(is));
                        textureModel.setMaskUrl(mask);
                        cc.ifPresent(c -> c.accept(true));
                    }
                    catch (IOException e) {
                        MoreCosmetics.log((String)("Failed to load mask from " + name + ": " + e.getMessage()));
                        cc.ifPresent(c -> c.accept(false));
                    }
                });
            }
        } else {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(mask));){
                textureModel.setMask(ImageIO.read(bis));
                cc.ifPresent(c -> c.accept(true));
            }
            catch (IOException e) {
                MoreCosmetics.log((String)("Failed to load mask from " + name + ": " + e.getMessage()));
                cc.ifPresent(c -> c.accept(false));
            }
        }
    }

    public void updateModelFile(CosmeticModel model) {
        if (model.getFile() == null) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(model.getFile()));){
            JsonObject obj = MoreCosmetics.PARSER.parse((Reader)reader).getAsJsonObject();
            obj.addProperty("name", model.getName());
            obj.addProperty("category", (Number)model.getCategory());
            obj.addProperty("height", (Number)Float.valueOf(model.getHeight()));
            obj.addProperty("previewY", (Number)Float.valueOf(model.getPreviewY()));
            obj.addProperty("previewScale", (Number)Float.valueOf(model.getPreviewScale()));
            if (model.getPreviewRot() != null) {
                obj.add("previewRot", MoreCosmetics.GSON.toJsonTree((Object)model.getPreviewRot()));
            }
            this.saveModelData(obj, (PositionModel)model);
            if (model.hasItemModels()) {
                JsonArray itemModels = new JsonArray();
                for (ItemModel iModel : model.getItemModels()) {
                    JsonObject itemModel = new JsonObject();
                    itemModel.addProperty("item", (Number)iModel.getItemId());
                    this.saveModelData(itemModel, (PositionModel)iModel);
                    itemModels.add((JsonElement)itemModel);
                }
                obj.add("items", (JsonElement)itemModels);
            }
            if (model.hasTextureModels()) {
                JsonArray textureModels = new JsonArray();
                for (TextureModel tModel : model.getTextureModels()) {
                    JsonObject textureModel = new JsonObject();
                    textureModel.addProperty("url", tModel.getUrl());
                    textureModel.addProperty("opacity", (Number)Float.valueOf(tModel.getAlpha()));
                    textureModel.addProperty("color", (Number)tModel.getColor());
                    textureModel.addProperty("bound", (Number)tModel.getBound());
                    if (tModel.getMaskUrl() != null && tModel.getMaskUrl().startsWith("http")) {
                        textureModel.addProperty("mask", tModel.getMaskUrl());
                    }
                    this.saveModelData(textureModel, (PositionModel)tModel);
                    textureModels.add((JsonElement)textureModel);
                }
                obj.add("textures", (JsonElement)textureModels);
            }
            if (model.hasSubModels()) {
                JsonArray models = obj.get("models").getAsJsonArray();
                for (int i = 0; i < models.size(); ++i) {
                    JsonObject subModelObj = models.get(i).getAsJsonObject();
                    SubModel subModel = model.getSubModels()[i];
                    subModelObj.addProperty("visible", Boolean.valueOf(subModel.isVisible()));
                    this.saveModelData(subModelObj, (PositionModel)subModel);
                }
            }
            if (model.hasConfig()) {
                obj.add("config", MoreCosmetics.GSON.toJsonTree((Object)model.getConfig()));
            }
            FileUtils.writeStringToFile((File)model.getFile(), (String)obj.toString());
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    protected abstract void loadTexture(String var1, BufferedImage var2);

    private void loadModelData(JsonObject obj, PositionModel model) {
        this.loadPosition(obj, model);
        this.loadAnimations(obj, (AnimationModel)model);
    }

    private void saveModelData(JsonObject obj, PositionModel model) {
        this.savePosition(obj, model);
        this.saveAnimations(obj, (AnimationModel)model);
    }

    private void loadPosition(JsonObject obj, PositionModel model) {
        ModelPosition pos;
        if (obj.has("pos") && (pos = ModelPosition.getById((int)obj.get("pos").getAsInt())) != null) {
            model.setPosition(pos);
        }
        if (obj.has("scale")) {
            model.setScale(obj.get("scale").getAsFloat());
        }
        if (obj.has("x")) {
            model.setX(obj.get("x").getAsFloat());
        }
        if (obj.has("y")) {
            model.setY(obj.get("y").getAsFloat());
        }
        if (obj.has("z")) {
            model.setZ(obj.get("z").getAsFloat());
        }
        if (obj.has("yaw")) {
            model.setYaw(obj.get("yaw").getAsFloat());
        }
        if (obj.has("pitch")) {
            model.setPitch(obj.get("pitch").getAsFloat());
        }
        if (obj.has("roll")) {
            model.setRoll(obj.get("roll").getAsFloat());
        }
    }

    private void savePosition(JsonObject obj, PositionModel model) {
        obj.addProperty("pos", (Number)model.getPosition().getId());
        obj.addProperty("scale", (Number)Float.valueOf(model.getScale()));
        obj.addProperty("x", (Number)Float.valueOf(model.getX()));
        obj.addProperty("y", (Number)Float.valueOf(model.getY()));
        obj.addProperty("z", (Number)Float.valueOf(model.getZ()));
        obj.addProperty("yaw", (Number)Float.valueOf(model.getYaw()));
        obj.addProperty("pitch", (Number)Float.valueOf(model.getPitch()));
        obj.addProperty("roll", (Number)Float.valueOf(model.getRoll()));
    }

    private void loadAnimations(JsonObject obj, AnimationModel model) {
        if (obj.has("animations")) {
            for (JsonElement element : obj.get("animations").getAsJsonArray()) {
                AnimationAxis axis;
                JsonObject anObj = element.getAsJsonObject();
                Animation animation = new Animation(AnimationType.getById((int)anObj.get("type").getAsInt()));
                if (anObj.has("axis") && (axis = AnimationAxis.getById((int)anObj.get("axis").getAsInt())) != null) {
                    animation.setAxis(axis);
                }
                animation.setMultiplier(anObj.get("mult").getAsFloat());
                model.addAnimation(animation);
            }
        }
    }

    private void saveAnimations(JsonObject obj, AnimationModel model) {
        JsonArray animations = new JsonArray();
        for (Animation animation : model.getAnimations().values()) {
            JsonObject anObj = new JsonObject();
            anObj.addProperty("type", (Number)animation.getType().getId());
            if (animation.getAxis() != null) {
                anObj.addProperty("axis", (Number)animation.getAxis().getId());
            }
            anObj.addProperty("mult", (Number)Float.valueOf(animation.getMultiplier()));
            animations.add((JsonElement)anObj);
        }
        obj.add("animations", (JsonElement)animations);
    }

    public CosmeticModel getModel(Integer id) {
        return (CosmeticModel)this.cosmetics.get(id);
    }

    public HashMap<Integer, CosmeticModel> getCosmetics() {
        return this.cosmetics;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public File getLoadDir() {
        return this.localCosmeticsDir;
    }

    public int getOnlineCount() {
        return this.onlineCount;
    }
}

