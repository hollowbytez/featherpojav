/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.ConstantValue
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;

/*
 * Exception performing whole class analysis ignored.
 */
public class ConstantValue
implements IValue {
    private final double value;

    public ConstantValue(double value) {
        this.value = value;
    }

    public double get() {
        return this.value;
    }

    public static ConstantValue fromDouble(double d) {
        return new ConstantValue(d);
    }

    public static ConstantValue fromFloat(float d) {
        return new ConstantValue((double)d);
    }

    public static ConstantValue parseDouble(String s) {
        return new ConstantValue(Double.parseDouble(s));
    }

    public static ConstantValue parseFloat(String s) {
        return new ConstantValue((double)Float.parseFloat(s));
    }

    public static ConstantValue subtract(IValue first, IValue second) {
        return ConstantValue.fromDouble((double)(first.get() - second.get()));
    }
}

