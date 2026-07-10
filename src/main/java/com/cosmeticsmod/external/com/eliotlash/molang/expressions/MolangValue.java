/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Constant
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangValue
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 */
package com.cosmeticsmod.external.com.eliotlash.molang.expressions;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.Constant;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class MolangValue
extends MolangExpression {
    public IValue value;
    public boolean returns;

    public MolangValue(MolangParser context, IValue value) {
        super(context);
        this.value = value;
    }

    public MolangExpression addReturn() {
        this.returns = true;
        return this;
    }

    public double get() {
        return this.value.get();
    }

    public String toString() {
        return (this.returns ? "return " : "") + this.value.toString();
    }

    public JsonElement toJson() {
        if (this.value instanceof Constant) {
            return new JsonPrimitive((Number)this.value.get());
        }
        return super.toJson();
    }
}

