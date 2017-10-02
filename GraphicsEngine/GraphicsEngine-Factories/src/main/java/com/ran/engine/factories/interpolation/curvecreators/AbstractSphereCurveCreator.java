package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.algebra.common.ArithmeticOperations;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.util.List;

public abstract class AbstractSphereCurveCreator extends AbstractInterpolatedCurveCreator<
        ThreeDoubleVector, ThreeDoubleVector, SimpleInputParameters> {

    @Override
    protected void validateVerticesList(List<ThreeDoubleVector> verticesList) {
        super.validateVerticesList(verticesList);
        double radius = verticesList.get(0).getNorm();
        if (verticesList.stream().anyMatch(vertice -> ArithmeticOperations.doubleNotEquals(vertice.getNorm(), radius))) {
            throw new InterpolationException("All vertices must belong to the same sphere");
        }
    }

}