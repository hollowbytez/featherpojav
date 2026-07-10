import os
import re

# BoxManager
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\gui\core\box\BoxManager.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("public interface BoxGuiInstance {\n    }", "public interface BoxGuiInstance {\n        void refreshGui();\n    }")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# PresetManager
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\user\presets\PresetManager.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("public enum SortMode {\n        NAME, DATE, TYPE\n    }", """public enum SortMode {
        NAME, DATE, TYPE;
        public java.util.Comparator<com.cosmeticsmod.morecosmetics.user.presets.Preset> getComparator() {
            return (a, b) -> 0;
        }
    }""")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# GifDecoder
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\utils\GifDecoder.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("""    public static class ImageFrame {
        public java.awt.image.BufferedImage getImage() { return null; }
        public int getDelay() { return 0; }
    }""", """    public static class ImageFrame {
        private java.awt.image.BufferedImage image;
        private int delay;
        private String disposal;
        public ImageFrame(java.awt.image.BufferedImage image, int delay, String disposal) {
            this.image = image; this.delay = delay; this.disposal = disposal;
        }
        public java.awt.image.BufferedImage getImage() { return image; }
        public int getDelay() { return delay; }
        public String getDisposal() { return disposal; }
    }""")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# NettyClient
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\networking\NettyClient.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("EpollEventLoopGroup eventGroup =", "io.netty.channel.EventLoopGroup eventGroup =")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# UserHandler
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\user\UserHandler.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("cos.forEach(val -> cosList.add(val.intValue()));", "cos.forEach(val -> cosList.add(((Number)val).intValue()));")
c = c.replace("popups.forEach(val -> this.viewedPopups.add(val.intValue()));", "popups.forEach(val -> this.viewedPopups.add(((Number)val).intValue()));")
c = c.replace("userCosmetics.values().forEach(data -> data.setActive(false));", "userCosmetics.values().forEach(data -> ((com.cosmeticsmod.morecosmetics.models.model.ModelData)data).setActive(false));")
c = c.replace("this.currentOnlineData.put(en.getKey(),", "this.currentOnlineData.put((Integer)en.getKey(),")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# SkinOptionsMixin
f = r"A:\MCMDS\src\main\java\v1_21\compatibility\mixin\SkinOptionsMixin.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("this.addDrawableChild((Element)ButtonWidget.builder", "this.addDrawableChild(ButtonWidget.builder")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# SkinTexturesMixin
f = r"A:\MCMDS\src\main\java\v1_21\compatibility\mixin\SkinTexturesMixin.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("cir.setReturnValue((Object)ModelCosmeticRenderer.NEXT_CAPE_LOC);", "cir.setReturnValue(ModelCosmeticRenderer.NEXT_CAPE_LOC);")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# DrawUtils
f = r"A:\MCMDS\src\main\java\v1_21\DrawUtils.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("(float)z).color(g, h, j, f)", "(z ? 1.0f : 0.0f)).color(g, h, j, f)")
with open(f, "w", encoding="utf-8") as file: file.write(c)

# CustomTextBox
f = r"A:\MCMDS\src\main\java\v1_21\gui\components\CustomTextBox.java"
with open(f, "r", encoding="utf-8") as file: c = file.read()
c = c.replace("this.currentText = s;", "this.currentText = (String)s;")
with open(f, "w", encoding="utf-8") as file: file.write(c)

print("Fixed syntax errors.")
