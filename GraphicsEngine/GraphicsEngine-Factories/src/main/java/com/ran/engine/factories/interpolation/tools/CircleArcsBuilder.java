package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

public class CircleArcsBuilder {

    private static CircleArcsBuilder INSTANCE = new CircleArcsBuilder();

    private static CircleArcsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildCircle(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint, TwoDoubleVector thirdPoint) {
        return null;
    }

    public static class Result {

        private final DoubleFunction<TwoDoubleVector> firstArc;
        private final DoubleFunction<TwoDoubleVector> secondArc;
        private final double firstAngle;
        private final double secondAngle;
        private final double firstArcLength;
        private final double secondArcLength;

        public Result(DoubleFunction<TwoDoubleVector> firstArc, DoubleFunction<TwoDoubleVector> secondArc,
                      double firstAngle, double secondAngle, double firstArcLength, double secondArcLength) {
            this.firstArc = firstArc;
            this.secondArc = secondArc;
            this.firstAngle = firstAngle;
            this.secondAngle = secondAngle;
            this.firstArcLength = firstArcLength;
            this.secondArcLength = secondArcLength;
        }

        public DoubleFunction<TwoDoubleVector> getFirstArc() {
            return firstArc;
        }

        public DoubleFunction<TwoDoubleVector> getSecondArc() {
            return secondArc;
        }

        public double getFirstAngle() {
            return firstAngle;
        }

        public double getSecondAngle() {
            return secondAngle;
        }

        public double getFirstArcLength() {
            return firstArcLength;
        }

        public double getSecondArcLength() {
            return secondArcLength;
        }

        public Pair<Double, Double> getAngles() {
            return new Pair<>(firstAngle, secondAngle);
        }

        public Pair<Double, Double> getArcsLengths() {
            return new Pair<>(firstArcLength, secondArcLength);
        }

    }

}