/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.apache.commons.io.FilenameUtils
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.CustomButton
 *  v1_21.morecosmetics.gui.elements.list.BaseElement
 *  v1_21.morecosmetics.gui.elements.list.TextureElement
 *  v1_21.morecosmetics.gui.elements.list.TextureElement$TextureSelectionGui
 *  v1_21.morecosmetics.gui.screen.ScreenWrapper
 *  v1_21.morecosmetics.gui.screen.UIScreen
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.gui.elements.list;

import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.FilenameUtils;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.gui.components.CustomButton;
import v1_21.morecosmetics.gui.elements.list.BaseElement;
import v1_21.morecosmetics.gui.elements.list.TextureElement;
import v1_21.morecosmetics.gui.screen.ScreenWrapper;
import v1_21.morecosmetics.gui.screen.UIScreen;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
public class TextureElement
extends BaseElement {
    public static final int ROW_HEIGHT = 32 + DrawUtils.getFontHeight() + 5;
    public static final int CATEGORY_WIDTH = 30;
    public static final int SCROLLBAR_WIDTH = 2;
    public static final String[] SUPPORTED_TYPES = new String[]{".png", ".jpeg", ".jpg", ".gif"};
    private CustomTextureManager ctm = CustomTextureManager.getGlobalInstance();
    private ArrayList<TextureCategory> filteredCategories;
    private ArrayList<TextureCategory> nativeCategories;
    private Consumer<TextureEntry> callback;
    private CustomButton browseButton;
    private boolean openedSelector;
    private boolean isShown;
    private boolean displayTopBar = true;
    private int selectedCat;
    private int rowsInLine;
    private long lastClicked;
    private TextureEntry lastEntry;
    private final String byStr;

    public TextureElement(String title, ArrayList<TextureCategory> nativeCategories, int selectedCat, int rowsInLine, Consumer<TextureEntry> callback) {
        super(title);
        this.selectedCat = selectedCat;
        this.rowsInLine = rowsInLine;
        this.callback = callback;
        this.nativeCategories = nativeCategories;
        this.filteredCategories = nativeCategories;
        this.byStr = "by ";
        for (TextureCategory category : this.filteredCategories) {
            if (!category.isLocalExploration()) continue;
            File dir = new File(category.getLocalTargetPath());
            if (!dir.exists()) {
                System.err.println("Couldn't find " + dir.getAbsolutePath());
                continue;
            }
            if (!dir.isDirectory()) {
                System.err.println(dir.getAbsolutePath() + " isn't a directory!");
                continue;
            }
            ArrayList<TextureEntry> files = new ArrayList<TextureEntry>();
            for (File f : dir.listFiles()) {
                if (!f.getName().toLowerCase().endsWith(".png")) continue;
                files.add(new TextureEntry(FilenameUtils.getBaseName((String)f.getName()), f.getAbsolutePath()));
            }
            if (files.isEmpty()) continue;
            category.fillEntries(textureEntries -> textureEntries.addAll(files), 1);
        }
        this.browseButton = new CustomButton(0, 0, 40, 18, "Browse");
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
        this.browseButton.xPosition = x + compWidth - 40;
        this.browseButton.yPosition = y + 1;
        this.browseButton.drawButton(mouseX, mouseY);
        if (this.openedSelector && !this.isShown) {
            this.isShown = true;
            this.filteredCategories = this.nativeCategories;
            ScreenWrapper.displayOverlay((UIScreen)new TextureSelectionGui(this));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.browseButton.mousePressed(mouseX, mouseY)) {
            this.openedSelector = !this.openedSelector;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseRelease(int mouseX, int mouseY, int state) {
        super.mouseRelease(mouseX, mouseY, state);
    }

    public void keyTyped(char charCode, int keyCode) {
        super.keyTyped(charCode, keyCode);
    }

    public class TextureSelectionGui extends v1_21.morecosmetics.gui.screen.UIScreen {
        public TextureSelectionGui(TextureElement element) {}
        @Override public void initGui() {}
        @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
        @Override public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    }

}

