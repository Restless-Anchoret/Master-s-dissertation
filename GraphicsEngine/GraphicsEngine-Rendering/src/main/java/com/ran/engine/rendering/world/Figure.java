package com.ran.engine.rendering.world;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import java.util.Collections;
import java.util.List;

public class Figure {
    
    private final List<ThreeDoubleVector> vertices;
    private final List<Pair<Integer, Integer>> figureEdges;

    public Figure(List<ThreeDoubleVector> vertices, List<Pair<Integer, Integer>> figureEdges) {
        this.vertices = vertices;
        this.figureEdges = figureEdges;
    }

    public List<ThreeDoubleVector> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<Pair<Integer, Integer>> getFigureEdges() {
        return Collections.unmodifiableList(figureEdges);
    }

    @Override
    public String toString() {
        return "Figure{" + "vertices=" + vertices + ","
                + "figureEdges=" + figureEdges + '}';
    }
    
}