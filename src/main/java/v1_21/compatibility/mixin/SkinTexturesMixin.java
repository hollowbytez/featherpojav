/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.util.SkinTextures
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  v1_21.morecosmetics.compatibility.mixin.SkinTexturesMixin
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 */
package v1_21.morecosmetics.compatibility.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.SkinTextures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;

@Environment(value=EnvType.CLIENT)
@Mixin(value={SkinTextures.class})
public abstract class SkinTexturesMixin {
    @Inject(at={@At(value="HEAD")}, method={"capeTexture"}, cancellable=true)
    public void capeTexture(CallbackInfoReturnable<Identifier> cir) {
        if (ModelCosmeticRenderer.NEXT_CAPE_LOC != null) {
            cir.setReturnValue(ModelCosmeticRenderer.NEXT_CAPE_LOC);
        }
    }
}

