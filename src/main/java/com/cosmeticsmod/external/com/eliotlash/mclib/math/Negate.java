/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Negate
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;

public class Negate
implements IValue {
    public IValue value;

    public Negate(IValue value) {
        this.value = value;
    }

    public double get() {
        return this.value.get() == 0.0 ? 1.0 : 0.0;
    }

    public String toString() {
        return "!" + this.value.toString();
    }
}

