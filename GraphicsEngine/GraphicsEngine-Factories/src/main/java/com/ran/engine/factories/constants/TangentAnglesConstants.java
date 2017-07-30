package com.ran.engine.factories.constants;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TangentAnglesConstants {

    public final static Color DARK_GRAY_COLOR = new Color(115, 115, 115);

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
                new Pair<>(new ThreeDoubleVector(0.0, 0.0, 3.0), -4.0 * Math.PI / 10.0),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0, 0.0), null),
                new Pair<>(new ThreeDoubleVector(3.0, 0.0, 0.0), -Math.PI / 2.0),
                new Pair<>(new ThreeDoubleVector(0.0, 0.0, -3.0), -7.0 * Math.PI / 12.0),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, -3.0, 0.0), null),
                new Pair<>(new ThreeDoubleVector(-3.0, 0.0, 0.0), -Math.PI / 2.0)
        );
    }

    public static List<Pair<ThreeDoubleVector, Double>> getListWithAllTangentAngles() {
        return Arrays.asList(
                new Pair<>(new ThreeDoubleVector(0.0, 0.0, 3.0), -Math.PI / 2.0),
                new Pair<>(new ThreeDoubleVector(0.0, 3.0, 0.0), 0.0),
                new Pair<>(new ThreeDoubleVector(3.0, 0.0, 0.0), -Math.PI / 2.0),
                new Pair<>(new ThreeDoubleVector(0.0, 0.0, -3.0), -Math.PI / 2.0),
                new Pair<>(new ThreeDoubleVector(0.0, -3.0, 0.0), 0.0),
                new Pair<>(new ThreeDoubleVector(-3.0, 0.0, 0.0), 0.0)
        );
    }

    public static List<Pair<ThreeDoubleVector, Double>> getCloseListWithoutTangentAngles() {
        return Arrays.asList(
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0 / Math.sqrt(2.0), 3.0 / Math.sqrt(2.0)), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0 / Math.sqrt(2.0), 3.0 / Math.sqrt(2.0), 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0, 0.0, 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0 / Math.sqrt(2.0), 0.0, 3.0 / Math.sqrt(2.0)), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), null)
        );
    }

    public static List<Pair<ThreeDoubleVector, Double>> getCloseListWithSomeTangentAngles() {
        return Arrays.asList(
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), -2.0 * Math.PI / 3.0),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0 / Math.sqrt(2.0), 3.0 / Math.sqrt(2.0)), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 3.0, 0.0), 0.0),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0 / Math.sqrt(2.0), 3.0 / Math.sqrt(2.0), 0.0), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0, 0.0, 0.0), Math.PI / 8.0),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(3.0 / Math.sqrt(2.0), 0.0, 3.0 / Math.sqrt(2.0)), null),
                new Pair<ThreeDoubleVector, Double>(new ThreeDoubleVector(0.0, 0.0, 3.0), -2.0 * Math.PI / 3.0)
        );
    }

}