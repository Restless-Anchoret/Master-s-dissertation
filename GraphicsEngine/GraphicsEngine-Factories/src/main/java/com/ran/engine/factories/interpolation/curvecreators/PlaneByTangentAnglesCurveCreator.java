package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.*;
import com.ran.engine.factories.util.GroupMultiplicationOperationFactory;

import java.util.ArrayList;
import java.util.List;

public class PlaneByTangentAnglesCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<TwoDoubleVector, Double>, TwoDoubleVector, SimpleInputParameters> {

    private static final PlaneByTangentAnglesCurveCreator INSTANCE = new PlaneByTangentAnglesCurveCreator();

    public static PlaneByTangentAnglesCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<TwoDoubleVector> interpolateCurve(
            List<Pair<TwoDoubleVector, Double>> verticesWithTangentAnglesList, SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesWithTangentAnglesList);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = verticesWithTangentAnglesList.size();

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        CircleArcsBuilder circleArcsBuilder = CircleArcsBuilder.getInstance();
        SegmentsBuilder segmentsBuilder = SegmentsBuilder.getInstance();
        TangentSegmentBuilder tangentSegmentBuilder = TangentSegmentBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        List<DoubleFunction<TwoDoubleVector>> constantFunctions = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            constantFunctions.add(DoubleFunction.createConstantFunction(verticesWithTangentAnglesList.get(i).getLeft()));
        }

        List<CircleArcsBuilder.Result> circleArcsResults = new ArrayList<>(k - 2);
        for (int i = 1; i < k - 1; i++) {
            circleArcsResults.add(circleArcsBuilder.buildCircle(
                    verticesWithTangentAnglesList.get(i - 1).getLeft(),
                    verticesWithTangentAnglesList.get(i).getLeft(),
                    verticesWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }

        List<SegmentsBuilder.Result> directSegmentsResults = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            directSegmentsResults.add(segmentsBuilder.buildSegment(
                    verticesWithTangentAnglesList.get(i).getLeft(),
                    verticesWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }

        List<TangentSegmentBuilder.Result> tangentSegmentBuilderResults = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            if (verticesWithTangentAnglesList.get(i).getRight() == null) {
                tangentSegmentBuilderResults.add(null);
            } else {
                Double forwardLength = null, backLength = null;
                if (i + 1 < k) {
                    if (verticesWithTangentAnglesList.get(i + 1).getRight() != null || i + 1 == k - 1) {
                        forwardLength = directSegmentsResults.get(i).getLength();
                    } else {
                        forwardLength = circleArcsResults.get(i).getFirstArcLength();
                    }
                }
                if (i - 1 >= 0) {
                    if (verticesWithTangentAnglesList.get(i - 1).getRight() != null || i - 1 == 0) {
                        backLength = directSegmentsResults.get(i - 1).getLength();
                    } else {
                        backLength = circleArcsResults.get(i - 2).getSecondArcLength();
                    }
                }
                tangentSegmentBuilderResults.add(tangentSegmentBuilder.buildTangent(
                        verticesWithTangentAnglesList.get(i).getLeft(),
                        verticesWithTangentAnglesList.get(i).getRight(),
                        forwardLength, backLength));
            }
        }

        List<Pair<Double, Double>> arcsLengths = new ArrayList<>(k - 2);
        for (int i = 0; i < k - 2; i++) {
            if (verticesWithTangentAnglesList.get(i + 1).getRight() == null) {
                arcsLengths.add(circleArcsResults.get(i).getArcsLengths());
            } else {
                arcsLengths.add(tangentSegmentBuilderResults.get(i + 1).getLengths());
            }
        }

        List<DoubleFunction<TwoDoubleVector>> deformedSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            if (verticesWithTangentAnglesList.get(i).getRight() != null &&
                    verticesWithTangentAnglesList.get(i + 1).getRight() != null) {
                DoubleFunction<TwoDoubleVector> firstDeformedCurve = deformationCreator.deformCurves(
                        tangentSegmentBuilderResults.get(i).getForwardSegment().substract(constantFunctions.get(i)),
                        directSegmentsResults.get(i).getSegment().substract(constantFunctions.get(i)), degree,
                        GroupMultiplicationOperationFactory.getSummationOperation());
                DoubleFunction<TwoDoubleVector> secondDeformedCurve = deformationCreator.deformCurvesWithCommonEnd(
                        directSegmentsResults.get(i).getSegment().substract(constantFunctions.get(i)),
                        tangentSegmentBuilderResults.get(i + 1).getBackSegment().substract(constantFunctions.get(i + 1)), degree,
                        GroupMultiplicationOperationFactory.getSummationOperation());
                deformedSegments.add(deformationCreator.deformCurves(
                        firstDeformedCurve, secondDeformedCurve, degree)
                        .add(constantFunctions.get(i)));
            } else if (verticesWithTangentAnglesList.get(i).getRight() != null) {
                if (i == k - 2) {
                    deformedSegments.add(deformationCreator.deformCurves(
                            tangentSegmentBuilderResults.get(i).getForwardSegment().substract(constantFunctions.get(i)),
                            directSegmentsResults.get(i).getSegment().substract(constantFunctions.get(i)), degree,
                            GroupMultiplicationOperationFactory.getSummationOperation())
                            .add(constantFunctions.get(i)));
                } else {
                    deformedSegments.add(deformationCreator.deformCurves(
                            tangentSegmentBuilderResults.get(i).getForwardSegment().substract(constantFunctions.get(i)),
                            circleArcsResults.get(i).getFirstArc().substract(constantFunctions.get(i)), degree,
                            GroupMultiplicationOperationFactory.getSummationOperation())
                            .add(constantFunctions.get(i)));
                }
            } else if (verticesWithTangentAnglesList.get(i + 1).getRight() != null) {
                if (i == 0) {
                    deformedSegments.add(deformationCreator.deformCurvesWithCommonEnd(
                            directSegmentsResults.get(i).getSegment().substract(constantFunctions.get(i)),
                            tangentSegmentBuilderResults.get(i + 1).getBackSegment().substract(constantFunctions.get(i + 1)), degree,
                            GroupMultiplicationOperationFactory.getSummationOperation())
                            .add(constantFunctions.get(i)));
                } else {
                    deformedSegments.add(deformationCreator.deformCurvesWithCommonEnd(
                            circleArcsResults.get(i - 1).getSecondArc().substract(constantFunctions.get(i)),
                            tangentSegmentBuilderResults.get(i + 1).getBackSegment().substract(constantFunctions.get(i + 1)), degree,
                            GroupMultiplicationOperationFactory.getSummationOperation())
                            .add(constantFunctions.get(i)));
                }
            } else {
                if (i == 0) {
                    deformedSegments.add(circleArcsResults.get(i).getFirstArc());
                } else if (i == k - 2) {
                    deformedSegments.add(circleArcsResults.get(i - 1).getSecondArc());
                } else {
                    deformedSegments.add(deformationCreator.deformCurves(
                            circleArcsResults.get(i - 1).getSecondArc().substract(constantFunctions.get(i)),
                            circleArcsResults.get(i).getFirstArc().substract(constantFunctions.get(i)), degree,
                            GroupMultiplicationOperationFactory.getSummationOperation())
                            .add(constantFunctions.get(i)));
                }
            }
        }

        List<Double> timeMoments = timeMomentsUtil.countTimeMoments(arcsLengths, t0, t1, k);
        return buildFinalCurve(timeMoments, deformedSegments, k - 1);
    }

}
