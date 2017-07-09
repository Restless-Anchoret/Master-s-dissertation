package com.ran.engine.rendering.algebraic.common;

import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

public class VectorManipulator {

    public static double countVectorAngle(TwoDoubleVector vector) {
        TwoDoubleVector normalizedVector = vector.normalized();
        double angle = Math.atan2(normalizedVector.getY(), normalizedVector.getX());
        if (angle < 0.0) {
            angle += Math.PI * 2.0;
        }
        return angle;
    }

    public static double countAngleBetweenVectors(TwoDoubleVector firstVector, TwoDoubleVector secondVector) {
        double firstAngle = countVectorAngle(firstVector);
        double secondAngle = countVectorAngle(secondVector);
        if (secondAngle > firstAngle) {
            return secondAngle - firstAngle;
        } else {
            return secondAngle - firstAngle + Math.PI * 2.0;
        }
    }

}