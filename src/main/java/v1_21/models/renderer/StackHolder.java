/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.util.math.MatrixStack
 *  org.joml.Vector3f
 *  v1_21.morecosmetics.models.renderer.QuaternionHelper
 *  v1_21.morecosmetics.models.renderer.StackHolder
 */
package v1_21.morecosmetics.models.renderer;

import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;
import v1_21.morecosmetics.models.renderer.QuaternionHelper;

@Environment(value=EnvType.CLIENT)
public class StackHolder
implements RenderStack<MatrixStack> {
    public static MatrixStack STACK;
    private static StackHolder instance;

    public static void update(MatrixStack stack) {
        STACK = stack;
    }

    private StackHolder() {
    }

    public void push() {
        STACK.push();
    }

    public void pop() {
        STACK.pop();
    }

    public void translate(float x, float y, float z) {
        STACK.translate(x, y, z);
    }

    public void scale(float x, float y, float z) {
        STACK.scale(x, y, z);
    }

    public void rotate(float x, float y, float z) {
        if (z != 0.0f) {
            this.rotateZ(z);
        }
        if (y != 0.0f) {
            this.rotateY(y);
        }
        if (x != 0.0f) {
            this.rotateX(x);
        }
    }

    public void rotateX(float radian) {
        STACK.multiply(QuaternionHelper.getDegreesQuaternion((Vector3f)QuaternionHelper.POSITIVE_X, (float)radian));
    }

    public void rotateY(float radian) {
        STACK.multiply(QuaternionHelper.getDegreesQuaternion((Vector3f)QuaternionHelper.POSITIVE_Y, (float)radian));
    }

    public void rotateZ(float radian) {
        STACK.multiply(QuaternionHelper.getDegreesQuaternion((Vector3f)QuaternionHelper.POSITIVE_Z, (float)radian));
    }

    public MatrixStack get() {
        return STACK;
    }

    public static StackHolder getInstance() {
        return instance;
    }

    static {
        instance = new StackHolder();
    }
}

