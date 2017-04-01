package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

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
    
}