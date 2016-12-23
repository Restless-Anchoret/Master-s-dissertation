package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.interpolation.curvecreators.InterpolatedOrientationCurveCreator;
import com.ran.dissertation.world.Orientation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return makeRotationAnimation(offset, new ThreeDoubleVector(0.0, 0.0, 1.0), frames);
    }
    
    public List<Orientation> makeRotationAnimation(ThreeDoubleVector offset, ThreeDoubleVector axis, int frames) {
        List<Orientation> orientations = new ArrayList<>(frames);
        for (int i = 0; i < frames; i++) {
            double angle = 2 * Math.PI * i / frames;
            orientations.add(Orientation.createForOffsetAndRotation(offset, axis, angle));
        }
        return orientations;
    }
    
    public List<Orientation> makeInterpolatedOrientationCurveAnimation(List<Quaternion> quaternions,
            int degree, int frames, ThreeDoubleVector offset) {
        DoubleFunction<Quaternion> interpolatedCurve =
                new InterpolatedOrientationCurveCreator().interpolateCurve(quaternions, new Pair<>(0.0, 1.0), degree);
        double parameterStart = interpolatedCurve.getMinParameterValue();
        double parameterEnd = interpolatedCurve.getMaxParameterValue();
        List<Quaternion> orientationQuaternions = interpolatedCurve.applyForGrid(parameterStart, parameterEnd, frames - 1);
        return orientationQuaternions.stream().map(quaternion -> new Orientation(offset, quaternion))
                .collect(Collectors.toList());
    }
    
}