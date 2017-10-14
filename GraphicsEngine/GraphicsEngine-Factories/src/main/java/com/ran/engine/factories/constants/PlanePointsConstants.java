package com.ran.engine.factories.constants;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.Arrays;
import java.util.List;

public class PlanePointsConstants {

    private PlanePointsConstants() { }

    public static List<TwoDoubleVector> getListForSimpleInterpolation() {
//        return Arrays.asList(
//                new TwoDoubleVector(-7.0, 0.0),
//                new TwoDoubleVector(-5.0, -5.0),
//                new TwoDoubleVector(1.0, -3.5),
//                new TwoDoubleVector(3.0, -7.0),
//                new TwoDoubleVector(7.0, -3.0),
//                new TwoDoubleVector(4.0, 1.0),
//                new TwoDoubleVector(1.0, 2.0),
//                new TwoDoubleVector(0.0, 3.0),
//                new TwoDoubleVector(5.0, 3.0)
//        );
        return Arrays.asList(
                new TwoDoubleVector(-8.0, -1.0),
                new TwoDoubleVector(-4.0, 2.0),
                new TwoDoubleVector(0.0, -2.0),
                new TwoDoubleVector(6.0, 4.0),
                new TwoDoubleVector(8.0, 0.0)
        );
    }

    public static List<Pair<TwoDoubleVector, Double>> getListForTangentInterpolation() {
        return Arrays.asList(
                new Pair<>(new TwoDoubleVector(-8.0, -1.0), Math.PI / 8.0),
                new Pair<>(new TwoDoubleVector(-4.0, 2.0), -Math.PI / 8.0),
                new Pair<>(new TwoDoubleVector(0.0, -2.0), Math.PI / 16.0),
                new Pair<>(new TwoDoubleVector(6.0, 4.0), 0.0),
                new Pair<>(new TwoDoubleVector(8.0, 0.0), -Math.PI)
        );
    }

}
