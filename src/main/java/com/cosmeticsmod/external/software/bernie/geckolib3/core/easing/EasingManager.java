/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingManager
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingManager$1
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingManager$EasingFunctionArgs
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.util.Memoizer
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.easing;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingManager;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.util.Memoizer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.DoubleStream;

/*
 * Exception performing whole class analysis ignored.
 */
public class EasingManager {
    static Function<Double, Double> quart = EasingManager.poly((double)4.0);
    static Function<Double, Double> quint = EasingManager.poly((double)5.0);
    static Function<EasingFunctionArgs, Function<Double, Double>> getEasingFunction = Memoizer.memoize(EasingManager::getEasingFuncImpl);

    public static double ease(double number, EasingType easingType, List<Double> easingArgs) {
        Double firstArg = easingArgs == null || easingArgs.size() < 1 ? null : easingArgs.get(0);
        return (Double)((Function)getEasingFunction.apply(new EasingFunctionArgs(easingType, firstArg))).apply(number);
    }

    static Function<Double, Double> getEasingFuncImpl(EasingFunctionArgs args) {
        switch (args.easingType) {
            case Step:
                return EasingManager.in((Function)EasingManager.step((Double)args.arg0));
            case EaseInSine:
                return EasingManager.in(EasingManager::sin);
            case EaseOutSine:
                return EasingManager.out(EasingManager::sin);
            case EaseInOutSine:
                return EasingManager.inOut(EasingManager::sin);
            case EaseInQuad:
                return EasingManager.in(EasingManager::quad);
            case EaseOutQuad:
                return EasingManager.out(EasingManager::quad);
            case EaseInOutQuad:
                return EasingManager.inOut(EasingManager::quad);
            case EaseInCubic:
                return EasingManager.in(EasingManager::cubic);
            case EaseOutCubic:
                return EasingManager.out(EasingManager::cubic);
            case EaseInOutCubic:
                return EasingManager.inOut(EasingManager::cubic);
            case EaseInQuart:
                return EasingManager.in((Function)quart);
            case EaseOutQuart:
                return EasingManager.out((Function)quart);
            case EaseInOutQuart:
                return EasingManager.inOut((Function)quart);
            case EaseInQuint:
                return EasingManager.in((Function)quint);
            case EaseOutQuint:
                return EasingManager.out((Function)quint);
            case EaseInOutQuint:
                return EasingManager.inOut((Function)quint);
            case EaseInExpo:
                return EasingManager.in(EasingManager::exp);
            case EaseOutExpo:
                return EasingManager.out(EasingManager::exp);
            case EaseInOutExpo:
                return EasingManager.inOut(EasingManager::exp);
            case EaseInCirc:
                return EasingManager.in(EasingManager::circle);
            case EaseOutCirc:
                return EasingManager.out(EasingManager::circle);
            case EaseInOutCirc:
                return EasingManager.inOut(EasingManager::circle);
            case EaseInBack:
                return EasingManager.in((Function)EasingManager.back((Double)args.arg0));
            case EaseOutBack:
                return EasingManager.out((Function)EasingManager.back((Double)args.arg0));
            case EaseInOutBack:
                return EasingManager.inOut((Function)EasingManager.back((Double)args.arg0));
            case EaseInElastic:
                return EasingManager.in((Function)EasingManager.elastic((Double)args.arg0));
            case EaseOutElastic:
                return EasingManager.out((Function)EasingManager.elastic((Double)args.arg0));
            case EaseInOutElastic:
                return EasingManager.inOut((Function)EasingManager.elastic((Double)args.arg0));
            case EaseInBounce:
                return EasingManager.in((Function)EasingManager.bounce((Double)args.arg0));
            case EaseOutBounce:
                return EasingManager.out((Function)EasingManager.bounce((Double)args.arg0));
            case EaseInOutBounce:
                return EasingManager.inOut((Function)EasingManager.bounce((Double)args.arg0));
            default:
                return EasingManager.in(EasingManager::linear);
        }
    }

    static Function<Double, Double> in(Function<Double, Double> easing) {
        return easing;
    }

    static Function<Double, Double> out(Function<Double, Double> easing) {
        return t -> 1.0 - (Double)easing.apply(1.0 - t);
    }

    static Function<Double, Double> inOut(Function<Double, Double> easing) {
        return t -> {
            if (t < 0.5) {
                return (Double)easing.apply(t * 2.0) / 2.0;
            }
            return 1.0 - (Double)easing.apply((1.0 - t) * 2.0) / 2.0;
        };
    }

    static Function<Double, Double> step0() {
        return n -> n > 0.0 ? 1.0 : 0.0;
    }

    static Function<Double, Double> step1() {
        return n -> n >= 1.0 ? 1.0 : 0.0;
    }

    static double linear(double t) {
        return t;
    }

    static double quad(double t) {
        return t * t;
    }

    static double cubic(double t) {
        return t * t * t;
    }

    static Function<Double, Double> poly(double n) {
        return t -> Math.pow(t, n);
    }

    static double sin(double t) {
        return 1.0 - Math.cos((float)(t * Math.PI / 2.0));
    }

    static double circle(double t) {
        return 1.0 - Math.sqrt(1.0 - t * t);
    }

    static double exp(double t) {
        return Math.pow(2.0, 10.0 * (t - 1.0));
    }

    static Function<Double, Double> elastic(Double bounciness) {
        double p = (bounciness == null ? 1.0 : bounciness) * Math.PI;
        return t -> 1.0 - Math.pow(Math.cos((float)(t * Math.PI / 2.0)), 3.0) * Math.cos((float)(t * p));
    }

    static Function<Double, Double> back(Double s) {
        double p = s == null ? 1.70158 : s * 1.70158;
        return t -> t * t * ((p + 1.0) * t - p);
    }

    public static Function<Double, Double> bounce(Double s) {
        double k = s == null ? 0.5 : s;
        Function<Double, Double> q = x -> 7.5625 * x * x;
        Function<Double, Double> w = x -> 30.25 * k * Math.pow(x - 0.5454545454545454, 2.0) + 1.0 - k;
        Function<Double, Double> r = x -> 121.0 * k * k * Math.pow(x - 0.8181818181818182, 2.0) + 1.0 - k * k;
        Function<Double, Double> t = x -> 484.0 * k * k * k * Math.pow(x - 0.9545454545454546, 2.0) + 1.0 - k * k * k;
        return x -> EasingManager.min((double)((Double)q.apply((Double)x)), (double)((Double)w.apply((Double)x)), (double)((Double)r.apply((Double)x)), (double)((Double)t.apply((Double)x)));
    }

    static Function<Double, Double> step(Double stepArg) {
        int steps = stepArg != null ? stepArg.intValue() : 2;
        double[] intervals = EasingManager.stepRange((int)steps);
        return t -> intervals[EasingManager.findIntervalBorderIndex((double)t, (double[])intervals, (boolean)false)];
    }

    static double min(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }

    static int findIntervalBorderIndex(double point, double[] intervals, boolean useRightBorder) {
        if (point < intervals[0]) {
            return 0;
        }
        if (point > intervals[intervals.length - 1]) {
            return intervals.length - 1;
        }
        int indexOfNumberToCompare = 0;
        int leftBorderIndex = 0;
        int rightBorderIndex = intervals.length - 1;
        while (rightBorderIndex - leftBorderIndex != 1) {
            indexOfNumberToCompare = leftBorderIndex + (rightBorderIndex - leftBorderIndex) / 2;
            if (point >= intervals[indexOfNumberToCompare]) {
                leftBorderIndex = indexOfNumberToCompare;
                continue;
            }
            rightBorderIndex = indexOfNumberToCompare;
        }
        return useRightBorder ? rightBorderIndex : leftBorderIndex;
    }

    static double[] stepRange(int steps) {
        double stop = 1.0;
        if (steps < 2) {
            throw new IllegalArgumentException("steps must be > 2, got:" + steps);
        }
        double stepLength = 1.0 / (double)steps;
        AtomicInteger i = new AtomicInteger();
        return DoubleStream.generate(() -> (double)i.getAndIncrement() * stepLength).limit(steps).toArray();
    }

    public static class EasingFunctionArgs {
        public Object arg0;
        public EasingType easingType;
        public EasingFunctionArgs() {}
        public EasingFunctionArgs(EasingType easingType, Double arg0) {
            this.easingType = easingType;
            this.arg0 = arg0;
        }
    }
}

