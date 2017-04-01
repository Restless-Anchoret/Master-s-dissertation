package com.ran.engine.factories.objects;

import com.ran.engine.factories.interpolation.curvecreators.InterpolatedPlainCurveCreator;
import com.ran.engine.factories.interpolation.curvecreators.InterpolatedSphereCurveCreator;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.SingleDouble;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.Figure;

import java.util.ArrayList;
import java.util.List;

public class InterpolatedFiguresFactory extends FigureFactory {

    private static final InterpolatedFiguresFactory INSTANCE = new InterpolatedFiguresFactory();

    public static InterpolatedFiguresFactory getInstance() {
        return INSTANCE;
    }

    public Figure makeInterpolatedCurve(List<ThreeDoubleVector> verticesForInterpolation, int degree, int segments) {
        DoubleFunction<ThreeDoubleVector> interpolatedCurve =
                new InterpolatedSphereCurveCreator().interpolateCurve(verticesForInterpolation, new Pair<>(0.0, 1.0), degree);
        double parameterStart = interpolatedCurve.getMinParameterValue();
        double parameterEnd = interpolatedCurve.getMaxParameterValue();
        List<ThreeDoubleVector> vertices = interpolatedCurve.applyForGrid(parameterStart, parameterEnd, segments);
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            figureEdges.add(new Pair<>(i, i + 1));
        }
        return new Figure(vertices, figureEdges);
    }

    public Figure makeSpline(List<Pair<Double, Double>> pointsWithValues, int degree, int segments,
                             CoordinatesConverter coordinatesConverter) {
        DoubleFunction<SingleDouble> splineFunction = new InterpolatedPlainCurveCreator()
                .interpolateCurve(pointsWithValues, null, degree);
        return makeFigureByFunction(splineFunction, segments, coordinatesConverter);
    }



}