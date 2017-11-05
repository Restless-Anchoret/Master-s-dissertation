package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.common.ArithmeticOperations;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.function.DoubleMultifunction;
import com.ran.engine.algebra.matrix.DoubleMatrix;
import com.ran.engine.algebra.vector.DoubleVector;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSphereCurveCreator extends AbstractInterpolatedCurveCreator<
        ThreeDoubleVector, ThreeDoubleVector, SimpleInputParameters> {

    @Override
    protected void validateVerticesList(List<ThreeDoubleVector> verticesList) {
        super.validateVerticesList(verticesList);
        double radius = verticesList.get(0).getNorm();
        if (verticesList.stream().anyMatch(vertice -> ArithmeticOperations.doubleNotEquals(vertice.getNorm(), radius))) {
            throw new InterpolationException("All vertices must belong to the same sphere");
        }
    }

    protected DoubleFunction<ThreeDoubleVector> buildFinalCurve(List<Double> timeMoments,
                                                                List<ThreeDoubleVector> vertices,
                                                                List<DoubleFunction<DoubleMatrix>> rotationsOnSegments,
                                                                int segmentsQuantity) {
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();
        List<DoubleFunction<ThreeDoubleVector>> curveSegments = new ArrayList<>(segmentsQuantity);
        for (int i = 0; i < segmentsQuantity; i++) {
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<DoubleMatrix> currentRotation = rotationsOnSegments.get(i);
            DoubleVector currentVertice = vertices.get(i).getDoubleVector();
            DoubleFunction<ThreeDoubleVector> curveSegmentWithoutAligning = new DoubleFunction<>(
                    point -> new ThreeDoubleVector(currentRotation.apply(point).multiply(currentVertice)), 0.0, 1.0
            );
            DoubleFunction<ThreeDoubleVector> alignedCurveSegment =
                    curveSegmentWithoutAligning.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(curveSegments);
    }

}
