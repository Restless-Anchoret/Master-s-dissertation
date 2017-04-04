package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.tools.BigArcsBuilder;
import com.ran.engine.factories.interpolation.tools.CurvesSmoothingCreator;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class BezierSphereCurveByMiddlePointsCreator extends AbstractSphereCurveCreator {

    private static final BezierSphereCurveByMiddlePointsCreator INSTANCE = new BezierSphereCurveByMiddlePointsCreator();

    public static BezierSphereCurveByMiddlePointsCreator getInstance() {
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

//        List<DoubleFunction<DoubleMatrix>> rotationsOnBigArcs = new ArrayList<>(k - 1);
//        List<Double> bigArcsAngles = new ArrayList<>(k - 1);
        List<ThreeDoubleVector> arcsCenters = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            BigArcsBuilder.Result bigArcsBuilderResult = bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesList.get(i), verticesList.get(i + 1));
//            rotationsOnBigArcs.add(bigArcsBuilderResult.getRotation());
//            bigArcsAngles.add(bigArcsBuilderResult.getAngle());
            arcsCenters.add(new ThreeDoubleVector(bigArcsBuilderResult.getRotation().apply(0.5)
                    .multiply(verticesList.get(i).getDoubleVector())));
        }

        List<DoubleFunction<DoubleMatrix>> firstHalfRotations = new ArrayList<>(k - 1);
        List<DoubleFunction<DoubleMatrix>> secondHalfRotations = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            BigArcsBuilder.Result firstHalfBigArcsBuilderResult = bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesList.get(i), arcsCenters.get(i));
            firstHalfRotations.add(firstHalfBigArcsBuilderResult.getRotation());
            BigArcsBuilder.Result secondHalfBigArcsBuilderResult = bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    arcsCenters.get(i), verticesList.get(i + 1));
            secondHalfRotations.add(secondHalfBigArcsBuilderResult.getRotation());
        }

        List<DoubleFunction<DoubleMatrix>> resultRotationsSegments = new ArrayList<>(k);
        resultRotationsSegments.add(firstHalfRotations.get(0));
        for (int i = 1; i < k - 1; i++) {
            resultRotationsSegments.add(curvesSmoothingCreator.smoothCurves(
                    secondHalfRotations.get(i - 1), firstHalfRotations.get(i), degree));
        }



        return null;
    }

}