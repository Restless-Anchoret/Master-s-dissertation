package com.ran.engine.factories.objects;

import com.ran.engine.factories.interpolation.ArcsBuilder;
import com.ran.engine.factories.interpolation.ParabolaBuilder;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.SingleDouble;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
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
        for (DoubleFunction<DoubleMatrix> rotation:
                Arrays.asList(arcBuilderResult.getFirstRotation(), arcBuilderResult.getSecondRotation())) {
            ThreeDoubleVector rotationStart = (rotation == arcBuilderResult.getFirstRotation() ? firstPoint : secondPoint);
            for (int i = 1; i <= halfSegments; i++) {
                double parameter = (double)i / (double)halfSegments;
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

}
