/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.cosmeticsmod.morecosmetics.utils.VersionAdapter
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.CustomTextBox
 *  v1_21.morecosmetics.models.renderer.StackHolder
 */
package v1_21.morecosmetics.gui.components;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.cosmeticsmod.morecosmetics.utils.VersionAdapter;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.models.renderer.StackHolder;

/*
 * Exception performing whole class analysis ignored.
 */
@Environment(value=EnvType.CLIENT)
public class CustomTextBox {
    private int height;
    private int width;
    private int maxLength = 300;
    private int cursorCounter;
    public int xPosition;
    public int yPosition;
    private int lineScrollOffset;
    private int cursorPosition;
    private long lastCursorMove;
    private int selectionEnd;
    private boolean cursorDisplayEnabled = true;
    private boolean focused;
    private boolean hover;
    private boolean visible = true;
    private boolean enabled = true;
    private boolean centered;
    private String currentText = "";
    private String placeHolder = "";

    public CustomTextBox(int x, int y, int par5Width, int par6Height) {
        this.width = par5Width;
        this.height = par6Height;
        this.xPosition = x;
        this.yPosition = y;
    }

    public void drawTextBox(int mouseX, int mouseY) {
        this.drawTextBox(mouseX, mouseY, 0);
    }

    public void drawTextBox(int mouseX, int mouseY, int offset) {
        if (this.visible) {
            ++this.cursorCounter;
            this.hover = mouseX >= this.xPosition && mouseX <= this.xPosition + this.width && mouseY >= this.yPosition && mouseY <= this.yPosition + this.height;
            DrawUtils.drawRoundedRect((int)(this.xPosition - 1), (int)(this.yPosition - 1), (int)(this.xPosition + this.width + 1), (int)(this.yPosition + this.height + 1), (int)UIConstants.UI_SEPARATION_COLOR);
            DrawUtils.drawRoundedRect((int)this.xPosition, (int)this.yPosition, (int)(this.xPosition + this.width), (int)(this.yPosition + this.height), (int)UIConstants.UI_COMPONENT_COLOR);
            if (this.focused || this.hover) {
                DrawUtils.drawRoundedRect((int)this.xPosition, (int)this.yPosition, (int)(this.xPosition + this.width), (int)(this.yPosition + this.height), (int)UIConstants.UI_COMPONENT_HOVER);
            }
            this.xPosition += 2;
            this.yPosition = this.yPosition + 3 + offset;
            this.drawText(mouseX, mouseY);
            this.xPosition -= 2;
            this.yPosition = this.yPosition - 3 + offset;
            if (!this.enabled) {
                DrawUtils.drawRect((int)(this.xPosition - 1), (int)(this.yPosition - 1), (int)(this.xPosition + this.width + 1), (int)(this.yPosition + this.height + 1), (int)UIConstants.UI_COMPONENT_COLOR);
            }
        }
    }

    private void drawText(int mouseX, int mouseY) {
        MatrixStack stack = StackHolder.STACK;
        int j = this.cursorPosition - this.lineScrollOffset;
        int k = this.selectionEnd - this.lineScrollOffset;
        String s = DrawUtils.trimStringToWidth((String)this.currentText.substring(this.lineScrollOffset), (int)this.width, (boolean)false);
        boolean flag = j >= 0 && j <= s.length();
        boolean displayCursor = this.focused && this.cursorDisplayEnabled && System.currentTimeMillis() % 600L > 300L && flag;
        int l = this.centered ? this.xPosition + this.width / 2 - 2 : this.xPosition;
        int i1 = this.yPosition + this.height - 14;
        int j1 = l;
        VersionAdapter versionAdapter = MoreCosmetics.getInstance().getVersionAdapter();
        if (this.focused && System.currentTimeMillis() - this.lastCursorMove >= 100L && (versionAdapter.isKeyDown(263) || versionAdapter.isKeyDown(262))) {
            int move;
            this.lastCursorMove = System.currentTimeMillis();
            int n = move = versionAdapter.isKeyDown(263) ? -1 : 1;
            if (Screen.hasShiftDown()) {
                if (Screen.hasControlDown()) {
                    this.setSelectionPos(this.getNthWordFromPos(move, this.selectionEnd));
                } else {
                    this.setSelectionPos(this.selectionEnd + move);
                }
            } else if (Screen.hasControlDown()) {
                this.setCursorPosition(this.getNthWordFromCursor(move));
            } else {
                this.moveCursorBy(move);
            }
        }
        if (k > s.length()) {
            k = s.length();
        }
        if (s.length() > 0) {
            String s1;
            String string = s1 = flag ? s.substring(0, j) : s;
            j1 = this.centered ? DrawUtils.drawCenteredString((String)s1, (float)j1, (float)i1, (int)Color.WHITE.getRGB()) : DrawUtils.drawString((String)s1, (float)j1, (float)i1, (int)Color.WHITE.getRGB());
        } else if (this.centered) {
            DrawUtils.drawCenteredString((String)this.placeHolder, (float)j1, (float)i1, (int)UIConstants.UI_COMPONENT_HOVER);
        } else {
            DrawUtils.drawString((String)this.placeHolder, (float)j1, (float)i1, (int)UIConstants.UI_COMPONENT_HOVER);
        }
        boolean flag2 = this.cursorPosition < this.currentText.length() || this.currentText.length() >= this.maxLength;
        int k1 = j1;
        if (!flag) {
            k1 = j > 0 ? l + this.width : l;
        } else if (flag2) {
            k1 = j1 - 1;
            --j1;
        }
        if (s.length() > 0 && flag && j < s.length()) {
            j1 = DrawUtils.drawString((String)s.substring(j), (float)j1, (float)i1, (int)Color.WHITE.getRGB());
        }
        if (displayCursor) {
            DrawUtils.drawRect((int)(k1 - 1), (int)(i1 - 1), (int)k1, (int)(i1 - 1 + DrawUtils.getFontHeight()), (int)Color.GRAY.getRGB());
        }
        if (k != j) {
            int l1 = l + DrawUtils.getStringWidth((String)s.substring(0, k));
            this.drawCursorVertical(stack, k1, i1 - 1, l1 - 1, i1 + 1 + DrawUtils.getFontHeight());
        }
    }

    private void drawCursorVertical(MatrixStack stack, int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
        if (p_146188_1_ < p_146188_3_) {
            int i = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = i;
        }
        if (p_146188_2_ < p_146188_4_) {
            int j = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = j;
        }
        if (p_146188_3_ > this.xPosition + this.width) {
            p_146188_3_ = this.xPosition + this.width;
        }
        if (p_146188_1_ > this.xPosition + this.width) {
            p_146188_1_ = this.xPosition + this.width;
        }
        DrawUtils.drawRect((int)p_146188_1_, (int)p_146188_2_, (int)p_146188_3_, (int)p_146188_4_, (int)Utils.toRGB((int)255, (int)255, (int)255, (int)30));
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (this.currentText.length() > maxLength) {
            this.currentText = this.currentText.substring(0, maxLength);
        }
    }

    public void setText(String text) {
        this.currentText = text.length() > this.maxLength ? text.substring(0, this.maxLength) : text;
        this.setCursorPositionEnd();
    }

    public boolean textBoxKeyTyped(char charCode, int keyCode) {
        if (!this.focused) {
            return false;
        }
        if (Screen.isSelectAll((int)keyCode)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        if (Screen.isCopy((int)keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            return true;
        }
        if (Screen.isPaste((int)keyCode)) {
            if (this.enabled) {
                this.writeText(MinecraftClient.getInstance().keyboard.getClipboard());
            }
            return true;
        }
        if (Screen.isCut((int)keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            if (this.enabled) {
                this.writeText("");
            }
            return true;
        }
        switch (keyCode) {
            case 259: {
                if (Screen.hasControlDown() && this.enabled) {
                    this.deleteWords(-1);
                } else if (this.enabled) {
                    this.deleteFromCursor(-1);
                }
                return true;
            }
            case 268: {
                if (Screen.hasShiftDown()) {
                    this.setSelectionPos(0);
                } else {
                    this.setCursorPosition(0);
                }
                return true;
            }
            case 262: 
            case 263: {
                return true;
            }
            case 269: {
                if (Screen.hasShiftDown()) {
                    this.setSelectionPos(this.currentText.length());
                } else {
                    this.setCursorPositionEnd();
                }
                return true;
            }
            case 261: {
                if (Screen.hasControlDown() && this.enabled) {
                    this.deleteWords(1);
                } else if (this.enabled) {
                    this.deleteFromCursor(1);
                }
                return true;
            }
        }
        if (CustomTextBox.isValidChar((char)charCode)) {
            if (this.enabled) {
                this.writeText(Character.toString(charCode));
            }
            return true;
        }
        return false;
    }

    public static boolean isValidChar(char chr) {
        return chr != '\u00a7' && chr >= ' ' && chr != '\u007f';
    }

    public static String stripInvalidChars(String s) {
        return CustomTextBox.stripInvalidChars((String)s, (boolean)false);
    }

    public static String stripInvalidChars(String s, boolean allowLinebreaks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (CustomTextBox.isValidChar((char)c)) {
                stringBuilder.append(c);
                continue;
            }
            if (!allowLinebreaks || c != '\n') continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.setFocused(this.hover);
        if (this.focused && mouseButton == 0) {
            int i = mouseX - this.xPosition;
            String s = DrawUtils.trimStringToWidth((String)this.currentText.substring(this.lineScrollOffset), (int)this.width, (boolean)false);
            this.setCursorPosition(DrawUtils.trimStringToWidth((String)s, (int)i, (boolean)false).length() + this.lineScrollOffset);
        }
        return this.focused;
    }

    public String getSelectedText() {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.currentText.substring(i, j);
    }

    public void writeText(String text) {
        Object s = "";
        String filtered = CustomTextBox.stripInvalidChars((String)text);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxLength - this.currentText.length() - (i - j);
        int l = 0;
        if (this.currentText.length() > 0) {
            s = (String)s + this.currentText.substring(0, i);
        }
        if (k < filtered.length()) {
            s = (String)s + filtered.substring(0, k);
            l = k;
        } else {
            s = (String)s + filtered;
            l = filtered.length();
        }
        if (this.currentText.length() > 0 && j < this.currentText.length()) {
            s = (String)s + this.currentText.substring(j);
        }
        this.currentText = (String)s;
        this.moveCursorBy(i - this.selectionEnd + l);
    }

    public void deleteWords(int amount) {
        if (this.currentText.length() != 0 && this.selectionEnd != this.cursorPosition) {
            this.writeText("");
        }
    }

    public void deleteFromCursor(int amount) {
        if (this.currentText.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean flag = amount < 0;
                int i = flag ? this.cursorPosition + amount : this.cursorPosition;
                int j = flag ? this.cursorPosition : this.cursorPosition + amount;
                Object s = "";
                if (i >= 0) {
                    s = this.currentText.substring(0, i);
                }
                if (j < this.currentText.length()) {
                    s = (String)s + this.currentText.substring(j);
                }
                this.currentText = (String)s;
                if (flag) {
                    this.moveCursorBy(amount);
                }
            }
        }
    }

    private void setSelectionPos(int selectedPos) {
        this.selectionEnd = selectedPos = MathUtils.clampInt((int)selectedPos, (int)0, (int)this.currentText.length());
        String s = DrawUtils.trimStringToWidth((String)this.currentText.substring(Math.max(0, this.lineScrollOffset)), (int)this.width, (boolean)false);
        int k = s.length() + this.lineScrollOffset;
        if (selectedPos == this.lineScrollOffset) {
            this.lineScrollOffset -= DrawUtils.trimStringToWidth((String)this.currentText, (int)this.width, (boolean)true).length();
        }
        if (selectedPos > k) {
            this.lineScrollOffset += selectedPos - k;
        } else if (selectedPos <= this.lineScrollOffset) {
            this.lineScrollOffset -= this.lineScrollOffset - selectedPos;
        }
        this.lineScrollOffset = MathUtils.clampInt((int)this.lineScrollOffset, (int)0, (int)this.currentText.length());
    }

    private void setCursorPositionEnd() {
        this.setCursorPosition(this.currentText.length());
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public void moveCursorBy(int duration) {
        this.setCursorPosition(this.selectionEnd + duration);
    }

    public void setCursorPosition(int position) {
        this.cursorPosition = position;
        this.cursorPosition = MathUtils.clampInt((int)this.cursorPosition, (int)0, (int)this.currentText.length());
        this.setSelectionPos(this.cursorPosition);
    }

    public int getNthWordFromCursor(int p_146187_1_) {
        return this.getNthWordFromPos(p_146187_1_, this.cursorPosition);
    }

    public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
        return this.dontKnowTheRealNameOfThisMethod(p_146183_1_, p_146183_2_, true);
    }

    public int dontKnowTheRealNameOfThisMethod(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
        int i = p_146197_2_;
        boolean flag = p_146197_1_ < 0;
        int j = Math.abs(p_146197_1_);
        for (int k = 0; k < j; ++k) {
            if (!flag) {
                int l = this.currentText.length();
                if ((i = this.currentText.indexOf(32, i)) == -1) {
                    i = l;
                    continue;
                }
                while (p_146197_3_ && i < l && this.currentText.charAt(i) == ' ') {
                    ++i;
                }
                continue;
            }
            while (p_146197_3_ && i > 0 && this.currentText.charAt(i - 1) == ' ') {
                --i;
            }
            while (i > 0 && this.currentText.charAt(i - 1) != ' ') {
                --i;
            }
        }
        return i;
    }

    public void setFocused(boolean focused) {
        if (focused && !this.focused) {
            this.cursorCounter = 0;
        }
        this.focused = focused;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public int getHeight() {
        return this.height;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public String getText() {
        return this.currentText;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setCursorDisplayEnabled(boolean cursorDisplayEnabled) {
        this.cursorDisplayEnabled = cursorDisplayEnabled;
    }

    public boolean isKeyComboCtrlA() {
        return Screen.hasControlDown() && MoreCosmetics.getInstance().getVersionAdapter().isKeyDown(65);
    }

    public boolean isKeyComboCtrlC() {
        return Screen.hasControlDown() && MoreCosmetics.getInstance().getVersionAdapter().isKeyDown(67);
    }

    public boolean isKeyComboCtrlV() {
        return Screen.hasControlDown() && MoreCosmetics.getInstance().getVersionAdapter().isKeyDown(86);
    }

    public boolean isKeyComboCtrlX() {
        return Screen.hasControlDown() && MoreCosmetics.getInstance().getVersionAdapter().isKeyDown(88);
    }
}

