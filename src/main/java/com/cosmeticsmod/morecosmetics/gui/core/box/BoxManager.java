/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiListener
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListManager
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 */
package com.cosmeticsmod.morecosmetics.gui.core.box;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;
import com.cosmeticsmod.morecosmetics.gui.core.GuiListener;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListManager;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import java.util.ArrayList;

public class BoxManager {
    public static final int APPEND = 58;
    public static final int SPACING_BOUNCE = 8;
    public static final int ENTRY_BOUNCE = 32;
    public static final int BOX_WIDTH_EXTRA = 40;
    public static final int ENTRY_PUFFER = 3;
    public static final int NAV_HEIGHT = 22;
    public static final int FONT_HEIGHT = 9;
    public static final int ROW_HEIGHT = 51;
    public static final BoxCategory PLACEHOLDER = new BoxCategory("", "");
    public static final BoxCategory SEPARATION_LINE = new BoxCategory("", "");
    private static GuiListener guiListener;
    private ArrayList<BoxCategory> categories = new ArrayList();
    private GuiComponent selectedEntry;
    private ListManager listManager;
    private ListManager settingsListManager;
    private BoxGuiInstance guiInstance;
    private int selectedCategory;
    private int currentRow;
    private int maxRows;
    private int width;
    private int height;
    private int barStart;
    private int barEnd;
    private int xRows;
    private int yRows;
    private int frameWidth;
    private int frameHeight;
    private int extensionStartRow;
    private int visibleCategories;
    private int navIndex;
    private boolean scrollbarRequired;
    private boolean navScrollRequired;
    private boolean mousePressed;
    private boolean extensionVisible;

    public BoxManager(int width, int height, BoxGuiInstance guiInstance) {
        this.height = height;
        this.width = width;
        this.guiInstance = guiInstance;
        this.listManager = new ListManager(this, this.baseBoxSplitX(), false);
        this.settingsListManager = new ListManager(this, this.baseBoxEndX() + (this.scrollbarRequired ? 5 : 0), true);
    }

    public String getCurrentTitle() {
        return this.categories.size() > this.selectedCategory ? ((BoxCategory)this.categories.get(this.selectedCategory)).getTitle() : "";
    }

    public void fetchCategories() {
        if (this.categories.size() - 1 < this.selectedCategory) {
            return;
        }
        for (BoxCategory cat : this.categories) {
            for (int i = 0; i < cat.getEntries().size(); ++i) {
                ((GuiComponent)cat.getEntries().get(i)).setComponentInfo(this, i);
            }
        }
        this.currentRow = 0;
        int selectedSize = ((BoxCategory)this.categories.get(this.selectedCategory)).getEntries().size();
        this.scrollbarRequired = selectedSize > this.xRows * this.yRows;
        this.maxRows = Math.round((float)selectedSize / (float)this.xRows);
    }

    public void draw(Object stack, int mouseX, int mouseY, boolean enabled) {
        int ySteps;
        int y;
        if (guiListener != null) {
            guiListener.drawScreen(stack, mouseX, mouseY, enabled);
        }
        if (!enabled) {
            return;
        }
        int max = this.categories.size();
        if (max == 0) {
            return;
        }
        if (this.selectedCategory >= max) {
            this.selectedCategory = max - 1;
        }
        if (this.navScrollRequired = (y = this.baseBoxStartY() + 10) + (ySteps = 21) + this.categories.size() * ySteps > this.height) {
            this.visibleCategories = 0;
            int maxY = y + ySteps + ySteps;
            while (maxY < this.height) {
                maxY += ySteps;
                ++this.visibleCategories;
            }
            this.navIndex = MathUtils.clampInt((int)this.navIndex, (int)0, (int)(this.categories.size() - this.visibleCategories));
        } else {
            this.navIndex = 0;
        }
        for (int i = this.navIndex; i < this.categories.size(); ++i) {
            BoxCategory category = (BoxCategory)this.categories.get(i);
            if (y + ySteps + ySteps > this.height) break;
            if (category != PLACEHOLDER) {
                this.guiInstance.drawCategory(y, this.selectedCategory == i, i == 0, category);
            }
            if (this.selectedCategory == i) {
                if (category.isListCategory()) {
                    this.listManager.draw(stack, mouseX, mouseY, 219);
                } else {
                    if (this.scrollbarRequired) {
                        float startPercentage = (float)this.currentRow / (float)this.maxRows;
                        this.barStart = (int)((float)this.frameHeight * startPercentage) + this.baseBoxStartY();
                        float endPercentage = (float)(this.currentRow + this.yRows) / (float)this.maxRows;
                        this.barEnd = (int)((float)this.frameHeight * endPercentage) + this.baseBoxStartY();
                    }
                    int currX = 0;
                    int currY = this.baseBoxStartY() + 2;
                    boolean first = true;
                    for (int j = this.currentRow * this.xRows; j < category.getEntries().size(); ++j) {
                        GuiComponent entry = (GuiComponent)category.getEntries().get(j);
                        if (j / this.xRows > this.currentRow + this.yRows - 1) break;
                        if (first) {
                            currX = this.baseBoxSplitX();
                            first = false;
                        }
                        entry.drawComponent(stack, currX, currY, 72, 51, mouseX, mouseY);
                        if ((currX += 75) + 32 + 40 < this.baseBoxEndX()) continue;
                        currY += 54;
                        first = true;
                    }
                }
            }
            y += ySteps;
        }
        if (this.scrollbarRequired && !((BoxCategory)this.categories.get(this.selectedCategory)).isListCategory()) {
            this.guiInstance.drawScrollbar();
        }
        if ((this.extensionVisible || ((BoxCategory)this.categories.get(this.selectedCategory)).isListCategory()) && this.listManager.isScrollBarRequired()) {
            this.guiInstance.drawListManagerScrollbar(this.listManager);
        }
        if (this.extensionVisible) {
            this.guiInstance.translateUI(true, 180.0f);
            this.settingsListManager.draw(stack, mouseX, mouseY, 150);
            this.guiInstance.translateUI(false, 0.0f);
        }
    }

    private void handleClick(int mouseX, int mouseY) {
        if (mouseX >= this.baseBoxStartX() && mouseX <= this.baseBoxSplitX() && mouseY >= this.baseBoxStartY() + 2 && mouseY <= this.baseBoxEndY()) {
            int newSelectedCat = this.navIndex + (mouseY - (this.baseBoxStartY() + 2)) / 21;
            if (this.categories.size() > newSelectedCat) {
                BoxCategory category = (BoxCategory)this.categories.get(newSelectedCat);
                if (category == PLACEHOLDER || category == SEPARATION_LINE) {
                    return;
                }
                if (category.hasCallback()) {
                    category.runCallback();
                    return;
                }
                this.selectedCategory = newSelectedCat;
                if (category.isListCategory()) {
                    this.listManager.setComponents((java.util.ArrayList)((BoxCategory)this.categories.get(this.selectedCategory)).getEntries());
                    this.listManager.fetchComponents();
                    this.listManager.updateResolution(this.baseBoxSplitX(), this.baseBoxEndY(), 0);
                }
                this.selectedEntry = null;
                this.extensionVisible = false;
            }
            this.fetchCategories();
        }
    }

    public void handleMouseInput(int mouseX, int mouseY) {
        int mouseWheel = (int)Math.signum(MoreCosmetics.getInstance().getVersionAdapter().getMouseDWheel());
        if (mouseWheel != 0) {
            if (this.navScrollRequired && mouseX < this.baseBoxSplitX()) {
                this.navIndex = MathUtils.clampInt((int)(this.navIndex -= mouseWheel), (int)0, (int)(this.categories.size() - this.visibleCategories));
            } else {
                if (this.scrollbarRequired) {
                    this.updateScrollAmount(mouseWheel);
                }
                if (((BoxCategory)this.categories.get(this.selectedCategory)).isListCategory() && this.listManager.isScrollBarRequired() && mouseX < this.baseBoxEndX()) {
                    this.listManager.updateScrollAmount(mouseWheel);
                }
                if (mouseX > this.baseBoxEndX() + 4 && this.extensionVisible && this.settingsListManager.isScrollBarRequired()) {
                    this.settingsListManager.updateScrollAmount(mouseWheel);
                }
            }
        }
    }

    public void keyTyped(char charCode, int keyCode) {
        if (guiListener != null) {
            guiListener.keyTyped(charCode, keyCode);
        }
        if (this.categories.size() > this.selectedCategory) {
            BoxCategory selectedCat = (BoxCategory)this.categories.get(this.selectedCategory);
            if (!selectedCat.isListCategory()) {
                selectedCat.getEntries().forEach(boxEntry -> boxEntry.keyTyped(charCode, keyCode));
            }
            if (selectedCat.isListCategory()) {
                this.listManager.keyTyped(charCode, keyCode);
            }
            if (this.extensionVisible) {
                this.settingsListManager.keyTyped(charCode, keyCode);
            }
        }
    }

    public void updateResolution(int width, int height) {
        this.width = width;
        this.height = height;
        this.frameWidth = this.baseBoxEndX() + 1 - this.baseBoxSplitX();
        this.frameHeight = this.baseBoxEndY() - this.baseBoxStartY();
        this.xRows = this.frameWidth / 75;
        this.yRows = this.frameHeight / 54;
        this.xRows = this.xRows < 1 ? 1 : this.xRows;
        this.yRows = this.yRows < 1 ? 1 : this.yRows;
        this.fetchCategories();
        if (this.extensionVisible) {
            this.extensionStartRow = this.selectedEntry.getComponentIndex() / this.xRows - this.currentRow;
            this.settingsListManager.updateResolution(this.baseBoxEndX() + (this.isScrollbarRequired() ? 5 : 0), this.baseBoxEndY(), this.extensionStartRow);
        }
        if (this.categories.size() > this.selectedCategory && ((BoxCategory)this.categories.get(this.selectedCategory)).isListCategory()) {
            this.listManager.updateResolution(this.baseBoxSplitX(), this.baseBoxEndY());
        }
    }

    public void refreshList() {
        if (this.categories.size() > this.selectedCategory && ((BoxCategory)this.categories.get(this.selectedCategory)).isListCategory()) {
            this.listManager.setComponents((java.util.ArrayList)((BoxCategory)this.categories.get(this.selectedCategory)).getEntries());
        }
    }

    public void updateScrollAmount(int amount) {
        this.currentRow -= amount;
        this.currentRow = this.currentRow + this.yRows > this.maxRows ? this.maxRows - this.yRows : this.currentRow;
        this.currentRow = this.currentRow < 0 ? 0 : this.currentRow;
        for (int i = 0; i < ((BoxCategory)this.categories.get(this.selectedCategory)).getEntries().size(); ++i) {
            GuiComponent entry = (GuiComponent)((BoxCategory)this.categories.get(this.selectedCategory)).getEntries().get(i);
            if (i >= this.currentRow * this.xRows && i <= (this.currentRow + this.yRows) * this.xRows) continue;
            entry.setMouseOver(false);
        }
        if (this.extensionVisible) {
            if (this.selectedEntry.getComponentIndex() >= this.currentRow * this.xRows && this.selectedEntry.getComponentIndex() <= (this.currentRow + this.yRows) * this.xRows) {
                this.extensionStartRow = this.selectedEntry.getComponentIndex() / this.xRows - this.currentRow;
            } else if (this.selectedEntry instanceof ListComponent) {
                this.extensionStartRow = this.selectedEntry.getComponentIndex() - this.listManager.getCurrentRow();
            }
            this.settingsListManager.updateResolution(this.baseBoxEndX() + (this.isScrollbarRequired() ? 5 : 0), this.height, this.extensionStartRow);
        } else {
            this.extensionVisible = false;
        }
    }

    public void setExtensionVisible(boolean state) {
        this.setExtensionVisible(state, null);
    }

    public void setExtensionVisible(boolean state, GuiComponent component) {
        if (component != null) {
            this.settingsListManager.setComponents(component.getChildComponents());
            if (component instanceof ListComponent) {
                this.extensionStartRow = component.getComponentIndex() - this.listManager.getCurrentRow();
                if (component.hasChildComponents()) {
                    ((ListComponent)component).setLineVisible(state);
                }
            } else {
                this.extensionStartRow = component.getComponentIndex() / this.xRows - this.currentRow;
            }
            this.selectedEntry = component;
        }
        this.extensionVisible = state;
        for (GuiComponent entry : ((BoxCategory)this.categories.get(this.selectedCategory)).getEntries()) {
            entry.setEnabled(!this.extensionVisible);
            if (state || !(entry instanceof ListComponent)) continue;
            ((ListComponent)entry).setLineVisible(false);
        }
        if (this.extensionVisible) {
            if (this.selectedEntry != null) {
                this.selectedEntry.setEnabled(true);
            }
            this.settingsListManager.updateResolution(this.baseBoxEndX() + (this.isScrollbarRequired() ? 5 : 0), this.height, this.extensionStartRow);
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.extensionVisible && mouseX < this.baseBoxEndX()) {
            this.setExtensionVisible(false, null);
        }
        this.handleClick(mouseX, mouseY);
        if (this.categories.size() > this.selectedCategory) {
            BoxCategory selectedCat = (BoxCategory)this.categories.get(this.selectedCategory);
            if (!selectedCat.isListCategory()) {
                selectedCat.getEntries().forEach(boxEntry -> boxEntry.mouseClicked(mouseX, mouseY, mouseButton));
            }
            if (this.extensionVisible) {
                this.settingsListManager.mouseClicked(mouseX, mouseY, mouseButton);
            }
            if (selectedCat.isListCategory()) {
                this.listManager.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        return guiListener == null ? false : guiListener.mouseClicked(this.guiInstance, mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.categories.size() > this.selectedCategory) {
            BoxCategory selectedCat = (BoxCategory)this.categories.get(this.selectedCategory);
            if (!selectedCat.isListCategory()) {
                selectedCat.getEntries().forEach(boxEntry -> boxEntry.mouseReleased(mouseX, mouseY));
            }
            if (this.extensionVisible) {
                this.settingsListManager.mouseReleased(mouseX, mouseY, state);
            }
            if (selectedCat.isListCategory()) {
                this.listManager.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public ArrayList<BoxCategory> getCategories() {
        return this.categories;
    }

    public int getSelectedCategoryIndex() {
        return this.selectedCategory;
    }

    public BoxCategory getSelectedCategory() {
        return (BoxCategory)this.categories.get(this.selectedCategory);
    }

    public int baseBoxStartX() {
        return 0;
    }

    public int baseBoxStartY() {
        return 22;
    }

    public int baseBoxEndX() {
        return 318;
    }

    public int baseBoxEndY() {
        return this.height;
    }

    public int baseBoxSplitX() {
        return 93;
    }

    public int getBarStart() {
        return this.barStart;
    }

    public int getBarEnd() {
        return this.barEnd;
    }

    public static void setGuiListener(GuiListener guiListener) {
        BoxManager.guiListener = guiListener;
    }

    public boolean isScrollbarRequired() {
        return this.scrollbarRequired || this.listManager.isScrollBarRequired();
    }

    public BoxGuiInstance getGuiInstance() {
        return this.guiInstance;
    }

    public interface BoxGuiInstance {
        void refreshGui();
        default void drawCategory(int y, boolean selected, boolean first, com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory category) {}
        default void drawScrollbar() {}
        default void drawListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
        default void drawSettingsListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
        default void onCustomAction(String[] args) {}
        default void translateUI(boolean push, float offset) {}
        default void drawRoundedRect(int x1, int y1, int x2, int y2, int color, int radius) {}
    }
}

