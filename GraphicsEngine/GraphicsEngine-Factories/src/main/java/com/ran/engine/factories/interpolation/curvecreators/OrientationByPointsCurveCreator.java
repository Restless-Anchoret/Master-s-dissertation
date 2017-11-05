package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.CurvesDeformationCreator;
import com.ran.engine.factories.interpolation.tools.OrientationArcsBuilder;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;

import java.util.ArrayList;
import java.util.List;

public class OrientationByPointsCurveCreator extends AbstractOrientationCurveCreator {

    private static final OrientationByPointsCurveCreator INSTANCE = new OrientationByPointsCurveCreator();

    public static OrientationByPointsCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<Quaternion> interpolateCurve(List<Quaternion> quaternions,
                                                       SimpleInputParameters parameters, int degree) {
        validateVerticesList(quaternions);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = quaternions.size();

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        OrientationArcsBuilder orientationArcsBuilder = OrientationArcsBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        List<DoubleFunction<Quaternion>> rotationsOnSegments = new ArrayList<>(k - 1);
        List<Pair<Double, Double>> rotationAngles = new ArrayList<>(k - 2);
        OrientationArcsBuilder.Result currentOrientationArcsBuildingResult =
                orientationArcsBuilder.buildArcsBetweenQuaternionsOnThreeDimensionalSphere(
                        quaternions.get(0), quaternions.get(1), quaternions.get(2));
        rotationsOnSegments.add(currentOrientationArcsBuildingResult.getFirstRotation());
        rotationAngles.add(currentOrientationArcsBuildingResult.getAngles());

//        System.out.println("Before cycle");
        for (int i = 1; i < k - 2; i++) {
//            System.out.println("Iteration " + i);
            OrientationArcsBuilder.Result nextOrientationArcsBuildingResult =
                    orientationArcsBuilder.buildArcsBetweenQuaternionsOnThreeDimensionalSphere(
                            quaternions.get(i), quaternions.get(i + 1), quaternions.get(i + 2));
//            System.out.println(currentOrientationArcsBuildingResult.getSecondRotation().apply(0.0));
//            System.out.println(nextOrientationArcsBuildingResult.getFirstRotation().apply(0.0));
            DoubleFunction<Quaternion> deformedFunction = deformationCreator.deformCurves(
                    currentOrientationArcsBuildingResult.getSecondRotation(),
                    nextOrientationArcsBuildingResult.getFirstRotation(), degree);
            rotationsOnSegments.add(deformedFunction);
            rotationAngles.add(nextOrientationArcsBuildingResult.getAngles());
            currentOrientationArcsBuildingResult = nextOrientationArcsBuildingResult;
        }
//        System.out.println("After cycle");
        rotationsOnSegments.add(currentOrientationArcsBuildingResult.getSecondRotation());
//        System.out.println("After building segments");
        
        List<Double> timeMoments = timeMomentsUtil.countTimeMoments(rotationAngles, t0, t1, k);
        return buildFinalCurve(timeMoments, quaternions, rotationsOnSegments, k - 1);
    }
    
}
