package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.CircleArcsBuilder;
import com.ran.engine.factories.interpolation.tools.CurvesDeformationCreator;
import com.ran.engine.factories.interpolation.tools.TimeMomentsUtil;
import com.ran.engine.factories.util.GroupMultiplicationOperationFactory;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.function.DoubleMultifunction;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class PlaneByPointsCurveCreator extends AbstractPlainCurveCreator {

    private static final PlaneByPointsCurveCreator INSTANCE = new PlaneByPointsCurveCreator();

    public static PlaneByPointsCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<TwoDoubleVector> interpolateCurve(
            List<TwoDoubleVector> vertices, SimpleInputParameters parameters, int degree) {
        validateVerticesList(vertices);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = vertices.size();

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        CircleArcsBuilder circleArcsBuilder = CircleArcsBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        List<DoubleFunction<TwoDoubleVector>> constantFunctions = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            constantFunctions.add(DoubleFunction.createConstantFunction(vertices.get(i)));
        }

        List<DoubleFunction<TwoDoubleVector>> segments = new ArrayList<>(k - 1);
        List<Pair<Double, Double>> arcsLengths = new ArrayList<>(k - 2);
        CircleArcsBuilder.Result currentArcsBuildingResult =
                circleArcsBuilder.buildCircle(vertices.get(0), vertices.get(1), vertices.get(2));
        segments.add(currentArcsBuildingResult.getFirstArc());
        arcsLengths.add(currentArcsBuildingResult.getArcsLengths());

        for (int i = 1; i < k - 2; i++) {
            CircleArcsBuilder.Result nextArcsBuildingResult =
                    circleArcsBuilder.buildCircle(vertices.get(i), vertices.get(i + 1), vertices.get(i + 2));
            DoubleFunction<TwoDoubleVector> deformedFunction = deformationCreator.deformCurves(
                    currentArcsBuildingResult.getSecondArc().substract(constantFunctions.get(i)),
                    nextArcsBuildingResult.getFirstArc().substract(constantFunctions.get(i)), degree,
                    GroupMultiplicationOperationFactory.getSummationOperation());
            segments.add(deformedFunction.add(constantFunctions.get(i)));
            arcsLengths.add(nextArcsBuildingResult.getArcsLengths());
            currentArcsBuildingResult = nextArcsBuildingResult;
        }
        segments.add(currentArcsBuildingResult.getSecondArc());

        List<Double> timeMoments = timeMomentsUtil.countTimeMoments(arcsLengths, t0, t1, k);
        List<DoubleFunction<TwoDoubleVector>> curveSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
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