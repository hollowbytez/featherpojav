/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.MinecraftClient
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  v1_21.morecosmetics.compatibility.mixin.MinecraftTickMixin
 */
package v1_21.morecosmetics.compatibility.mixin;

import com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={MinecraftClient.class})
public class MinecraftTickMixin {
    @Inject(at={@At(value="HEAD")}, method={"tick"})
    public void onTick(CallbackInfo ci) {
        MoreCosmeticsAPI.onTick();
    }
}

