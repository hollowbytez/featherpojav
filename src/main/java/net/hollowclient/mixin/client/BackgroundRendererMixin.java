package net.hollowclient.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.hollowclient.client.config.HollowConfig;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void onApplyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        if (HollowConfig.INSTANCE.noFog) {
            RenderSystem.setShaderFogStart(viewDistance * 2.0F);
            RenderSystem.setShaderFogEnd(viewDistance * 3.0F);
            ci.cancel();
        }
    }
}
