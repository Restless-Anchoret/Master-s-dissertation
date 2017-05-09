package com.ran.engine.factories.constants;

import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.Arrays;
import java.util.List;

public class TangentAnglesConstants {

    private TangentAnglesConstants() { }

    public static List<Pair<ThreeDoubleVector, Double>> getListWithoutTangentAngles() {
        return Arrays.asList(
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0, 0.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, -3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, -3.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(-3.0, 0.0, 0.0), null)
        );
    }

    public static List<Pair<ThreeDoubleVector, Double>> getListWithSomeTangentAngles() {
        return Arrays.asList(
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0, 0.0), null),
                new Pair<>(new ThreeDoubleVector(3.0, 0.0, 0.0), -Math.PI / 2.0),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, -3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, -3.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(-3.0, 0.0, 0.0), null)
        );
    }

    public static List<Pair<ThreeDoubleVector, Double>> getListWithAllTangentAngles() {
        return Arrays.asList(
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0, 0.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, -3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, -3.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(-3.0, 0.0, 0.0), null)
        );
    }

}