package net.hollowclient.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(SplashOverlay.class)
public abstract class SplashOverlayMixin {

    @Shadow @Final private boolean reloading;
    @Shadow private float progress;
    @Shadow private long reloadCompleteTime;
    @Shadow private long reloadStartTime;
    @Shadow @Final private ResourceReload reload;
    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private java.util.function.Consumer<Optional<Throwable>> exceptionHandler;

    private static final Identifier LOGO = Identifier.of("hollowclient", "textures/gui/loading_logo.png");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void customRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // 1. Defensive Check: Null client or context
        if (this.client == null || context == null) return;

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        // 1.5. AGGRESSIVE ANTI-WHITE-FLASH:
        // Always draw a solid black background behind everything first
        context.fill(0, 0, width, height, 0xFF000000);

        // 2. Safe Time Tracking for state (Copied vanilla fade logic)
        long currentTime = Util.getMeasuringTimeMs();
        if (this.reloading && this.reloadStartTime == -1L) {
            this.reloadStartTime = currentTime;
        }

        float fadeOutTime = this.reloadCompleteTime > -1L ? (float)(currentTime - this.reloadCompleteTime) / 1000.0F : -1.0F;
        float fadeInTime = this.reloadStartTime > -1L ? (float)(currentTime - this.reloadStartTime) / 500.0F : -1.0F;

        // 3. Defensive Check: Fade limits
        float alpha = 1.0F;
        if (fadeOutTime >= 1.0F) {
            alpha = 0.0F; // Fully done
            this.client.setOverlay(null);
            if (this.client.currentScreen != null) {
                this.client.currentScreen.render(context, 0, 0, delta);
            }
            int l = MathHelper.ceil((1.0F - MathHelper.clamp(fadeOutTime - 1.0F, 0.0F, 1.0F)) * 255.0F);
            context.fill(0, 0, width, height, ColorHelper.Argb.getArgb(l, 0, 0, 0));
        } else if (this.reloading) {
            if (this.client.currentScreen != null && fadeInTime < 1.0F) {
                this.client.currentScreen.render(context, mouseX, mouseY, delta);
            }
            int l = MathHelper.ceil(MathHelper.clamp((double)fadeInTime, 0.15, 1.0) * 255.0);
            context.fill(0, 0, width, height, ColorHelper.Argb.getArgb(l, 0, 0, 0));
        } else {
            // CRITICAL: Proper OpenGL clear for window state
            RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 1.0F);
            RenderSystem.clear(16384, MinecraftClient.IS_SYSTEM_MAC); // 16384 = GL_COLOR_BUFFER_BIT
        }

        // 4. Update internal progress safely
        float realProgress = this.reload.getProgress();
        this.progress = MathHelper.clamp(this.progress * 0.95F + realProgress * 0.050000012F, 0.0F, 1.0F);
        
        // Critical Fix: Floating point asymptote check from vanilla
        if (realProgress >= 1.0F) {
            this.progress = 1.0F;
        }

        // 9. CRITICAL FIX: Tell Minecraft the reload is actually finished
        if (this.reloadCompleteTime == -1L && this.reload.isComplete() && (!this.reloading || fadeInTime >= 2.0F)) {
            try {
                this.reload.throwException();
                this.exceptionHandler.accept(Optional.empty());
            } catch (Throwable t) {
                this.exceptionHandler.accept(Optional.of(t));
            }
            this.reloadCompleteTime = currentTime;
            if (this.client.currentScreen != null) {
                this.client.currentScreen.init(this.client, context.getScaledWindowWidth(), context.getScaledWindowHeight());
            }
        }

        // 4b. 15 Second Timeout Fallback
        if (this.reloadStartTime > -1L && (currentTime - this.reloadStartTime) > 15000L && fadeOutTime < 1.0F) {
            this.client.setOverlay(null);
            System.err.println("[HollowClient] Loading Screen 15s timeout reached. Forcing game launch.");
            ci.cancel();
            return;
        }

        // 5. Draw Custom Logo if alpha > 0
        if (alpha > 0.0F && fadeOutTime < 1.0F) {
            try {
                // Smooth continuous rotation (360° over 4 seconds)
                float timeSec = (currentTime % 4000L) / 4000.0F;
                
                // Ease-in-out rotation for 360 degrees:
                // A cubic ease-in-out curve for angle:
                float ease = timeSec < 0.5F ? 4.0F * timeSec * timeSec * timeSec : 1.0F - (float)Math.pow(-2.0F * timeSec + 2.0F, 3.0F) / 2.0F;
                float angle = ease * 360.0F;

                int logoSize = 128; // Adjust based on preference
                int x = width / 2;
                int y = height / 2;

                MatrixStack matrices = context.getMatrices();
                matrices.push();
                matrices.translate(x, y, 0);

                // 6. Defensive check: Math operations
                if (Float.isNaN(angle)) angle = 0;
                
                // Rotation
                matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(angle));

                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                
                // Draw a soft white glow behind the logo
                net.hollowclient.client.gui.RenderUtils.drawRoundedRect(matrices, -logoSize / 2 - 12, -logoSize / 2 - 12, logoSize + 24, logoSize + 24, (logoSize + 24) / 2f, 0x22FFFFFF);
                // Draw a thin white stroke outline circle behind the logo
                net.hollowclient.client.gui.RenderUtils.drawRoundedRect(matrices, -logoSize / 2 - 2, -logoSize / 2 - 2, logoSize + 4, logoSize + 4, (logoSize + 4) / 2f, 0xFFFFFFFF);
                
                // Restore state before drawing the main logo
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                // 7. Defensive Resource Bind
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F - MathHelper.clamp(fadeOutTime, 0.0F, 1.0F));
                context.drawTexture(LOGO, -logoSize / 2, -logoSize / 2, 0, 0, logoSize, logoSize, logoSize, logoSize);
                
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                
                // 7b. CRITICAL: Restore GL state so TitleScreen and F11 Fullscreen don't break
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                
                matrices.pop();
            } catch (Exception e) {
                // 8. Final Stability Loop Fallback
                System.err.println("[HollowClient] Loading screen logo render error: " + e.getMessage());
            }
        }

        ci.cancel(); // Cancel original rendering completely
    }
}
