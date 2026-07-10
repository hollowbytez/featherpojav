/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.ListElement
 *  v1_21.morecosmetics.gui.elements.list.ListElement$ListGui
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.gui.elements.list.BaseElement;
import v1_21.morecosmetics.gui.elements.list.ListElement;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;
import v1_21.morecosmetics.gui.screen.UIScreen;

@Environment(value=EnvType.CLIENT)
public class ListElement
extends BaseElement {
    private int current;
    private Map<String, FontImage> entries = new LinkedHashMap();
    private Consumer<String> callback;
    private CustomButton openListBtn;
    private long lastClicked;
    private int btnWidth = 40;

    public ListElement(String title, List<String> entries, int current, Consumer<String> callback) {
        super(title);
        for (String entry : entries) {
            this.entries.put(entry, null);
        }
        this.current = current;
        this.callback = callback;
        this.setBtnWidth(entries.get(current));
        this.openListBtn = new CustomButton(0, 0, this.btnWidth, 18, entries.get(current));
    }

    public ListElement(String title, Map<String, FontImage> entries, String curentText, int current, Consumer<String> callback) {
        super(title);
        this.entries = entries;
        this.current = current;
        this.callback = callback;
        this.setBtnWidth(curentText);
        this.openListBtn = new CustomButton(0, 0, this.btnWidth, 18, curentText);
    }

    private void setBtnWidth(String entry) {
        this.btnWidth = Math.max(DrawUtils.getStringWidth((String)entry) + 4, 40);
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.openListBtn.xPosition = x + compWidth - this.btnWidth;
        this.openListBtn.yPosition = y + 1;
        this.openListBtn.drawButton(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.openListBtn.mousePressed(mouseX, mouseY)) {
            ScreenWrapper.displayOverlay((UIScreen)new ListGui(this));
        }
    }

    public int getControlWidth() {
        return this.btnWidth;
    }

    public class ListGui extends v1_21.morecosmetics.gui.screen.UIScreen {
        public ListGui(ListElement element) {}
        @Override public void initGui() {}
        @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
        @Override public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    }

}

