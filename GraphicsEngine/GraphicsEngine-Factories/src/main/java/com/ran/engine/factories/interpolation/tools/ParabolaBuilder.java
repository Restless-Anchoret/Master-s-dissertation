package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.EquationsSolver;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.exception.AlgebraicException;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.SingleDouble;
import com.ran.engine.algebra.vector.TwoDoubleVector;

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
        
        double a11 = x2 * x2 - x1 * x1;
        double a12 = x2 - x1;
        double a21 = x3 * x3 - x1 * x1;
        double a22 = x3 - x1;

        double f1New = f2 - f1;
        double f2New = f3 - f1;

        TwoDoubleVector equationSolution = EquationsSolver.
                solveTwoEquationsSystem(a11, a12, a21, a22, f1New, f2New);
        if (equationSolution == null) {
            throw new AlgebraicException("Cannot build parabola over points: " + firstPoint + ", " +
                    secondPoint + ", " + thirdPoint);
        }

        double a = equationSolution.getX();
        double b = equationSolution.getY();
        double c = f1 - x1 * x1 * a - x1 * b;

        DoubleFunction<SingleDouble> function =
                new DoubleFunction<>(point -> new SingleDouble(a * point * point + b * point + c), x1, x3);
        return function;
    }
    
}