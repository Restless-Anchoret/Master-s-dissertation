package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Orientation;
import java.util.ArrayList;
import java.util.List;

public class AnimationFactory {

    private static final AnimationFactory INSTANCE = new AnimationFactory();

    public static AnimationFactory getInstance() {
        return INSTANCE;
    }
    
    private AnimationFactory() { }
    
    public List<Orientation> makeZRotationAnimation(int frames) {
        return makeZRotationAnimation(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, frames);
    }
    
    public List<Orientation> makeZRotationAnimation(ThreeDoubleVector offset, int frames) {
        ThreeDoubleVector zAxis = new ThreeDoubleVector(0.0, 0.0, 1.0);
        List<Orientation> orientations = new ArrayList<>(frames);
        for (int i = 0; i < frames; i++) {
            double angle = 2 * Math.PI * i / frames;
            orientations.add(Orientation.createForOffsetAndRotation(offset, zAxis, angle));
        }
        return orientations;
    }
    
}