/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI
 *  com.cosmeticsmod.morecosmetics.utils.OpenMode
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.player.PlayerModelPart
 *  net.minecraft.text.Text
 *  net.minecraft.client.gui.Element
 *  net.minecraft.client.gui.widget.ButtonWidget
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.gui.screen.option.SkinOptionsScreen
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  v1_21.morecosmetics.compatibility.mixin.SkinOptionsMixin
 */
package v1_21.morecosmetics.compatibility.mixin;

import com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI;
import com.cosmeticsmod.morecosmetics.utils.OpenMode;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.text.Text;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={SkinOptionsScreen.class})
public abstract class SkinOptionsMixin
extends Screen {
    private SkinOptionsMixin(Text title) {
        super(title);
    }

    @Inject(at={@At(value="TAIL")}, method={"addOptions"})
    public void addButton(CallbackInfo ci) {
        if (!OpenMode.openOnlyOn((OpenMode)OpenMode.KEYBIND)) {
            int i = PlayerModelPart.values().length + 4;
            if (i % 2 == 1) {
                ++i;
            }
            this.addDrawableChild(ButtonWidget.builder((Text)Text.literal((String)"\u00a7aMoreCosmetics"), btn -> MoreCosmeticsAPI.openUI((boolean)false)).dimensions(this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), 200, 20).build());
        }
    }
}

