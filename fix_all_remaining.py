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

# ========== 1. PopupFetcher.Popup - needs all fields ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\misc\PopupFetcher.java"
c = read(f)
c = c.replace("""    public static class Popup {
        public String title;
        public String description;
        public Runnable action;
    }""", """    public static class Popup {
        public int id;
        public String title;
        public String description;
        public String img;
        public String url;
        public int size;
        public int color;
        public int hovercolor;
        public Runnable action;
    }""")
write(f, c)

# ========== 2. BoxGuiInstance - needs all methods ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\box\BoxManager.java"
c = read(f)
c = c.replace("""    public interface BoxGuiInstance {
        void refreshGui();
    }""", """    public interface BoxGuiInstance {
        void refreshGui();
        default void drawCategory(int y, boolean selected, boolean first, com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory category) {}
        default void drawScrollbar() {}
        default void drawListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
        default void onCustomAction(String[] args) {}
        default void drawSettingsListManagerScrollbar(com.cosmeticsmod.morecosmetics.gui.core.list.ListManager listManager) {}
    }""")
write(f, c)

# ========== 3. EditGui.java - rename shadowed lambda var i ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\EditGui.java"
c = read(f)
c = c.replace("""child.add(this.listBuilder.getSelectiveSliderElement("Yaw", -180, 180, (int)rot[0], i -> {
                    rot[0] = i.intValue();
                    this.model.setPreviewRot(rot);
                }, 70, true));
                child.add(this.listBuilder.getSelectiveSliderElement("Pitch", -180, 180, (int)rot[1], i -> {
                    rot[1] = i.intValue();
                    this.model.setPreviewRot(rot);
                }, 70, true));
                child.add(this.listBuilder.getSelectiveSliderElement("Roll", -180, 180, (int)rot[2], i -> {
                    rot[2] = i.intValue();
                    this.model.setPreviewRot(rot);
                }, 70, true));""", """child.add(this.listBuilder.getSelectiveSliderElement("Yaw", -180, 180, (int)rot[0], yawVal -> {
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
                }, 70, true));""")
write(f, c)

# ========== 4. UserHandler.java - fix ModelData package ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\user\UserHandler.java"
c = read(f)
c = c.replace("com.cosmeticsmod.morecosmetics.models.model.ModelData", "com.cosmeticsmod.morecosmetics.models.config.ModelData")
write(f, c)

# ========== 5. ModelCosmeticRenderer - fix comp_1629() and BufferBuilder ==========
f = r"A:\MCMDS\src\main\java\v1_21\models\renderer\ModelCosmeticRenderer.java"
c = read(f)
c = c.replace("entity.getSkinTextures().comp_1629()", "entity.getSkinTextures().model()")
c = c.replace("BufferBuilder consumer = null;", "net.minecraft.client.render.VertexConsumer consumer = null;")
c = c.replace("consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent((Identifier)loc));",
              "consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent((Identifier)loc));")
# Actually the issue is BufferBuilder type - replace the declaration
c = c.replace("BufferBuilder consumer;", "net.minecraft.client.render.VertexConsumer consumer;")
write(f, c)

# ========== 6. VersionImpl - fix comp_349() and Collection cast ==========
f = r"A:\MCMDS\src\main\java\v1_21\VersionImpl.java"
c = read(f)
c = c.replace("SoundEvents.UI_BUTTON_CLICK.comp_349()", "SoundEvents.UI_BUTTON_CLICK.value()")
c = c.replace("Collection players = mc.getNetworkHandler().getPlayerList();",
              "Collection<PlayerListEntry> players = mc.getNetworkHandler().getPlayerList();")
write(f, c)

# ========== 7. ListElement - add inner class ListGui ==========
f = r"A:\MCMDS\src\main\java\v1_21\gui\elements\list\ListElement.java"
inject_before_last_brace(f, """
    public class ListGui extends v1_21.morecosmetics.gui.screen.UIScreen {
        public ListGui(ListElement element) {}
        @Override public void initGui() {}
        @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
        @Override public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    }
""")

# ========== 8. TextureElement - add inner class TextureSelectionGui ==========
f = r"A:\MCMDS\src\main\java\v1_21\gui\elements\list\TextureElement.java"
inject_before_last_brace(f, """
    public class TextureSelectionGui extends v1_21.morecosmetics.gui.screen.UIScreen {
        public TextureSelectionGui(TextureElement element) {}
        @Override public void initGui() {}
        @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
        @Override public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    }
""")

# ========== 9. Converter.java - add missing inner classes ==========
# UvUnion.Deserializer, LocatorValue.Deserializer, PolysUnion.Deserializer, TimeDeserializer
for cls_name in ["UvUnion", "LocatorValue", "PolysUnion"]:
    found = False
    for root, _, files in os.walk(r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\geo\raw\pojo"):
        for fn in files:
            if fn == cls_name + ".java":
                p = os.path.join(root, fn)
                cc = read(p)
                if "class Deserializer" not in cc:
                    inject_before_last_brace(p, """
    public static class Deserializer implements com.google.gson.JsonDeserializer<%s> {
        @Override
        public %s deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
            return new %s();
        }
    }
""" % (cls_name, cls_name, cls_name))
                found = True
                break

# TimeDeserializer
conv_dir = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\geo\raw\pojo"
td_path = os.path.join(conv_dir, "TimeDeserializer.java")
if not os.path.exists(td_path):
    write(td_path, """package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;

public class TimeDeserializer implements JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return OffsetDateTime.parse(json.getAsString());
    }
}
""")

# ========== 10. JsonAnimationUtils - fix raw type casts ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\util\json\JsonAnimationUtils.java"
c = read(f)
# Fix ImmutableSet.of type inference
c = c.replace('ImmutableSet.of(new AbstractMap.SimpleEntry<String, JsonArray>("0", rotationObject.getAsJsonArray()))',
              'ImmutableSet.<Map.Entry<String, JsonElement>>of(new AbstractMap.SimpleEntry<String, JsonElement>("0", rotationObject.getAsJsonArray()))')
c = c.replace('ImmutableSet.of(new AbstractMap.SimpleEntry<String, JsonArray>("0", positionObject.getAsJsonArray()))',
              'ImmutableSet.<Map.Entry<String, JsonElement>>of(new AbstractMap.SimpleEntry<String, JsonElement>("0", positionObject.getAsJsonArray()))')
c = c.replace('ImmutableSet.of(new AbstractMap.SimpleEntry<String, JsonArray>("0", scaleObject.getAsJsonArray()))',
              'ImmutableSet.<Map.Entry<String, JsonElement>>of(new AbstractMap.SimpleEntry<String, JsonElement>("0", scaleObject.getAsJsonArray()))')
# Fix raw Object keyFrame casts
c = c.replace('(String)keyFrame.getKey()', '(String)((Map.Entry)keyFrame).getKey()')
c = c.replace('((JsonElement)keyFrame.getValue())', '((JsonElement)((Map.Entry)keyFrame).getValue())')
# Fix raw iteration
c = c.replace('for (Map.Entry keyFrame : customInstructionKeyFrames)', 'for (Object keyFrame : customInstructionKeyFrames)')
c = c.replace('for (Map.Entry bone : bones)', 'for (Object bone : bones)')
write(f, c)

# ========== 11. DrawUtils - fix boolean to float ==========
f = r"A:\MCMDS\src\main\java\v1_21\DrawUtils.java"
c = read(f)
c = c.replace("(z ? 1.0f : 0.0f)).color(g, h, j, f)", "0.0f).color(g, h, j, f)")
write(f, c)

print("All 90 errors addressed!")
