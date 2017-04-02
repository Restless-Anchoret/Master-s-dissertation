package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.List;

public class InterpolatedByTangentAnglesSphereCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<ThreeDoubleVector, Double>, ThreeDoubleVector, SimpleInputParameters> {

    private static final InterpolatedByTangentAnglesSphereCurveCreator INSTANCE = new InterpolatedByTangentAnglesSphereCurveCreator();

    public static InterpolatedByTangentAnglesSphereCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<Pair<ThreeDoubleVector, Double>> verticesWithTangentAnglesList,
                                                              SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesWithTangentAnglesList);
        return null;
    }

    @Override
    protected void validateVerticesList(List<Pair<ThreeDoubleVector, Double>> verticesWithTangentAnglesList) {
        super.validateVerticesList(verticesWithTangentAnglesList);
        double radius = verticesWithTangentAnglesList.get(0).getLeft().getNorm();
        if (verticesWithTangentAnglesList.stream().anyMatch(
                verticeWithTangentAngle -> ArithmeticOperations.doubleNotEquals(verticeWithTangentAngle.getLeft().getNorm(), radius))) {
            throw new InterpolationException("All vertices must belong to the same sphere");
        }
    }

}