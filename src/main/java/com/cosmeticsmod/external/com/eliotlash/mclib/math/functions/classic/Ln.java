/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Ln
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function;

public class Ln
extends Function {
    public Ln(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    public int getRequiredArguments() {
        return 1;
    }

    public double get() {
        return Math.log(this.getArg(0));
    }
}

