/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Round
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function;

public class Round
extends Function {
    public Round(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    public int getRequiredArguments() {
        return 1;
    }

    public double get() {
        return Math.round(this.getArg(0));
    }
}

