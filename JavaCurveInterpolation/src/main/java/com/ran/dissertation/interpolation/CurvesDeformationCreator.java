package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;

public class CurvesDeformationCreator {

    private static final CurvesDeformationCreator INSTANCE = new CurvesDeformationCreator();
    
    public static CurvesDeformationCreator getInstance() {
        return INSTANCE;
    }
    
    private CurvesDeformationCreator() { }
    
    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree) {
        DoubleFunction<SingleDouble> smoothingPolynom = PolynomsCreator.getInstance().createSmoothingPolynom(degree);
        DoubleFunction<SingleDouble> tauMinus = new DoubleFunction<>(
                point -> new SingleDouble((1.0 - smoothingPolynom.apply(point).getValue()) * point), 0.0, 1.0);
        DoubleFunction<SingleDouble> tauPlus = new DoubleFunction<>(
                point -> new SingleDouble(smoothingPolynom.apply(point).getValue() * point), 0.0, 1.0);
        return secondCurve.superposition(tauPlus).multiply(firstCurve.superposition(tauMinus));
    }
    
}