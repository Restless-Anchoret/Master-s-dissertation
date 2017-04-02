package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.rendering.algebraic.quaternion.Quaternion;

import java.util.List;

public abstract class AbstractOrientationCurveCreator extends AbstractInterpolatedCurveCreator<
        Quaternion, Quaternion, SimpleInputParameters> {

    @Override
    protected void validateVerticesList(List<Quaternion> verticesList) {
        super.validateVerticesList(verticesList);
        if (verticesList.stream().anyMatch(quaternion -> !quaternion.isIdentity())) {
            throw new InterpolationException("All quaternions must be identity");
        }
    }

}