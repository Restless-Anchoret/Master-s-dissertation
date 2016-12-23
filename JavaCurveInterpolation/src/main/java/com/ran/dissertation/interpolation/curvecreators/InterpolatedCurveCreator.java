package com.ran.dissertation.interpolation.curvecreators;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import java.util.List;

public interface InterpolatedCurveCreator<I, O extends AlgebraicObject<O>, P> {

    DoubleFunction<O> interpolateCurve(List<I> inputList, P parameters, int degree);
    
}