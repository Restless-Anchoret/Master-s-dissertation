package com.ran.engine.algebra.common;

import com.ran.engine.algebra.vector.TwoDoubleVector;

public class EquationsSolver {

    public static TwoDoubleVector solveTwoEquationsSystem(
            double a11, double a12, double a21, double a22, double f1, double f2) {
        if (ArithmeticOperations.doubleEquals(a22 * a11 - a12 * a21, 0.0)) {
            return null;
        }

        double x, y;
        if (ArithmeticOperations.doubleNotEquals(a11, 0.0)) {
            y = (f2 * a11 - f1 * a21) / (a22 * a11 - a12 * a21);
            x = (f1  - a12 * y) / a11;
        } else {
            y = f1 / a12;
            x = (f2 - a22 * y) / a21;
        }
        return new TwoDoubleVector(x, y);
    }

}