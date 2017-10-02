package com.ran.engine.rendering.world;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

public class AnimationInfoBuilder {

    private DoubleFunction<Quaternion> animationFunction = new DoubleFunction<>(time -> Quaternion.IDENTITY_QUANTERNION);
    private Orientation initialOrientation = Orientation.INITIAL_ORIENTATION;
    private boolean animationCyclic = true;

    public AnimationInfoBuilder setAnimationFunctionAndOffset(DoubleFunction<Quaternion> animationFunction,
                                                              ThreeDoubleVector offset) {
        this.animationFunction = animationFunction;
        this.initialOrientation = new Orientation(offset,
                animationFunction.apply(animationFunction.getMinParameterValue()));
        return this;
    }

    public AnimationInfoBuilder setInitialOrientation(Orientation initialOrientation) {
        this.initialOrientation = initialOrientation;
        this.animationFunction = new DoubleFunction<>(time -> initialOrientation.getRotation());
        return this;
    }

    public AnimationInfoBuilder setAnimationCyclic(boolean animationCyclic) {
        this.animationCyclic = animationCyclic;
        return this;
    }

    public AnimationInfo build() {
        return new AnimationInfo(
                this.animationFunction,
                this.initialOrientation,
                this.animationCyclic);
    }

}
