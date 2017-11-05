package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.ArithmeticOperations;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.matrix.DoubleMatrix;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TangentOrientationBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(TangentOrientationBuilder.class);

    private static TangentOrientationBuilder INSTANCE = new TangentOrientationBuilder();
    private static final DoubleMatrix Z_HALF_PI_ROTATION = RotationCreator.getInstance().createRotation(
            ThreeDoubleVector.Z_ONE_THREE_DOUBLE_VECTOR, -Math.PI / 2.0);

    public static TangentOrientationBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildTangentOrientation(Quaternion orientation,
                                          double tangentAngle,
                                          Double forwardRotationAngle,
                                          Double backRotationAngle) {
        LOG.trace("orientation = {}, tangentAngle = {}, forwardRotationAngle = {}, backRotationAngle = {}",
                orientation, tangentAngle, forwardRotationAngle, backRotationAngle);
        ThreeDoubleVector a, b;
        ThreeDoubleVector point = orientation.multiply(Quaternion.createFromVector(ThreeDoubleVector.Z_ONE_THREE_DOUBLE_VECTOR))
                .multiply(orientation.getConjugate()).getVector();
        if (ArithmeticOperations.doubleEquals(point.getX(), 0.0) &&
                ArithmeticOperations.doubleEquals(point.getY(), 0.0)) {
            a = ThreeDoubleVector.X_ONE_THREE_DOUBLE_VECTOR;
            if (point.getZ() > 0.0) {
                b = ThreeDoubleVector.MINUS_Y_ONE_THREE_DOUBLE_VECTOR;
            } else {
                b = ThreeDoubleVector.Y_ONE_THREE_DOUBLE_VECTOR;
            }
        } else {
            a = new ThreeDoubleVector(Z_HALF_PI_ROTATION.multiply(
                    new ThreeDoubleVector(point.getX(), point.getY(), 0.0).getDoubleVector())).normalized();
            b = point.multiply(a).normalized();
        }
        ThreeDoubleVector n = a.multiply(Math.sin(tangentAngle)).add(b.multiply(-Math.cos(tangentAngle)));
        LOG.trace("a = {}, b = {}, n = {}", a, b, n);

        DoubleFunction<Quaternion> forwardRotation = null;
        DoubleFunction<Quaternion> backRotation = null;

        // todo: find out, why minus is needed before forwardRotationAngle, but not before backRotationAngle
        if (forwardRotationAngle != null) {
            forwardRotation = new DoubleFunction<>(
                    u -> Quaternion.createForRotation(n, -u * forwardRotationAngle),
                    0.0, 1.0);
        }
        if (backRotationAngle != null) {
            backRotation = new DoubleFunction<>(
                    u -> Quaternion.createForRotation(n, u * backRotationAngle),
                    0.0, 1.0);
        }
        return new Result(forwardRotation, backRotation, forwardRotationAngle, backRotationAngle);
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
