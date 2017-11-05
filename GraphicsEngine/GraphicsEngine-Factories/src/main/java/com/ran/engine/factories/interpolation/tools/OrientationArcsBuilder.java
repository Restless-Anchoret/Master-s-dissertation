package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.ArithmeticOperations;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.matrix.DoubleMatrix;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

public class OrientationArcsBuilder {

    private static final OrientationArcsBuilder INSTANCE = new OrientationArcsBuilder();

    public static OrientationArcsBuilder getInstance() {
        return INSTANCE;
    }
    
    public Result buildArcsBetweenQuaternionsOnThreeDimensionalSphere(
            Quaternion p1, Quaternion p2, Quaternion p3) {
//        System.out.println("p1 = " + p1);
//        System.out.println("p2 = " + p2);
//        System.out.println("p3 = " + p3);
        Quaternion hNotNormalized = p1.quaternionVectorMultiply(p2, p3);
//        System.out.println("hNotNormalized = " + hNotNormalized);
        if (ArithmeticOperations.doubleEquals(hNotNormalized.getNorm(), 0.0)) {
            OrientationBigArcsBuilder bigArcsBuilder = OrientationBigArcsBuilder.getInstance();
            OrientationBigArcsBuilder.Result firstArc = bigArcsBuilder.buildOrientationBigArcsBetweenQuaternions(p1, p2);
            OrientationBigArcsBuilder.Result secondArc = bigArcsBuilder.buildOrientationBigArcsBetweenQuaternions(p2, p3);
            return new Result(firstArc.getRotation(), secondArc.getRotation(), firstArc.getAngle(), secondArc.getAngle());
        } else {
            Quaternion h = hNotNormalized.normalized();
            Quaternion hConjugate = h.getConjugate();
//            System.out.println("h = " + h);
//            System.out.println("hConjugate = " + hConjugate);
            
            Quaternion r1 = p1.multiply(hConjugate);
            Quaternion r2 = p2.multiply(hConjugate);
            Quaternion r3 = p3.multiply(hConjugate);
//            System.out.println("r1 = " + r1);
//            System.out.println("r2 = " + r2);
//            System.out.println("r3 = " + r3);
            
            ThreeDoubleVector m1 = r1.getVector();
            ThreeDoubleVector m2 = r2.getVector();
            ThreeDoubleVector m3 = r3.getVector();
//            System.out.println("m1 = " + m1);
//            System.out.println("m2 = " + m2);
//            System.out.println("m3 = " + m3);
            
            ArcsBuilder.Result arcsBuilderResult = ArcsBuilder.getInstance()
                    .buildArcsBetweenVerticesOnSphere(m1, m2, m3);
            
            DoubleFunction<Quaternion> firstRotation = buildRotationFunction(
                    arcsBuilderResult.getFirstRotation(), m1, r1);
            DoubleFunction<Quaternion> secondRotation = buildRotationFunction(
                    arcsBuilderResult.getSecondRotation(), m2, r2);
                
            return new Result(firstRotation, secondRotation,
                    arcsBuilderResult.getFirstAngle(), arcsBuilderResult.getSecondAngle());
        }
    }
    
    private DoubleFunction<Quaternion> buildRotationFunction(DoubleFunction<DoubleMatrix> matrixRotation,
            ThreeDoubleVector m, Quaternion r) {
        return new DoubleFunction<>(
                point -> {
                    DoubleMatrix rotation = matrixRotation.apply(point);
                    Quaternion leftFactor = Quaternion.createFromVector(
                            new ThreeDoubleVector(rotation.multiply(m.getDoubleVector())));
                    return leftFactor.multiply(r.getConjugate());
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
