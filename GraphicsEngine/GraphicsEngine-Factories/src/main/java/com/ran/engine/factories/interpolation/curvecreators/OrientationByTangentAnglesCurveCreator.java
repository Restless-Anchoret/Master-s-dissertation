package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.quaternion.Quaternion;

import java.util.List;

public class OrientationByTangentAnglesCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<Quaternion, Double>, Quaternion, SimpleInputParameters> {

    @Override
    public DoubleFunction<Quaternion> interpolateCurve(
            List<Pair<Quaternion, Double>> quaternionsWithTangentAnglesList, SimpleInputParameters parameters, int degree) {
        validateVerticesList(quaternionsWithTangentAnglesList);
        return null;
    }

}