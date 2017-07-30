package com.ran.engine.factories.objects;

import com.ran.engine.factories.interpolation.curvecreators.*;
import com.ran.engine.factories.interpolation.input.EmptyInputParameters;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.SingleDouble;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.rendering.world.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class InterpolatedFiguresFactory extends FigureFactory {

    private static final Logger LOG = LoggerFactory.getLogger(InterpolatedFiguresFactory.class);

    private static final InterpolatedFiguresFactory INSTANCE = new InterpolatedFiguresFactory();

    public static InterpolatedFiguresFactory getInstance() {
        return INSTANCE;
    }

    public Figure makeInterpolatedCurve(List<ThreeDoubleVector> verticesForInterpolation, int degree, int segments) {
        DoubleFunction<ThreeDoubleVector> interpolatedCurve = SphereByPointsCurveCreator.getInstance()
                .interpolateCurve(verticesForInterpolation, new SimpleInputParameters(0.0, 1.0), degree);
        double parameterStart = interpolatedCurve.getMinParameterValue();
        double parameterEnd = interpolatedCurve.getMaxParameterValue();
        List<ThreeDoubleVector> vertices = interpolatedCurve.applyForGrid(parameterStart, parameterEnd, segments);
        List<Pair<Integer, Integer>> figureEdges = makeEdgesSimpleList(segments);
        return new Figure(vertices, figureEdges);
    }

    public Figure makeBezierCurveByMiddlePoints(List<ThreeDoubleVector> verticesForInterpolation, int degree, int segments) {
        DoubleFunction<ThreeDoubleVector> interpolatedCurve = SphereBezierCurveCreator.getInstance()
                .interpolateCurve(verticesForInterpolation, new SimpleInputParameters(0.0, 1.0), degree);
        double parameterStart = interpolatedCurve.getMinParameterValue();
        double parameterEnd = interpolatedCurve.getMaxParameterValue();
        List<ThreeDoubleVector> vertices = interpolatedCurve.applyForGrid(parameterStart, parameterEnd, segments);
        List<Pair<Integer, Integer>> figureEdges = makeEdgesSimpleList(segments);
        return new Figure(vertices, figureEdges);
    }

    public Figure makeInterpolatedCurveByTangentAngles(List<Pair<ThreeDoubleVector, Double>> verticesWithTangentAngles,
                                                          int degree, int segments) {
        LOG.trace("verticesWithTangentAngles = {}, degree = {}, segments = {}",
                verticesWithTangentAngles, degree, segments);
        DoubleFunction<ThreeDoubleVector> interpolatedCurve = SphereByTangentAnglesCurveCreator.getInstance()
                .interpolateCurve(verticesWithTangentAngles, new SimpleInputParameters(0.0, 1.0), degree);
        double parameterStart = interpolatedCurve.getMinParameterValue();
        double parameterEnd = interpolatedCurve.getMaxParameterValue();
        List<ThreeDoubleVector> vertices = interpolatedCurve.applyForGrid(parameterStart, parameterEnd, segments);
        List<Pair<Integer, Integer>> figureEdges = makeEdgesSimpleList(segments);
        Figure figure = new Figure(vertices, figureEdges);
        LOG.trace("figure = {}", figure);
        return figure;
    }

    public Figure makeSpline(List<Pair<Double, Double>> pointsWithValues, int degree, int segments,
                             CoordinatesConverter coordinatesConverter) {
        DoubleFunction<SingleDouble> splineFunction = new OneArgumentFunctionCurveCreator()
                .interpolateCurve(pointsWithValues, EmptyInputParameters.getInstance(), degree);
        return makeFigureByFunction(splineFunction, segments, coordinatesConverter);
    }

    public Figure makePlaneInterpolatedCurveByPoints(List<TwoDoubleVector> verticesForInterpolation, int degree, int segments) {
        DoubleFunction<TwoDoubleVector> interpolatedCurve = PlaneByPointsCurveCreator.getInstance()
                .interpolateCurve(verticesForInterpolation, new SimpleInputParameters(0.0, 1.0), degree);
        double parameterStart = interpolatedCurve.getMinParameterValue();
        double parameterEnd = interpolatedCurve.getMaxParameterValue();
        List<ThreeDoubleVector> vertices = interpolatedCurve.applyForGrid(parameterStart, parameterEnd, segments)
                .stream().map(point -> new ThreeDoubleVector(point.getX(), 0.0, point.getY()))
                .collect(Collectors.toList());
        List<Pair<Integer, Integer>> figureEdges = makeEdgesSimpleList(segments);
        return new Figure(vertices, figureEdges);
    }

}