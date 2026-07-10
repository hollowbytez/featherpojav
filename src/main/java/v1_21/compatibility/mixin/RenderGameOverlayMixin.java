/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.gui.hud.InGameOverlayRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  v1_21.morecosmetics.compatibility.mixin.RenderGameOverlayMixin
 */
package v1_21.morecosmetics.compatibility.mixin;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={InGameOverlayRenderer.class})
public class RenderGameOverlayMixin {
    @Inject(at={@At(value="HEAD")}, method={"renderOverlays"})
    private static void renderOverlays(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (CompatibilityManager.isOnForge()) {
            return;
        }
        v1_21.morecosmetics.models.renderer.StackHolder.update(matrices);
        if (!MoreCosmetics.getInstance().getVersionAdapter().isOnScreen("VersionGui")) {
            MoreCosmetics.getInstance().getNotificationHandler().draw((Object)matrices, -1);
        }
    }
}

