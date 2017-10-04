package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.function.DoubleMultifunction;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.CurvesSmoothingCreator;
import com.ran.engine.factories.interpolation.tools.SegmentsBuilder;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;
import com.ran.engine.factories.util.GroupMultiplicationOperationFactory;

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

        List<DoubleFunction<TwoDoubleVector>> constantFunctions = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            constantFunctions.add(DoubleFunction.createConstantFunction(vertices.get(i)));
        }

        List<TwoDoubleVector> segmentsCenters = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            TwoDoubleVector firstVector = vertices.get(i);
            TwoDoubleVector secondVector = vertices.get(i + 1);
            segmentsCenters.add(new TwoDoubleVector(
                    (firstVector.getX() + secondVector.getX()) / 2.0,
                    (firstVector.getY() + secondVector.getY()) / 2.0
            ));
        }

        List<DoubleFunction<TwoDoubleVector>> halfSegmentsForward = new ArrayList<>(k - 1);
        List<DoubleFunction<TwoDoubleVector>> halfSegmentsBack = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            SegmentsBuilder.Result firstHalfSegmentsBuilderResult = segmentsBuilder
                    .buildSegment(vertices.get(i), segmentsCenters.get(i));
            halfSegmentsForward.add(firstHalfSegmentsBuilderResult.getSegment());
            SegmentsBuilder.Result seconfHalfSegmentsBuilderResult = segmentsBuilder
                    .buildSegment(vertices.get(i + 1), segmentsCenters.get(i));
            halfSegmentsBack.add(seconfHalfSegmentsBuilderResult.getSegment());
        }

        List<DoubleFunction<TwoDoubleVector>> smoothedSegments = new ArrayList<>(k);
        smoothedSegments.add(halfSegmentsForward.get(0));
        for (int i = 1; i < k - 1; i++) {
            smoothedSegments.add(curvesSmoothingCreator.smoothCurves(
                    halfSegmentsBack.get(i - 1).substract(constantFunctions.get(i)),
                    halfSegmentsForward.get(i).substract(constantFunctions.get(i)),
                    degree, GroupMultiplicationOperationFactory.getSummationOperation())
                    .add(constantFunctions.get(i)));
        }
        smoothedSegments.add(new DoubleFunction<>(
                point -> halfSegmentsBack.get(k - 2).apply(1.0 - point)));

        List<Double> timeMoments = new ArrayList<>(k + 1);
        double timeDelta = t1 - t0;
        for (int i = 0; i < k + 1; i++) {
            timeMoments.add(t0 + i * timeDelta);
        }

        List<DoubleFunction<TwoDoubleVector>> curveSegments = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<TwoDoubleVector> currentCurve = smoothedSegments.get(i);
            DoubleFunction<TwoDoubleVector> alignedCurveSegment =
                    currentCurve.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(curveSegments);
    }

}