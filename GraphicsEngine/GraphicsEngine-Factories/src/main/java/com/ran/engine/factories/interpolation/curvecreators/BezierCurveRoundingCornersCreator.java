package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.List;

public class BezierCurveRoundingCornersCreator extends AbstractInterpolatedCurveCreator<
        ThreeDoubleVector, ThreeDoubleVector, SimpleInputParameters> {

    private static final BezierCurveRoundingCornersCreator INSTANCE = new BezierCurveRoundingCornersCreator();

    public static BezierCurveRoundingCornersCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> inputList,
                                                              SimpleInputParameters parameters, int degree) {
        return null;
    }

}