package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.exception.AlgebraicException;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

public class BigArcsBuilder {

    private static final BigArcsBuilder INSTANCE = new BigArcsBuilder();

    public static BigArcsBuilder getInstance() {
        return INSTANCE;
    }

    public BigArcsBuilder.Result buildBigArcBetweenVerticesOnSphere(
            ThreeDoubleVector p1, ThreeDoubleVector p2) {
        ThreeDoubleVector a = p1.multiply(p2);
        double aNorm = a.getNorm();
        if (ArithmeticOperations.doubleEquals(aNorm, 0.0)) {
            throw new AlgebraicException("Every two sequential vertices must not coincide");
        }

        ThreeDoubleVector n = a.multiply(1.0 / aNorm);
        double phi = Math.atan(aNorm / p1.scalarMultiply(p2));

        return new BigArcsBuilder.Result(
                new DoubleFunction<>(point -> RotationCreator.getInstance().createRotation(n, point * phi)),
                phi
        );
    }

    public static class Result {

        private final DoubleFunction<DoubleMatrix> rotation;
        private final double angle;

        public Result(DoubleFunction<DoubleMatrix> rotation, double angle) {
            this.rotation = rotation;
            this.angle = angle;
        }

        public DoubleFunction<DoubleMatrix> getRotation() {
            return rotation;
        }

        public double getAngle() {
            return angle;
        }

    }

}