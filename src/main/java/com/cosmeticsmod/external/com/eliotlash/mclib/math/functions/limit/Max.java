/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Max
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function;

public class Max
extends Function {
    public Max(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    public int getRequiredArguments() {
        return 2;
    }

    public double get() {
        return Math.max(this.getArg(0), this.getArg(1));
    }
}

