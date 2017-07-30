package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.ArithmeticOperations;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.matrix.DoubleMatrix;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

public class RotationCreator {

    private static final RotationCreator INSTANCE = new RotationCreator();
    
    public static RotationCreator getInstance() {
        return INSTANCE;
    }
    
    public DoubleMatrix createRotation(ThreeDoubleVector axis, double angle) {
        double n1 = axis.getX();
        double n2 = axis.getY();
        double n3 = axis.getZ();
        
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double vers = 1.0 - cos;
        
        double[][] matrix = new double[][] {
            {n1 * n1 + (1.0 - n1 * n1) * cos, n1 * n2 * vers - n3 * sin,       n1 * n3 * vers + n2 * sin      },
            {n2 * n1 * vers + n3 * sin,       n2 * n2 + (1.0 - n2 * n2) * cos, n2 * n3 * vers - n1 * sin      },
            {n3 * n1 * vers - n2 * sin,       n3 * n2 * vers + n1 * sin,       n3 * n3 + (1.0 - n3 * n3) * cos}
        };
        return new DoubleMatrix(matrix);
    }

    public Pair<ThreeDoubleVector, Double> getAxisAndAngleForRotation(DoubleMatrix rotation) {
        double r21Diff = rotation.get(1, 0) - rotation.get(0, 1);
        double r13Diff = rotation.get(0, 2) - rotation.get(2, 0);
        double r32Diff = rotation.get(2, 1) - rotation.get(1, 2);
        double sin = 0.5 * Math.sqrt(r21Diff * r21Diff + r13Diff * r13Diff + r32Diff * r32Diff);
        double cos = 0.5 * (rotation.get(0, 0) + rotation.get(1, 1) +
                rotation.get(2, 2) - 1.0);
        double phi = Math.atan2(sin, cos);
        ThreeDoubleVector axis;
        if (ArithmeticOperations.doubleEquals(phi, 0.0) ||
                ArithmeticOperations.doubleEquals(phi, Math.PI)) {
            axis = new ThreeDoubleVector(
                    Math.sqrt((rotation.get(0, 0) + 1.0) / 2.0),
                    Math.sqrt((rotation.get(1, 1) + 1.0) / 2.0),
                    Math.sqrt((rotation.get(2, 2) + 1.0) / 2.0)
            );
        } else {
            axis = new ThreeDoubleVector(
                    r32Diff / (2.0 * sin),
                    r13Diff / (2.0 * sin),
                    r21Diff / (2.0 * sin)
            );
        }
        return new Pair<>(axis, phi);
    }

    public DoubleMatrix createReversedRotationByRotation(DoubleMatrix rotation) {
        Pair<ThreeDoubleVector, Double> axisAndAngle = getAxisAndAngleForRotation(rotation);
        ThreeDoubleVector axis = axisAndAngle.getLeft();
        double angle = axisAndAngle.getRight();
        return createRotation(axis, -angle);
    }
    
}