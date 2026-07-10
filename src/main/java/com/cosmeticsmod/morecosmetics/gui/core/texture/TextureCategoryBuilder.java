/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.GuiLogo
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package com.cosmeticsmod.morecosmetics.gui.core.texture;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.texture.GuiLogo;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;

/*
 * Exception performing whole class analysis ignored.
 */
public class TextureCategoryBuilder {
    private static final Set<String> trustedUrls = new HashSet();
    private static final Set<String> allowedUrls = new HashSet();

    public static void addTrustedUrl(String ... url) {
        trustedUrls.addAll(Arrays.asList(url));
    }

    public static void addAllowedUrl(String ... url) {
        allowedUrls.addAll(Arrays.asList(url));
    }

    public static boolean isAllowed(String url) {
        return !MoreCosmetics.getInstance().getUserHandler().isOnlineMode() || !url.contains(".gif");
    }

    private static boolean isUrlInCollection(String url, Collection<String> urls) {
        String u = url.toLowerCase();
        for (String trusted : urls) {
            if (!u.startsWith(trusted)) continue;
            return true;
        }
        return false;
    }

    public static boolean isNoMaskNeeded(String url) {
        return TextureCategoryBuilder.isUrlInCollection((String)url, (Collection)trustedUrls);
    }

    public static boolean isAllowedTextureUrl(String url) {
        return TextureCategoryBuilder.isUrlInCollection((String)url, (Collection)allowedUrls) || TextureCategoryBuilder.isUrlInCollection((String)url, (Collection)trustedUrls);
    }

    public static void fetchOnline(String fetchURL, Consumer<ArrayList<TextureCategory>> callback) {
        ArrayList categories = new ArrayList();
        MoreCosmetics.debug((String)("Fetching categories from " + fetchURL));
        MoreCosmetics.EXECUTOR.execute(() -> {
            JsonElement categoryElement = Utils.readJsonFromUrl((String)fetchURL, (boolean)false, (Object[])new Object[0]);
            if (categoryElement == null) {
                MoreCosmetics.log((String)("Failed to fetch categories from " + fetchURL));
                return;
            }
            for (JsonElement element : categoryElement.getAsJsonArray()) {
                String referenceURL = "unknown";
                try {
                    JsonObject categoryObj = element.getAsJsonObject();
                    String categoryName = categoryObj.get("name").getAsString();
                    String categoryImage = categoryObj.get("img").getAsString();
                    referenceURL = categoryObj.get("url").getAsString();
                    String fRefUrl = TextureCategoryBuilder.formatURL((String)referenceURL, (String)"", (int)1);
                    JsonElement elementArray = Utils.readJsonFromUrl((String)fRefUrl, (boolean)false, (Object[])new Object[0]);
                    if (elementArray == null) {
                        MoreCosmetics.log((String)("Failed to fetch entry from " + fRefUrl));
                        continue;
                    }
                    JsonObject resources = elementArray.getAsJsonObject();
                    JsonArray entryArray = resources.getAsJsonArray("files");
                    if (entryArray.size() == 0) {
                        MoreCosmetics.log((String)("Requested URL (" + fRefUrl + ") is empty"));
                        continue;
                    }
                    int pages = resources.has("pages") ? resources.get("pages").getAsInt() : 1;
                    TextureCategory category = new TextureCategory(categoryName, categoryImage);
                    category.fillEntries(textureEntries -> textureEntries.addAll(TextureCategoryBuilder.fetchFromArray((JsonArray)entryArray)), pages);
                    if (categoryObj.has("subline")) {
                        category.setSubline(categoryObj.get("subline").getAsString());
                    }
                    if (categoryObj.has("icons")) {
                        category.setIcons((GuiLogo[])MoreCosmetics.GSON.fromJson(categoryObj.get("icons"), GuiLogo[].class));
                        UserHandler handler = MoreCosmetics.getInstance().getUserHandler();
                        category.prepareIconUrl(handler.getTextureToken(), handler.getCurrentUser().getUuid().toString());
                    }
                    category.setOnlineUpdater((updateURL, search, target, page) -> {
                        try {
                            JsonArray entryArray1;
                            String fUrl = TextureCategoryBuilder.formatURL((String)updateURL, (String)search, (int)page);
                            JsonElement elementArray1 = Utils.readJsonFromUrl((String)fUrl, (boolean)false, (Object[])new Object[0]);
                            if (elementArray1 == null) {
                                MoreCosmetics.log((String)("Failed to fetch entry from " + fUrl));
                                return new ArrayList();
                            }
                            JsonObject resources1 = elementArray1.getAsJsonObject();
                            JsonArray jsonArray = entryArray1 = resources1.has("files") ? resources1.getAsJsonArray("files") : new JsonArray();
                            if (entryArray1.size() == 0) {
                                MoreCosmetics.log((String)("Requested URL (" + fUrl + ") is empty while page update " + page));
                                return new ArrayList();
                            }
                            if (target != null && resources1.has("pages")) {
                                target.setMaxPages(resources1.get("pages").getAsInt());
                            }
                            return TextureCategoryBuilder.fetchFromArray((JsonArray)entryArray1);
                        }
                        catch (Exception e) {
                            MoreCosmetics.log((String)("Failed to fetch entry from " + updateURL + ": " + e));
                            return new ArrayList();
                        }
                    }, referenceURL);
                    categories.add(category);
                }
                catch (Exception e) {
                    MoreCosmetics.log((String)("Failed to fetch categories from " + referenceURL + ": " + e));
                }
            }
            callback.accept(categories);
        });
    }

    private static String formatURL(String url, String search, int page) {
        StringJoiner excludes = new StringJoiner(",");
        if (!ModConfig.getConfig().nsfwTextures) {
            excludes.add("nsfw");
        }
        if (MoreCosmetics.getInstance().getUserHandler().isOnlineMode()) {
            excludes.add("animated");
        }
        return url.replace("{search}", search.replace(" ", "+")).replace("{page}", page + "").replace("{excludes}", excludes.toString());
    }

    private static List<TextureEntry> fetchFromArray(JsonArray filesArray) {
        ArrayList<TextureEntry> entries = new ArrayList<TextureEntry>();
        for (JsonElement entryElement : filesArray) {
            try {
                String entryCreator;
                JsonObject obj = entryElement.getAsJsonObject();
                String entryName = obj.get("name").getAsString();
                if (entryName.length() > 31) {
                    entryName = entryName.substring(0, 28).concat("...");
                }
                String entryImage = obj.get("download").getAsString();
                String entryPreview = obj.has("preview") ? obj.get("preview").getAsString() : entryImage;
                String string = entryCreator = obj.has("creator") ? obj.get("creator").getAsString() : null;
                if (!TextureCategoryBuilder.isAllowed((String)entryImage)) continue;
                TextureEntry entry = new TextureEntry(entryName, entryImage, entryPreview, entryCreator);
                if (obj.has("colors")) {
                    JsonArray array = obj.getAsJsonArray("colors");
                    int[] colors = new int[array.size()];
                    for (int i = 0; i < array.size(); ++i) {
                        String color = array.get(i).isJsonNull() ? "" : array.get(i).getAsString();
                        colors[i] = color.isEmpty() ? -1 : 0xFF000000 | Color.decode("#".concat(color)).getRGB();
                    }
                    entry.setColors(colors);
                }
                entries.add(entry);
            }
            catch (Exception e) {
                MoreCosmetics.log((String)"Failed to fetch texture entry!");
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
        return entries;
    }

    static {
        trustedUrls.add("https://cl.cosmeticsmod.com/");
        trustedUrls.add("https://dl.cosmeticsmod.com/");
        trustedUrls.add("http://dl.cosmeticsmod.com/");
        trustedUrls.add("http://logo.cosmeticsmod.com/");
        allowedUrls.add("https://cdn.discordapp.com/");
        allowedUrls.add("https://media.discordapp.net/");
    }
}

