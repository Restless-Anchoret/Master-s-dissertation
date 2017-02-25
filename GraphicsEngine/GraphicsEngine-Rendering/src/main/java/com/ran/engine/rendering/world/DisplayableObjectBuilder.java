package com.ran.engine.rendering.world;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.quaternion.Quaternion;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.awt.Color;

public class DisplayableObjectBuilder {

    private final Figure figure;
    private final DoubleFunction<Quaternion> animationFunction;
    private ThreeDoubleVector offset = ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR;
    private Color color = DisplayableObject.DEFAULT_FIGURE_COLOR;
    private float edgePaintWidth = DisplayableObject.DEFAULT_EDGE_PAINT_WIDTH;
    private int verticePaintRadius = DisplayableObject.DEFAULT_VERTICE_PAINT_RADIUS;
    private boolean visible = true;
    private boolean animationCyclic = true;

    public DisplayableObjectBuilder(Figure figure, DoubleFunction<Quaternion> animationFunction) {
        this.figure = figure;
        this.animationFunction = animationFunction;
    }

    public DisplayableObjectBuilder setOffset(ThreeDoubleVector offset) {
        this.offset = offset;
        return this;
    }

    public DisplayableObjectBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public DisplayableObjectBuilder setEdgePaintWidth(float edgePaintWidth) {
        this.edgePaintWidth = edgePaintWidth;
        return this;
    }

    public DisplayableObjectBuilder setVerticePaintRadius(int verticePaintRadius) {
        this.verticePaintRadius = verticePaintRadius;
        return this;
    }

    public DisplayableObjectBuilder setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public DisplayableObjectBuilder setAnimationCyclic(boolean animationCyclic) {
        this.animationCyclic = animationCyclic;
        return this;
    }

    public DisplayableObject build() {
        return new DisplayableObject(figure, color, edgePaintWidth, verticePaintRadius, visible,
                animationFunction, offset, animationCyclic);
    }

}
