package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.SingleDouble;
import java.util.ArrayList;
import java.util.List;

public class TimeMomentsUtil {

    private static final TimeMomentsUtil INSTANCE = new TimeMomentsUtil();
    
    public static TimeMomentsUtil getInstance() {
        return INSTANCE;
    }
    
    public List<Double> countTimeMoments(List<Pair<Double, Double>> rotationAngles, double t0, double t1, int k) {
        List<Double> timeMoments = new ArrayList<>(k);
        timeMoments.add(t0);
        timeMoments.add(t1);
        double timeBeforePrevious = t0;
        double timePrevous = t1;
        for (Pair<Double, Double> angles: rotationAngles) {
            double phi = Math.abs(angles.getLeft());
            double psi = Math.abs(angles.getRight());
            double timeNext = timePrevous + (timePrevous - timeBeforePrevious) * (psi / phi);
            timeMoments.add(timeNext);
            timeBeforePrevious = timePrevous;
            timePrevous = timeNext;
        }
        return timeMoments;
    }
    
    public DoubleFunction<SingleDouble> buildAligningFunction(double t0, double t1) {
        return new DoubleFunction<>(point -> new SingleDouble((point - t0) / (t1 - t0)), t0, t1);
    }
    
}