package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

public class TangentBuilder {

    private static final TangentBuilder INSTANCE = new TangentBuilder();
    private static final DoubleMatrix Z_HALF_PI_ROTATION = RotationCreator.getInstance().createRotation(
            new ThreeDoubleVector(0.0, 0.0, 1.0), Math.PI / 2.0);

    public static TangentBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildTangent(ThreeDoubleVector point,
                               double tangentAngle,
                               Double forwardRotationAngle,
                               Double backRotationAngle) {
        ThreeDoubleVector a, b;
        if (ArithmeticOperations.doubleEquals(point.getX(), 0.0) &&
                ArithmeticOperations.doubleEquals(point.getY(), 0.0)) {
            a = ThreeDoubleVector.X_ONE_THREE_DOUBLE_VECTOR;
            b = ThreeDoubleVector.Y_ONE_THREE_DOUBLE_VECTOR;
        } else {
            a = new ThreeDoubleVector(Z_HALF_PI_ROTATION.multiply(
                    new ThreeDoubleVector(point.getX(), point.getY(), 0.0).getDoubleVector())).normalized();
            b = point.multiply(a).normalized();
        }
        ThreeDoubleVector n = a.multiply(-Math.sin(tangentAngle)).add(b.multiply(Math.cos(tangentAngle)));

        DoubleFunction<DoubleMatrix> forwardRotation = null;
        DoubleFunction<DoubleMatrix> backRotation = null;

        if (forwardRotationAngle != null) {
            forwardRotation = new DoubleFunction<>(
                    u -> RotationCreator.getInstance().createRotation(n, u * forwardRotationAngle),
                    0.0, 1.0);
        }
        if (backRotationAngle != null) {
            backRotation = new DoubleFunction<>(
                    u -> RotationCreator.getInstance().createRotation(n, -u * backRotationAngle),
                    0.0, 1.0);
        }
        return new Result(forwardRotation, backRotation, forwardRotationAngle, backRotationAngle);
    }

    public static class Result {
        private final DoubleFunction<DoubleMatrix> forwardRotation;
        private final DoubleFunction<DoubleMatrix> backRotation;
        private final Double forwardAngle, backAngle;

        public Result(DoubleFunction<DoubleMatrix> forwardRotation, DoubleFunction<DoubleMatrix> backRotation,
                      Double forwardAngle, Double backAngle) {
            this.forwardRotation = forwardRotation;
            this.backRotation = backRotation;
            this.forwardAngle = forwardAngle;
            this.backAngle = backAngle;
        }

        public DoubleFunction<DoubleMatrix> getForwardRotation() {
            return forwardRotation;
        }

        public DoubleFunction<DoubleMatrix> getBackRotation() {
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