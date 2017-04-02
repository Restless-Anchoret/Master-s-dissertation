package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.List;

public class BezierSphereCurveRoundingCornersCreator extends AbstractSphereCurveCreator {

    private static final BezierSphereCurveRoundingCornersCreator INSTANCE = new BezierSphereCurveRoundingCornersCreator();

    public static BezierSphereCurveRoundingCornersCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> verticesList,
                                                              SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesList);
        return null;
    }

}