package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.exception.AlgebraicException;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.SingleDouble;
import com.ran.engine.factories.util.GroupMultiplicationOperationFactory;
import com.ran.engine.algebra.common.AlgebraicObject;

import java.util.function.BiFunction;

public class CurvesDeformationCreator {

    private static final CurvesDeformationCreator INSTANCE = new CurvesDeformationCreator();
    
    public static CurvesDeformationCreator getInstance() {
        return INSTANCE;
    }
    
    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree) {
        return deformCurves(firstCurve, secondCurve, degree,
                GroupMultiplicationOperationFactory.getMultiplicationOperation());
    }
    
    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree,
            BiFunction<DoubleFunction<T>, DoubleFunction<T>, DoubleFunction<T>> groupMultiplicationOperation) {
        if (!firstCurve.apply(0.0).equals(secondCurve.apply(0.0))) {
            throw new AlgebraicException("Start points of curves must coincide for curves deformation");
        }
        DoubleFunction<SingleDouble> smoothingPolynom = PolynomsCreator.getInstance().createSmoothingPolynom(degree);
        DoubleFunction<SingleDouble> tauMinus = new DoubleFunction<>(
                point -> new SingleDouble((1.0 - smoothingPolynom.apply(point).getValue()) * point), 0.0, 1.0);
        DoubleFunction<SingleDouble> tauPlus = new DoubleFunction<>(
                point -> new SingleDouble(smoothingPolynom.apply(point).getValue() * point), 0.0, 1.0);
        return groupMultiplicationOperation.apply(secondCurve.superposition(tauPlus), firstCurve.superposition(tauMinus));
    }

    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurvesWithCommonEnd(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree) {
        return deformCurvesWithCommonEnd(firstCurve, secondCurve, degree,
                GroupMultiplicationOperationFactory.getMultiplicationOperation());
    }

    public <T extends AlgebraicObject<T>> DoubleFunction<T> deformCurvesWithCommonEnd(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree,
            BiFunction<DoubleFunction<T>, DoubleFunction<T>, DoubleFunction<T>> groupMultiplicationOperation) {
        if (!firstCurve.apply(0.0).equals(secondCurve.apply(0.0))) {
            throw new AlgebraicException("Start points of curves must coincide for curves deformation");
        }
        DoubleFunction<SingleDouble> smoothingPolynom = PolynomsCreator.getInstance().createSmoothingPolynom(degree);
        DoubleFunction<SingleDouble> tauMinus = new DoubleFunction<>(
                point -> new SingleDouble((1.0 - smoothingPolynom.apply(point).getValue()) * point), 0.0, 1.0);
        DoubleFunction<SingleDouble> tauMinusReversed = tauMinus.reversed();
        DoubleFunction<SingleDouble> tauPlus = new DoubleFunction<>(
                point -> new SingleDouble(smoothingPolynom.apply(point).getValue() * point), 0.0, 1.0);
        DoubleFunction<SingleDouble> tauPlusReversed = tauPlus.reversed();
        DoubleFunction<SingleDouble> tauPlusFixed = new DoubleFunction<SingleDouble>(
                point -> new SingleDouble(1.0 - tauPlusReversed.apply(point).getValue()), 0.0, 1.0);
        return groupMultiplicationOperation.apply(secondCurve.superposition(tauMinusReversed), firstCurve.superposition(tauPlusFixed));
    }
    
}