package net.featherpojav.mixin.client;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Session.class)
public class SessionMixin {
    @Inject(method = "getUsername", at = @At("HEAD"), cancellable = true)
    private void onGetUsername(CallbackInfoReturnable<String> cir) {
        if (FeatherConfig.INSTANCE.nickHider) {
            cir.setReturnValue("HollowBytez");
        }
    }
}
