package com.ran.engine.factories.animations;

import com.ran.engine.factories.interpolation.curvecreators.OrientationByPointsCurveCreator;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.util.List;

public class AnimationFactory {
    
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
                new OrientationByPointsCurveCreator().interpolateCurve(quaternions, new SimpleInputParameters(0.0, 1.0), degree);
        double t0 = interpolatedCurve.getMinParameterValue();
        double t1 = interpolatedCurve.getMaxParameterValue();
        return new DoubleFunction<>(
                time -> interpolatedCurve.apply(t0 + (time / timePeriod) * (t1 - t0)),
                0.0, timePeriod
        );
    }
    
}
