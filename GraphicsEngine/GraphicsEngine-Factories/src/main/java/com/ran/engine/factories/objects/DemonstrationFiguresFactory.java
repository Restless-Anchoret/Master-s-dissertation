package com.ran.engine.factories.objects;

import com.ran.engine.factories.interpolation.tools.*;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.SingleDouble;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;
import com.ran.engine.rendering.world.Figure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemonstrationFiguresFactory extends FigureFactory {

    private static final DemonstrationFiguresFactory INSTANCE = new DemonstrationFiguresFactory();

    public static DemonstrationFiguresFactory getInstance() {
        return INSTANCE;
    }

    public Figure makeParabolaByPoints(Pair<Double, Double> firstPoint,
                                       Pair<Double, Double> secondPoint, Pair<Double, Double> thirdPoint,
                                       int segments, CoordinatesConverter converter) {
        DoubleFunction<SingleDouble> parabolaFunction = ParabolaBuilder.getInstance()
                .buildParabolaByThreePoints(firstPoint, secondPoint, thirdPoint);
        return makeFigureByFunction(parabolaFunction, segments, converter);
    }

    public Figure makeFigureByParabolas(List<Pair<Double, Double>> pointsWithValues,
                                        int segmentsPerParabola, CoordinatesConverter converter) {
        List<Figure> figures = new ArrayList<>(pointsWithValues.size() - 2);
        for (int i = 0; i < pointsWithValues.size() - 2; i++) {
            figures.add(makeParabolaByPoints(pointsWithValues.get(i), pointsWithValues.get(i + 1),
                    pointsWithValues.get(i + 2), segmentsPerParabola, converter));
        }
        return makeMultiFigure(figures);
    }

    public Figure makeArcOnSphereByPoints(ThreeDoubleVector firstPoint,
                                          ThreeDoubleVector secondPoint, ThreeDoubleVector thirdPoint, int halfSegments) {
        ArcsBuilder.Result arcBuilderResult = ArcsBuilder.getInstance()
                .buildArcsBetweenVerticesOnSphere(firstPoint, secondPoint, thirdPoint);
        List<ThreeDoubleVector> vertices = new ArrayList<>(halfSegments * 2 + 1);
        vertices.add(firstPoint);
        for (DoubleFunction<DoubleMatrix> rotation :
                Arrays.asList(arcBuilderResult.getFirstRotation(), arcBuilderResult.getSecondRotation())) {
            ThreeDoubleVector rotationStart = (rotation == arcBuilderResult.getFirstRotation() ? firstPoint : secondPoint);
            for (int i = 1; i <= halfSegments; i++) {
                double parameter = (double) i / (double) halfSegments;
                DoubleMatrix currentRotation = rotation.apply(parameter);
                ThreeDoubleVector currentVertice = new ThreeDoubleVector(
                        currentRotation.multiply(rotationStart.getDoubleVector()));
                vertices.add(currentVertice);
            }
        }
        return new Figure(vertices, makeEdgesSimpleList(halfSegments * 2));
    }

    public Figure makeFigureByArcs(List<ThreeDoubleVector> vertices, int halfSegmentsPerArc) {
        List<Figure> figures = new ArrayList<>(vertices.size() - 2);
        for (int i = 0; i < vertices.size() - 2; i++) {
            figures.add(makeArcOnSphereByPoints(vertices.get(i), vertices.get(i + 1),
                    vertices.get(i + 2), halfSegmentsPerArc));
        }
        return makeMultiFigure(figures);
    }

    public Figure makeBigArcOnSphereByPoints(ThreeDoubleVector firstPoint,
                                             ThreeDoubleVector secondPoint, int segments) {
        BigArcsBuilder.Result bigArcBuilderResult = BigArcsBuilder.getInstance()
                .buildBigArcBetweenVerticesOnSphere(firstPoint, secondPoint);
        List<ThreeDoubleVector> vertices = new ArrayList<>(segments + 1);
        DoubleFunction<DoubleMatrix> rotation = bigArcBuilderResult.getRotation();
        for (int i = 0; i <= segments; i++) {
            double parameter = (double) i / (double) segments;
            DoubleMatrix currentRotation = rotation.apply(parameter);
            ThreeDoubleVector currentVertice = new ThreeDoubleVector(
                    currentRotation.multiply(firstPoint.getDoubleVector()));
            vertices.add(currentVertice);
        }
        return new Figure(vertices, makeEdgesSimpleList(segments));
    }

    public Figure makeFigureByBigArcs(List<ThreeDoubleVector> vertices, int segmentsPerArc) {
        List<Figure> figures = new ArrayList<>(vertices.size() - 1);
        for (int i = 0; i < vertices.size() - 1; i++) {
            figures.add(makeBigArcOnSphereByPoints(vertices.get(i), vertices.get(i + 1), segmentsPerArc));
        }
        return makeMultiFigure(figures);
    }

    public Figure makeTangentOnSphereByPoint(ThreeDoubleVector point, double tangentAngle, int halfSegments) {
        TangentBuilder.Result tangentBuilderResult = TangentBuilder.getInstance()
                .buildTangent(point, tangentAngle, Math.PI / 6.0, Math.PI / 6.0);
        List<ThreeDoubleVector> vertices = new ArrayList<>(2 * halfSegments + 1);
        for (int i = halfSegments; i > 0; i--) {
            double parameter = (double) i / (double) halfSegments;
            DoubleMatrix currentRotation = tangentBuilderResult.getBackRotation().apply(parameter);
            ThreeDoubleVector currentVertice = new ThreeDoubleVector(
                    currentRotation.multiply(point.getDoubleVector()));
            vertices.add(currentVertice);
        }
        for (int i = 0; i <= halfSegments; i++) {
            double parameter = (double) i / (double) halfSegments;
            DoubleMatrix currentRotation = tangentBuilderResult.getForwardRotation().apply(parameter);
            ThreeDoubleVector currentVertice = new ThreeDoubleVector(
                    currentRotation.multiply(point.getDoubleVector()));
            vertices.add(currentVertice);
        }
        return new Figure(vertices, makeEdgesSimpleList(2 * halfSegments));
    }

    public Figure makeCircleArcByPoints(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint,
                                        TwoDoubleVector thirdPoint, int halfSegments) {
        CoordinatesConverter converter = CoordinatesConverter.CONVERTER_TO_XZ;
        CircleArcsBuilder.Result arcBuilderResult = CircleArcsBuilder.getInstance()
                .buildCircle(firstPoint, secondPoint, thirdPoint);
        List<ThreeDoubleVector> vertices = new ArrayList<>(halfSegments * 2 + 1);
        vertices.add(converter.convert(firstPoint));
        for (DoubleFunction<TwoDoubleVector> arc :
                Arrays.asList(arcBuilderResult.getFirstArc(), arcBuilderResult.getSecondArc())) {
            for (int i = 1; i <= halfSegments; i++) {
                double parameter = (double) i / (double) halfSegments;
                TwoDoubleVector currentPoint = arc.apply(parameter);
                ThreeDoubleVector currentVertice = converter.convert(currentPoint);
                vertices.add(currentVertice);
            }
        }
        return new Figure(vertices, makeEdgesSimpleList(halfSegments * 2));
    }

    public Figure makeFigureByCircleArcs(List<TwoDoubleVector> vertices, int halfSegmentsPerArc) {
        List<Figure> figures = new ArrayList<>(vertices.size() - 2);
        for (int i = 0; i < vertices.size() - 2; i++) {
            figures.add(makeCircleArcByPoints(vertices.get(i), vertices.get(i + 1),
                    vertices.get(i + 2), halfSegmentsPerArc));
        }
        return makeMultiFigure(figures);
    }

}
