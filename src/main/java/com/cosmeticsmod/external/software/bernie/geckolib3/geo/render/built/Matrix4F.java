/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Matrix4F
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.morecosmetics.utils.MathUtils;

public class Matrix4F {
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;

    public Matrix4F() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 0.0f;
    }

    public Matrix4F(Matrix4F var1) {
        this.m00 = var1.m00;
        this.m01 = var1.m01;
        this.m02 = var1.m02;
        this.m03 = var1.m03;
        this.m10 = var1.m10;
        this.m11 = var1.m11;
        this.m12 = var1.m12;
        this.m13 = var1.m13;
        this.m20 = var1.m20;
        this.m21 = var1.m21;
        this.m22 = var1.m22;
        this.m23 = var1.m23;
        this.m30 = var1.m30;
        this.m31 = var1.m31;
        this.m32 = var1.m32;
        this.m33 = var1.m33;
    }

    public final void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public final void mul(Matrix4F var1) {
        float var2 = this.m00 * var1.m00 + this.m01 * var1.m10 + this.m02 * var1.m20 + this.m03 * var1.m30;
        float var3 = this.m00 * var1.m01 + this.m01 * var1.m11 + this.m02 * var1.m21 + this.m03 * var1.m31;
        float var4 = this.m00 * var1.m02 + this.m01 * var1.m12 + this.m02 * var1.m22 + this.m03 * var1.m32;
        float var5 = this.m00 * var1.m03 + this.m01 * var1.m13 + this.m02 * var1.m23 + this.m03 * var1.m33;
        float var6 = this.m10 * var1.m00 + this.m11 * var1.m10 + this.m12 * var1.m20 + this.m13 * var1.m30;
        float var7 = this.m10 * var1.m01 + this.m11 * var1.m11 + this.m12 * var1.m21 + this.m13 * var1.m31;
        float var8 = this.m10 * var1.m02 + this.m11 * var1.m12 + this.m12 * var1.m22 + this.m13 * var1.m32;
        float var9 = this.m10 * var1.m03 + this.m11 * var1.m13 + this.m12 * var1.m23 + this.m13 * var1.m33;
        float var10 = this.m20 * var1.m00 + this.m21 * var1.m10 + this.m22 * var1.m20 + this.m23 * var1.m30;
        float var11 = this.m20 * var1.m01 + this.m21 * var1.m11 + this.m22 * var1.m21 + this.m23 * var1.m31;
        float var12 = this.m20 * var1.m02 + this.m21 * var1.m12 + this.m22 * var1.m22 + this.m23 * var1.m32;
        float var13 = this.m20 * var1.m03 + this.m21 * var1.m13 + this.m22 * var1.m23 + this.m23 * var1.m33;
        float var14 = this.m30 * var1.m00 + this.m31 * var1.m10 + this.m32 * var1.m20 + this.m33 * var1.m30;
        float var15 = this.m30 * var1.m01 + this.m31 * var1.m11 + this.m32 * var1.m21 + this.m33 * var1.m31;
        float var16 = this.m30 * var1.m02 + this.m31 * var1.m12 + this.m32 * var1.m22 + this.m33 * var1.m32;
        float var17 = this.m30 * var1.m03 + this.m31 * var1.m13 + this.m32 * var1.m23 + this.m33 * var1.m33;
        this.m00 = var2;
        this.m01 = var3;
        this.m02 = var4;
        this.m03 = var5;
        this.m10 = var6;
        this.m11 = var7;
        this.m12 = var8;
        this.m13 = var9;
        this.m20 = var10;
        this.m21 = var11;
        this.m22 = var12;
        this.m23 = var13;
        this.m30 = var14;
        this.m31 = var15;
        this.m32 = var16;
        this.m33 = var17;
    }

    public final void rotX(float var1) {
        float var2 = MathUtils.sin((float)var1);
        float var3 = MathUtils.cos((float)var1);
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = var3;
        this.m12 = -var2;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = var2;
        this.m22 = var3;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public final void rotY(float var1) {
        float var3;
        float var2 = MathUtils.sin((float)var1);
        this.m00 = var3 = MathUtils.cos((float)var1);
        this.m01 = 0.0f;
        this.m02 = var2;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = -var2;
        this.m21 = 0.0f;
        this.m22 = var3;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public final void rotZ(float var1) {
        float var3;
        float var2 = MathUtils.sin((float)var1);
        this.m00 = var3 = MathUtils.cos((float)var1);
        this.m01 = -var2;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = var2;
        this.m11 = var3;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public final void mulX(float var1) {
        float var2 = MathUtils.sin((float)var1);
        float var3 = MathUtils.cos((float)var1);
        this.m00 *= 1.0f;
        this.m01 *= 0.0f;
        this.m02 *= 0.0f;
        this.m03 *= 0.0f;
        this.m10 *= 0.0f;
        this.m11 *= var3;
        this.m12 *= -var2;
        this.m13 *= 0.0f;
        this.m20 *= 0.0f;
        this.m21 *= var2;
        this.m22 *= var3;
        this.m23 *= 0.0f;
        this.m30 *= 0.0f;
        this.m31 *= 0.0f;
        this.m32 *= 0.0f;
        this.m33 *= 1.0f;
    }

    public final void mulY(float var1) {
        float var2 = MathUtils.sin((float)var1);
        float var3 = MathUtils.cos((float)var1);
        this.m00 *= var3;
        this.m01 *= 0.0f;
        this.m02 *= var2;
        this.m03 *= 0.0f;
        this.m10 *= 0.0f;
        this.m11 *= 1.0f;
        this.m12 *= 0.0f;
        this.m13 *= 0.0f;
        this.m20 *= -var2;
        this.m21 *= 0.0f;
        this.m22 *= var3;
        this.m23 *= 0.0f;
        this.m30 *= 0.0f;
        this.m31 *= 0.0f;
        this.m32 *= 0.0f;
        this.m33 *= 1.0f;
    }

    public final void mulZ(float var1) {
        float var2 = MathUtils.sin((float)var1);
        float var3 = MathUtils.cos((float)var1);
        this.m00 *= var3;
        this.m01 *= -var2;
        this.m02 *= 0.0f;
        this.m03 *= 0.0f;
        this.m10 *= var2;
        this.m11 *= var3;
        this.m12 *= 0.0f;
        this.m13 *= 0.0f;
        this.m20 *= 0.0f;
        this.m21 *= 0.0f;
        this.m22 *= 1.0f;
        this.m23 *= 0.0f;
        this.m30 *= 0.0f;
        this.m31 *= 0.0f;
        this.m32 *= 0.0f;
        this.m33 *= 1.0f;
    }

    public final void mul(float var1) {
        this.m00 *= var1;
        this.m01 *= var1;
        this.m02 *= var1;
        this.m03 *= var1;
        this.m10 *= var1;
        this.m11 *= var1;
        this.m12 *= var1;
        this.m13 *= var1;
        this.m20 *= var1;
        this.m21 *= var1;
        this.m22 *= var1;
        this.m23 *= var1;
        this.m30 *= var1;
        this.m31 *= var1;
        this.m32 *= var1;
        this.m33 *= var1;
    }

    public final float getM00() {
        return this.m00;
    }

    public final void setM00(float var1) {
        this.m00 = var1;
    }

    public final float getM01() {
        return this.m01;
    }

    public final void setM01(float var1) {
        this.m01 = var1;
    }

    public final float getM02() {
        return this.m02;
    }

    public final void setM02(float var1) {
        this.m02 = var1;
    }

    public final float getM10() {
        return this.m10;
    }

    public final void setM10(float var1) {
        this.m10 = var1;
    }

    public final float getM11() {
        return this.m11;
    }

    public final void setM11(float var1) {
        this.m11 = var1;
    }

    public final float getM12() {
        return this.m12;
    }

    public final void setM12(float var1) {
        this.m12 = var1;
    }

    public final float getM20() {
        return this.m20;
    }

    public final void setM20(float var1) {
        this.m20 = var1;
    }

    public final float getM21() {
        return this.m21;
    }

    public final void setM21(float var1) {
        this.m21 = var1;
    }

    public final float getM22() {
        return this.m22;
    }

    public final void setM22(float var1) {
        this.m22 = var1;
    }

    public final float getM03() {
        return this.m03;
    }

    public final void setM03(float var1) {
        this.m03 = var1;
    }

    public final float getM13() {
        return this.m13;
    }

    public final void setM13(float var1) {
        this.m13 = var1;
    }

    public final float getM23() {
        return this.m23;
    }

    public final void setM23(float var1) {
        this.m23 = var1;
    }

    public final float getM30() {
        return this.m30;
    }

    public final void setM30(float var1) {
        this.m30 = var1;
    }

    public final float getM31() {
        return this.m31;
    }

    public final void setM31(float var1) {
        this.m31 = var1;
    }

    public final float getM32() {
        return this.m32;
    }

    public final void setM32(float var1) {
        this.m32 = var1;
    }

    public final float getM33() {
        return this.m33;
    }

    public final void setM33(float var1) {
        this.m33 = var1;
    }
}

