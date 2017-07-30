package com.ran.engine.rendering.world;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class DisplayableObject {

    private List<DisplayableObjectPart> displayableObjectParts;
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
        this.displayableObjectParts = Collections.singletonList(new DisplayableObjectPart(
                figure,
                color,
                edgePaintWidth,
                verticePaintRadius
        ));
        this.visible = visible;
        this.animationFunction = animationFunction;
        this.offset = offset;
        this.animationCyclic = animationCyclic;
        this.currentOrientation = new Orientation(offset, animationFunction.apply(currentAnimationTime));
    }

    public List<DisplayableObjectPart> getDisplayableObjectParts() {
        return displayableObjectParts;
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
        for (DisplayableObjectPart part: displayableObjectParts) {
            part.setCurrentFigureVertices(null);
        }
    }
    
    public void updateCurrentFiguresVertices() {
        for (DisplayableObjectPart part: displayableObjectParts) {
            if (part.getCurrentFigureVertices() != null) {
                return;
            }
            part.setCurrentFigureVertices(OrientationMapper.getInstance()
                    .orientVertices(part.getFigure().getVertices(), currentOrientation));
        }
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