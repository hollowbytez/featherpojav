/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 */
package com.cosmeticsmod.morecosmetics.models.renderer;

public interface RenderStack<T> {
    public void push();

    public void pop();

    public void translate(float var1, float var2, float var3);

    public void scale(float var1, float var2, float var3);

    public void rotate(float var1, float var2, float var3);

    public void rotateX(float var1);

    public void rotateY(float var1);

    public void rotateZ(float var1);

    default public T get() {
        return null;
    }
}

