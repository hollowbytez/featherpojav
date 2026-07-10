/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Sqrt
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function;

public class Sqrt
extends Function {
    public Sqrt(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    public int getRequiredArguments() {
        return 1;
    }

    public double get() {
        return Math.sqrt(this.getArg(0));
    }
}

