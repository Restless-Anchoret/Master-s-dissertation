package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.List;

public class BezierSphereCurveByMiddlePointsCreator extends AbstractSphereCurveCreator {

    private static final BezierSphereCurveByMiddlePointsCreator INSTANCE = new BezierSphereCurveByMiddlePointsCreator();

    public static BezierSphereCurveByMiddlePointsCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> verticesList,
                                                              SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesList);
        return null;
    }

}