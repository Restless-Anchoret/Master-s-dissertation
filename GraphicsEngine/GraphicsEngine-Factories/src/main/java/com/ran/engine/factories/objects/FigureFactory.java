package com.ran.engine.factories.objects;

import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.SingleDouble;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.rendering.world.Figure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FigureFactory {
    
    private static final FigureFactory INSTANCE = new FigureFactory();

    public static FigureFactory getInstance() {
        return INSTANCE;
    }
    
    public Figure makeFigureWithOneVertice(ThreeDoubleVector vertice) {
        return new Figure(Collections.singletonList(vertice), Collections.emptyList());
    }
    
    public Figure makeGrid(int xCellsQuantity, int yCellsQuantity, int zCellsQuantity,
            double xCellWidth, double yCellWidth, double zCellWidth) {
        return makeGrid(xCellsQuantity, yCellsQuantity, zCellsQuantity, xCellWidth, yCellWidth, zCellWidth,
                ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR);
    }
    
    public Figure makeGrid(int xCellsQuantity, int yCellsQuantity, int zCellsQuantity,
            double xCellWidth, double yCellWidth, double zCellWidth, ThreeDoubleVector center) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        
        double xHalfSize = xCellWidth * xCellsQuantity / 2.0;
        double yHalfSize = yCellWidth * yCellsQuantity / 2.0;
        double zHalfSize = zCellWidth * zCellsQuantity / 2.0;
        
        int[][][] indexes = new int[xCellsQuantity + 1][yCellsQuantity + 1][zCellsQuantity + 1];
        int currentIndex = 0;
        for (int i = 0; i <= xCellsQuantity; i++) {
            for (int j = 0; j <= yCellsQuantity; j++) {
                for (int k = 0; k <= zCellsQuantity; k++) {
                    double x = i * xCellWidth - xHalfSize + center.getX();
                    double y = j * yCellWidth - yHalfSize + center.getY();
                    double z = k * zCellWidth - zHalfSize + center.getZ();
                    vertices.add(new ThreeDoubleVector(x, y, z));
                    indexes[i][j][k] = currentIndex;
                    if (i > 0) {
                        figureEdges.add(new Pair<>(indexes[i - 1][j][k], currentIndex));
                    }
                    if (j > 0) {
                        figureEdges.add(new Pair<>(indexes[i][j - 1][k], currentIndex));
                    }
                    if (k > 0) {
                        figureEdges.add(new Pair<>(indexes[i][j][k - 1], currentIndex));
                    }
                    currentIndex++;
                }
            }
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makePlainGrid(int xCellsQuantity, int yCellsQuantity,
            double xCellWidth, double yCellWidth) {
        return makeGrid(xCellsQuantity, yCellsQuantity, 0, xCellWidth, yCellWidth, 0.0);
    }

    public Figure makeVerticalPlainGrid(int xCellsQuantity, int zCellsQuantity,
                                        double xCellWidth, double zCellWidth) {
        return makeGrid(xCellsQuantity, 0, zCellsQuantity, xCellWidth, 0, zCellWidth);
    }
    
    public Figure makeParallelepiped(double xWidth, double yWidth, double zWidth) {
        return makeGrid(1, 1, 1, xWidth, yWidth, zWidth);
    }
    
    public Figure makeCube(double width) {
        return makeParallelepiped(width, width, width);
    }
    
    public Figure makeOval(ThreeDoubleVector center, ThreeDoubleVector firstAxis,
            ThreeDoubleVector secondAxis, int segments) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        for (int i = 0; i < segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            ThreeDoubleVector vertice = firstAxis.multiply(Math.sin(angle))
                    .add(secondAxis.multiply(Math.cos(angle)))
                    .add(center);
            vertices.add(vertice);
            figureEdges.add(new Pair<>(i, (i + 1) % segments));
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makeMultiFigure(List<Figure> figures) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        int currentVerticesQuantity = 0;
        for (Figure figure: figures) {
            vertices.addAll(figure.getVertices());
            for (Pair<Integer, Integer> figureEdge: figure.getFigureEdges()) {
                figureEdges.add(new Pair<>(figureEdge.getLeft() + currentVerticesQuantity,
                        figureEdge.getRight() + currentVerticesQuantity));
            }
            currentVerticesQuantity += figure.getVertices().size();
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makeSphereFrame(ThreeDoubleVector center, double radius, int segments) {
        ThreeDoubleVector xAxis = new ThreeDoubleVector(radius, 0.0, 0.0);
        ThreeDoubleVector yAxis = new ThreeDoubleVector(0.0, radius, 0.0);
        ThreeDoubleVector zAxis = new ThreeDoubleVector(0.0, 0.0, radius);
        
        Figure xyCircle = makeOval(center, xAxis, yAxis, segments);
        Figure xzCircle = makeOval(center, xAxis, zAxis, segments);
        Figure yzCircle = makeOval(center, yAxis, zAxis, segments);
        
        return makeMultiFigure(Arrays.asList(xyCircle, xzCircle, yzCircle));
    }
    
    public Figure makeGlobe(ThreeDoubleVector center, double radius, int meridiansHalfQuantity) {
        ThreeDoubleVector zAxis = new ThreeDoubleVector(0.0, 0.0, radius);
        List<Figure> circles = new ArrayList<>(meridiansHalfQuantity);
        for (int i = 0; i < meridiansHalfQuantity; i++) {
            double angle = Math.PI * i / meridiansHalfQuantity;
            ThreeDoubleVector meridianAxis = new ThreeDoubleVector(
                    radius * Math.sin(angle), radius * Math.cos(angle), 0.0);
            circles.add(makeOval(center, zAxis, meridianAxis, meridiansHalfQuantity * 2));
        }
        return makeMultiFigure(circles);
    }

    public Figure makeFigureByPoints(List<TwoDoubleVector> points,
                                     CoordinatesConverter coordinatesConverter) {
        List<ThreeDoubleVector> vertices = points.stream()
                .map(point -> coordinatesConverter.convert(point.getX(), point.getY()))
                .collect(Collectors.toList());
        List<Pair<Integer, Integer>> figureEdges = Collections.emptyList();
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makeFigureByPointsWithValues(List<Pair<Double, Double>> pointsWithValues,
            CoordinatesConverter coordinatesConverter) {
        List<TwoDoubleVector> points = pointsWithValues.stream()
                .map(pointWithValue -> new TwoDoubleVector(pointWithValue.getLeft(), pointWithValue.getRight()))
                .collect(Collectors.toList());
        return makeFigureByPoints(points, coordinatesConverter);
    }
    
    public Figure makeFigureByFunction(DoubleFunction<SingleDouble> function,
            int segments, CoordinatesConverter coordinatesConverter) {
        List<ThreeDoubleVector> vertices = new ArrayList<>(segments + 1);
        double parameterStart = function.getMinParameterValue();
        double parameterEnd = function.getMaxParameterValue();
        for (int i = 0; i <= segments; i++) {
            double point = parameterStart + (parameterEnd - parameterStart) * i / segments;
            double value = function.apply(point).getValue();
            vertices.add(coordinatesConverter.convert(point, value));
        }
        return new Figure(vertices, makeEdgesSimpleList(segments));
    }

    public List<Pair<Integer, Integer>> makeEdgesSimpleList(int segments) {
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            figureEdges.add(new Pair<>(i, i + 1));
        }
        return figureEdges;
    }

    public Figure makeAeroplane(double scale) {
        List<ThreeDoubleVector> vertices = Arrays.asList(
                new ThreeDoubleVector(scale * -0.25, 0, scale * 0.5),
                new ThreeDoubleVector(0, 0, 0),
                new ThreeDoubleVector(scale * 0.25, 0, scale * 0.5),
                new ThreeDoubleVector(scale * 0.25, 0, scale * 2.5),
                new ThreeDoubleVector(0, 0, scale * 3),
                new ThreeDoubleVector(scale * -0.25, 0, scale * 2.5),
                new ThreeDoubleVector(scale * -0.25, 0, scale * 2.5),
                new ThreeDoubleVector(scale * -0.75, 0, scale * 2.25),
                new ThreeDoubleVector(scale * -0.75, 0, scale * 1.75),
                new ThreeDoubleVector(scale * -0.25, 0, scale * 2),
                new ThreeDoubleVector(scale * 0.25, 0, scale * 2.5),
                new ThreeDoubleVector(scale * 0.75, 0, scale * 2.25),
                new ThreeDoubleVector(scale * 0.75, 0, scale * 1.75),
                new ThreeDoubleVector(scale * 0.25, 0, scale * 2)
        );
        List<Pair<Integer, Integer>> figureEdges = Arrays.asList(
                new Pair<>(0, 1),
                new Pair<>(1, 2),
                new Pair<>(2, 3),
                new Pair<>(3, 4),
                new Pair<>(4, 5),
                new Pair<>(5, 0),
                new Pair<>(6, 7),
                new Pair<>(7, 8),
                new Pair<>(8, 9),
                new Pair<>(10, 11),
                new Pair<>(11, 12),
                new Pair<>(12, 13)
        );
        return new Figure(vertices, figureEdges);
    }

}