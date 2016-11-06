package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import java.util.List;

public class InterpolatedOrientationCurveCreator {

    private static final InterpolatedOrientationCurveCreator INSTANCE = new InterpolatedOrientationCurveCreator();
    
    public static InterpolatedOrientationCurveCreator getInstance() {
        return INSTANCE;
    }
    
    private InterpolatedOrientationCurveCreator() { }
    
    public DoubleFunction<Quaternion> interpolateOrientationCurve(List<Quaternion> quaternions,
            int degree, double t0, double t1) {
        return null;
    }
    
}