package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;

public class TangentOrientationBuilder {

    private static TangentOrientationBuilder INSTANCE = new TangentOrientationBuilder();

    public static TangentOrientationBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildTangentOrientation(Quaternion orientation,
                                          double tangentAngle,
                                          Double forwardRotationAngle,
                                          Double backRotationAngle) {
        return null;
    }

    public static class Result {
        private final DoubleFunction<Quaternion> forwardRotation;
        private final DoubleFunction<Quaternion> backRotation;
        private final Double forwardAngle, backAngle;

        public Result(DoubleFunction<Quaternion> forwardRotation,
                      DoubleFunction<Quaternion> backRotation,
                      Double forwardAngle, Double backAngle) {
            this.forwardRotation = forwardRotation;
            this.backRotation = backRotation;
            this.forwardAngle = forwardAngle;
            this.backAngle = backAngle;
        }

        public DoubleFunction<Quaternion> getForwardRotation() {
            return forwardRotation;
        }

        public DoubleFunction<Quaternion> getBackRotation() {
            return backRotation;
        }

        public Double getForwardAngle() {
            return forwardAngle;
        }

        public Double getBackAngle() {
            return backAngle;
        }

        public Pair<Double, Double> getAngles() {
            return new Pair<>(forwardAngle, backAngle);
        }

    }

}
