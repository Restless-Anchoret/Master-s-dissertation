package com.ran.engine.factories.interpolation;

import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.exception.AlgebraicException;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.SingleDouble;

public class ParabolaBuilder {

    private static final ParabolaBuilder INSTANCE = new ParabolaBuilder();

    public static ParabolaBuilder getInstance() {
        return INSTANCE;
    }
    
    public DoubleFunction<SingleDouble> buildParabolaByThreePoints(Pair<Double, Double> firstPoint,
            Pair<Double, Double> secondPoint, Pair<Double, Double> thirdPoint) {
        double x1 = firstPoint.getLeft();
        double x2 = secondPoint.getLeft();
        double x3 = thirdPoint.getLeft();
        
        double f1 = firstPoint.getRight();
        double f2 = secondPoint.getRight();
        double f3 = thirdPoint.getRight();
//        System.out.println("x: " + x1 + " " + x2 + " " + x3);
//        System.out.println("f: " + f1 + " " + f2 + " " + f3);
        
        double a11 = x2 * x2 - x1 * x1;
        double a12 = x2 - x1;
        double a21 = x3 * x3 - x1 * x1;
        double a22 = x3 - x1;
//        System.out.println("a: " + a11 + " " + a12 + " " + a21 + " " + a22);
        
        if (ArithmeticOperations.doubleEquals(a22 * a11 - a12 * a21, 0.0)) {
            throw new AlgebraicException("Cannot build parabola over points: " + firstPoint + ", " +
                    secondPoint + ", " + thirdPoint);
        }
        
        double f1New = f2 - f1;
        double f2New = f3 - f1;
//        System.out.println("fNew: " + f1New + " " + f2New);
        
        double a, b;
        if (ArithmeticOperations.doubleNotEquals(a11, 0.0)) {
            b = (f2New * a11 - f1New * a21) / (a22 * a11 - a12 * a21);
            a = (f1New  - a12 * b) / a11;
        } else {
            b = f1New / a12;
            a = (f2New - a22 * b) / a21;
        }
        double c = f1 - x1 * x1 * a - x1 * b;
//        System.out.println("a = " + a + "; b = " + b + "; c = " + c);
        DoubleFunction<SingleDouble> function =
                new DoubleFunction<>(point -> new SingleDouble(a * point * point + b * point + c), x1, x3);
//        System.out.println("At x1: " + function.apply(x1));
//        System.out.println("At x2: " + function.apply(x2));
//        System.out.println("At x3: " + function.apply(x3));
        return function;
    }
    
}