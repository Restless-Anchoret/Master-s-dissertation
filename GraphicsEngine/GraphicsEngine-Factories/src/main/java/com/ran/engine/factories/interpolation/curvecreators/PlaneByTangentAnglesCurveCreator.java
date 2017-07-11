package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

import java.util.List;

public class PlaneByTangentAnglesCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<TwoDoubleVector, Double>, TwoDoubleVector, SimpleInputParameters> {

    private static final PlaneByTangentAnglesCurveCreator INSTANCE = new PlaneByTangentAnglesCurveCreator();

    public static PlaneByTangentAnglesCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<TwoDoubleVector> interpolateCurve(
            List<Pair<TwoDoubleVector, Double>> verticesWithTangentAnglesList, SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesWithTangentAnglesList);
        return null;
    }

}