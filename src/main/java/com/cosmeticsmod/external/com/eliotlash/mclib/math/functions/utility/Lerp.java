/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.Lerp
 *  com.cosmeticsmod.external.com.eliotlash.mclib.utils.Interpolations
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function;
import com.cosmeticsmod.external.com.eliotlash.mclib.utils.Interpolations;

public class Lerp
extends Function {
    public Lerp(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    public int getRequiredArguments() {
        return 3;
    }

    public double get() {
        return Interpolations.lerp((double)this.getArg(0), (double)this.getArg(1), (double)this.getArg(2));
    }
}

