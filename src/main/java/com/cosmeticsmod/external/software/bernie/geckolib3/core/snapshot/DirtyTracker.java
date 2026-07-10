/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.DirtyTracker
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone;

public class DirtyTracker {
    public IBone model;
    public boolean hasScaleChanged;
    public boolean hasPositionChanged;
    public boolean hasRotationChanged;

    public DirtyTracker(boolean hasScaleChanged, boolean hasPositionChanged, boolean hasRotationChanged, IBone model) {
        this.hasScaleChanged = hasScaleChanged;
        this.hasPositionChanged = hasPositionChanged;
        this.hasRotationChanged = hasRotationChanged;
        this.model = model;
    }
}

