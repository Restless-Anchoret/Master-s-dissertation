package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.rendering.algebraic.common.AlgebraicObject;

import java.util.List;

public abstract class AbstractInterpolatedCurveCreator<I, O extends AlgebraicObject<O>, P extends InputParameters>
        implements InterpolatedCurveCreator<I, O, P> {

    protected void validateVerticesList(List<I> verticesList) {
        if (verticesList.size() < 3) {
            throw new InterpolationException("Interpolation requires at least 3 vertices");
        }
    }

}