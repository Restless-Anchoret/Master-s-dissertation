package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.exception.AlgebraicException;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

public class ArcsBuilder {

    private static final ArcsBuilder INSTANCE = new ArcsBuilder();

    public static ArcsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildArcsBetweenVerticesOnSphere(
            ThreeDoubleVector p1, ThreeDoubleVector p2, ThreeDoubleVector p3) {
        ThreeDoubleVector a = (p3.substract(p2)).multiply(p1.substract(p2));
        double aNorm = a.getNorm();
        if (ArithmeticOperations.doubleEquals(aNorm, 0.0)) {
            throw new AlgebraicException("Every three sequential vertices must not coincide");
        }

        double mixedProduction = p1.mixedMultiply(p2, p3);
        ThreeDoubleVector n = a.multiply(1.0 / aNorm);
        ThreeDoubleVector c = n.multiply(mixedProduction / aNorm);

        ThreeDoubleVector r1 = p1.substract(c);
        ThreeDoubleVector r2 = p2.substract(c);
        ThreeDoubleVector r3 = p3.substract(c);

        ThreeDoubleVector n1 = r1.multiply(r2);
        ThreeDoubleVector n2 = r2.multiply(r3);

        double n1Norm = n1.getNorm();
        double n2Norm = n2.getNorm();
        double s1 = r1.scalarMultiply(r2);
        double s2 = r2.scalarMultiply(r3);

        double firstAtan2 = Math.atan2(n1Norm, s1);
        double phi = -(n1.scalarMultiply(n) > 0 ? firstAtan2 : 2 * Math.PI - firstAtan2);

        double secondAtan2 = Math.atan2(n2Norm, s2);
        double psi = -(n2.scalarMultiply(n) > 0 ? secondAtan2 : 2 * Math.PI - secondAtan2);

        return new Result(
                new DoubleFunction<>(point -> RotationCreator.getInstance().createRotation(n, point * phi), 0.0, 1.0),
                new DoubleFunction<>(point -> RotationCreator.getInstance().createRotation(n, point * psi), 0.0, 1.0),
                phi, psi);
    }

    public static class Result {

        private final DoubleFunction<DoubleMatrix> firstRotation;
        private final DoubleFunction<DoubleMatrix> secondRotation;
        private final double firstAngle;
        private final double secondAngle;

        public Result(DoubleFunction<DoubleMatrix> firstRotation, DoubleFunction<DoubleMatrix> secondRotation, double firstAngle, double secondAngle) {
            this.firstRotation = firstRotation;
            this.secondRotation = secondRotation;
            this.firstAngle = firstAngle;
            this.secondAngle = secondAngle;
        }

        public DoubleFunction<DoubleMatrix> getFirstRotation() {
            return firstRotation;
        }

        public DoubleFunction<DoubleMatrix> getSecondRotation() {
            return secondRotation;
        }

        public double getFirstAngle() {
            return firstAngle;
        }

        public double getSecondAngle() {
            return secondAngle;
        }

        public Pair<Double, Double> getAngles() {
            return new Pair<>(firstAngle, secondAngle);
        }
        
    }

}
