package com.ran.engine.rendering.world;

import com.ran.engine.algebra.function.DoubleFunction;

import java.awt.*;

public class StaticObject extends DisplayableObject {

    public StaticObject(Figure figure, Orientation orientation, Color color,
                        float edgePaintWidth, int verticePaintRadius, boolean visible) {
        super(figure, color, edgePaintWidth, verticePaintRadius, visible,
                new DoubleFunction<>(time -> orientation.getRotation()),
                orientation.getOffset(), false);
    }

    public StaticObject(Figure figure) {
        this(figure, Orientation.INITIAL_ORIENTATION, DEFAULT_FIGURE_COLOR,
                DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public StaticObject(Figure figure, Orientation orientation) {
        this(figure, orientation, DEFAULT_FIGURE_COLOR, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public StaticObject(Figure figure, Orientation orientation, Color color) {
        this(figure, orientation, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public StaticObject(Figure figure, Orientation orientation, Color color,
                             float edgePaintWidth, int verticePaintRadius) {
        this(figure, orientation, color, edgePaintWidth, verticePaintRadius, true);
    }

    @Override
    public void setAnimationTurnedOn(boolean animationTurnedOn) { }

    @Override
    public void updateAnimationForDeltaTime(double deltaTime) { }

}
