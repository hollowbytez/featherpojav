/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangAssignment
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression
 */
package com.cosmeticsmod.external.com.eliotlash.molang.expressions;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression;

public class MolangAssignment
extends MolangExpression {
    public Variable variable;
    public IValue expression;

    public MolangAssignment(MolangParser context, Variable variable, IValue expression) {
        super(context);
        this.variable = variable;
        this.expression = expression;
    }

    public double get() {
        double value = this.expression.get();
        this.variable.set(value);
        return value;
    }

    public String toString() {
        return this.variable.getName() + " = " + this.expression.toString();
    }
}

