package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

import java.util.List;

public class PlaneBezierCurveCreator extends AbstractPlainCurveCreator {

    private static final PlaneBezierCurveCreator INSTANCE = new PlaneBezierCurveCreator();

    public static PlaneBezierCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<TwoDoubleVector> interpolateCurve(
            List<TwoDoubleVector> vertices, SimpleInputParameters parameters, int degree) {
        validateVerticesList(vertices);
        return null;
    }

}