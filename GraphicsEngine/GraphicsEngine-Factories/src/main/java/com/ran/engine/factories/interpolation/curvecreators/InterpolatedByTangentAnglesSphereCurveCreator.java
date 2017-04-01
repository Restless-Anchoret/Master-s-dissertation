package com.ran.engine.factories.interpolation.curvecreators;

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
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<Pair<ThreeDoubleVector, Double>> inputList, SimpleInputParameters parameters, int degree) {
        return null;
    }

}