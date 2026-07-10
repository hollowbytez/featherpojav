/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  net.minecraft.client.render.entity.feature.CapeFeatureRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  v1_21.morecosmetics.compatibility.mixin.RenderCapeMixin
 *  v1_21.morecosmetics.models.cloaks.CloakRenderer
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomImage
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.compatibility.mixin;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.MoreCosmeticsAPI;
import com.cosmeticsmod.morecosmetics.models.ModelHandler;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import v1_21.morecosmetics.models.cloaks.CloakRenderer;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomImage;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
@Mixin(value={CapeFeatureRenderer.class})
public abstract class RenderCapeMixin {
    private final CloakRenderer cloakRenderer = new CloakRenderer();
    private final CustomTextureManager textureManager = new CustomTextureManager("mcloaks");
    private boolean lastState;

    @Inject(at={@At(value="HEAD")}, method={"render"}, cancellable=true)
    public void injectCapeRender(MatrixStack stack, VertexConsumerProvider provider, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        String cloak;
        CustomImage image;
        StackHolder.update((MatrixStack)stack);
        CosmeticUser user = MoreCosmetics.getInstance().getUserHandler().getUser(entity.getUuid());
        ModelCosmeticRenderer.NEXT_CAPE_LOC = null;
        if (user != null && MoreCosmeticsAPI.isCloakEnabled() && ModConfig.getConfig().cloaks && user.hasCloak() && (image = this.textureManager.getImage(cloak = user.getCloak().getUrl(), cloak, null, ModelHandler.getCloakTransformer())) != null) {
            boolean mojangRender = ModConfig.getConfig().cloakCompatibility;
            if (this.lastState != mojangRender) {
                this.lastState = mojangRender;
                this.textureManager.remove(cloak);
            }
            if (mojangRender) {
                ModelCosmeticRenderer.NEXT_CAPE_LOC = image.getLocation();
            } else if (this.cloakRenderer.render(stack, entity, image.getLocation(), tickDelta, provider, light)) {
                ci.cancel();
            }
        }
    }
}

