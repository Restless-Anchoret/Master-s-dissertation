package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.matrix.DoubleMatrix;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.ArcsBuilder;
import com.ran.engine.factories.interpolation.tools.CurvesDeformationCreator;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;

import java.util.ArrayList;
import java.util.List;

public class SphereByPointsCurveCreator extends AbstractSphereCurveCreator {

    private static final SphereByPointsCurveCreator INSTANCE = new SphereByPointsCurveCreator();

    public static SphereByPointsCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> vertices,
                                                              SimpleInputParameters parameters, int degree) {
        validateVerticesList(vertices);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = vertices.size();

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        ArcsBuilder arcsBuilder = ArcsBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();
        
        List<DoubleFunction<DoubleMatrix>> rotationsOnSegments = new ArrayList<>(k - 1);
        List<Pair<Double, Double>> rotationAngles = new ArrayList<>(k - 2);
        ArcsBuilder.Result currentArcsBuildingResult =
                arcsBuilder.buildArcsBetweenVerticesOnSphere(vertices.get(0), vertices.get(1), vertices.get(2));
        rotationsOnSegments.add(currentArcsBuildingResult.getFirstRotation());
        rotationAngles.add(currentArcsBuildingResult.getAngles());
        
        for (int i = 1; i < k - 2; i++) {
            ArcsBuilder.Result nextArcsBuildingResult =
                    arcsBuilder.buildArcsBetweenVerticesOnSphere(vertices.get(i), vertices.get(i + 1), vertices.get(i + 2));
            DoubleFunction<DoubleMatrix> deformedFunction = deformationCreator.deformCurves(
                    currentArcsBuildingResult.getSecondRotation(),
                    nextArcsBuildingResult.getFirstRotation(), degree);
            rotationsOnSegments.add(deformedFunction);
            rotationAngles.add(nextArcsBuildingResult.getAngles());
            currentArcsBuildingResult = nextArcsBuildingResult;
        }
        rotationsOnSegments.add(currentArcsBuildingResult.getSecondRotation());
        
        List<Double> timeMoments = timeMomentsUtil.countTimeMoments(rotationAngles, t0, t1, k);
        return buildFinalCurve(timeMoments, vertices, rotationsOnSegments, k - 1);
    }
    
}
