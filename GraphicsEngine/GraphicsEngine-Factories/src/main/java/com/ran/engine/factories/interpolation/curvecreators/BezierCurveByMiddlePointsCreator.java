package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.List;

public class BezierCurveByMiddlePointsCreator extends AbstractInterpolatedCurveCreator<
        ThreeDoubleVector, ThreeDoubleVector, SimpleInputParameters> {

    private static final BezierCurveByMiddlePointsCreator INSTANCE = new BezierCurveByMiddlePointsCreator();

    public static BezierCurveByMiddlePointsCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> inputList,
                                                              SimpleInputParameters parameters, int degree) {
        return null;
    }

}