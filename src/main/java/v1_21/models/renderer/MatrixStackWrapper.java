/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoCube
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.util.math.MatrixStack
 *  org.joml.Vector3f
 *  v1_21.morecosmetics.models.renderer.MatrixStackWrapper
 *  v1_21.morecosmetics.models.renderer.QuaternionHelper
 */
package v1_21.morecosmetics.models.renderer;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoCube;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;
import v1_21.morecosmetics.models.renderer.QuaternionHelper;

@Environment(value=EnvType.CLIENT)
public class MatrixStackWrapper
implements RenderStack<MatrixStack> {
    private MatrixStack stack;

    public void update(MatrixStack stack) {
        this.stack = stack;
    }

    public void push() {
        this.stack.push();
    }

    public void pop() {
        this.stack.pop();
    }

    public MatrixStack get() {
        return this.stack;
    }

    public void translate(float x, float y, float z) {
        this.stack.translate(x, y, z);
    }

    public void moveToPivot(GeoCube cube) {
        Vec3F pivot = cube.pivot;
        this.translate(pivot.getX() / 16.0f, pivot.getY() / 16.0f, pivot.getZ() / 16.0f);
    }

    public void moveBackFromPivot(GeoCube cube) {
        Vec3F pivot = cube.pivot;
        this.translate(-pivot.getX() / 16.0f, -pivot.getY() / 16.0f, -pivot.getZ() / 16.0f);
    }

    public void moveToPivot(GeoBone bone) {
        this.translate(bone.rotationPointX / 16.0f, bone.rotationPointY / 16.0f, bone.rotationPointZ / 16.0f);
    }

    public void moveBackFromPivot(GeoBone bone) {
        this.translate(-bone.rotationPointX / 16.0f, -bone.rotationPointY / 16.0f, -bone.rotationPointZ / 16.0f);
    }

    public void translate(GeoBone bone) {
        this.translate(-bone.getPositionX() / 16.0f, bone.getPositionY() / 16.0f, bone.getPositionZ() / 16.0f);
    }

    public void scale(float x, float y, float z) {
        this.stack.scale(x, y, z);
    }

    public void scale(GeoBone bone) {
        this.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
    }

    public void rotateX(float radian) {
        this.stack.multiply(QuaternionHelper.getRadialQuaternion((Vector3f)QuaternionHelper.POSITIVE_X, (float)radian));
    }

    public void rotateY(float radian) {
        this.stack.multiply(QuaternionHelper.getRadialQuaternion((Vector3f)QuaternionHelper.POSITIVE_Y, (float)radian));
    }

    public void rotateZ(float radian) {
        this.stack.multiply(QuaternionHelper.getRadialQuaternion((Vector3f)QuaternionHelper.POSITIVE_Z, (float)radian));
    }

    public void rotate(GeoBone bone) {
        if (bone.getRotationZ() != 0.0f) {
            this.rotateZ(bone.getRotationZ());
        }
        if (bone.getRotationY() != 0.0f) {
            this.rotateY(bone.getRotationY());
        }
        if (bone.getRotationX() != 0.0f) {
            this.rotateX(bone.getRotationX());
        }
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

    public void rotate(GeoCube bone) {
        Vec3F rotation = bone.rotation;
        this.stack.multiply(QuaternionHelper.getQuaternion((float)0.0f, (float)0.0f, (float)rotation.getZ(), (boolean)false));
        this.stack.multiply(QuaternionHelper.getQuaternion((float)0.0f, (float)rotation.getY(), (float)0.0f, (boolean)false));
        this.stack.multiply(QuaternionHelper.getQuaternion((float)rotation.getX(), (float)0.0f, (float)0.0f, (boolean)false));
    }

    public MatrixStack getStack() {
        return this.stack;
    }
}

