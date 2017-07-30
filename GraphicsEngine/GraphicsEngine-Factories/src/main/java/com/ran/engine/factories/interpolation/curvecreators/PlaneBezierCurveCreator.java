package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.CurvesSmoothingCreator;
import com.ran.engine.factories.interpolation.tools.SegmentsBuilder;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.DoubleVector;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class PlaneBezierCurveCreator extends AbstractPlainCurveCreator {

    private static final PlaneBezierCurveCreator INSTANCE = new PlaneBezierCurveCreator();

    public static PlaneBezierCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<TwoDoubleVector> interpolateCurve(
            List<TwoDoubleVector> vertices, SimpleInputParameters parameters, int degree) {
        validateVerticesList(vertices);
        
        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = vertices.size();

        CurvesSmoothingCreator curvesSmoothingCreator = CurvesSmoothingCreator.getInstance();
        SegmentsBuilder segmentsBuilder = SegmentsBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        List<TwoDoubleVector> segmentsCenters = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            TwoDoubleVector firstVector = vertices.get(i);
            TwoDoubleVector secondVector = vertices.get(i + 1);
            segmentsCenters.add(new TwoDoubleVector(
                    (firstVector.getX() + secondVector.getX()) / 2.0,
                    (firstVector.getY() + secondVector.getY()) / 2.0
            ));
        }

        List<TwoDoubleVector> halfSegmentsForward = new ArrayList<>(k - 1);
        List<TwoDoubleVector> halfSegmentsBack = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
//            BigArcsBuilder.Result firstHalfBigArcsBuilderResult = segmentsBuilder.buildBigArcBetweenVerticesOnSphere(
//                    vertices.get(i), segmentsCenters.get(i));
//            halfSegmentsForward.add(firstHalfBigArcsBuilderResult.getRotation());
//            BigArcsBuilder.Result secondHalfBigArcsBuilderResult = segmentsBuilder.buildBigArcBetweenVerticesOnSphere(
//                    vertices.get(i + 1), segmentsCenters.get(i));
//            halfSegmentsBack.add(secondHalfBigArcsBuilderResult.getRotation());
        }

        List<DoubleFunction<DoubleMatrix>> smoothedRotations = new ArrayList<>(k);
//        smoothedRotations.add(halfSegmentsForward.get(0));
//        for (int i = 1; i < k - 1; i++) {
//            smoothedRotations.add(curvesSmoothingCreator.smoothCurves(
//                    halfSegmentsBack.get(i - 1), halfSegmentsForward.get(i), degree));
//        }
//        smoothedRotations.add(new DoubleFunction<>(
//                point -> halfSegmentsBack.get(k - 2).apply(1.0 - point),
//                0.0, 1.0)
//        );

        List<Double> timeMoments = new ArrayList<>(k + 1);
        double timeDelta = t1 - t0;
        for (int i = 0; i < k + 1; i++) {
            timeMoments.add(t0 + i * timeDelta);
        }

        List<DoubleFunction<ThreeDoubleVector>> curveSegments = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<DoubleMatrix> currentRotation = smoothedRotations.get(i);
            DoubleVector currentVertice = vertices.get(i).getDoubleVector();
            DoubleFunction<ThreeDoubleVector> curveSegmentWithoutAligning = new DoubleFunction<>(
                    point -> new ThreeDoubleVector(currentRotation.apply(point).multiply(currentVertice)), 0.0, 1.0
            );
            DoubleFunction<ThreeDoubleVector> alignedCurveSegment =
                    curveSegmentWithoutAligning.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
//        return DoubleMultifunction.makeMultifunction(curveSegments);
        return null;
    }

}