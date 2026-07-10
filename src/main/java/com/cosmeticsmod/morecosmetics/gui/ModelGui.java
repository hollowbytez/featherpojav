/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui$1
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 *  com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.config.ConfigAccessor
 *  com.cosmeticsmod.morecosmetics.models.config.ModelConfig
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.SettingType
 *  com.cosmeticsmod.morecosmetics.models.editor.LocalServer
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory
 *  com.cosmeticsmod.morecosmetics.nametags.EnumNametag
 *  com.cosmeticsmod.morecosmetics.nametags.Nametag
 *  com.cosmeticsmod.morecosmetics.nametags.NametagHandler
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo
 *  com.cosmeticsmod.morecosmetics.networking.packets.Packet
 *  com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo
 *  com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.user.presets.PresetManager
 *  com.cosmeticsmod.morecosmetics.user.presets.PresetManager$SortMode
 *  com.cosmeticsmod.morecosmetics.utils.Authenticator
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.LanguageHandler
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.OpenMode
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole
 *  com.cosmeticsmod.morecosmetics.utils.debug.DebugInfo
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.cosmeticsmod.morecosmetics.gui;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry;
import com.cosmeticsmod.morecosmetics.models.ModelHandler;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.config.ConfigAccessor;
import com.cosmeticsmod.morecosmetics.models.config.ModelConfig;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.config.SettingType;
import com.cosmeticsmod.morecosmetics.models.editor.LocalServer;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory;
import com.cosmeticsmod.morecosmetics.nametags.EnumNametag;
import com.cosmeticsmod.morecosmetics.nametags.Nametag;
import com.cosmeticsmod.morecosmetics.nametags.NametagHandler;
import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import com.cosmeticsmod.morecosmetics.nametags.logo.EnumLogo;
import com.cosmeticsmod.morecosmetics.networking.packets.Packet;
import com.cosmeticsmod.morecosmetics.networking.packets.PacketInfo;
import com.cosmeticsmod.morecosmetics.networking.utils.EnumInfo;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.user.presets.PresetManager;
import com.cosmeticsmod.morecosmetics.utils.Authenticator;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.LanguageHandler;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.OpenMode;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.cosmeticsmod.morecosmetics.utils.debug.DebugConsole;
import com.cosmeticsmod.morecosmetics.utils.debug.DebugInfo;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Exception performing whole class analysis ignored.
 */
public class ModelGui
implements CustomBoxGui {
    private static ModelGui instance;
    private BoxElementBuilder boxBuilder;
    private ListElementBuilder listBuilder;
    private MoreCosmetics mod;
    private ModelLoader modelLoader;
    private PresetManager presetManager;
    private HashMap<Integer, BoxCategory> catMap = new HashMap();
    private BoxManager.BoxGuiInstance guiInstance;
    private int lastTextureUpload = -1;
    private long lastProfileAdded;
    private static final int[] NONVALID;

    public ModelGui(BoxManager.BoxGuiInstance guiInstance) {
        this.guiInstance = guiInstance;
        instance = this;
        this.mod = MoreCosmetics.getInstance();
        this.modelLoader = this.mod.getModelLoader();
        this.boxBuilder = this.mod.getBoxElementBuilder();
        this.listBuilder = this.mod.getListElementBuilder();
        this.presetManager = new PresetManager();
        this.presetManager.loadPresets();
        for (ModelCategory cat : ModelCategory.VALUES) {
            this.catMap.put(cat.getId(), new BoxCategory(cat.getTitle(), cat.getLocationName()).setTitle(cat.getTitle() + " Cosmetics"));
        }
    }

    public void fillGui(List<BoxCategory> list) {
        boolean online = this.mod.getUserHandler().isOnlineMode();
        boolean onModdingPlatform = !"Vanilla".equals(CompatibilityManager.PLATFORM);
        ModConfig config = ModConfig.getConfig();
        list.clear();
        this.catMap.values().forEach(boxCat -> boxCat.getEntries().clear());
        this.modelLoader.getCosmetics().values().stream().filter(model -> !online || this.mod.getUserHandler().getOnlineCosmetics().containsKey(model.getId())).forEach(model -> ((BoxCategory)this.catMap.get(model.getCategory())).getEntries().add(this.getComponent(model)));
        this.catMap.values().stream().filter(l -> !l.getEntries().isEmpty()).forEach(list::add);
        if (NametagHandler.isNametagEnabled() && !online) {
            Nametag nametag = this.getUser().hasNametag() ? this.getUser().getNametag() : new Nametag("Nametag");
            list.add(new BoxCategory("Nametag", "nametag").fillEntries(guiComponents -> {
                guiComponents.add(this.listBuilder.getSwitchElement("Nametag", this.getUser().hasNametag(), false, enabled -> this.getUser().setNametag((Nametag)(enabled != false ? nametag : null))));
                guiComponents.add(this.listBuilder.getTextBoxElement("First Line", Utils.replaceColorCodesReverse((String)nametag.getTag()), null, null, arg_0 -> ((Nametag)nametag).setTag(arg_0)));
                guiComponents.add(this.listBuilder.getTextBoxElement("Second Line", Utils.replaceColorCodesReverse((String)nametag.getSecondTag()), null, null, arg_0 -> ((Nametag)nametag).setSecondTag(arg_0)));
                guiComponents.add(this.listBuilder.getSliderElement("Size", 1, 5, nametag.getScaleNum(), arg_0 -> ((Nametag)nametag).setScaleNum(arg_0)));
                guiComponents.add(this.listBuilder.getListElement("Mode", Stream.of(EnumNametag.values()).map(Enum::name).collect(Collectors.toList()), nametag.getMode().ordinal(), mode -> nametag.setMode(EnumNametag.valueOf((String)mode))));
                TreeMap map = new TreeMap();
                ArrayList ids = new ArrayList(this.mod.getFontHandler().getFonts().keySet());
                ids.forEach(id -> {
                    FontImage cfr_ignored_0 = (FontImage)map.put(this.mod.getFontHandler().getFont((Integer)id).name, this.mod.getNametagHandler().getFontRendererMap().get(id));
                });
                guiComponents.add(this.listBuilder.getListElement("Font", map, this.mod.getFontHandler().getFont((Integer)Integer.valueOf((int)nametag.getFont())).name, ids.indexOf(nametag.getFont()), name -> nametag.setFont(this.mod.getFontHandler().getId(name))));
                ArrayList<TextureCategory> textureCatList = new ArrayList<TextureCategory>();
                TextureCategory socialMediaCat = new TextureCategory("CosmeticsMod", EnumLogo.SOCIALMEDIA.format("cm"));
                socialMediaCat.fillEntries(textureList -> {
                    textureList.add(new TextureEntry("Youtube", EnumLogo.SOCIALMEDIA.format("yt")));
                    textureList.add(new TextureEntry("Twitch", EnumLogo.SOCIALMEDIA.format("tw")));
                    textureList.add(new TextureEntry("Discord", EnumLogo.SOCIALMEDIA.format("dc")));
                    textureList.add(new TextureEntry("Instagram", EnumLogo.SOCIALMEDIA.format("ig")));
                    textureList.add(new TextureEntry("Snapchat", EnumLogo.SOCIALMEDIA.format("sc")));
                    textureList.add(new TextureEntry("TikTok", EnumLogo.SOCIALMEDIA.format("tk")));
                    textureList.add(new TextureEntry("Twitter", EnumLogo.SOCIALMEDIA.format("tr")));
                    textureList.add(new TextureEntry("Skype", EnumLogo.SOCIALMEDIA.format("sk")));
                    textureList.add(new TextureEntry("Steam", EnumLogo.SOCIALMEDIA.format("st")));
                    textureList.add(new TextureEntry("Fiverr", EnumLogo.SOCIALMEDIA.format("fr")));
                    textureList.add(new TextureEntry("Reddit", EnumLogo.SOCIALMEDIA.format("rd")));
                    textureList.add(new TextureEntry("SoundCloud", EnumLogo.SOCIALMEDIA.format("sd")));
                    textureList.add(new TextureEntry("Teamspeak", EnumLogo.SOCIALMEDIA.format("ts")));
                    textureList.add(new TextureEntry("Spotify", EnumLogo.SOCIALMEDIA.format("sp")));
                }, 1);
                textureCatList.add(socialMediaCat);
                guiComponents.add(this.listBuilder.getTextureElement("Logo", textureCatList, 0, 3, texture -> nametag.setLogoURL(texture == null ? null : texture.getImageURL())));
                if (this.mod.getUserHandler().hasOnlineNametag()) {
                    guiComponents.add(this.listBuilder.getButtonElement("Change online Nametag", () -> Authenticator.getAuthenticator().openPanel()));
                }
            }, true));
        }
        if (ModelHandler.isCloakEnabled()) {
            list.add(new BoxCategory("Cloak", "cloak").fillEntries(guiComponents -> {
                ListComponent cloakSwitch = this.listBuilder.getSwitchElement("Cloak", this.getUser().getCloak().isEnabled(), false, enabled -> {
                    this.getUser().getCloak().toggle(enabled);
                    this.mod.getUserHandler().checkSettingsChanged();
                });
                guiComponents.add(cloakSwitch);
                ArrayList textureCatList = new ArrayList();
                ListComponent cloakLibrary = this.listBuilder.getTextureElement("Cloak Library", textureCatList, 0, 3, texture -> {
                    String url = this.getUrl(texture);
                    MoreCosmetics.debug((String)("[TEXTURE] Set texture: " + url));
                    if (this.validTexture(url)) {
                        this.getUser().getCloak().update(url);
                        cloakSwitch.update((Object)this.getUser().getCloak().isActive());
                        this.mod.getUserHandler().checkSettingsChanged();
                    }
                });
                cloakLibrary.onDiscover(() -> TextureCategoryBuilder.fetchOnline((String)"https://cl.cosmeticsmod.com/textures/cloaks.json", textureCatList::addAll));
                guiComponents.add(cloakLibrary);
                guiComponents.add(this.getFileSelectUpload(online, "Cloak", "cloaks", file -> {
                    MoreCosmetics.debug((String)("[TEXTURE] Set texture: " + file.getAbsolutePath()));
                    this.getUser().getCloak().update(file.getAbsolutePath());
                    cloakSwitch.update((Object)this.getUser().getCloak().isActive());
                }, new String[]{"png", "gif"}));
                guiComponents.add(this.listBuilder.getSwitchElement("Use Minecraft Renderer", config.cloakCompatibility, false, enabled -> {
                    config.cloakCompatibility = enabled;
                }).setDescription(LanguageHandler.get((String)"cloakcompatibility")));
            }, true));
        }
        list.add(BoxManager.SEPARATION_LINE);
        if (online) {
            list.add(new BoxCategory("Store", "cart").setCallback(() -> {
                String storeUrl = this.mod.getInfo().store;
                if (storeUrl != null) {
                    this.mod.getVersionAdapter().showConfirmDialog("CosmeticsMod Store", this.getRedirectionMsg(storeUrl), () -> this.mod.getVersionAdapter().openBrowser(storeUrl));
                }
            }));
        }
        list.add(new BoxCategory("Profile", "profile").fillEntries(guiComponents -> {
            boolean connected = this.mod.getConnection().isConnected();
            guiComponents.add(this.listBuilder.getTextElement("Username", this.mod.getVersionAdapter().getPlayerName()));
            guiComponents.add(this.listBuilder.getTextElement("Status", connected ? "\u00a7aConnected" : "\u00a7cNot connected"));
            if (connected) {
                guiComponents.add(this.listBuilder.getTextElement("First Start", MoreCosmetics.DATE_FORMAT.format(this.mod.getUserHandler().getFirstJoin())));
                guiComponents.add(this.listBuilder.getTextElement("Login Streak", this.mod.getUserHandler().getLoginStreak() + ""));
                guiComponents.add(this.listBuilder.getTextElement("Online Cosmetics", this.mod.getUserHandler().getOnlineCount() + " / " + this.mod.getModelLoader().getOnlineCount()));
            } else {
                guiComponents.add(this.listBuilder.getButtonElement("Connect", () -> this.mod.getConnection().reconnectAttempt(success -> {
                    if (success.booleanValue()) {
                        ModelGui.refreshGui();
                        NotificationHandler.sendSuccess((String)"Connection", (String)LanguageHandler.get((String)"connectionsuccess"));
                    } else {
                        NotificationHandler.sendError((String)LanguageHandler.get((String)"connectionfailed"));
                    }
                })));
            }
            if (this.mod.getUserHandler().hasOnlineNametag()) {
                guiComponents.add(this.listBuilder.getButtonElement("Change online Nametag", () -> Authenticator.getAuthenticator().openPanel()));
            }
        }, true));
        list.add(new BoxCategory("Presets", "presets").fillEntries(guiComponents -> {
            guiComponents.add(this.listBuilder.getButtonElement("Add new Preset", () -> {
                if (System.currentTimeMillis() - this.lastProfileAdded < 2000L) {
                    return;
                }
                this.lastProfileAdded = System.currentTimeMillis();
                int presets = this.presetManager.getPresets().size();
                if (presets < 100) {
                    String name = "Preset #" + (presets + 1);
                    this.presetManager.saveToProfile(name, this.mod.getUserHandler().getCurrentUser(), this.isOnline());
                    ModelGui.refreshGui();
                } else {
                    NotificationHandler.sendError((String)"You can't have more than 100 presets!");
                }
            }));
            guiComponents.add(this.listBuilder.getListElement("Sort by", Stream.of(PresetManager.SortMode.values()).map(Enum::name).collect(Collectors.toList()), this.presetManager.getMode().ordinal(), mode -> {
                this.presetManager.setMode(PresetManager.SortMode.valueOf((String)mode));
                this.presetManager.sortPresets();
                ModelGui.refreshGui();
            }));
            guiComponents.add(this.listBuilder.getSeparationElement((online ? "Online" : "Offline") + " User Presets", UIConstants.UI_SEPARATION_COLOR));
            this.presetManager.getPresets().stream().filter(pr -> pr.isOnline() == online).forEach(preset -> guiComponents.add(this.listBuilder.getButtonElement(preset.getName(), "Load", () -> {
                this.presetManager.applyPreset(this.mod.getUserHandler().getCurrentUser(), preset);
                NotificationHandler.sendSuccess((String)"Preset", (String)"Preset loaded!");
                ModelGui.refreshGui();
            }).fillChild(subList -> {
                Consumer<String> cb = name -> {
                    if (name.length() > 0 && !name.trim().isEmpty()) {
                        this.presetManager.updateName(preset, name);
                        ModelGui.refreshGui();
                    }
                };
                subList.add(this.listBuilder.getTextBoxElement("Name", preset.getName(), cb, null, null, cb, 20));
                subList.add(this.listBuilder.getButtonElement("Overwrite", () -> {
                    this.presetManager.updatePreset(preset, this.mod.getUserHandler().getCurrentUser());
                    NotificationHandler.sendSuccess((String)"Preset", (String)"Preset updated!");
                    ModelGui.refreshGui();
                }));
                subList.add(this.listBuilder.getButtonElement("Delete", () -> {
                    this.presetManager.deletePreset(preset);
                    NotificationHandler.sendSuccess((String)"Preset", (String)"Preset deleted!");
                    ModelGui.refreshGui();
                }));
                subList.add(this.listBuilder.getSwitchElement("Load Cosmetics", preset.cosmetics, false, enabled -> this.presetManager.updateApplies(preset, enabled.booleanValue(), preset.cloak, preset.nametag)));
                subList.add(this.listBuilder.getSwitchElement("Load Cloak", preset.cloak, false, enabled -> this.presetManager.updateApplies(preset, config.cosmetics, enabled.booleanValue(), preset.nametag)));
                subList.add(this.listBuilder.getSwitchElement("Load Nametag", preset.nametag, false, enabled -> this.presetManager.updateApplies(preset, preset.cosmetics, preset.cloak, enabled.booleanValue())));
            })));
        }, true));
        list.add(new BoxCategory("Settings", "settings").fillEntries(guiComponents -> {
            guiComponents.add(this.listBuilder.getSeparationElement("General", UIConstants.UI_SEPARATION_COLOR));
            if (CompatibilityManager.shouldShowName()) {
                guiComponents.add(this.listBuilder.getSwitchElement("Show Name", config.showName, false, enabled -> {
                    config.showName = enabled;
                }));
            }
            guiComponents.add(this.listBuilder.getSwitchElement("Show Preview", config.showPreview, false, enabled -> {
                config.showPreview = enabled;
            }));
            guiComponents.add(this.listBuilder.getSwitchElement("Show Cosmetics", config.cosmetics, false, enabled -> {
                config.cosmetics = enabled;
            }));
            guiComponents.add(this.listBuilder.getSwitchElement("Show Nametags", config.nametags, false, enabled -> {
                config.nametags = enabled;
            }));
            guiComponents.add(this.listBuilder.getSwitchElement("Show Cloaks", config.cloaks, false, enabled -> {
                config.cloaks = enabled;
            }));
            guiComponents.add(this.listBuilder.getSliderElement("Rainbow Speed", 1, 10, config.rainbowSpeed, i -> {
                config.rainbowSpeed = i;
            }, 60, true));
            guiComponents.add(this.listBuilder.getSeparationElement("Cosmetic Behavior", UIConstants.UI_SEPARATION_COLOR));
            guiComponents.add(this.listBuilder.getSwitchElement("Hide When Wearing Armor", config.armorMode, false, enabled -> {
                config.armorMode = enabled;
            }).setDescription(LanguageHandler.get((String)"armormode")));
            if (CompatibilityManager.isOnFabric() || CompatibilityManager.isOnForge() && CompatibilityManager.isVersionOrHigher((int)1202)) {
                guiComponents.add(this.listBuilder.getSwitchElement("Replace Minecraft Shield", config.replaceShield, false, enabled -> {
                    config.replaceShield = enabled;
                }));
            }
            if (CompatibilityManager.getVersionAsNumber() > 113) {
                guiComponents.add(this.listBuilder.getSwitchElement("Display Damage Tint", config.damageTint, false, enabled -> {
                    config.damageTint = enabled;
                }));
            }
            guiComponents.add(this.listBuilder.getSeparationElement("GUI", UIConstants.UI_SEPARATION_COLOR));
            guiComponents.add(this.listBuilder.getSwitchElement("Custom Font", config.customFont, false, enabled -> {
                config.customFont = enabled;
                this.mod.getFontHandler().setCustomFont(config.customFont);
            }));
            guiComponents.add(this.listBuilder.getSwitchElement("Animated Preview", config.animatedPreview, false, enabled -> {
                config.animatedPreview = enabled;
            }));
            guiComponents.add(this.listBuilder.getColorPickerElement("Accent Color", config.accentColor, UIConstants.UI_ACCENT_COLOR, true, i -> {
                UIConstants.UI_ACCENT_COLOR = config.accentColor = i.intValue();
            }));
            guiComponents.add(this.listBuilder.getKeyBoxElement("Open GUI Key", config.key, key -> {
                config.key = key;
            }));
            guiComponents.add(this.listBuilder.getSliderElement("GUI Scale", 1, 10, config.guiScale, i -> {
                config.guiScale = i;
                this.mod.getVersionAdapter().setGuiScale(0.5f + (float)config.guiScale / 20.0f);
            }, 60, false));
            if (onModdingPlatform) {
                guiComponents.add(this.listBuilder.getListElement("GUI Open Mode", Stream.of(OpenMode.values()).map(Enum::name).collect(Collectors.toList()), config.openMode, mode -> {
                    config.openMode = OpenMode.valueOf((String)mode).ordinal();
                }));
            } else if (config.openMode != OpenMode.ALL.ordinal()) {
                config.openMode = OpenMode.ALL.ordinal();
            }
            guiComponents.add(this.listBuilder.getSeparationElement("Userdata", UIConstants.UI_SEPARATION_COLOR));
            guiComponents.add(this.listBuilder.getButtonElement("Refresh Userdata", () -> this.mod.getUserHandler().reload(() -> {
                this.guiInstance.refreshGui();
                NotificationHandler.sendInfo((String)"Settings", (String)"Userdata refreshed");
            })));
            guiComponents.add(this.listBuilder.getButtonElement("Sync Userdata", () -> this.mod.getUserHandler().sync(() -> {
                this.guiInstance.refreshGui();
                NotificationHandler.sendInfo((String)"Settings", (String)"Userdata synced!");
            })));
            guiComponents.add(this.listBuilder.getButtonElement("Reset Userdata", () -> this.mod.getVersionAdapter().showConfirmDialog("Reset", LanguageHandler.get((String)"datareset"), () -> {
                this.mod.getConnection().sendAsync((Packet)new PacketInfo(EnumInfo.RESET_DATA));
                this.mod.getUserHandler().resetData();
                this.guiInstance.refreshGui();
                NotificationHandler.sendInfo((String)"Settings", (String)"Userdata reset!");
            })).setDescription(LanguageHandler.get((String)"resetinfo")));
            guiComponents.add(this.listBuilder.getSeparationElement("Editor", UIConstants.UI_SEPARATION_COLOR));
            guiComponents.add(this.listBuilder.getSwitchElement("Editor Mode", config.editorMode, false, enabled -> {
                config.editorMode = enabled;
                LocalServer.toggle((boolean)enabled);
                this.guiInstance.refreshGui();
                NotificationHandler.sendInfo((String)"Settings", (String)("Editor Mode: " + (enabled != false ? "\u00a7aON" : "\u00a7cOFF")));
            }));
            guiComponents.add(this.listBuilder.getButtonElement("Open Folder", () -> this.mod.getVersionAdapter().openFile(this.modelLoader.getLoadDir())));
            guiComponents.add(this.listBuilder.getButtonElement("Reload Cosmetics", () -> {
                this.modelLoader.loadCustomModels();
                this.guiInstance.refreshGui();
                NotificationHandler.sendInfo((String)"Settings", (String)"Cosmetics reloaded!");
            }));
            guiComponents.add(this.listBuilder.getSeparationElement("Debug", UIConstants.UI_SEPARATION_COLOR));
            guiComponents.add(this.listBuilder.getButtonElement("Debug Info", () -> {
                String debugInfo = DebugInfo.getClientInfo();
                MoreCosmetics.debug((String)debugInfo);
                this.mod.getVersionAdapter().copyToClipboard(debugInfo);
                NotificationHandler.sendInfo((String)"Settings", (String)"Copied to clipboard!");
            }).setDescription(LanguageHandler.get((String)"debuginfo")));
            guiComponents.add(this.listBuilder.getButtonElement("Debug Console", DebugConsole::open));
            guiComponents.add(this.listBuilder.getSeparationElement("Miscellaneous", UIConstants.UI_SEPARATION_COLOR));
            guiComponents.add(this.listBuilder.getButtonElement("Credentials", () -> this.mod.getVersionAdapter().openBrowser("https://cosmeticsmod.com/credits")));
            guiComponents.add(this.listBuilder.getButtonElement("Privacy Policy", () -> this.mod.getVersionAdapter().openBrowser("https://cosmeticsmod.com/privacy")));
        }, true));
        UIConstants.UI_ACCENT_COLOR = config.accentColor;
    }

    public void onGuiClosed() {
        this.mod.getUserHandler().saveUserConfig();
        ModConfig.saveConfig();
    }

    private ListComponent getFileSelectUpload(boolean online, String texture, String folder, Consumer<File> fileSelector, String ... ext) {
        ListComponent comp = online ? this.listBuilder.getButtonElement(texture + " Upload", () -> {
            if (this.lastTextureUpload != this.getUser().getCosmetics().size()) {
                this.lastTextureUpload = this.getUser().getCosmetics().size();
                this.mod.getConnection().sendAsync((Packet)new PacketInfo(EnumInfo.TEXTURE_LOGIN));
            }
            this.mod.getVersionAdapter().showConfirmDialog(texture + " Upload", this.getRedirectionMsg(this.mod.getUserHandler().getUploadUrl()), () -> this.mod.getVersionAdapter().openBrowser(this.mod.getUserHandler().getUploadUrl()));
        }) : this.listBuilder.getButtonElement("File Selector", () -> this.openCopyFileChooser(folder, "Texture file", fileSelector, ext));
        return comp;
    }

    private BoxComponent getComponent(CosmeticModel model) {
        BoxComponent element = this.boxBuilder.getCosmeticElement(model, this.getUser(), (enabled) -> this.handleGetComp68(model, enabled));
        if (ModConfig.loadConfig().editorMode && model.isCustom()) {
            element.getChildComponents().add(this.listBuilder.getButtonElement("Editor", () -> this.handleGetComp69(model)));
        }
        if (!model.hasConfig()) {
            return element;
        }
        element.getChildComponents().add(this.listBuilder.getSeparationElement(model.getName()));
        ModelData data = this.getUser().getCosmetics().getOrDefault(model.getId(), this.mod.getUserHandler().loadData(model, true));
        for (ModelConfig config : model.getConfig()) {
            SettingType type = SettingType.byId(config.type);
            if (type == null) continue;
            Object defaultVal = data != null ? ConfigAccessor.get(config, data) : config.value;
            if (data == null && config.mult != 1.0f && defaultVal instanceof Number) {
                defaultVal = ((Number)defaultVal).floatValue() / config.mult;
            }
            ListComponent comp = null;
            switch (type) {
                case SWITCH: {
                    if (config.min != 0 || config.max != 0) {
                        float vF = (float)config.min / config.mult;
                        float vT = (float)config.max / config.mult;
                        boolean bool = defaultVal != null && ((Number)defaultVal).floatValue() == vT;
                        comp = this.listBuilder.getSwitchElement(config.name, bool, true, (enabled) -> this.handleGetComp70(config, model, vT, vF, enabled));
                    } else {
                        boolean bool = defaultVal != null && (Boolean)defaultVal;
                        comp = this.listBuilder.getSwitchElement(config.name, bool, true, (enabled) -> this.handleGetComp71(config, model, enabled));
                    }
                    break;
                }
                case TEXTURE: {
                    if (config.src != null && config.src.startsWith("http")) {
                        ArrayList<com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory> textureCatList = new ArrayList<>();
                        comp = this.listBuilder.getTextureElement(config.name, textureCatList, 0, config.max == 0 ? 3 : config.max, (texture) -> this.handleGetComp72(config, model, element, texture));
                        comp.onDiscover(() -> ModelGui.handleGetComp73(config, textureCatList));
                        element.getChildComponents().add(comp);
                        element.getChildComponents().add(this.getFileSelectUpload(this.mod.getUserHandler().isOnlineMode(), "Texture", model.getName().toLowerCase(), (file) -> this.handleGetComp74(config, model, file), new String[]{"png", "gif"}));
                        continue;
                    }
                    break;
                }
                case TEXTBOX: {
                    String str = defaultVal == null ? "" : (String)defaultVal;
                    Consumer<String> cb = (url) -> this.handleGetComp75(config, model, url);
                    comp = this.listBuilder.getTextBoxElement(config.name, str, cb, cb, null);
                    break;
                }
                case SLIDER: {
                    float currentVal = defaultVal == null ? 0.0f : ((Number)defaultVal).floatValue();
                    comp = this.listBuilder.getSliderElement(config.name, config.min, config.max, (int)(currentVal * config.mult), (i) -> this.handleGetComp76(config, model, i));
                    break;
                }
                case ITEM: {
                    int currentItem = defaultVal == null ? 0 : ((Number)defaultVal).intValue();
                    comp = this.listBuilder.getNumberBoxElement(config.name, 1, 426, currentItem, (id) -> this.handleGetComp77(config, model, id));
                    break;
                }
                case NUMBERBOX: {
                    int currentNum = defaultVal == null ? 0 : ((Number)defaultVal).intValue();
                    comp = this.listBuilder.getNumberBoxElement(config.name, config.min, config.max, currentNum, (id) -> this.handleGetComp78(config, model, id));
                    break;
                }
                case COLOR: {
                    int currentColor = defaultVal == null ? 0 : ((Number)defaultVal).intValue();
                    currentColor = currentColor == 1 ? currentColor : -16777216 | currentColor;
                    int defaultColor = currentColor == 1 ? ((Number)config.value).intValue() : currentColor;
                    comp = this.listBuilder.getColorPickerElement(config.name, currentColor, defaultColor, config.min == 0, (rgb) -> this.handleColorPick79(config, model, rgb));
                    break;
                }
            }
            if (comp != null) {
                element.getChildComponents().add(comp);
            }
        }
        return element;
    }

    private boolean validTexture(String url) {
        if (!this.mod.getUserHandler().isOnlineMode()) {
            return true;
        }
        return url == null || TextureCategoryBuilder.isNoMaskNeeded((String)url);
    }

    private void applyColors(CosmeticModel model, ArrayList<ListComponent> components, TextureEntry texture) {
        if (texture == null) {
            return;
        }
        int[] colors = texture.getColors();
        if (colors == null) {
            return;
        }
        int index = 0;
        for (ModelConfig colorConf : model.getConfig()) {
            int color;
            if (colorConf.type != SettingType.COLOR.getId()) continue;
            if (index >= colors.length) break;
            if ((color = colors[index++]) == -1) continue;
            this.update(colorConf, model.getId().intValue(), (Object)color);
        }
        index = 0;
        for (ListComponent component : components) {
            int color;
            if (component.getType() != SettingType.COLOR.getId()) continue;
            if (index >= colors.length) break;
            if ((color = colors[index++]) == -1) continue;
            component.update((Object)color);
        }
    }

    private void openCopyFileChooser(String folder, String title, Consumer<File> callback, String ... extensions) {
        File textureDir = new File(MoreCosmetics.ROOT_DIR, "textures/" + folder);
        textureDir.mkdirs();
        this.mod.getVersionAdapter().openFileChooser(title, textureDir, file -> {
            if (!file.getParentFile().equals(textureDir)) {
                try {
                    File target = new File(textureDir, file.getName());
                    Files.copy(file.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    callback.accept(target);
                }
                catch (IOException e) {
                    MoreCosmetics.catchThrowable((Throwable)e);
                    callback.accept((File)file);
                }
            }
        }, title, extensions);
    }

    private String getRedirectionMsg(String url) {
        int trim = url.indexOf("/", 10);
        if (trim != -1 && url.length() > trim) {
            url = url.substring(0, trim + 1);
        }
        return String.format(LanguageHandler.get((String)"redirection"), url);
    }

    private String getUrl(TextureEntry texture) {
        if (texture == null) {
            return null;
        }
        return texture.getImageURL();
    }

    public void update(ModelConfig config, int id, Object obj) {
        ModelData data = (ModelData)this.getUser().getCosmetics().get(id);
        if (data != null) {
            ConfigAccessor.set((ModelConfig)config, (ModelData)data, (Object)obj);
            this.mod.getUserHandler().saveData(id, data);
        }
    }

    public String getHeadline() {
        String headLine = "\u00a7fPowered by CosmeticsMod";
        return headLine;
    }

    public boolean isOnline() {
        return this.mod.getUserHandler().isOnlineMode();
    }

    private boolean isValid(int itemID) {
        return !ArrayUtils.contains((int[])NONVALID, (int)itemID);
    }

    private CosmeticUser getUser() {
        return this.mod.getUserHandler().getCurrentUser();
    }

    public static ModelGui getInstance() {
        return instance;
    }

    public static void refreshGui() {
        if (instance != null) {
            ModelGui.instance.guiInstance.refreshGui();
        }
    }

    private /* synthetic */ void handleColorPick79(ModelConfig config, CosmeticModel model, Integer rgb) {
        this.update(config, model.getId().intValue(), (Object)rgb);
    }

    private /* synthetic */ void handleGetComp78(ModelConfig config, CosmeticModel model, Integer id) {
        this.update(config, model.getId().intValue(), (Object)id);
    }

    private /* synthetic */ void handleGetComp77(ModelConfig config, CosmeticModel model, Integer id) {
        if (this.isValid(id.intValue())) {
            this.update(config, model.getId().intValue(), (Object)id);
        }
    }

    private /* synthetic */ void handleGetComp76(ModelConfig config, CosmeticModel model, Integer i) {
        this.update(config, model.getId().intValue(), (Object)Float.valueOf((float)i.intValue() / config.mult));
    }

    private /* synthetic */ void handleGetComp75(ModelConfig config, CosmeticModel model, String url) {
        this.update(config, model.getId().intValue(), (Object)url);
    }

    private /* synthetic */ void handleGetComp74(ModelConfig config, CosmeticModel model, File file) {
        MoreCosmetics.debug((String)("[TEXTURE] Set texture: " + file.getAbsolutePath()));
        this.update(config, model.getId().intValue(), (Object)file.getAbsolutePath());
    }

    private static /* synthetic */ void handleGetComp73(ModelConfig config, ArrayList textureCatList) {
        TextureCategoryBuilder.fetchOnline((String)config.src, textureCatList::addAll);
    }

    private /* synthetic */ void handleGetComp72(ModelConfig config, CosmeticModel model, BoxComponent element, TextureEntry texture) {
        String url = this.getUrl(texture);
        MoreCosmetics.debug((String)("[TEXTURE] Set texture: " + url));
        if (this.validTexture(url)) {
            this.update(config, model.getId().intValue(), (Object)url);
            this.applyColors(model, element.getChildComponents(), texture);
        }
    }

    private /* synthetic */ void handleGetComp71(ModelConfig config, CosmeticModel model, Boolean enabled) {
        this.update(config, model.getId().intValue(), (Object)enabled);
    }

    private /* synthetic */ void handleGetComp70(ModelConfig config, CosmeticModel model, float vT, float vF, Boolean enabled) {
        this.update(config, model.getId().intValue(), (Object)Float.valueOf(enabled != false ? vT : vF));
    }

    private /* synthetic */ void handleGetComp69(CosmeticModel model) {
        this.mod.getVersionAdapter().showEditorScreen(model);
    }

    private /* synthetic */ void handleGetComp68(CosmeticModel model, Boolean enabled) {
        this.mod.getUserHandler().toggleCosmetic(enabled.booleanValue(), model);
    }

    static {
        NONVALID = new int[]{8, 9, 10, 11, 26, 34, 36, 43, 51, 55, 59, 60, 62, 63, 64, 68, 71, 74, 75, 83, 90, 92, 93, 94, 104, 105, 115, 117, 118, 119, 124, 125, 127, 132, 140, 141, 142, 144, 149, 150};
    }
}

