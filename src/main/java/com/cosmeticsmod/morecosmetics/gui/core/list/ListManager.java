/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListManager
 */
package com.cosmeticsmod.morecosmetics.gui.core.list;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import java.util.ArrayList;

public class ListManager {
    public static final int DEFAULT_SLOT_HEIGHT = 20;
    public static final int TILE_EXTENDED_WIDTH = 150;
    public static final int TILE_MOVE_DURATION = 5;
    private BoxManager boxManager;
    private ArrayList<ListComponent> components = new ArrayList();
    private int horizontalOffset;
    private int startY;
    private int endY;
    private int currentHeight;
    private int barStart;
    private int barEnd;
    private int enabledEntries;
    private int visibleRows;
    private int currentRow;
    private int maxRows;
    private int scrollDrawX;
    private boolean scrollBarRequired;
    private boolean extensionInstance;

    public ListManager(BoxManager boxManager, int horizontalOffset, boolean rounded) {
        this.horizontalOffset = horizontalOffset;
        this.boxManager = boxManager;
        this.extensionInstance = rounded;
    }

    public void updateResolution(int horizontalOffset, int height) {
        this.updateResolution(horizontalOffset, height, 0);
    }

    public void updateResolution(int horizontalOffset, int height, int boxSelectionRow) {
        this.horizontalOffset = horizontalOffset;
        this.fetchYBounce(height, boxSelectionRow);
        this.fetchComponents();
        this.currentRow = 0;
    }

    public void fetchComponents() {
        this.enabledEntries = 0;
        for (ListComponent component : this.components) {
            this.getSubs(component);
        }
        float entryHeight = 20 + (this.extensionInstance ? 0 : 1);
        this.visibleRows = (int)Math.floor((float)(this.endY - this.startY) / entryHeight);
        this.maxRows = this.enabledEntries;
        this.scrollBarRequired = this.maxRows > this.visibleRows;
    }

    public void draw(Object stack, int mouseX, int mouseY, int TILE_WIDTH) {
        this.scrollDrawX = 0;
        float startPercentage = (float)this.currentRow / (float)this.maxRows;
        this.barStart = this.startY + (int)((float)(this.endY - this.startY) * startPercentage);
        float endPercentage = (float)(this.currentRow + this.visibleRows) / (float)this.maxRows;
        this.barEnd = this.startY + (int)((float)(this.endY - this.startY) * endPercentage);
        if (this.components.size() > 0) {
            this.currentHeight = this.startY + 2;
            if (this.extensionInstance) {
                int bottom = this.currentHeight + this.components.size() * 20 + 3;
                if ((float)bottom > 345.0f) {
                    this.currentHeight = (int)((float)this.currentHeight - ((float)bottom - 345.0f));
                    bottom = this.currentHeight + this.components.size() * 20 + 3;
                }
                this.boxManager.getGuiInstance().drawRoundedRect(this.horizontalOffset, this.currentHeight - 1, this.horizontalOffset + TILE_WIDTH + 3, bottom, UIConstants.UI_SEPARATION_COLOR, 5);
                this.boxManager.getGuiInstance().drawRoundedRect(this.horizontalOffset + 1, this.currentHeight, this.horizontalOffset + TILE_WIDTH + 3 - 1, bottom - 1, UIConstants.UI_BACKGROUND_COLOR, 5);
            }
            for (int i = this.currentRow; i < this.components.size(); ++i) {
                ListComponent current = (ListComponent)this.components.get(i);
                if (this.currentHeight > this.endY) break;
                current.drawComponent(stack, this.horizontalOffset, this.currentHeight, TILE_WIDTH, 20, mouseX, mouseY);
                this.scrollDrawX = this.horizontalOffset + TILE_WIDTH > this.scrollDrawX ? this.horizontalOffset + TILE_WIDTH : this.scrollDrawX;
                this.currentHeight += 20 + (this.extensionInstance ? 0 : 1);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.components.forEach(listComponent -> listComponent.mouseClicked(mouseX, mouseY, mouseButton));
        this.fetchComponents();
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.components.forEach(listComponent -> listComponent.mouseRelease(mouseX, mouseY, state));
    }

    public void updateScrollAmount(int amount) {
        this.currentRow -= amount;
        this.currentRow = this.currentRow + this.visibleRows > this.maxRows ? this.maxRows - this.visibleRows : this.currentRow;
        this.currentRow = this.currentRow < 0 ? 0 : this.currentRow;
    }

    public void keyTyped(char charCode, int keyCode) {
        this.components.forEach(component -> component.keyTyped(charCode, keyCode));
    }

    private void getSubs(ListComponent component) {
        ++this.enabledEntries;
        if (component.isChildVisible()) {
            for (ListComponent cache : component.getChildComponents()) {
                this.getSubs(cache);
            }
        }
    }

    private void fetchYBounce(int height, int boxSelectionRow) {
        this.startY = this.boxManager.baseBoxStartY();
        this.endY = height;
        if (this.extensionInstance) {
            if (this.boxManager.getSelectedCategory().isListCategory()) {
                this.startY = this.boxManager.baseBoxStartY() + boxSelectionRow * 20 + 2;
            } else {
                this.startY = this.boxManager.baseBoxStartY() + boxSelectionRow * 54 - 2;
                this.endY = this.startY + this.components.size() * 21;
            }
        }
    }

    public boolean isScrollBarRequired() {
        return this.scrollBarRequired;
    }

    public int getStartY() {
        return this.startY;
    }

    public int getEndY() {
        return this.endY;
    }

    public int getBarStart() {
        return this.barStart;
    }

    public int getBarEnd() {
        return this.barEnd;
    }

    public int getScrollDrawX() {
        return this.scrollDrawX;
    }

    public ArrayList<ListComponent> getComponents() {
        return this.components;
    }

    public void setComponents(ArrayList<ListComponent> components) {
        this.components = components;
    }

    public int getCurrentRow() {
        return this.currentRow;
    }
}

