package com.ran.engine.rendering.world;

import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.awt.*;
import java.util.List;

public class WorldObjectPart {

    private Figure figure;
    private Color color;
    private float edgePaintWidth;
    private int verticePaintRadius;

    private List<ThreeDoubleVector> currentFigureVertices = null;

    protected WorldObjectPart(Figure figure, Color color, float edgePaintWidth, int verticePaintRadius) {
        this.figure = figure;
        this.color = color;
        this.edgePaintWidth = edgePaintWidth;
        this.verticePaintRadius = verticePaintRadius;
    }

    public Figure getFigure() {
        return figure;
    }

    public Color getColor() {
        return color;
    }

    public float getEdgePaintWidth() {
        return edgePaintWidth;
    }

    public int getVerticePaintRadius() {
        return verticePaintRadius;
    }

    public List<ThreeDoubleVector> getCurrentFigureVertices() {
        return currentFigureVertices;
    }

    public void setCurrentFigureVertices(List<ThreeDoubleVector> currentFigureVertices) {
        this.currentFigureVertices = currentFigureVertices;
    }

}
