/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Constant
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Group
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.MathBuilder
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Negate
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Negative
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Operator
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Ternary
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Abs
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Cos
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Exp
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Ln
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Mod
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Pow
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Sin
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Sqrt
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Clamp
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Max
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Min
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Ceil
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Floor
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Round
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Trunc
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.Lerp
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.LerpRotate
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.Random
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.Constant;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Group;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Negate;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Negative;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Operator;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Ternary;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.Function;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Abs;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Cos;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Exp;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Ln;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Mod;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Pow;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Sin;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.classic.Sqrt;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Clamp;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Max;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.limit.Min;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Ceil;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Floor;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Round;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.rounding.Trunc;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.Lerp;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.LerpRotate;
import com.cosmeticsmod.external.com.eliotlash.mclib.math.functions.utility.Random;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathBuilder {
    public Map<String, Variable> variables = new HashMap();
    public Map<String, Class<? extends Function>> functions = new HashMap();

    public MathBuilder() {
        this.register(new Variable("PI", Math.PI));
        this.register(new Variable("E", Math.E));
        this.functions.put("floor", Floor.class);
        this.functions.put("round", Round.class);
        this.functions.put("ceil", Ceil.class);
        this.functions.put("trunc", Trunc.class);
        this.functions.put("clamp", Clamp.class);
        this.functions.put("max", Max.class);
        this.functions.put("min", Min.class);
        this.functions.put("abs", Abs.class);
        this.functions.put("cos", Cos.class);
        this.functions.put("sin", Sin.class);
        this.functions.put("exp", Exp.class);
        this.functions.put("ln", Ln.class);
        this.functions.put("sqrt", Sqrt.class);
        this.functions.put("mod", Mod.class);
        this.functions.put("pow", Pow.class);
        this.functions.put("lerp", Lerp.class);
        this.functions.put("lerprotate", LerpRotate.class);
        this.functions.put("random", Random.class);
    }

    public void register(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

    public IValue parse(String expression) throws Exception {
        return this.parseSymbols(this.breakdownChars(this.breakdown(expression)));
    }

    public String[] breakdown(String expression) throws Exception {
        if (!expression.matches("^[\\w\\d\\s_+-/*%^&|<>=!?:.,()]+$")) {
            throw new Exception("Given expression '" + expression + "' contains illegal characters!");
        }
        expression = expression.replaceAll("\\s+", "");
        String[] chars = expression.split("(?!^)");
        int left = 0;
        int right = 0;
        for (String s : chars) {
            if (s.equals("(")) {
                ++left;
                continue;
            }
            if (!s.equals(")")) continue;
            ++right;
        }
        if (left != right) {
            throw new Exception("Given expression '" + expression + "' has more uneven amount of parenthesis, there are " + left + " open and " + right + " closed!");
        }
        return chars;
    }

    public List<Object> breakdownChars(String[] chars) {
        ArrayList<Object> symbols = new ArrayList<Object>();
        String buffer = "";
        int len = chars.length;
        block0: for (int i = 0; i < len; ++i) {
            boolean longOperator;
            String s = chars[i];
            boolean bl = longOperator = i > 0 && this.isOperator(chars[i - 1] + s);
            if (this.isOperator(s) || longOperator || s.equals(",")) {
                if (s.equals("-")) {
                    boolean isOperatorBehind;
                    int size = symbols.size();
                    boolean isFirst = size == 0 && buffer.isEmpty();
                    boolean bl2 = isOperatorBehind = size > 0 && (this.isOperator(symbols.get(size - 1)) || symbols.get(size - 1).equals(",")) && buffer.isEmpty();
                    if (isFirst || isOperatorBehind) {
                        buffer = buffer + s;
                        continue;
                    }
                }
                if (longOperator) {
                    s = chars[i - 1] + s;
                    buffer = buffer.substring(0, buffer.length() - 1);
                }
                if (!buffer.isEmpty()) {
                    symbols.add(buffer);
                    buffer = "";
                }
                symbols.add(s);
                continue;
            }
            if (s.equals("(")) {
                if (!buffer.isEmpty()) {
                    symbols.add(buffer);
                    buffer = "";
                }
                int counter = 1;
                for (int j = i + 1; j < len; ++j) {
                    String c = chars[j];
                    if (c.equals("(")) {
                        ++counter;
                    } else if (c.equals(")")) {
                        --counter;
                    }
                    if (counter == 0) {
                        symbols.add(this.breakdownChars(buffer.split("(?!^)")));
                        i = j;
                        buffer = "";
                        continue block0;
                    }
                    buffer = buffer + c;
                }
                continue;
            }
            buffer = buffer + s;
        }
        if (!buffer.isEmpty()) {
            symbols.add(buffer);
        }
        return symbols;
    }

    public IValue parseSymbols(List<Object> symbols) throws Exception {
        int op;
        IValue ternary = this.tryTernary(symbols);
        if (ternary != null) {
            return ternary;
        }
        int size = symbols.size();
        if (size == 1) {
            return this.valueFromObject(symbols.get(0));
        }
        if (size == 2) {
            Object first = symbols.get(0);
            Object second = symbols.get(1);
            if ((this.isVariable(first) || first.equals("-")) && second instanceof List) {
                return this.createFunction((String)first, (List)second);
            }
        }
        int lastOp = op = this.seekLastOperator(symbols);
        while (op != -1) {
            int leftOp = this.seekLastOperator(symbols, op - 1);
            if (leftOp != -1) {
                Operation left = this.operationForOperator((String)symbols.get(leftOp));
                Operation right = this.operationForOperator((String)symbols.get(op));
                if (right.value > left.value) {
                    IValue leftValue = this.parseSymbols(symbols.subList(0, leftOp));
                    IValue rightValue = this.parseSymbols(symbols.subList(leftOp + 1, size));
                    return new Operator(left, leftValue, rightValue);
                }
                if (left.value > right.value) {
                    Operation initial = this.operationForOperator((String)symbols.get(lastOp));
                    if (initial.value < left.value) {
                        IValue leftValue2 = this.parseSymbols(symbols.subList(0, lastOp));
                        IValue rightValue2 = this.parseSymbols(symbols.subList(lastOp + 1, size));
                        return new Operator(initial, leftValue2, rightValue2);
                    }
                    IValue leftValue2 = this.parseSymbols(symbols.subList(0, op));
                    IValue rightValue2 = this.parseSymbols(symbols.subList(op + 1, size));
                    return new Operator(right, leftValue2, rightValue2);
                }
            }
            op = leftOp;
        }
        Operation operation = this.operationForOperator((String)symbols.get(lastOp));
        return new Operator(operation, this.parseSymbols(symbols.subList(0, lastOp)), this.parseSymbols(symbols.subList(lastOp + 1, size)));
    }

    protected int seekLastOperator(List<Object> symbols) {
        return this.seekLastOperator(symbols, symbols.size() - 1);
    }

    protected int seekLastOperator(List<Object> symbols, int offset) {
        for (int i = offset; i >= 0; --i) {
            Object o = symbols.get(i);
            if (!this.isOperator(o)) continue;
            return i;
        }
        return -1;
    }

    protected int seekFirstOperator(List<Object> symbols) {
        return this.seekFirstOperator(symbols, 0);
    }

    protected int seekFirstOperator(List<Object> symbols, int offset) {
        int size = symbols.size();
        for (int i = offset; i < size; ++i) {
            Object o = symbols.get(i);
            if (!this.isOperator(o)) continue;
            return i;
        }
        return -1;
    }

    protected IValue tryTernary(List<Object> symbols) throws Exception {
        int question = -1;
        int questions = 0;
        int colon = -1;
        int colons = 0;
        int size = symbols.size();
        for (int i = 0; i < size; ++i) {
            Object object = symbols.get(i);
            if (!(object instanceof String)) continue;
            if (object.equals("?")) {
                if (question == -1) {
                    question = i;
                }
                ++questions;
                continue;
            }
            if (!object.equals(":")) continue;
            if (colons + 1 == questions && colon == -1) {
                colon = i;
            }
            ++colons;
        }
        if (questions == colons && question > 0 && question + 1 < colon && colon < size - 1) {
            return new Ternary(this.parseSymbols(symbols.subList(0, question)), this.parseSymbols(symbols.subList(question + 1, colon)), this.parseSymbols(symbols.subList(colon + 1, size)));
        }
        return null;
    }

    protected IValue createFunction(String first, List<Object> args) throws Exception {
        if (first.equals("!")) {
            return new Negate(this.parseSymbols(args));
        }
        if (first.startsWith("!") && first.length() > 1) {
            return new Negate(this.createFunction(first.substring(1), args));
        }
        if (first.equals("-")) {
            return new Negative(this.parseSymbols(args));
        }
        if (first.startsWith("-") && first.length() > 1) {
            return new Negative(this.createFunction(first.substring(1), args));
        }
        if (!this.functions.containsKey(first)) {
            throw new Exception("Function '" + first + "' couldn't be found!");
        }
        ArrayList<IValue> values = new ArrayList<IValue>();
        ArrayList<Object> buffer = new ArrayList<Object>();
        for (Object o : args) {
            if (o.equals(",")) {
                values.add(this.parseSymbols(buffer));
                buffer.clear();
                continue;
            }
            buffer.add(o);
        }
        if (!buffer.isEmpty()) {
            values.add(this.parseSymbols(buffer));
        }
        Class function = (Class)this.functions.get(first);
        Constructor ctor = function.getConstructor(IValue[].class, String.class);
        Function func = (Function)ctor.newInstance(values.toArray(new IValue[values.size()]), first);
        return func;
    }

    public IValue valueFromObject(Object object) throws Exception {
        if (object instanceof String) {
            String symbol = (String)object;
            if (symbol.startsWith("!")) {
                return new Negate(this.valueFromObject((Object)symbol.substring(1)));
            }
            if (this.isDecimal(symbol)) {
                return new Constant(Double.parseDouble(symbol));
            }
            if (this.isVariable((Object)symbol)) {
                if (symbol.startsWith("-")) {
                    Variable value = this.getVariable(symbol = symbol.substring(1));
                    if (value != null) {
                        return new Negative((IValue)value);
                    }
                } else {
                    Variable value2 = this.getVariable(symbol);
                    if (value2 != null) {
                        return value2;
                    }
                }
            }
        } else if (object instanceof List) {
            return new Group(this.parseSymbols((List)object));
        }
        throw new Exception("Given object couldn't be converted to value! " + object);
    }

    protected Variable getVariable(String name) {
        return (Variable)this.variables.get(name);
    }

    protected Operation operationForOperator(String op) throws Exception {
        for (Operation operation : Operation.values()) {
            if (!operation.sign.equals(op)) continue;
            return operation;
        }
        throw new Exception("There is no such operator '" + op + "'!");
    }

    protected boolean isVariable(Object o) {
        return o instanceof String && !this.isDecimal((String)o) && !this.isOperator((String)o);
    }

    protected boolean isOperator(Object o) {
        return o instanceof String && this.isOperator((String)o);
    }

    protected boolean isOperator(String s) {
        return Operation.OPERATORS.contains(s) || s.equals("?") || s.equals(":");
    }

    protected boolean isDecimal(String s) {
        return s.matches("^-?\\d+(\\.\\d+)?$");
    }
}

