package net.hollowclient.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.hollowclient.client.config.HollowConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class HollowCapesScreen extends Screen {
    private final Screen parent;
    private int panelX, panelY, panelW, panelH;
    private String hoveredCapeCode = null;

    public HollowCapesScreen(Screen parent) {
        super(Text.of("Cosmetics - Capes Selection"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        panelW = (int) (this.width * 0.8);
        panelH = (int) (this.height * 0.85);
        panelX = (this.width - panelW) / 2;
        panelY = (this.height - panelH) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.fill(0, 0, this.width, this.height, 0x44000000);

        // Main Panel Background
        RenderUtils.drawRoundedRect(context.getMatrices(), panelX, panelY, panelW, panelH, 8, 0xD9141416);
        RenderUtils.drawRoundedOutline(context.getMatrices(), panelX, panelY, panelW, panelH, 8, 1.0f, 0x30FFFFFF);

        // Header Text
        context.drawCenteredTextWithShadow(this.textRenderer, "✦ COSMETICS: CAPES SELECTION ✦", panelX + panelW / 2, panelY + 12, 0xFFFFFFFF);
        context.fill(panelX + 10, panelY + 26, panelX + panelW - 10, panelY + 27, 0x30FFFFFF);

        // Back Button
        int backX = panelX + 15;
        int backY = panelY + 10;
        boolean backHovered = mouseX >= backX && mouseX <= backX + 45 && mouseY >= backY && mouseY <= backY + 14;
        context.drawText(this.textRenderer, "◀ Back", backX, backY, backHovered ? 0xFFFF3366 : 0xFF888888, true);

        // Layout Separation: Left 65% for Grid, Right 35% for 3D Preview
        int gridW = (int) (panelW * 0.65);
        int previewW = panelW - gridW - 24;
        int previewX = panelX + gridW + 12;
        int previewY = panelY + 36;
        int previewH = panelH - 48;

        // Render Grid of 22 Capes (4 Columns, 6 Rows)
        int cols = 4;
        int pad = 8;
        int startX = panelX + 16;
        int startY = panelY + 36;
        int cardW = (gridW - 32 - pad * (cols - 1)) / cols;
        int cardH = 26;

        hoveredCapeCode = null;
        int index = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (index > 22) break;
                
                int cx = startX + col * (cardW + pad);
                int cy = startY + row * (cardH + pad);
                String capeName = "Cape " + index;
                String capeCode = "cape_" + index;
                boolean isActive = HollowConfig.INSTANCE.currentCape.equals(capeCode);
                
                boolean hovered = mouseX >= cx && mouseX <= cx + cardW && mouseY >= cy && mouseY <= cy + cardH;
                if (hovered) {
                    hoveredCapeCode = capeCode;
                }
                
                int bg = isActive ? 0x80330A1A : (hovered ? 0x502A2B36 : 0x40110408);
                int border = isActive ? 0xFFFF1A55 : (hovered ? 0x90FF1A55 : 0x20FFFFFF);
                
                RenderUtils.drawRoundedRect(context.getMatrices(), cx, cy, cardW, cardH, 4, bg);
                RenderUtils.drawRoundedOutline(context.getMatrices(), cx, cy, cardW, cardH, 4, 1.0f, border);
                
                int tx = cx + (cardW - this.textRenderer.getWidth(capeName)) / 2;
                int ty = cy + (cardH - this.textRenderer.fontHeight) / 2;
                context.drawText(this.textRenderer, capeName, tx, ty, isActive ? 0xFFFF6688 : 0xFFDDDDDD, true);
                
                index++;
            }
        }

        // --- Render 3D Preview Panel (Right Side) ---
        RenderUtils.drawRoundedRect(context.getMatrices(), previewX, previewY, previewW, previewH, 6, 0x50110408);
        RenderUtils.drawRoundedOutline(context.getMatrices(), previewX, previewY, previewW, previewH, 6, 1.0f, 0x30FFFFFF);

        String activePreviewCape = hoveredCapeCode != null ? hoveredCapeCode : HollowConfig.INSTANCE.currentCape;
        String previewLabel = (hoveredCapeCode != null ? "PREVIEWING: " : "ACTIVE: ") + activePreviewCape.replace("cape_", "Cape ");
        
        context.drawCenteredTextWithShadow(this.textRenderer, "3D CAPE VIEW", previewX + previewW / 2, previewY + 10, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, previewLabel, previewX + previewW / 2, previewY + previewH - 18, 0xFFFF1A55);

        // Draw the 3D rotating cape in the preview box center
        int previewCenterX = previewX + previewW / 2;
        int previewCenterY = previewY + previewH / 2 - 10;
        int capeScale = Math.min(previewW, previewH) / 2; // Scale based on panel size

        Identifier previewTexture = Identifier.of("hollowclient", "textures/capes/" + activePreviewCape + ".png");
        draw3DRotatingCape(context, previewCenterX, previewCenterY, capeScale, previewTexture);
        
        super.render(context, mouseX, mouseY, delta);
    }

    private void draw3DRotatingCape(DrawContext context, int x, int y, int scale, Identifier texture) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        
        // Translate to position and scale
        matrices.translate(x, y, 200.0f);
        matrices.scale(scale, scale, scale);

        // Rotation Math (rotating around Y axis over time)
        float rotation = (System.currentTimeMillis() % 6000) / 6000.0f * 360.0f;
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_X.rotationDegrees(15.0f)); // Subtle tilt

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

        // Dimensions of cape (aspect ratio matching standard Minecraft cape: 10 wide, 16 high, 1 deep)
        float w = 0.28f;
        float h = 0.48f;
        float d = 0.028f;

        // Texture UV bounds (total texture is 64x32)
        // 1. Back Face (Main display: U=12 to 22, V=1 to 17)
        float bu1 = 12f / 64f, bu2 = 22f / 64f;
        float bv1 = 1f / 32f, bv2 = 17f / 32f;
        buffer.vertex(matrix, -w, -h, d).texture(bu2, bv1);
        buffer.vertex(matrix, -w, h, d).texture(bu2, bv2);
        buffer.vertex(matrix, w, h, d).texture(bu1, bv2);
        buffer.vertex(matrix, w, -h, d).texture(bu1, bv1);

        // 2. Front Face (Player facing: U=1 to 11, V=1 to 17)
        float fu1 = 1f / 64f, fu2 = 11f / 64f;
        float fv1 = 1f / 32f, fv2 = 17f / 32f;
        buffer.vertex(matrix, -w, -h, -d).texture(fu1, fv1);
        buffer.vertex(matrix, w, -h, -d).texture(fu2, fv1);
        buffer.vertex(matrix, w, h, -d).texture(fu2, fv2);
        buffer.vertex(matrix, -w, h, -d).texture(fu1, fv2);

        // 3. Left Face (U=11 to 12, V=1 to 17)
        float lu1 = 11f / 64f, lu2 = 12f / 64f;
        float lv1 = 1f / 32f, lv2 = 17f / 32f;
        buffer.vertex(matrix, -w, -h, -d).texture(lu1, lv1);
        buffer.vertex(matrix, -w, h, -d).texture(lu1, lv2);
        buffer.vertex(matrix, -w, h, d).texture(lu2, lv2);
        buffer.vertex(matrix, -w, -h, d).texture(lu2, lv1);

        // 4. Right Face (U=22 to 23, V=1 to 17)
        float ru1 = 22f / 64f, ru2 = 23f / 64f;
        float rv1 = 1f / 32f, rv2 = 17f / 32f;
        buffer.vertex(matrix, w, -h, d).texture(ru1, rv1);
        buffer.vertex(matrix, w, h, d).texture(ru1, rv2);
        buffer.vertex(matrix, w, h, -d).texture(ru2, rv2);
        buffer.vertex(matrix, w, -h, -d).texture(ru2, rv1);

        // 5. Top Face (U=12 to 22, V=0 to 1)
        float tu1 = 12f / 64f, tu2 = 22f / 64f;
        float tv1 = 0f / 32f, tv2 = 1f / 32f;
        buffer.vertex(matrix, -w, -h, -d).texture(tu1, tv1);
        buffer.vertex(matrix, -w, -h, d).texture(tu1, tv2);
        buffer.vertex(matrix, w, -h, d).texture(tu2, tv2);
        buffer.vertex(matrix, w, -h, -d).texture(tu2, tv1);

        // 6. Bottom Face (U=22 to 32, V=0 to 1)
        float botu1 = 22f / 64f, botu2 = 32f / 64f;
        float botv1 = 0f / 32f, botv2 = 1f / 32f;
        buffer.vertex(matrix, -w, h, d).texture(botu1, botv1);
        buffer.vertex(matrix, -w, h, -d).texture(botu1, botv2);
        buffer.vertex(matrix, w, h, -d).texture(botu2, botv2);
        buffer.vertex(matrix, w, h, d).texture(botu2, botv1);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        matrices.pop();
        RenderSystem.enableCull();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Back Button click
        int backX = panelX + 15;
        int backY = panelY + 10;
        if (mouseX >= backX && mouseX <= backX + 45 && mouseY >= backY && mouseY <= backY + 14) {
            if (this.client != null) this.client.setScreen(this.parent);
            return true;
        }

        // Check grid clicks
        int gridW = (int) (panelW * 0.65);
        int cols = 4;
        int pad = 8;
        int startX = panelX + 16;
        int startY = panelY + 36;
        int cardW = (gridW - 32 - pad * (cols - 1)) / cols;
        int cardH = 26;

        int index = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (index > 22) break;
                
                int cx = startX + col * (cardW + pad);
                int cy = startY + row * (cardH + pad);
                
                if (mouseX >= cx && mouseX <= cx + cardW && mouseY >= cy && mouseY <= cy + cardH) {
                    HollowConfig.INSTANCE.currentCape = "cape_" + index;
                    HollowConfig.save();
                    return true;
                }
                index++;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
