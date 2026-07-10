/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.EditGui
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 *  com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.animation.Animation
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationType
 *  com.cosmeticsmod.morecosmetics.models.config.ModelConfig
 *  com.cosmeticsmod.morecosmetics.models.config.SettingType
 *  com.cosmeticsmod.morecosmetics.models.model.AnimationModel
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.SubModel
 *  com.cosmeticsmod.morecosmetics.models.model.TextureModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 */
package com.cosmeticsmod.morecosmetics.gui;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.animation.Animation;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationType;
import com.cosmeticsmod.morecosmetics.models.config.ModelConfig;
import com.cosmeticsmod.morecosmetics.models.config.SettingType;
import com.cosmeticsmod.morecosmetics.models.model.AnimationModel;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.SubModel;
import com.cosmeticsmod.morecosmetics.models.model.TextureModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditGui
implements CustomBoxGui {
    private static EditGui instance;
    private ListElementBuilder listBuilder;
    private BoxElementBuilder boxBuilder;
    private ModelLoader modelLoader;
    private CosmeticModel model;
    private List<ModelConfig> config;
    private List<BoxCategory> rootList;
    private int texIndex;
    private boolean quit;
    private BoxManager.BoxGuiInstance guiInstance;

    public EditGui(BoxManager.BoxGuiInstance guiInstance) {
        this.guiInstance = guiInstance;
        instance = this;
        this.modelLoader = MoreCosmetics.getInstance().getModelLoader();
        this.listBuilder = MoreCosmetics.getInstance().getListElementBuilder();
        this.boxBuilder = MoreCosmetics.getInstance().getBoxElementBuilder();
    }

    public void setModel(CosmeticModel model) {
        this.model = model;
        this.config = model.hasConfig() ? new ArrayList<ModelConfig>(Arrays.asList(model.getConfig())) : new ArrayList();
        MoreCosmetics.getInstance().getUserHandler().clearConfig(model.getId().intValue());
        this.quit = false;
    }

    public void fillGui(List<BoxCategory> list) {
        this.rootList = list;
        try {
            int i;
            list.clear();
            list.add(new BoxCategory("General", "settings").fillEntries(comp -> {
                List cat = Stream.of(ModelCategory.VALUES).map(Enum::name).collect(Collectors.toList());
                comp.add(this.listBuilder.getListElement("Category", cat, this.model.getCategory(), c -> this.model.setCategory(ModelCategory.valueOf((String)c).getId())));
                comp.add(this.listBuilder.getSliderElement("Name Height", 0, 10, (int)(this.model.getHeight() / 10.0f), heightVal -> this.model.setHeight((float)heightVal.intValue() / 10.0f)));
                comp.add(this.listBuilder.getSeparationElement("Position", UIConstants.UI_SEPARATION_COLOR));
                this.fillPositionComp((List)comp, (PositionModel)this.model, "");
                comp.add(this.listBuilder.getSeparationElement("Simple Animations", UIConstants.UI_SEPARATION_COLOR));
                this.fillAnimationComp((List)comp, (AnimationModel)this.model);
            }, true));
            list.add(new BoxCategory("Preview", "preview").fillEntries(comp -> comp.add(this.boxBuilder.getCosmeticElement(this.model, MoreCosmetics.getInstance().getUserHandler().getCurrentUser(), enabled -> {}).fillChild(child -> {
                float[] fArray;
                child.add(this.listBuilder.getSliderElement("Scale", 1, 20, (int)(this.model.getPreviewScale() * 10.0f), scaleVal -> this.model.setPreviewScale((float)scaleVal.intValue() / 10.0f)));
                child.add(this.listBuilder.getSliderElement("Y-Height", -10, 10, (int)(this.model.getPreviewY() * -10.0f), yHeightVal -> this.model.setPreviewY((float)yHeightVal.intValue() / -10.0f)));
                if (this.model.getPreviewRot() == null) {
                    float[] fArray2 = new float[3];
                    fArray2[0] = 0.0f;
                    fArray2[1] = 0.0f;
                    fArray = fArray2;
                    fArray2[2] = 0.0f;
                } else {
                    fArray = this.model.getPreviewRot();
                }
                float[] rot = fArray;
                child.add(this.listBuilder.getSelectiveSliderElement("Yaw", -180, 180, (int)rot[0], yawVal -> {
                    rot[0] = yawVal.intValue();
                    this.model.setPreviewRot(rot);
                }, 70, true));
                child.add(this.listBuilder.getSelectiveSliderElement("Pitch", -180, 180, (int)rot[1], pitchVal -> {
                    rot[1] = pitchVal.intValue();
                    this.model.setPreviewRot(rot);
                }, 70, true));
                child.add(this.listBuilder.getSelectiveSliderElement("Roll", -180, 180, (int)rot[2], rollVal -> {
                    rot[2] = rollVal.intValue();
                    this.model.setPreviewRot(rot);
                }, 70, true));
            })), false));
            if (this.model.getSubModels().length > 0) {
                list.add(BoxManager.SEPARATION_LINE);
                i = 0;
                for (SubModel subModel : this.model.getSubModels()) {
                    String path = "model." + i++ + ".";
                    list.add(new BoxCategory(subModel.getName(), "cube").setTitle("SubModel " + subModel.getName()).fillEntries(comp -> {
                        comp.add(this.listBuilder.getSeparationElement("Configurable", UIConstants.UI_SEPARATION_COLOR));
                        comp.add(this.getVisibleSetting(subModel, path));
                        comp.add(this.getColorSetting(subModel, path));
                        comp.add(this.getIlluminationSetting(subModel, path));
                        comp.add(this.listBuilder.getSeparationElement("Position", UIConstants.UI_SEPARATION_COLOR));
                        this.fillPositionComp((List)comp, (PositionModel)subModel, path);
                        comp.add(this.listBuilder.getSeparationElement("Simple Animations", UIConstants.UI_SEPARATION_COLOR));
                        this.fillAnimationComp((List)comp, (AnimationModel)subModel);
                    }, true));
                }
            }
            list.add(BoxManager.SEPARATION_LINE);
            i = 0;
            for (TextureModel textureModel : this.model.getTextureModels()) {
                String path = "texture." + i++ + ".";
                list.add(new BoxCategory("Image #" + i, "image").fillEntries(comp -> this.fillTextureComp((List)comp, textureModel, path), true));
            }
            this.texIndex = i;
            if (this.texIndex < 5) {
                list.add(new BoxCategory("Add Image", "plus").setCallback(() -> {
                    String path = "texture." + this.texIndex++ + ".";
                    list.add(list.size() - 1, new BoxCategory("Texture #" + this.texIndex, "image").fillEntries(comp -> {
                        TextureModel textureModel = new TextureModel("", this.texIndex - 1);
                        this.fillTextureComp((List)comp, textureModel, path);
                        this.model.getTextureModels().add(textureModel);
                        this.guiInstance.refreshGui();
                    }, true));
                }));
            }
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    private void fillTextureComp(List<GuiComponent> list, TextureModel model, String path) {
        list.add((GuiComponent)this.listBuilder.getButtonElement("Remove Image", () -> {
            this.model.getTextureModels().remove(model);
            for (SubModel subModel : this.model.getSubModels()) {
                if (!subModel.hasTextures()) continue;
                subModel.getTextures().remove(model);
            }
            this.rootList.remove(model);
            this.guiInstance.refreshGui();
        }));
        list.add((GuiComponent)this.listBuilder.getSeparationElement("Image Settings", UIConstants.UI_SEPARATION_COLOR));
        Consumer<String> cbAllowed = url -> {
            if (TextureCategoryBuilder.isAllowedTextureUrl((String)url)) {
                model.setUrl(url);
            } else {
                NotificationHandler.sendError((String)"This url is not allowed!");
            }
        };
        list.add((GuiComponent)this.listBuilder.getTextBoxElement("Url", model.getUrl(), cbAllowed, cbAllowed, null));
        Consumer<String> cbMask = url -> {
            if (url.isEmpty() && model.getMaskUrl() != null && !model.getMaskUrl().isEmpty()) {
                model.setMaskUrl(null);
                model.setMask(null);
                if (model.getUrl() != null) {
                    this.guiInstance.onCustomAction(new String[]{"flush", model.getUrl() + this.model.getName()});
                }
            } else if (TextureCategoryBuilder.isAllowedTextureUrl((String)url)) {
                this.modelLoader.loadMask(model, this.model.getName(), url, success -> {
                    if (success.booleanValue()) {
                        if (model.getUrl() != null) {
                            this.guiInstance.onCustomAction(new String[]{"flush", model.getUrl() + this.model.getName()});
                        }
                        model.setMaskUrl(url);
                    } else {
                        NotificationHandler.sendError((String)"Failed to load mask!");
                    }
                });
            } else {
                NotificationHandler.sendError((String)"This url is not allowed!");
            }
        };
        list.add((GuiComponent)this.listBuilder.getTextBoxElement("Mask Url", model.getMaskUrl(), cbMask, cbMask, null).setDescription("Define a mask (shape) for the texture"));
        list.add((GuiComponent)this.listBuilder.getSliderElement("Opacity", 1, 10, (int)(model.getAlpha() * 10.0f), v -> model.setAlpha((float)v.intValue() / 10.0f)));
        list.add((GuiComponent)this.listBuilder.getColorPickerElement("Color Overlay", model.getColor(), model.getColor(), true, arg_0 -> ((TextureModel)model).setColor(arg_0)));
        list.add((GuiComponent)this.listBuilder.getSeparationElement("Configurable", UIConstants.UI_SEPARATION_COLOR));
        String key = "url";
        Optional<ModelConfig> ocfg = this.config.stream().filter(cf -> cf.key.equals(path + key)).findFirst();
        ModelConfig cfg = ocfg.orElseGet(() -> {
            ModelConfig c = new ModelConfig();
            c.name = "Url";
            c.key = path + key;
            c.value = model.getUrl();
            c.type = SettingType.TEXTBOX.getId();
            return c;
        });
        ListComponent textureUrl = this.listBuilder.getSwitchElement("Url Textbox", ocfg.isPresent(), false, enabled -> {
            if (!enabled.booleanValue()) {
                this.config.remove(cfg);
            } else {
                cfg.value = model.getUrl();
                this.config.add(cfg);
            }
        });
        Consumer<String> cb = s -> {
            cfg.name = s;
        };
        textureUrl.getChildComponents().add(this.listBuilder.getTextBoxElement("Config name", cfg.name, cb, cb, cb));
        list.add((GuiComponent)textureUrl);
        list.add((GuiComponent)this.listBuilder.getSeparationElement("Position", UIConstants.UI_SEPARATION_COLOR));
        List subModels = Arrays.stream(this.model.getSubModels()).map(SubModel::getName).collect(Collectors.toList());
        subModels.add(0, "None");
        list.add((GuiComponent)this.listBuilder.getListElement("Attach to SubModel", subModels, model.getBound() + 1, str -> {
            int i = subModels.indexOf(str);
            for (SubModel subModel : this.model.getSubModels()) {
                if (!subModel.hasTextures()) continue;
                subModel.getTextures().remove(model);
            }
            if (i > 0) {
                model.setBound(i - 1);
                this.model.getSubModels()[i - 1].getTextures().add(model);
            } else {
                model.setBound(-1);
            }
        }));
        this.fillPositionComp(list, (PositionModel)model, path);
        list.add((GuiComponent)this.listBuilder.getSeparationElement("Simple Animations", UIConstants.UI_SEPARATION_COLOR));
        this.fillAnimationComp(list, (AnimationModel)model);
    }

    private void fillPositionComp(List<GuiComponent> list, PositionModel model, String path) {
        List pos = Stream.of(ModelPosition.VALUES).map(Enum::name).collect(Collectors.toList());
        list.add((GuiComponent)this.listBuilder.getListElement("Position", pos, pos.indexOf(model.getPosition().name()), p -> model.setPosition(ModelPosition.valueOf((String)p))));
        list.add((GuiComponent)this.getPositionComp("Scale", 1, 200, 100.0f, model, "scale", path));
        list.add((GuiComponent)this.getPositionComp("Offset X", -180, 180, 100.0f, model, "x", path));
        list.add((GuiComponent)this.getPositionComp("Offset Y", -180, 180, 100.0f, model, "y", path));
        list.add((GuiComponent)this.getPositionComp("Offset Z", -180, 180, 100.0f, model, "z", path));
        list.add((GuiComponent)this.getPositionComp("Rotation Yaw", -180, 180, 1.0f, model, "yaw", path));
        list.add((GuiComponent)this.getPositionComp("Rotation Pitch", -180, 180, 1.0f, model, "pitch", path));
        list.add((GuiComponent)this.getPositionComp("Rotation Roll", -180, 180, 1.0f, model, "roll", path));
    }

    public ListComponent getColorSetting(SubModel model, String path) {
        String key = "color";
        Optional<ModelConfig> ocfg = this.config.stream().filter(cf -> cf.key.equals(path + key)).findFirst();
        ModelConfig cfg = ocfg.orElseGet(() -> {
            ModelConfig c = new ModelConfig();
            c.name = model.getName();
            c.key = path + key;
            c.value = model.getColor();
            c.type = SettingType.COLOR.getId();
            return c;
        });
        ListComponent comp = this.listBuilder.getSwitchElement("Color", ocfg.isPresent(), false, enabled -> {
            if (!enabled.booleanValue()) {
                this.config.remove(cfg);
            } else {
                this.config.add(cfg);
            }
        });
        ArrayList child = comp.getChildComponents();
        Consumer<String> cb = s -> {
            cfg.name = s;
        };
        child.add(this.listBuilder.getTextBoxElement("Config name", cfg.name, cb, cb, cb));
        child.add(this.listBuilder.getSwitchElement("Rainbow option", cfg.min != 1, false, enabled -> {
            cfg.min = enabled != false ? 0 : 1;
        }));
        return comp;
    }

    public ListComponent getVisibleSetting(SubModel model, String path) {
        String key = "visible";
        Optional<ModelConfig> ocfg = this.config.stream().filter(cf -> cf.key.equals(path + key)).findFirst();
        ModelConfig cfg = ocfg.orElseGet(() -> {
            ModelConfig c = new ModelConfig();
            c.name = "Visible";
            c.key = path + key;
            c.value = true;
            c.type = SettingType.SWITCH.getId();
            return c;
        });
        ListComponent comp = this.listBuilder.getSwitchElement("Visibility", ocfg.isPresent(), false, enabled -> {
            if (!enabled.booleanValue()) {
                this.config.remove(cfg);
            } else {
                this.config.add(cfg);
            }
        });
        Consumer<String> cb = s -> {
            cfg.name = s;
        };
        comp.getChildComponents().add(this.listBuilder.getTextBoxElement("Config name", cfg.name, cb, cb, cb));
        return comp;
    }

    public ListComponent getIlluminationSetting(SubModel model, String path) {
        String key = "illum";
        Optional<ModelConfig> ocfg = this.config.stream().filter(cf -> cf.key.equals(path + key)).findFirst();
        ModelConfig cfg = ocfg.orElseGet(() -> {
            ModelConfig c = new ModelConfig();
            c.name = "Illumination";
            c.key = path + key;
            c.type = SettingType.SWITCH.getId();
            c.value = false;
            return c;
        });
        ListComponent comp = this.listBuilder.getSwitchElement("Illumination", ocfg.isPresent(), false, enabled -> {
            if (!enabled.booleanValue()) {
                this.config.remove(cfg);
            } else {
                this.config.add(cfg);
            }
        });
        if (!(cfg.value instanceof Boolean)) {
            cfg.value = false;
            MoreCosmetics.log((String)"Invalid value for illumination");
        }
        ArrayList child = comp.getChildComponents();
        Consumer<String> cb = s -> {
            cfg.name = s;
        };
        child.add(this.listBuilder.getTextBoxElement("Config name", cfg.name, cb, cb, cb));
        child.add(this.listBuilder.getSwitchElement("Default illumination", ((Boolean)cfg.value).booleanValue(), false, enabled -> {
            cfg.value = enabled;
        }));
        return comp;
    }

    public ListComponent getPositionComp(String name, int min, int max, float mult, PositionModel model, String key, String path) {
        try {
            Field f = PositionModel.class.getDeclaredField(key);
            f.setAccessible(true);
            int currentVal = (int)(((Float)f.get(model)).floatValue() * mult);
            ListComponent comp = this.listBuilder.getSelectiveSliderElement(name, min, max, currentVal, i -> {
                try {
                    f.set(model, Float.valueOf((float)i.intValue() / mult));
                }
                catch (IllegalAccessException e) {
                    MoreCosmetics.catchThrowable((Throwable)e);
                }
            }, 120, true);
            return comp;
        }
        catch (Exception e) {
            MoreCosmetics.catchThrowable((Throwable)e);
            return null;
        }
    }

    private void fillAnimationComp(List<GuiComponent> list, AnimationModel model) {
        for (AnimationType type : AnimationType.VALUES) {
            if (type == AnimationType.MOTION) continue;
            list.add(this.listBuilder.getSwitchElement(type.getName(), model.hasAnimation(type), true, enabled -> {
                if (enabled.booleanValue()) {
                    model.getAnimations().put(type, new Animation(type));
                } else {
                    model.getAnimations().remove(type);
                }
            }).fillChild(comp -> {
                comp.add(this.listBuilder.getSeparationElement(type.getName()));
                Animation animation = model.getAnimation(type);
                if (animation.getAxis() != null) {
                    List pos = Stream.of(AnimationAxis.values()).map(Enum::name).collect(Collectors.toList());
                    comp.add(this.listBuilder.getListElement("Axis", pos, pos.indexOf(animation.getAxis().name()), p -> {
                        animation.setAxis(AnimationAxis.valueOf((String)p));
                        model.getAnimations().put(type, animation);
                    }));
                }
                comp.add(this.listBuilder.getSliderElement("Multiplier", -10, 10, (int)animation.getMultiplier(), i -> {
                    animation.setMultiplier((float)i.intValue());
                    model.getAnimations().put(type, animation);
                }, 84, true));
            }));
        }
    }

    public void onGuiClosed() {
        if (!this.quit) {
            MoreCosmetics.getInstance().runDelayed(() -> MoreCosmetics.getInstance().getVersionAdapter().showConfirmDialog("Confirm", LanguageHandler.get((String)"savechanges"), save -> {
                if (save.booleanValue()) {
                    this.save();
                } else {
                    this.cancel();
                }
            }));
        }
    }

    public void save() {
        MoreCosmetics.getInstance().getUserHandler().clearConfig(this.model.getId().intValue());
        this.checkTextureModels();
        this.config.removeIf(cfg -> this.filter(cfg.key, "model.", this.model.getSubModels().length) || this.filter(cfg.key, "texture.", this.model.getTextureModels().size()));
        this.model.setConfig(this.config.toArray(new ModelConfig[0]));
        this.modelLoader.updateModelFile(this.model);
        this.quit = true;
        MoreCosmetics.getInstance().openUI(false);
    }

    private void checkTextureModels() {
        for (int i = 0; i < this.model.getTextureModels().size(); ++i) {
            TextureModel tm = (TextureModel)this.model.getTextureModels().get(i);
            if (i == tm.getId()) continue;
            int id = i;
            MoreCosmetics.log((String)("Fixing texture model id from " + tm.getId() + " to " + id));
            this.config.stream().filter(cf -> cf.key.equals("texture." + tm.getId() + ".url")).findFirst().ifPresent(cfg -> {
                cfg.key = "texture." + id + ".url";
            });
            tm.setId(id);
        }
    }

    private boolean filter(String key, String path, int maxIndex) {
        if (key.startsWith(path)) {
            int index = Integer.parseInt(key.substring(path.length(), path.length() + 1));
            return index >= maxIndex;
        }
        return false;
    }

    public void cancel() {
        MoreCosmetics.getInstance().getUserHandler().clearConfig(this.model.getId().intValue());
        if (this.model.getFile() != null) {
            this.modelLoader.loadModel(this.model.getFile(), this.model.isCustom());
        }
        this.quit = true;
        MoreCosmetics.getInstance().openUI(false);
    }

    public static EditGui getInstance() {
        return instance;
    }

    public static void updateModel(CosmeticModel model) {
        if (instance != null) {
            instance.setModel(model);
        }
    }

    public static void refreshGui() {
        if (instance != null) {
            EditGui.instance.guiInstance.refreshGui();
        }
    }
}

