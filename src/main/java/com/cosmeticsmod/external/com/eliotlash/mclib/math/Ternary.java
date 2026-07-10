/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Ternary
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;

public class Ternary
implements IValue {
    public IValue condition;
    public IValue ifTrue;
    public IValue ifFalse;

    public Ternary(IValue condition, IValue ifTrue, IValue ifFalse) {
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public double get() {
        return this.condition.get() != 0.0 ? this.ifTrue.get() : this.ifFalse.get();
    }

    public String toString() {
        return this.condition.toString() + " ? " + this.ifTrue.toString() + " : " + this.ifFalse.toString();
    }
}

