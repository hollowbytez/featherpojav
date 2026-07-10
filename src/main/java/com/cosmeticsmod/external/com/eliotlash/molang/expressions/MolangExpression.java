/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Constant
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangValue
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 */
package com.cosmeticsmod.external.com.eliotlash.molang.expressions;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.Constant;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/*
 * Exception performing whole class analysis ignored.
 */
public abstract class MolangExpression
implements IValue {
    public MolangParser context;

    public static boolean isZero(MolangExpression expression) {
        return MolangExpression.isConstant((MolangExpression)expression, (double)0.0);
    }

    public static boolean isOne(MolangExpression expression) {
        return MolangExpression.isConstant((MolangExpression)expression, (double)1.0);
    }

    public static boolean isConstant(MolangExpression expression, double x) {
        if (expression instanceof MolangValue) {
            MolangValue value = (MolangValue)expression;
            return value.value instanceof Constant && Operation.equals((double)value.value.get(), (double)x);
        }
        return false;
    }

    public static boolean isExpressionConstant(MolangExpression expression) {
        if (expression instanceof MolangValue) {
            MolangValue value = (MolangValue)expression;
            return value.value instanceof Constant;
        }
        return false;
    }

    public MolangExpression(MolangParser context) {
        this.context = context;
    }

    public JsonElement toJson() {
        return new JsonPrimitive(this.toString());
    }
}

