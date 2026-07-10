/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Operator
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation;

public class Operator
implements IValue {
    public Operation operation;
    public IValue a;
    public IValue b;

    public Operator(Operation op, IValue a, IValue b) {
        this.operation = op;
        this.a = a;
        this.b = b;
    }

    public double get() {
        return this.operation.calculate(this.a.get(), this.b.get());
    }

    public String toString() {
        return this.a.toString() + " " + this.operation.sign + " " + this.b.toString();
    }
}

