package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.CurvesSmoothingCreator;
import com.ran.engine.factories.interpolation.tools.OrientationBigArcsBuilder;

import java.util.ArrayList;
import java.util.List;

public class OrientationBezierCurveCreator extends AbstractOrientationCurveCreator {

    @Override
    public DoubleFunction<Quaternion> interpolateCurve(
            List<Quaternion> quaternions, SimpleInputParameters parameters, int degree) {
        validateVerticesList(quaternions);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = quaternions.size();

        CurvesSmoothingCreator curvesSmoothingCreator = CurvesSmoothingCreator.getInstance();
        OrientationBigArcsBuilder bigArcsBuilder = OrientationBigArcsBuilder.getInstance();

        List<Quaternion> arcsCenters = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            OrientationBigArcsBuilder.Result bigArcsBuilderResult = bigArcsBuilder
                    .buildOrientationBigArcsBetweenQuaternions(quaternions.get(i), quaternions.get(i + 1));
            arcsCenters.add(bigArcsBuilderResult.getRotation().apply(0.5).multiply(quaternions.get(i)));
        }

        List<DoubleFunction<Quaternion>> halfRotationsForward = new ArrayList<>(k - 1);
        List<DoubleFunction<Quaternion>> halfRotationsBack = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            OrientationBigArcsBuilder.Result firstHalfBigArcsBuilderResult = bigArcsBuilder
                    .buildOrientationBigArcsBetweenQuaternions(quaternions.get(i), arcsCenters.get(i));
            halfRotationsForward.add(firstHalfBigArcsBuilderResult.getRotation());
            OrientationBigArcsBuilder.Result secondHalfBigArcsBuilderResult = bigArcsBuilder
                    .buildOrientationBigArcsBetweenQuaternions(quaternions.get(i + 1), arcsCenters.get(i));
            halfRotationsBack.add(secondHalfBigArcsBuilderResult.getRotation());
        }

        List<DoubleFunction<Quaternion>> smoothedRotations = new ArrayList<>(k);
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
        return buildFinalCurve(timeMoments, quaternions, smoothedRotations, k);
    }

}
