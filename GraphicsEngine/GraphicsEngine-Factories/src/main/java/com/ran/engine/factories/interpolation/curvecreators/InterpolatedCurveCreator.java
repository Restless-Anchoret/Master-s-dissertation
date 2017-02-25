package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.rendering.algebraic.common.AlgebraicObject;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import java.util.List;

public interface InterpolatedCurveCreator<I, O extends AlgebraicObject<O>, P> {

    DoubleFunction<O> interpolateCurve(List<I> inputList, P parameters, int degree);
    
}