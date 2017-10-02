package com.ran.engine.rendering.world;

import java.awt.*;
import java.util.Collections;

public class WorldObjectPartBuilder {

    private static final Figure EMPTY_FIGURE = new Figure(Collections.emptyList(), Collections.emptyList());
    private static final Color DEFAULT_FIGURE_COLOR = Color.BLACK;
    private static final float DEFAULT_EDGE_PAINT_WIDTH = 1.0f;
    private static final int DEFAULT_VERTICE_PAINT_RADIUS = 2;

    private Figure figure = EMPTY_FIGURE;
    private Color color = DEFAULT_FIGURE_COLOR;
    private float edgePaintWidth = DEFAULT_EDGE_PAINT_WIDTH;
    private int verticePaintRadius = DEFAULT_VERTICE_PAINT_RADIUS;

    public WorldObjectPartBuilder setFigure(Figure figure) {
        this.figure = figure;
        return this;
    }

    public WorldObjectPartBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public WorldObjectPartBuilder setEdgePaintWidth(float edgePaintWidth) {
        this.edgePaintWidth = edgePaintWidth;
        return this;
    }

    public WorldObjectPartBuilder setVerticePaintRadius(int verticePaintRadius) {
        this.verticePaintRadius = verticePaintRadius;
        return this;
    }

    public WorldObjectPart build() {
        return new WorldObjectPart(
                this.figure,
                this.color,
                this.edgePaintWidth,
                this.verticePaintRadius);
    }

}
