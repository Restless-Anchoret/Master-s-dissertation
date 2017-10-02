package com.ran.engine.rendering.world;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;

public class AnimationInfo {

    private final DoubleFunction<Quaternion> animationFunction;
    private Orientation currentOrientation;
    private final boolean animationCyclic;

    private boolean animationTurnedOn = false;
    private double currentAnimationTime = 0.0;

    protected AnimationInfo(DoubleFunction<Quaternion> animationFunction,
                         Orientation currentOrientation, boolean animationCyclic) {
        this.animationFunction = animationFunction;
        this.currentOrientation = currentOrientation;
        this.animationCyclic = animationCyclic;
    }

    public DoubleFunction<Quaternion> getAnimationFunction() {
        return animationFunction;
    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    public boolean isAnimationCyclic() {
        return animationCyclic;
    }

    public boolean isAnimationTurnedOn() {
        return animationTurnedOn;
    }

    public void setAnimationTurnedOn(boolean animationTurnedOn) {
        this.animationTurnedOn = animationTurnedOn;
        currentAnimationTime %= animationFunction.getMaxParameterValue();
    }

    public double getCurrentAnimationTime() {
        return currentAnimationTime;
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
    }

}
