/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.Entity
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.entity.EntityRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  v1_21.morecosmetics.compatibility.mixin.RenderEntityMixin
 */
package v1_21.morecosmetics.compatibility.mixin;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(value={EntityRenderer.class})
public class RenderEntityMixin {
    @Inject(at={@At(value="HEAD")}, method={"render"})
    public void onRender(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        SharedVars.LIGHT = light;
        MoreCosmeticsAPI.onRenderName((Object)matrices, (Object)entity, (double)entity.getX(), (double)(entity.getY() + 0.25), (double)entity.getZ());
        UUID uuid = entity.getUuid();
        MoreCosmetics mod = MoreCosmetics.getInstance();
        CosmeticUser user = (CosmeticUser)mod.getUserHandler().getUsers().get(uuid);
        if (user != null && user.getNametagHeight() > 0.0f && !uuid.equals(mod.getVersionAdapter().getUuid(true))) {
            matrices.translate(0.0f, user.getNametagHeight(), 0.0f);
        }
    }
}

