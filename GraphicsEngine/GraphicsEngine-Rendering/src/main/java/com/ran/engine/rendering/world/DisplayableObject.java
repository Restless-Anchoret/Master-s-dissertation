package com.ran.engine.rendering.world;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.awt.*;
import java.util.List;

public class DisplayableObject {

    public static final Color DEFAULT_FIGURE_COLOR = Color.BLACK;
    public static final float DEFAULT_EDGE_PAINT_WIDTH = 1.0f;
    public static final int DEFAULT_VERTICE_PAINT_RADIUS = 2;
    
    private final Figure figure;
    private List<ThreeDoubleVector> currentFigureVertices = null;
    private Color color;
    private float edgePaintWidth;
    private int verticePaintRadius;
    private boolean visible;

    private final DoubleFunction<Quaternion> animationFunction;
    private double currentAnimationTime = 0;
    private ThreeDoubleVector offset;
    private Orientation currentOrientation;
    private boolean animationTurnedOn = false;
    private final boolean animationCyclic;

    public DisplayableObject(Figure figure, Color color, float edgePaintWidth,
                             int verticePaintRadius, boolean visible,
                             DoubleFunction<Quaternion> animationFunction,
                             ThreeDoubleVector offset, boolean animationCyclic) {
        this.figure = figure;
        this.color = color;
        this.edgePaintWidth = edgePaintWidth;
        this.verticePaintRadius = verticePaintRadius;
        this.visible = visible;
        this.animationFunction = animationFunction;
        this.offset = offset;
        this.animationCyclic = animationCyclic;
        this.currentOrientation = new Orientation(offset, animationFunction.apply(currentAnimationTime));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getEdgePaintWidth() {
        return edgePaintWidth;
    }

    public void setEdgePaintWidth(float edgePaintWidth) {
        this.edgePaintWidth = edgePaintWidth;
    }

    public int getVerticePaintRadius() {
        return verticePaintRadius;
    }

    public void setVerticePaintRadius(int verticePaintRadius) {
        this.verticePaintRadius = verticePaintRadius;
    }

    public Figure getFigure() {
        return figure;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public DoubleFunction<Quaternion> getAnimationFunction() {
        return animationFunction;
    }

    public double getCurrentAnimationTime() {
        return currentAnimationTime;
    }

    public ThreeDoubleVector getOffset() {
        return offset;
    }

    public void setOffset(ThreeDoubleVector offset) {
        this.offset = offset;
    }

    public boolean isAnimationTurnedOn() {
        return animationTurnedOn;
    }

    public void setAnimationTurnedOn(boolean animationTurnedOn) {
        this.animationTurnedOn = animationTurnedOn;
        currentAnimationTime %= animationFunction.getMaxParameterValue();
    }

    public boolean isAnimationCyclic() {
        return animationCyclic;
    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
        this.currentFigureVertices = null;
    }

    public List<ThreeDoubleVector> getCurrentFigureVertices() {
        updateCurrentFigureVertices();
        return currentFigureVertices;
    }
    
    public void updateCurrentFigureVertices() {
        if (currentFigureVertices != null) {
            return;
        }
        currentFigureVertices = OrientationMapper.getInstance().orientVertices(figure.getVertices(), currentOrientation);
    }

    public void updateAnimationForDeltaTime(double deltaTime) {
        if (!animationTurnedOn) {
            return;
        }
        currentAnimationTime += deltaTime;
        if (currentAnimationTime > animationFunction.getMaxParameterValue()) {
            if (animationCyclic) {
                currentAnimationTime %= animationFunction.getMaxParameterValue();
            } else {
                currentAnimationTime = animationFunction.getMaxParameterValue();
                animationTurnedOn = false;
            }
        }
        setCurrentOrientation(new Orientation(offset, animationFunction.apply(currentAnimationTime)));
    }
    
}