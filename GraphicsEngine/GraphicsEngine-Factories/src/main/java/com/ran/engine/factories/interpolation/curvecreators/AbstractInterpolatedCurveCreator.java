package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.function.DoubleMultifunction;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.input.InputParameters;
import com.ran.engine.algebra.common.AlgebraicObject;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInterpolatedCurveCreator<I, O extends AlgebraicObject<O>, P extends InputParameters>
        implements InterpolatedCurveCreator<I, O, P> {

    protected void validateVerticesList(List<I> verticesList) {
        if (verticesList.size() < 3) {
            throw new InterpolationException("Interpolation requires at least 3 vertices");
        }
    }

    protected DoubleFunction<TwoDoubleVector> buildFinalCurve(List<Double> timeMoments,
                                                              List<DoubleFunction<TwoDoubleVector>> segments,
                                                              int segmentsQuantity) {
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();
        List<DoubleFunction<TwoDoubleVector>> curveSegments = new ArrayList<>(segmentsQuantity);
        for (int i = 0; i < segmentsQuantity; i++) {
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<TwoDoubleVector> currentSegment = segments.get(i);
            DoubleFunction<TwoDoubleVector> alignedCurveSegment =
                    currentSegment.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(curveSegments);
    }

}
