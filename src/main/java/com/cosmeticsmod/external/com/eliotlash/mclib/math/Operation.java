/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Operation
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.math;

import java.util.HashSet;
import java.util.Set;

/*
 * Exception performing whole class analysis ignored.
 */
public enum Operation {
    ADD("+", 0) {
        public double calculate(double a, double b) { return a + b; }
    },
    SUB("-", 0) {
        public double calculate(double a, double b) { return a - b; }
    },
    MUL("*", 1) {
        public double calculate(double a, double b) { return a * b; }
    },
    DIV("/", 1) {
        public double calculate(double a, double b) { return b == 0 ? Double.NaN : a / b; }
    },
    MOD("%", 1) {
        public double calculate(double a, double b) { return a % b; }
    },
    POW("^", 2) {
        public double calculate(double a, double b) { return Math.pow(a, b); }
    },
    AND("&&", -1) {
        public double calculate(double a, double b) { return (a != 0 && b != 0) ? 1 : 0; }
    },
    OR("||", -2) {
        public double calculate(double a, double b) { return (a != 0 || b != 0) ? 1 : 0; }
    },
    LESS("<", -1) {
        public double calculate(double a, double b) { return a < b ? 1 : 0; }
    },
    LESS_THAN("<=", -1) {
        public double calculate(double a, double b) { return a <= b ? 1 : 0; }
    },
    GREATER_THAN(">=", -1) {
        public double calculate(double a, double b) { return a >= b ? 1 : 0; }
    },
    GREATER(">", -1) {
        public double calculate(double a, double b) { return a > b ? 1 : 0; }
    },
    EQUALS("==", -1) {
        public double calculate(double a, double b) { return equals(a, b) ? 1 : 0; }
    },
    NOT_EQUALS("!=", -1) {
        public double calculate(double a, double b) { return !equals(a, b) ? 1 : 0; }
    };

    public static final Set<String> OPERATORS;
    public final String sign;
    public final int value;

    public static boolean equals(double a, double b) {
        return Math.abs(a - b) < 1.0E-5;
    }

    private Operation(String sign, int value) {
        this.sign = sign;
        this.value = value;
    }

    public abstract double calculate(double var1, double var3);

    static {
        OPERATORS = new HashSet();
        for (Operation op : Operation.values()) {
            OPERATORS.add(op.sign);
        }
    }
}

