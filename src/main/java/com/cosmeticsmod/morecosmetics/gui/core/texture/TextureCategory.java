/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.GuiLogo
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory$EnumSearchType
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory$UpdateInterface
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 */
package com.cosmeticsmod.morecosmetics.gui.core.texture;

import com.cosmeticsmod.morecosmetics.gui.core.texture.GuiLogo;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TextureCategory {
    private static final int PAGE_UPDATE_DURATION = 750;
    private String name;
    private String iconURL;
    private String localTargetPath;
    private String referenceURL;
    private String subline;
    private GuiLogo[] icons;
    private List<TextureEntry> allEntries = new ArrayList();
    private UpdateInterface updateInterface;
    private String currentSearch = "";
    private int currentPage;
    private int maxPages;
    private long lastPageUpdate;
    private int selectedIndex = -1;

    public TextureCategory(String name, String iconURL, ArrayList<TextureEntry> entries, int selectedIndex) {
        this.name = name;
        this.iconURL = iconURL;
        this.selectedIndex = selectedIndex;
    }

    public TextureCategory(String name, String iconURL) {
        this.name = name;
        this.iconURL = iconURL;
    }

    public TextureCategory fillEntries(Consumer<List<TextureEntry>> fillCall, int maxPages) {
        fillCall.accept(this.allEntries);
        this.maxPages = maxPages;
        return this;
    }

    public TextureCategory setLocal(String targetPath) {
        this.localTargetPath = targetPath;
        return this;
    }

    public void setSelectedIndex(int selectedEntry) {
        this.selectedIndex = selectedEntry;
    }

    public String getName() {
        return this.name;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public TextureEntry getSelectedEntry() {
        if (this.selectedIndex < 0 || this.selectedIndex >= this.allEntries.size()) {
            return null;
        }
        return (TextureEntry)this.allEntries.get(this.selectedIndex);
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public boolean isLocalExploration() {
        return this.localTargetPath != null && !this.localTargetPath.isEmpty();
    }

    public boolean hasOnlineSearch() {
        return this.referenceURL != null && !this.referenceURL.isEmpty() && this.updateInterface != null;
    }

    public int getMaxPages() {
        return this.maxPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public UpdateInterface getUpdateInterface() {
        return this.updateInterface;
    }

    public String getReferenceURL() {
        return this.referenceURL;
    }

    public TextureCategory setOnlineUpdater(UpdateInterface updateInterface, String referenceURL) {
        this.referenceURL = referenceURL;
        this.updateInterface = updateInterface;
        return this;
    }

    public List<TextureEntry> searchForWord(String currentSearch, TextureCategory target, EnumSearchType type) {
        this.currentSearch = currentSearch;
        this.currentPage = 1;
        return this.updateInterface.updatePage(this.referenceURL, currentSearch, target, 1);
    }

    public boolean increasePage() {
        if (System.currentTimeMillis() - this.lastPageUpdate < 750L) {
            return false;
        }
        this.lastPageUpdate = System.currentTimeMillis();
        if (this.currentPage < this.maxPages) {
            if (this.updateInterface == null) {
                return false;
            }
            this.allEntries.addAll(this.updateInterface.updatePage(this.referenceURL, this.currentSearch, null, ++this.currentPage));
            return true;
        }
        return false;
    }

    public List<TextureEntry> getAllEntries() {
        return this.allEntries;
    }

    public String getLocalTargetPath() {
        return this.localTargetPath;
    }

    public void setSubline(String subline) {
        this.subline = subline;
    }

    public String getSubline() {
        return this.subline;
    }

    public void setIcons(GuiLogo[] icons) {
        this.icons = icons;
    }

    public void prepareIconUrl(String token, String uuid) {
        for (GuiLogo icon : this.icons) {
            icon.setLink(icon.getLink().replace("{token}", token).replace("{uuid}", uuid));
        }
    }

    public GuiLogo[] getIcons() {
        return this.icons;
    }

    public interface UpdateInterface {
        java.util.List<TextureEntry> updatePage(String referenceURL, String search, TextureCategory target, int page);
    }
    public enum EnumSearchType {
        CONTAINS, EQUALS, STARTS_WITH
    }
}

