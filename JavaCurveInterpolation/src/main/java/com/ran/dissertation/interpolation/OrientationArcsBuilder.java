package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.matrix.DoubleMatrix;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class OrientationArcsBuilder {

    private static final OrientationArcsBuilder INSTANCE = new OrientationArcsBuilder();

    public static OrientationArcsBuilder getInstance() {
        return INSTANCE;
    }

    private OrientationArcsBuilder() { }
    
    public Result buildArcsBetweenQuaternionsOnThreeDimensionalSphere(
            Quaternion p1, Quaternion p2, Quaternion p3) {
        Quaternion h = p1.quaternionVectorMultiply(p2, p3).normalized();
        Quaternion hConjugate = h.getConjugate();
        
        Quaternion r1 = p1.multiply(hConjugate);
        Quaternion r2 = p2.multiply(hConjugate);
        Quaternion r3 = p3.multiply(hConjugate);
        
        ThreeDoubleVector m1 = r1.getVector();
        ThreeDoubleVector m2 = r2.getVector();
        ThreeDoubleVector m3 = r3.getVector();
        
        ArcsBuilder.Result arcsBuilderResult = ArcsBuilder.getInstance()
                .buildArcsBetweenVerticesOnSphere(m1, m2, m3);
        
        DoubleFunction<Quaternion> firstRotation = buildRotationFunction(
                arcsBuilderResult.getFirstRotation(), m1, r1.getConjugate());
        DoubleFunction<Quaternion> secondRotation = buildRotationFunction(
                arcsBuilderResult.getSecondRotation(), m2, r2.getConjugate());
        
        return new Result(firstRotation, secondRotation,
                arcsBuilderResult.getFirstAngle(), arcsBuilderResult.getSecondAngle());
    }
    
    private DoubleFunction<Quaternion> buildRotationFunction(DoubleFunction<DoubleMatrix> matrixRotation,
            ThreeDoubleVector m, Quaternion rConjugate) {
        return new DoubleFunction<>(
                point -> {
                    DoubleMatrix rotation = matrixRotation.apply(point);
                    Quaternion leftFactor = Quaternion.createFromVector(
                            new ThreeDoubleVector(rotation.multiply(m.getDoubleVector())));
                    return leftFactor.multiply(rConjugate);
                },
                0.0, 1.0
        );
    }
    
    public static class Result {
        
        private final DoubleFunction<Quaternion> firstRotation;
        private final DoubleFunction<Quaternion> secondRotation;
        private final double firstAngle;
        private final double secondAngle;

        public Result(DoubleFunction<Quaternion> firstRotation, DoubleFunction<Quaternion> secondRotation, double firstAngle, double secondAngle) {
            this.firstRotation = firstRotation;
            this.secondRotation = secondRotation;
            this.firstAngle = firstAngle;
            this.secondAngle = secondAngle;
        }

        public DoubleFunction<Quaternion> getFirstRotation() {
            return firstRotation;
        }

        public DoubleFunction<Quaternion> getSecondRotation() {
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