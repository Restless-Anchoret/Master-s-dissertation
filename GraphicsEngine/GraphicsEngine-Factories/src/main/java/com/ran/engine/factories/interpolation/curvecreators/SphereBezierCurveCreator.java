package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.BigArcsBuilder;
import com.ran.engine.factories.interpolation.tools.CurvesSmoothingCreator;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.function.DoubleMultifunction;
import com.ran.engine.algebra.matrix.DoubleMatrix;
import com.ran.engine.algebra.vector.DoubleVector;
import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class SphereBezierCurveCreator extends AbstractSphereCurveCreator {

    private static final SphereBezierCurveCreator INSTANCE = new SphereBezierCurveCreator();

    public static SphereBezierCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> verticesList,
                                                              SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesList);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = verticesList.size();

        CurvesSmoothingCreator curvesSmoothingCreator = CurvesSmoothingCreator.getInstance();
        BigArcsBuilder bigArcsBuilder = BigArcsBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        List<ThreeDoubleVector> arcsCenters = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            BigArcsBuilder.Result bigArcsBuilderResult = bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesList.get(i), verticesList.get(i + 1));
            arcsCenters.add(new ThreeDoubleVector(bigArcsBuilderResult.getRotation().apply(0.5)
                    .multiply(verticesList.get(i).getDoubleVector())));
        }

        List<DoubleFunction<DoubleMatrix>> halfRotationsForward = new ArrayList<>(k - 1);
        List<DoubleFunction<DoubleMatrix>> halfRotationsBack = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            BigArcsBuilder.Result firstHalfBigArcsBuilderResult = bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesList.get(i), arcsCenters.get(i));
            halfRotationsForward.add(firstHalfBigArcsBuilderResult.getRotation());
            BigArcsBuilder.Result secondHalfBigArcsBuilderResult = bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesList.get(i + 1), arcsCenters.get(i));
            halfRotationsBack.add(secondHalfBigArcsBuilderResult.getRotation());
        }

        List<DoubleFunction<DoubleMatrix>> smoothedRotations = new ArrayList<>(k);
        smoothedRotations.add(halfRotationsForward.get(0));
        for (int i = 1; i < k - 1; i++) {
            smoothedRotations.add(curvesSmoothingCreator.smoothCurves(
                    halfRotationsBack.get(i - 1), halfRotationsForward.get(i), degree));
        }
        smoothedRotations.add(new DoubleFunction<>(
                point -> halfRotationsBack.get(k - 2).apply(1.0 - point),
                0.0, 1.0)
        );

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
            DoubleVector currentVertice = verticesList.get(i).getDoubleVector();
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