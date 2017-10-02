package com.ran.engine.rendering.world;

import java.util.function.Function;

public class AffineTransformation implements Function<Orientation, Orientation> {

    private Function<Orientation, Orientation> orientationMaker;

    public AffineTransformation(Function<Orientation, Orientation> orientationMaker) {
        this.orientationMaker = orientationMaker;
    }
    
    public void performTransformation(WorldObject worldObject) {
        worldObject.setCurrentOrientation(
                orientationMaker.apply(worldObject.getWorldObjectContent()
                        .getAnimationInfo().getCurrentOrientation()));
    }

    @Override
    public Orientation apply(Orientation orientation) {
        return orientationMaker.apply(orientation);
    }

}
