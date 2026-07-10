/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.builder.ListElementContainer
 *  v1_21.morecosmetics.gui.elements.list.ButtonElement
 *  v1_21.morecosmetics.gui.elements.list.ColorPickerElement
 *  v1_21.morecosmetics.gui.elements.list.KeyBoxElement
 *  v1_21.morecosmetics.gui.elements.list.ListElement
 *  v1_21.morecosmetics.gui.elements.list.NumberBoxElement
 *  v1_21.morecosmetics.gui.elements.list.SelectiveSliderElement
 *  v1_21.morecosmetics.gui.elements.list.SeparationElement
 *  v1_21.morecosmetics.gui.elements.list.SliderElement
 *  v1_21.morecosmetics.gui.elements.list.SwitchElement
 *  v1_21.morecosmetics.gui.elements.list.TextBoxElement
 *  v1_21.morecosmetics.gui.elements.list.TextElement
 *  v1_21.morecosmetics.gui.elements.list.TextureElement
 */
package v1_21.morecosmetics.gui.builder;

import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry;
import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.elements.list.ButtonElement;
import v1_21.morecosmetics.gui.elements.list.ColorPickerElement;
import v1_21.morecosmetics.gui.elements.list.KeyBoxElement;
import v1_21.morecosmetics.gui.elements.list.ListElement;
import v1_21.morecosmetics.gui.elements.list.NumberBoxElement;
import v1_21.morecosmetics.gui.elements.list.SelectiveSliderElement;
import v1_21.morecosmetics.gui.elements.list.SeparationElement;
import v1_21.morecosmetics.gui.elements.list.SliderElement;
import v1_21.morecosmetics.gui.elements.list.SwitchElement;
import v1_21.morecosmetics.gui.elements.list.TextBoxElement;
import v1_21.morecosmetics.gui.elements.list.TextElement;
import v1_21.morecosmetics.gui.elements.list.TextureElement;

@Environment(value=EnvType.CLIENT)
public class ListElementContainer
extends ListElementBuilder {
    public ListComponent getSwitchElement(String title, boolean state, boolean tileEnabled, Consumer<Boolean> callback) {
        return new SwitchElement(title, state, tileEnabled, callback);
    }

    public ListComponent getTextBoxElement(String title, String currentText, Consumer<String> confirmCallback, Consumer<String> delayedCallback, Consumer<String> liveCallback, Consumer<String> unfocusCallback, int maxLength) {
        return new TextBoxElement(title, currentText, confirmCallback, delayedCallback, liveCallback, unfocusCallback, maxLength);
    }

    public ListComponent getButtonElement(String title, Runnable callback) {
        return new ButtonElement(title, callback);
    }

    public ListComponent getButtonElement(String title, String buttonText, Runnable callback) {
        return new ButtonElement(title, buttonText, callback);
    }

    public ListComponent getSliderElement(String title, int min, int max, int current, Consumer<Integer> callback, int width, boolean directCallback) {
        return new SliderElement(title, min, max, current, callback, width, directCallback);
    }

    public ListComponent getSelectiveSliderElement(String title, int min, int max, int current, Consumer<Integer> callback, int width, boolean directCallback) {
        return new SelectiveSliderElement(title, min, max, current, callback, width, directCallback);
    }

    public ListComponent getColorPickerElement(String title, int selected, int defaultColor, boolean rainbow, Consumer<Integer> callback) {
        return new ColorPickerElement(title, selected, defaultColor, rainbow, callback);
    }

    public ListComponent getNumberBoxElement(String title, int min, int max, int current, Consumer<Integer> callback) {
        return new NumberBoxElement(title, min, max, current, callback);
    }

    public ListComponent getSeparationElement(String text, int lineColor) {
        return new SeparationElement(text, lineColor);
    }

    public ListComponent getTextElement(String title, String text) {
        return new TextElement(title, text);
    }

    public ListComponent getListElement(String title, Map<String, FontImage> entries, String currentText, int current, Consumer<String> callback) {
        return new ListElement(title, entries, currentText, current, callback);
    }

    public ListComponent getListElement(String title, List<String> entries, int current, Consumer<String> callback) {
        return new ListElement(title, entries, current, callback);
    }

    public ListComponent getTextureElement(String title, ArrayList<TextureCategory> categories, int selectedCat, int rowsInLine, Consumer<TextureEntry> callback) {
        return new TextureElement(title, categories, selectedCat, rowsInLine, callback);
    }

    public ListComponent getKeyBoxElement(String title, int currentKey, Consumer<Integer> callback) {
        return new KeyBoxElement(title, currentKey, callback);
    }
}

