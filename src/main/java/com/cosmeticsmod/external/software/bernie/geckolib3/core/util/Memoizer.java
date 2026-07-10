/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.util.Memoizer
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Memoizer<T, U> {
    private final Map<T, U> cache = new ConcurrentHashMap();

    private Memoizer() {
    }

    private Function<T, U> doMemoize(Function<T, U> function) {
        return input -> this.cache.computeIfAbsent(input, function::apply);
    }

    public static <T, U> Function<T, U> memoize(Function<T, U> function) {
        return new Memoizer().doMemoize(function);
    }
}

