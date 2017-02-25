package com.ran.engine.factories.objects;

import com.ran.engine.factories.interpolation.curvecreators.InterpolatedOrientationCurveCreator;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.quaternion.Quaternion;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.List;

public class AnimationFactory {

    private static final AnimationFactory INSTANCE = new AnimationFactory();

    public static AnimationFactory getInstance() {
        return INSTANCE;
    }
    
    public DoubleFunction<Quaternion> makeZRotationAnimation(double timePeriod) {
        return makeRotationAnimation(new ThreeDoubleVector(0.0, 0.0, 1.0), timePeriod);
    }
    
    public DoubleFunction<Quaternion> makeRotationAnimation(ThreeDoubleVector axis, double timePeriod) {
        return new DoubleFunction<>(
                time -> Quaternion.createForRotation(axis, (time / timePeriod) * Math.PI * 2.0),
                0.0, timePeriod
        );
    }
    
    public DoubleFunction<Quaternion> makeInterpolatedOrientationCurveAnimation(List<Quaternion> quaternions,
            int degree, double timePeriod) {
        DoubleFunction<Quaternion> interpolatedCurve =
                new InterpolatedOrientationCurveCreator().interpolateCurve(quaternions, new Pair<>(0.0, 1.0), degree);
        double t0 = interpolatedCurve.getMinParameterValue();
        double t1 = interpolatedCurve.getMaxParameterValue();
        return new DoubleFunction<>(time -> interpolatedCurve.apply(t0 + (time / timePeriod) * (t1 - t0)));
    }
    
}