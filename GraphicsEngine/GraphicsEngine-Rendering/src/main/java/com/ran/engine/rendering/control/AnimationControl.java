package com.ran.engine.rendering.control;

import com.ran.engine.rendering.world.AnimationInfo;

public class AnimationControl extends AbstractControl {

    public AnimationControl() {
        super();
    }

    @Override
    protected void doIncreaseParameter() {
        changeAnimationTurnedOn();
    }

    @Override
    protected void doDecreaseParameter() {
        changeAnimationTurnedOn();
    }

    private void changeAnimationTurnedOn() {
        AnimationInfo animationInfo = getWorldObject().getWorldObjectContent().getAnimationInfo();
        animationInfo.setAnimationTurnedOn(!animationInfo.isAnimationTurnedOn());
    }

    @Override
    protected void parameterChanged() { }

}
