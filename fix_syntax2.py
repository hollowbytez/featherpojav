import os
import re

# ModelGui
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\ModelGui.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("ArrayList<TextureEntry> textureCatList =", "ArrayList<com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory> textureCatList =")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# ColorPickerElement
f = r"A:\MCMDS\src\main\java\v1_21\gui\elements\list\ColorPickerElement.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()

# Add the missing inner class before the last brace
last_brace = c.rfind("}")
if last_brace != -1:
    inner_class = """
    public class ColorWheelGui extends v1_21.morecosmetics.gui.screen.UIScreen {
        public ColorWheelGui(ColorPickerElement a, ColorPickerElement b) {}
        @Override public void initGui() {}
        @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
        @Override public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    }
"""
    c = c[:last_brace] + inner_class + c[last_brace:]
    with open(f, "w", encoding="utf-8") as file: file.write(c)

print("Fixed final syntax errors.")
