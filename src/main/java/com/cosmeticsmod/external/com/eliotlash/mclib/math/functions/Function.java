/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math.functions;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;

public abstract class Function
implements IValue {
    protected IValue[] args;
    protected String name;

    public Function(IValue[] values, String name) throws Exception {
        if (values.length < this.getRequiredArguments()) {
            String message = String.format("Function '%s' requires at least %s arguments. %s are given!", this.getName(), this.getRequiredArguments(), values.length);
            throw new Exception(message);
        }
        this.args = values;
        this.name = name;
    }

    public double getArg(int index) {
        if (index < 0 || index >= this.args.length) {
            return 0.0;
        }
        return this.args[index].get();
    }

    public String toString() {
        String args = "";
        for (int i = 0; i < this.args.length; ++i) {
            args = args + this.args[i].toString();
            if (i >= this.args.length - 1) continue;
            args = args + ", ";
        }
        return this.getName() + "(" + args + ")";
    }

    public String getName() {
        return this.name;
    }

    public int getRequiredArguments() {
        return 0;
    }
}

