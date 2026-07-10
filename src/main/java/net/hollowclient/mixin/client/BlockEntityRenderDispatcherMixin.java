package net.hollowclient.mixin.client;

import net.hollowclient.client.config.HollowConfig;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

    @Inject(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void onRender(E blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (HollowConfig.INSTANCE.fastChest && blockEntity instanceof net.minecraft.block.entity.ChestBlockEntity) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                double distSq = client.player.squaredDistanceTo(
                    blockEntity.getPos().getX() + 0.5,
                    blockEntity.getPos().getY() + 0.5,
                    blockEntity.getPos().getZ() + 0.5
                );
                if (distSq > 1024) { // 32 blocks (32^2 = 1024)
                    ci.cancel();
                }
            }
        }
    }
}
