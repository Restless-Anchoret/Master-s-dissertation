package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;

import java.util.List;

public class OrientationBezierCurveCreator extends AbstractOrientationCurveCreator {

    @Override
    public DoubleFunction<Quaternion> interpolateCurve(
            List<Quaternion> quaternions, SimpleInputParameters parameters, int degree) {
        validateVerticesList(quaternions);
        return null;
    }

}