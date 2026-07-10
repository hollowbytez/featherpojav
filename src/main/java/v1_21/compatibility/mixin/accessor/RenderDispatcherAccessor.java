/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.client.util.SkinTextures$class_7920
 *  net.minecraft.client.render.entity.EntityRenderer
 *  net.minecraft.client.render.entity.EntityRenderDispatcher
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  v1_21.morecosmetics.compatibility.mixin.accessor.RenderDispatcherAccessor
 */
package v1_21.morecosmetics.compatibility.mixin.accessor;

import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(value=EnvType.CLIENT)
@Mixin(value={EntityRenderDispatcher.class})
public interface RenderDispatcherAccessor {
    @Accessor
    public Map<SkinTextures.Model, EntityRenderer<? extends PlayerEntity>> getModelRenderers();
}

