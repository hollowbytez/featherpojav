/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame;
import java.util.ArrayList;
import java.util.List;

public class VectorKeyFrameList<T extends KeyFrame> {
    public List<T> xKeyFrames;
    public List<T> yKeyFrames;
    public List<T> zKeyFrames;

    public VectorKeyFrameList(List<T> XKeyFrames, List<T> YKeyFrames, List<T> ZKeyFrames) {
        this.xKeyFrames = XKeyFrames;
        this.yKeyFrames = YKeyFrames;
        this.zKeyFrames = ZKeyFrames;
    }

    public VectorKeyFrameList() {
        this.xKeyFrames = new ArrayList();
        this.yKeyFrames = new ArrayList();
        this.zKeyFrames = new ArrayList();
    }

    public double getLastKeyframeTime() {
        double xTime = 0.0;
        for (KeyFrame frame : this.xKeyFrames) {
            xTime += frame.getLength().doubleValue();
        }
        double yTime = 0.0;
        for (KeyFrame frame : this.yKeyFrames) {
            yTime += frame.getLength().doubleValue();
        }
        double zTime = 0.0;
        for (KeyFrame frame : this.zKeyFrames) {
            zTime += frame.getLength().doubleValue();
        }
        return Math.max(xTime, Math.max(yTime, zTime));
    }
}

