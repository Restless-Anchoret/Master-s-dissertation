package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.util.Collections;
import java.util.List;

public class Figure {
    
    private final List<ThreeDoubleVector> vertices;
    private final List<FigureEdge> figureEdges;

    public Figure(List<ThreeDoubleVector> vertices, List<FigureEdge> figureEdges) {
        this.vertices = vertices;
        this.figureEdges = figureEdges;
    }

    public List<ThreeDoubleVector> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<FigureEdge> getFigureEdges() {
        return Collections.unmodifiableList(figureEdges);
    }

    @Override
    public String toString() {
        return "Figure{" + "vertices=" + vertices + ","
                + "figureEdges=" + figureEdges + '}';
    }
    
}