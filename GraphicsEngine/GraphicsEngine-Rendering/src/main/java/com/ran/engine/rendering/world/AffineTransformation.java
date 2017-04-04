package com.ran.engine.rendering.world;

import java.util.function.Function;

public class AffineTransformation implements Function<Orientation, Orientation> {

    private Function<Orientation, Orientation> orientationMaker;

    public AffineTransformation(Function<Orientation, Orientation> orientationMaker) {
        this.orientationMaker = orientationMaker;
    }
    
    public void performTransformation(DisplayableObject displayableObject) {
        displayableObject.setCurrentOrientation(
                orientationMaker.apply(displayableObject.getCurrentOrientation()));
    }

    @Override
    public Orientation apply(Orientation orientation) {
        return orientationMaker.apply(orientation);
    }

}