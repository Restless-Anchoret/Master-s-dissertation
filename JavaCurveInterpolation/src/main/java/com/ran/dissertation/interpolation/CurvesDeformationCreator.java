package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import java.util.function.BiFunction;

public class CurvesDeformationCreator {

    private static final CurvesDeformationCreator INSTANCE = new CurvesDeformationCreator();
    
    public static CurvesDeformationCreator getInstance() {
        return INSTANCE;
    }
    
    private CurvesDeformationCreator() { }
    
    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree) {
        return deformCurves(firstCurve, secondCurve, degree,
                GroupMultiplicationOperationFactory.getMultiplicationOperation());
    }
    
    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree,
            BiFunction<DoubleFunction<T>, DoubleFunction<T>, DoubleFunction<T>> groupMultiplicationOperation) {
        if (!firstCurve.apply(0.0).equals(secondCurve.apply(0.0))) {
            throw new AlgebraicException("Start and end points of curves must coincide for curves deformation");
        }
        DoubleFunction<SingleDouble> smoothingPolynom = PolynomsCreator.getInstance().createSmoothingPolynom(degree);
        DoubleFunction<SingleDouble> tauMinus = new DoubleFunction<>(
                point -> new SingleDouble((1.0 - smoothingPolynom.apply(point).getValue()) * point), 0.0, 1.0);
        DoubleFunction<SingleDouble> tauPlus = new DoubleFunction<>(
                point -> new SingleDouble(smoothingPolynom.apply(point).getValue() * point), 0.0, 1.0);
        return groupMultiplicationOperation.apply(secondCurve.superposition(tauPlus), firstCurve.superposition(tauMinus));
    }
    
}