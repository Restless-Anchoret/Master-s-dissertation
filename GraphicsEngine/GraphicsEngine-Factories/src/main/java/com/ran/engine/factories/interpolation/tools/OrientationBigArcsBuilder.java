package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

public class OrientationBigArcsBuilder {

    private static OrientationBigArcsBuilder INSTANCE = new OrientationBigArcsBuilder();

    public static OrientationBigArcsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildOrientationBigArcsBetweenQuaternions(Quaternion p1, Quaternion p2) {
        Quaternion r = p2.multiply(p1.getConjugate());
        ThreeDoubleVector axis = r.getVector().normalized();
        double cos = r.getScalar();
        double angle = (Math.acos(cos) * 2.0 + Math.PI) % (2.0 * Math.PI) - Math.PI;
        DoubleFunction<Quaternion> rotation = new DoubleFunction<>(
                point -> Quaternion.createForRotation(axis, angle * point),
                0.0, 1.0
        );
        return new Result(rotation, angle);
    }

    public static class Result {
        private DoubleFunction<Quaternion> rotation;
        private double angle;

        public Result(DoubleFunction<Quaternion> rotation, double angle) {
            this.rotation = rotation;
            this.angle = angle;
        }

        public DoubleFunction<Quaternion> getRotation() {
            return rotation;
        }

        public double getAngle() {
            return angle;
        }
    }

}
