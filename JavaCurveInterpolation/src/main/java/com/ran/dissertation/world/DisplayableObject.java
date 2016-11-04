package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.awt.Color;
import java.util.List;

public class DisplayableObject {

    private static final Color DEFAULT_FIGURE_COLOR = Color.BLACK;
    private static final int DEFAULT_EDGE_PAINT_WIDTH = 1;
    private static final int DEFAULT_VERTICE_PAINT_RADIUS = 2;
    
    private final Figure figure;
    private Orientation orientation;
    private List<ThreeDoubleVector> currentFigureVertices = null;
    private final Color color;
    private final int edgePaintWidth;
    private final int verticePaintRadius;

    public DisplayableObject(Figure figure) {
        this(figure, Orientation.INITIAL_ORIENTATION, DEFAULT_FIGURE_COLOR,
                DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public DisplayableObject(Figure figure, Orientation orientation) {
        this(figure, orientation, DEFAULT_FIGURE_COLOR, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public DisplayableObject(Figure figure, Orientation orientation, Color color) {
        this(figure, orientation, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public DisplayableObject(Figure figure, Orientation orientation, Color color, int edgePaintWidth, int verticePaintRadius) {
        this.figure = figure;
        this.orientation = orientation;
        this.color = color;
        this.edgePaintWidth = edgePaintWidth;
        this.verticePaintRadius = verticePaintRadius;
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

    public Figure getFigure() {
        return figure;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    // todo: maybe this method should be synchronized
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        this.currentFigureVertices = null;
    }

    // todo: maybe this method should be synchronized
    public List<ThreeDoubleVector> getCurrentFigureVertices() {
        updateCurrentFigureVertices();
        return currentFigureVertices;
    }
    
    public void updateCurrentFigureVertices() {
        if (currentFigureVertices != null) {
            return;
        }
        currentFigureVertices = OrientationMapper.getInstance().orientVertices(figure.getVertices(), orientation);
    }
    
}