import os

def inject_before_last_brace(path, content):
    with open(path, "r", encoding="utf-8") as f:
        lines = f.readlines()
        
    last_brace = -1
    for i in range(len(lines)-1, -1, -1):
        if "}" in lines[i]:
            last_brace = i
            break
            
    if last_brace != -1:
        lines.insert(last_brace, content)
        with open(path, "w", encoding="utf-8") as f:
            f.writelines(lines)
            
# BoxGuiInstance
path1 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\box\BoxManager.java"
inject_before_last_brace(path1, "\n    public interface BoxGuiInstance {\n    }\n")

# TextureCategory
path2 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\texture\TextureCategory.java"
inject_before_last_brace(path2, """
    public interface UpdateInterface {
        void update();
    }
    public enum EnumSearchType {
        CONTAINS, EQUALS, STARTS_WITH
    }
""")

# PopupFetcher
path3 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\misc\PopupFetcher.java"
inject_before_last_brace(path3, """
    public static class Popup {
        public String title;
        public String description;
        public Runnable action;
    }
""")

# PresetManager
path4 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\user\presets\PresetManager.java"
inject_before_last_brace(path4, """
    public enum SortMode {
        NAME, DATE, TYPE
    }
""")

# GifDecoder
path5 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\utils\GifDecoder.java"
inject_before_last_brace(path5, """
    public static class ImageFrame {
        public java.awt.image.BufferedImage getImage() { return null; }
        public int getDelay() { return 0; }
    }
""")

print("Injected missing inner classes.")
