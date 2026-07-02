package net.featherpojav.mixin.client;

import net.featherpojav.client.FeatherPojavModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(
        method = "updateMouse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"
        )
    )
    private void redirectChangeLookDirection(ClientPlayerEntity player, double dx, double dy) {
        if (FeatherPojavModClient.isFreelooking) {
            // Apply camera orientation changes client-side only
            FeatherPojavModClient.freelookYaw += dx;
            FeatherPojavModClient.freelookPitch += dy;

            // Clamp pitch to prevent camera flips
            if (FeatherPojavModClient.freelookPitch < -90.0f) {
                FeatherPojavModClient.freelookPitch = -90.0f;
            }
            if (FeatherPojavModClient.freelookPitch > 90.0f) {
                FeatherPojavModClient.freelookPitch = 90.0f;
            }
        } else {
            player.changeLookDirection(dx, dy);
        }
    }
}
