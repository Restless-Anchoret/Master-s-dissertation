package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;

public class OrientationBigArcsBuilder {

    private static OrientationBigArcsBuilder INSTANCE = new OrientationBigArcsBuilder();

    public static OrientationBigArcsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildOrientationBigArcsBetweenQuaternions(Quaternion p1, Quaternion p2) {
        return null;
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
