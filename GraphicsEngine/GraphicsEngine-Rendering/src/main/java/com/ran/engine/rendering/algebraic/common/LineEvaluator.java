package com.ran.engine.rendering.algebraic.common;

import com.ran.engine.rendering.algebraic.line.Line;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

public class LineEvaluator {

    public static boolean arePointsOnOneLine(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint, TwoDoubleVector thirdPoint) {
        TwoDoubleVector upperVector = secondPoint.substract(firstPoint);
        TwoDoubleVector lowerVector = thirdPoint.substract(firstPoint);
        double determinant = upperVector.getX() * lowerVector.getY() - upperVector.getY() * lowerVector.getX();
        return ArithmeticOperations.doubleEquals(determinant, 0.0);
    }

    public static Line evaluateMiddlePerpendicularLine(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint) {
        double x1 = firstPoint.getX();
        double x2 = secondPoint.getX();
        double y1 = firstPoint.getY();
        double y2 = secondPoint.getY();

        double a = x1 - x2;
        double b = y1 - y2;
        double c = (x2 - x1) * (x1 + x2) / 2.0 + (y2 - y1) * (y1 + y2) / 2.0;

        return new Line(a, b, c);
    }

    public static TwoDoubleVector evaluateLinesIntersection(Line firstLine, Line secondLine) {
        return EquationsSolver.solveTwoEquationsSystem(
                firstLine.getA(), firstLine.getB(),
                secondLine.getA(), secondLine.getB(),
                -firstLine.getC(), -secondLine.getC()
        );
    }

}