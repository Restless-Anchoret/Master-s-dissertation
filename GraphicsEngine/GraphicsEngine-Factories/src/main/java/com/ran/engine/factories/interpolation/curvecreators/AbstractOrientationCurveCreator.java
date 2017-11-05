package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.function.DoubleMultifunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOrientationCurveCreator extends AbstractInterpolatedCurveCreator<
        Quaternion, Quaternion, SimpleInputParameters> {

    @Override
    protected void validateVerticesList(List<Quaternion> verticesList) {
        super.validateVerticesList(verticesList);
        if (verticesList.stream().anyMatch(quaternion -> !quaternion.isIdentity())) {
            throw new InterpolationException("All quaternions must be identity");
        }
    }

    protected DoubleFunction<Quaternion> buildFinalCurve(List<Double> timeMoments,
                                                                List<Quaternion> quaternions,
                                                                List<DoubleFunction<Quaternion>> rotationsOnSegments,
                                                                int segmentsQuantity) {
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();
        List<DoubleFunction<Quaternion>> orientationCurveSegments = new ArrayList<>(segmentsQuantity);
        for (int i = 0; i < segmentsQuantity; i++) {
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<Quaternion> currentRotation = rotationsOnSegments.get(i);
            Quaternion currentQuaternion = quaternions.get(i);
            DoubleFunction<Quaternion> curveSegmentWithoutAligning = new DoubleFunction<>(
                    point -> currentRotation.apply(point).multiply(currentQuaternion), 0.0, 1.0
            );
            DoubleFunction<Quaternion> alignedCurveSegment =
                    curveSegmentWithoutAligning.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            orientationCurveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(orientationCurveSegments);
    }

}
