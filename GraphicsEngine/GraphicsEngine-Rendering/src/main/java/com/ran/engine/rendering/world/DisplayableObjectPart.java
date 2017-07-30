package com.ran.engine.rendering.world;

import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.awt.*;
import java.util.List;

public class DisplayableObjectPart {

    public static final Color DEFAULT_FIGURE_COLOR = Color.BLACK;
    public static final float DEFAULT_EDGE_PAINT_WIDTH = 1.0f;
    public static final int DEFAULT_VERTICE_PAINT_RADIUS = 2;

    private Figure figure;
    private List<ThreeDoubleVector> currentFigureVertices = null;
    private Color color;
    private float edgePaintWidth;
    private int verticePaintRadius;

    public DisplayableObjectPart(Figure figure, Color color, float edgePaintWidth, int verticePaintRadius) {
        this.figure = figure;
        this.color = color;
        this.edgePaintWidth = edgePaintWidth;
        this.verticePaintRadius = verticePaintRadius;
    }

    public DisplayableObjectPart(Figure figure, Color color) {
        this(figure, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public DisplayableObjectPart(Figure figure) {
        this(figure, DEFAULT_FIGURE_COLOR);
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

    //    public void updateCurrentFigureVertices(Orientation orientation) {
//        if (currentFigureVertices != null) {
//            return;
//        }
//        currentFigureVertices = OrientationMapper.getInstance().orientVertices(figure.getVertices(), orientation);
//    }

}
