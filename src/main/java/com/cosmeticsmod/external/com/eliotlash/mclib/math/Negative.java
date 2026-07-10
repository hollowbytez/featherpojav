/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Negative
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;

public class Negative
implements IValue {
    public IValue value;

    public Negative(IValue value) {
        this.value = value;
    }

    public double get() {
        return -this.value.get();
    }

    public String toString() {
        return "-" + this.value.toString();
    }
}

