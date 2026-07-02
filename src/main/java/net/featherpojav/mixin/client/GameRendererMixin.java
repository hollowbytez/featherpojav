package net.featherpojav.mixin.client;

import net.featherpojav.client.FeatherPojavModClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void onGetFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (FeatherPojavModClient.isZooming) {
            FeatherPojavModClient.targetZoomLevel = 4.0f;
        } else {
            FeatherPojavModClient.targetZoomLevel = 1.0f;
        }

        // Smoothly interpolate zoom transition (20% step per frame)
        FeatherPojavModClient.zoomLevel += (FeatherPojavModClient.targetZoomLevel - FeatherPojavModClient.zoomLevel) * 0.2f;

        if (FeatherPojavModClient.zoomLevel > 1.01f) {
            cir.setReturnValue(cir.getReturnValue() / FeatherPojavModClient.zoomLevel);
        }
    }
}
