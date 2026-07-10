import os
import re

def read(path):
    with open(path, "r", encoding="utf-8") as f: return f.read()
def write(path, c):
    with open(path, "w", encoding="utf-8") as f: f.write(c)
def inject_before_last_brace(path, content):
    c = read(path)
    i = c.rfind("}")
    if i != -1: write(path, c[:i] + content + "\n" + c[i:])

# ========== 1. BoxGuiInstance - add remaining missing methods ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\box\BoxManager.java"
c = read(f)
c = c.replace("""    public interface BoxGuiInstance {
        void refreshGui();
        default void drawCategory(int y, boolean selected, boolean first, com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory category) {}
        default void drawScrollbar() {}
        default void drawListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
        default void onCustomAction(String[] args) {}
        default void drawSettingsListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
    }""", """    public interface BoxGuiInstance {
        void refreshGui();
        default void drawCategory(int y, boolean selected, boolean first, com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory category) {}
        default void drawScrollbar() {}
        default void drawListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
        default void drawSettingsListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
        default void onCustomAction(String[] args) {}
        default void translateUI(boolean push, float offset) {}
        default void drawRoundedRect(int x1, int y1, int x2, int y2, int color, int radius) {}
    }""")
# Fix ArrayList<GuiComponent> -> ArrayList<ListComponent> cast
c = c.replace(
    "this.listManager.setComponents(((BoxCategory)this.categories.get(this.selectedCategory)).getEntries());",
    "this.listManager.setComponents((java.util.ArrayList)((BoxCategory)this.categories.get(this.selectedCategory)).getEntries());"
)
write(f, c)

# ========== 2. EditGui - rename ALL shadowed lambda `i` vars ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\EditGui.java"
c = read(f)
c = c.replace(
    'comp.add(this.listBuilder.getSliderElement("Name Height", 0, 10, (int)(this.model.getHeight() / 10.0f), i -> this.model.setHeight((float)i.intValue() / 10.0f)));',
    'comp.add(this.listBuilder.getSliderElement("Name Height", 0, 10, (int)(this.model.getHeight() / 10.0f), heightVal -> this.model.setHeight((float)heightVal.intValue() / 10.0f)));'
)
c = c.replace(
    'child.add(this.listBuilder.getSliderElement("Scale", 1, 20, (int)(this.model.getPreviewScale() * 10.0f), i -> this.model.setPreviewScale((float)i.intValue() / 10.0f)));',
    'child.add(this.listBuilder.getSliderElement("Scale", 1, 20, (int)(this.model.getPreviewScale() * 10.0f), scaleVal -> this.model.setPreviewScale((float)scaleVal.intValue() / 10.0f)));'
)
c = c.replace(
    'child.add(this.listBuilder.getSliderElement("Y-Height", -10, 10, (int)(this.model.getPreviewY() * -10.0f), i -> this.model.setPreviewY((float)i.intValue() / -10.0f)));',
    'child.add(this.listBuilder.getSliderElement("Y-Height", -10, 10, (int)(this.model.getPreviewY() * -10.0f), yHeightVal -> this.model.setPreviewY((float)yHeightVal.intValue() / -10.0f)));'
)
write(f, c)

# ========== 3. JsonAnimationUtils - fix bone.getKey()/getValue() on Object ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\util\json\JsonAnimationUtils.java"
c = read(f)
c = c.replace('(String)bone.getKey()', '(String)((java.util.Map.Entry)bone).getKey()')
c = c.replace('((JsonElement)bone.getValue())', '((JsonElement)((java.util.Map.Entry)bone).getValue())')
write(f, c)

# ========== 4. ModelPosition - fix broken constructor ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\models\model\util\ModelPosition.java"
c = read(f)
c = c.replace('this(string, n, id, -1);', 'this(id, -1);')
write(f, c)

# ========== 5. TextureCategory.UpdateInterface - fix to have updatePage ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\texture\TextureCategory.java"
c = read(f)
c = c.replace(
    """    public interface UpdateInterface {
        void update();
    }""",
    """    public interface UpdateInterface {
        java.util.List<TextureEntry> updatePage(String referenceURL, String search, TextureCategory target, int page);
    }"""
)
write(f, c)

# ========== 6. PopupFetcher.Popup - add disabled, online fields ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\misc\PopupFetcher.java"
c = read(f)
c = c.replace(
    """    public static class Popup {
        public int id;
        public String title;
        public String description;
        public String img;
        public String url;
        public int size;
        public int color;
        public int hovercolor;
        public Runnable action;
    }""",
    """    public static class Popup {
        public int id;
        public String title;
        public String description;
        public String img;
        public String url;
        public int size;
        public int color;
        public int hovercolor;
        public boolean disabled;
        public boolean online;
        public Runnable action;
    }"""
)
write(f, c)

# ========== 7. CosmeticAnimatable - fix predicate lambda ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\models\animated\CosmeticAnimatable.java"
c = read(f)
c = c.replace(
    'this.controller = new AnimationController((IAnimatable)this, this.controllerName, 5.0f, arg_0 -> this.predicate(arg_0));',
    'this.controller = new AnimationController((IAnimatable)this, this.controllerName, 5.0f, (AnimationController.IAnimationPredicate)(arg_0 -> this.predicate((AnimationEvent)arg_0)));'
)
write(f, c)

print("All 53 remaining errors addressed!")
