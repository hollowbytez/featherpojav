/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangMultiStatement
 *  com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangValue
 */
package com.cosmeticsmod.external.com.eliotlash.molang.expressions;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangExpression;
import com.cosmeticsmod.external.com.eliotlash.molang.expressions.MolangValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class MolangMultiStatement
extends MolangExpression {
    public List<MolangExpression> expressions = new ArrayList();
    public Map<String, Variable> locals = new HashMap();

    public MolangMultiStatement(MolangParser context) {
        super(context);
    }

    public double get() {
        double value = 0.0;
        for (MolangExpression expression : this.expressions) {
            value = expression.get();
        }
        return value;
    }

    public String toString() {
        StringJoiner builder = new StringJoiner("; ");
        for (MolangExpression expression : this.expressions) {
            builder.add(expression.toString());
            if (!(expression instanceof MolangValue) || !((MolangValue)expression).returns) continue;
            break;
        }
        return builder.toString();
    }
}

