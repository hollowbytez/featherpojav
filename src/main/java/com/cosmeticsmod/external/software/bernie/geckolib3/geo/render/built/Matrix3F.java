/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Matrix3F
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.morecosmetics.utils.MathUtils;

public class Matrix3F {
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;

    public Matrix3F() {
        this.m00 = 0.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 0.0f;
    }

    public Matrix3F(Matrix3F var1) {
        this.m00 = var1.m00;
        this.m01 = var1.m01;
        this.m02 = var1.m02;
        this.m10 = var1.m10;
        this.m11 = var1.m11;
        this.m12 = var1.m12;
        this.m20 = var1.m20;
        this.m21 = var1.m21;
        this.m22 = var1.m22;
    }

    public final void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }

    public final void mul(Matrix3F var1) {
        float var2 = this.m00 * var1.m00 + this.m01 * var1.m10 + this.m02 * var1.m20;
        float var3 = this.m00 * var1.m01 + this.m01 * var1.m11 + this.m02 * var1.m21;
        float var4 = this.m00 * var1.m02 + this.m01 * var1.m12 + this.m02 * var1.m22;
        float var5 = this.m10 * var1.m00 + this.m11 * var1.m10 + this.m12 * var1.m20;
        float var6 = this.m10 * var1.m01 + this.m11 * var1.m11 + this.m12 * var1.m21;
        float var7 = this.m10 * var1.m02 + this.m11 * var1.m12 + this.m12 * var1.m22;
        float var8 = this.m20 * var1.m00 + this.m21 * var1.m10 + this.m22 * var1.m20;
        float var9 = this.m20 * var1.m01 + this.m21 * var1.m11 + this.m22 * var1.m21;
        float var10 = this.m20 * var1.m02 + this.m21 * var1.m12 + this.m22 * var1.m22;
        this.m00 = var2;
        this.m01 = var3;
        this.m02 = var4;
        this.m10 = var5;
        this.m11 = var6;
        this.m12 = var7;
        this.m20 = var8;
        this.m21 = var9;
        this.m22 = var10;
    }

    public final void rotX(float var1) {
        float var2 = MathUtils.sin((float)var1);
        float var3 = MathUtils.cos((float)var1);
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = var3;
        this.m12 = -var2;
        this.m20 = 0.0f;
        this.m21 = var2;
        this.m22 = var3;
    }

    public final void rotY(float var1) {
        float var3;
        float var2 = MathUtils.sin((float)var1);
        this.m00 = var3 = MathUtils.cos((float)var1);
        this.m01 = 0.0f;
        this.m02 = var2;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = -var2;
        this.m21 = 0.0f;
        this.m22 = var3;
    }

    public final void rotZ(float var1) {
        float var3;
        float var2 = MathUtils.sin((float)var1);
        this.m00 = var3 = MathUtils.cos((float)var1);
        this.m01 = -var2;
        this.m02 = 0.0f;
        this.m10 = var2;
        this.m11 = var3;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }

    public final void mul(float var1) {
        this.m00 *= var1;
        this.m01 *= var1;
        this.m02 *= var1;
        this.m10 *= var1;
        this.m11 *= var1;
        this.m12 *= var1;
        this.m20 *= var1;
        this.m21 *= var1;
        this.m22 *= var1;
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
}

