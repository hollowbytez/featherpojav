package net.hollowclient.mixin.client;

import net.hollowclient.client.config.HollowConfig;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {

    @Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
    private void getSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) (Object) this;
        
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if (client.player != null && player.getUuid().equals(client.player.getUuid())) {
            if (HollowConfig.INSTANCE.customCape && HollowConfig.INSTANCE.currentCape != null) {
                SkinTextures original = cir.getReturnValue();
                Identifier capeId = Identifier.of("hollowclient", "textures/capes/" + HollowConfig.INSTANCE.currentCape + ".png");
                SkinTextures modified = new SkinTextures(
                    original.texture(),
                    original.textureUrl(),
                    capeId,
                    capeId,
                    original.model(),
                    original.secure()
                );
                cir.setReturnValue(modified);
            }
        }
    }
}
