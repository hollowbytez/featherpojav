/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.processor;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot;

public interface IBone {
    public float getRotationX();

    public float getRotationY();

    public float getRotationZ();

    public float getPositionX();

    public float getPositionY();

    public float getPositionZ();

    public float getScaleX();

    public float getScaleY();

    public float getScaleZ();

    public void setRotationX(float var1);

    public void setRotationY(float var1);

    public void setRotationZ(float var1);

    public void setPositionX(float var1);

    public void setPositionY(float var1);

    public void setPositionZ(float var1);

    public void setScaleX(float var1);

    public void setScaleY(float var1);

    public void setScaleZ(float var1);

    public void setPivotX(float var1);

    public void setPivotY(float var1);

    public void setPivotZ(float var1);

    public float getPivotX();

    public float getPivotY();

    public float getPivotZ();

    public boolean isHidden();

    public void setHidden(boolean var1);

    public void setModelRendererName(String var1);

    public void saveInitialSnapshot();

    public BoneSnapshot getInitialSnapshot();

    default public BoneSnapshot saveSnapshot() {
        return new BoneSnapshot(this);
    }

    public String getName();
}

