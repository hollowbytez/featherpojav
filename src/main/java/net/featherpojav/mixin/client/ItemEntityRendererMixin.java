package net.featherpojav.mixin.client;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    @Inject(
        method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER)
    )
    private void onRenderPush(ItemEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (FeatherConfig.INSTANCE.itemPhysics) {
            // Apply physics rotation and flat lay if on the ground or floating in water
            if (entity.isOnGround() || entity.isSubmergedInWater()) {
                // Lower slightly so item rests directly on block surface without clipping
                matrices.translate(0.0, -0.06, 0.0);

                // Rotate 90 degrees around X-axis to lay it flat (1.5707963f radians is 90 degrees)
                matrices.multiply(new Quaternionf().rotationX(1.5707963f));

                // Apply a distinct, static rotation based on entity ID to give items a natural scattered look
                float uniqueRotation = (float) ((entity.getId() % 16) * 22.5f);
                matrices.multiply(new Quaternionf().rotationZ(uniqueRotation * 0.017453292f)); // Degrees to radians
            }
        }
    }
}
