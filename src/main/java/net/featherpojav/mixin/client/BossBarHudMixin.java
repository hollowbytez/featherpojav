package net.featherpojav.mixin.client;

import net.featherpojav.client.config.FeatherConfig;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossBarHudMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, CallbackInfo ci) {
        if (!FeatherConfig.INSTANCE.bossBar) {
            ci.cancel();
        }
    }
}
