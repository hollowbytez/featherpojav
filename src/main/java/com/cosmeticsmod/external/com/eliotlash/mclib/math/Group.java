/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Group
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;

public class Group
implements IValue {
    private IValue value;

    public Group(IValue value) {
        this.value = value;
    }

    public double get() {
        return this.value.get();
    }

    public String toString() {
        return "(" + this.value.toString() + ")";
    }
}

