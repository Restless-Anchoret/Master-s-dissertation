package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.factories.util.GroupMultiplicationOperationFactory;
import com.ran.engine.algebra.common.AlgebraicObject;
import com.ran.engine.algebra.exception.AlgebraicException;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.SingleDouble;

import java.util.function.BiFunction;

public class CurvesSmoothingCreator {

    private static final CurvesSmoothingCreator INSTANCE = new CurvesSmoothingCreator();

    public static CurvesSmoothingCreator getInstance() {
        return INSTANCE;
    }

    public <T extends AlgebraicObject<T>> DoubleFunction<T> smoothCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree) {
        return smoothCurves(firstCurve, secondCurve, degree,
                GroupMultiplicationOperationFactory.getMultiplicationOperation());
    }

    public <T extends AlgebraicObject<T>> DoubleFunction<T> smoothCurves(
            DoubleFunction<T> firstCurve, DoubleFunction<T> secondCurve, int degree,
            BiFunction<DoubleFunction<T>, DoubleFunction<T>, DoubleFunction<T>> groupMultiplicationOperation) {
        if (!firstCurve.apply(0.0).equals(secondCurve.apply(0.0))) {
            throw new AlgebraicException("Start points of curves must coincide for curves deformation");
        }
        DoubleFunction<SingleDouble> smoothingPolynom = PolynomsCreator.getInstance().createSmoothingPolynom(degree);
        DoubleFunction<SingleDouble> sigmaMinus = new DoubleFunction<>(
                point -> new SingleDouble((1.0 - smoothingPolynom.apply(point).getValue()) * (1.0 - point)), 0.0, 1.0);
        DoubleFunction<SingleDouble> sigmaPlus = new DoubleFunction<>(
                point -> new SingleDouble(smoothingPolynom.apply(point).getValue() * point), 0.0, 1.0);
        return groupMultiplicationOperation.apply(secondCurve.superposition(sigmaPlus), firstCurve.superposition(sigmaMinus));
    }

}