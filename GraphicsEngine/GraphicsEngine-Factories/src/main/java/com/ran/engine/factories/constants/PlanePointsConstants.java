package com.ran.engine.factories.constants;

import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlanePointsConstants {

    private PlanePointsConstants() { }

    public static List<TwoDoubleVector> getListForSimpleInterpolation() {
        return Arrays.asList(
                new TwoDoubleVector(-7.0, 0.0),
                new TwoDoubleVector(-5.0, -5.0),
                new TwoDoubleVector(1.0, -3.5),
                new TwoDoubleVector(3.0, -7.0),
                new TwoDoubleVector(7.0, -3.0),
                new TwoDoubleVector(4.0, 1.0),
                new TwoDoubleVector(1.0, 2.0),
                new TwoDoubleVector(0.0, 3.0),
                new TwoDoubleVector(5.0, 3.0)
        );
    }

    public static List<TwoDoubleVector> getListForBezierInterpolation() {
        return Collections.emptyList();
    }

    public static List<TwoDoubleVector> getListForTangentInterpolation() {
        return Collections.emptyList();
    }

}