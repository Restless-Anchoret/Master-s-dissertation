package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.exception.CreationException;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.algebraic.vector.TwoIntVector;
import com.sun.prism.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Figure {
    
    private static final Color DEFAULT_FIGURE_COLOR = Color.BLACK;
    private static final int DEFAULT_EDGE_PAINT_WIDTH = 1;
    private static final int DEFAULT_VERTICE_PAINT_RADIUS = 3;
    
    public static Figure createNewFigure(List<ThreeDoubleVector> verticesList, List<TwoIntVector> verticePairsList) {
        return createNewFigure(verticesList, verticePairsList, DEFAULT_FIGURE_COLOR, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public static Figure createNewFigure(List<ThreeDoubleVector> verticesList, List<TwoIntVector> verticePairsList,
            Color color) {
        return createNewFigure(verticesList, verticePairsList, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public static Figure createNewFigure(List<ThreeDoubleVector> verticesList, List<TwoIntVector> verticePairsList,
            Color color, int edgePaintWidth, int verticePaintRadius) {
        List<FigureEdge> figureEdgesList = new ArrayList<>(verticePairsList.size());
        for (TwoIntVector intPair: verticePairsList) {
            int first = intPair.getX();
            int second = intPair.getY();
            if (first < 0 || first >= verticesList.size() ||
                    second < 0 || second >= verticesList.size()) {
                throw new CreationException("Invalid vertice pairs list");
            }
            figureEdgesList.add(new FigureEdge(verticesList.get(first), verticesList.get(second)));
        }
        return new Figure(verticesList, figureEdgesList, color, edgePaintWidth, verticePaintRadius);
    }
    
    private final List<ThreeDoubleVector> vertices;
    private final List<FigureEdge> figureEdges;
    private final Color color;
    private final int edgePaintWidth;
    private final int verticePaintRadius;

    private Figure(List<ThreeDoubleVector> vertices, List<FigureEdge> figureEdges,
            Color color, int edgePaintWidth, int verticePaintRadius) {
        this.vertices = vertices;
        this.figureEdges = figureEdges;
        this.color = color;
        this.edgePaintWidth = edgePaintWidth;
        this.verticePaintRadius = verticePaintRadius;
    }

    public List<ThreeDoubleVector> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<FigureEdge> getFigureEdges() {
        return Collections.unmodifiableList(figureEdges);
    }

    public Color getColor() {
        return color;
    }

    public int getEdgePaintWidth() {
        return edgePaintWidth;
    }

    public int getVerticePaintRadius() {
        return verticePaintRadius;
    }

    @Override
    public String toString() {
        return "Figure{" + "vertices=" + vertices + ","
                + "figureEdges=" + figureEdges +
                ", color=" + color +
                ", edgePaintWidth=" + edgePaintWidth +
                ", verticePaintRadius=" + verticePaintRadius + '}';
    }
    
}