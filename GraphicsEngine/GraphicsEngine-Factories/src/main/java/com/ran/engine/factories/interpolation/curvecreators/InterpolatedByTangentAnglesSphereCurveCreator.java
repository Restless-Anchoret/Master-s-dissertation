package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.tools.*;
import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class InterpolatedByTangentAnglesSphereCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<ThreeDoubleVector, Double>, ThreeDoubleVector, SimpleInputParameters> {

    private static final InterpolatedByTangentAnglesSphereCurveCreator INSTANCE = new InterpolatedByTangentAnglesSphereCurveCreator();

    public static InterpolatedByTangentAnglesSphereCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<Pair<ThreeDoubleVector, Double>> verticesWithTangentAnglesList,
                                                              SimpleInputParameters parameters, int degree) {
        validateVerticesList(verticesWithTangentAnglesList);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = verticesWithTangentAnglesList.size();

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        ArcsBuilder arcsBuilder = ArcsBuilder.getInstance();
        BigArcsBuilder bigArcsBuilder = BigArcsBuilder.getInstance();
        TangentBuilder tangentBuilder = TangentBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        List<ArcsBuilder.Result> smallArcsResults = new ArrayList<>(k - 2);
        for (int i = 1; i < k - 1; i++) {
            smallArcsResults.add(arcsBuilder.buildArcsBetweenVerticesOnSphere(
                    verticesWithTangentAnglesList.get(i - 1).getLeft(),
                    verticesWithTangentAnglesList.get(i).getLeft(),
                    verticesWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }

        List<BigArcsBuilder.Result> bigArcsResults = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            bigArcsResults.add(bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesWithTangentAnglesList.get(i).getLeft(),
                    verticesWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }

        List<TangentBuilder.Result> tangentBuilderResults = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            if (verticesWithTangentAnglesList.get(i).getRight() == null) {
                tangentBuilderResults.add(null);
            } else {
                Double forwardAngle = null, backAngle = null;
                if (i + 1 < k) {
                    if (verticesWithTangentAnglesList.get(i + 1).getRight() != null || i + 1 == k - 1) {
                        forwardAngle = bigArcsResults.get(i).getAngle();
                    } else {
                        forwardAngle = smallArcsResults.get(i).getFirstAngle();
                    }
                }
                if (i - 1 >= 0) {
                    if (verticesWithTangentAnglesList.get(i - 1).getRight() != null || i - 1 == 0) {
                        backAngle = bigArcsResults.get(i - 1).getAngle();
                    } else {
                        backAngle = smallArcsResults.get(i - 2).getSecondAngle();
                    }
                }
                tangentBuilderResults.add(tangentBuilder.buildTangent(
                        verticesWithTangentAnglesList.get(i).getLeft(),
                        verticesWithTangentAnglesList.get(i).getRight(),
                        forwardAngle,
                        backAngle
                ));
            }
        }

        List<DoubleFunction<DoubleMatrix>> rotationsOnSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            if (verticesWithTangentAnglesList.get(i).getRight() != null &&
                    verticesWithTangentAnglesList.get(i + 1).getRight() != null) {
                DoubleFunction<DoubleMatrix> firstDeformedCurve = deformationCreator.deformCurves(
                        tangentBuilderResults.get(i).getForwardRotation(),
                        bigArcsResults.get(i).getRotation(), degree);
                DoubleFunction<DoubleMatrix> secondDeformedCurve = deformReversedCurves(
                        deformationCreator, bigArcsResults.get(i).getRotation(),
                        tangentBuilderResults.get(i + 1).getBackRotation(), degree);
                rotationsOnSegments.add(deformationCreator.deformCurves(
                        firstDeformedCurve, secondDeformedCurve, degree));
            } else if (verticesWithTangentAnglesList.get(i).getRight() != null) {
                if (i == k - 2) {
                    rotationsOnSegments.add(deformationCreator.deformCurves(
                            tangentBuilderResults.get(i).getForwardRotation(),
                            bigArcsResults.get(i).getRotation(), degree));
                } else {
                    rotationsOnSegments.add(deformationCreator.deformCurves(
                            tangentBuilderResults.get(i).getForwardRotation(),
                            smallArcsResults.get(i).getFirstRotation(), degree));
                }
            } else if (verticesWithTangentAnglesList.get(i + 1).getRight() != null) {
                if (i == 0) {
                    rotationsOnSegments.add(deformReversedCurves(
                            deformationCreator, bigArcsResults.get(i).getRotation(),
                            tangentBuilderResults.get(i + 1).getBackRotation(), degree));
                } else {
                    rotationsOnSegments.add(deformReversedCurves(
                            deformationCreator, smallArcsResults.get(i - 1).getSecondRotation(),
                            tangentBuilderResults.get(i + 1).getBackRotation(), degree));
                }
            } else {
                if (i == 0) {
                    rotationsOnSegments.add(smallArcsResults.get(i).getFirstRotation());
                } else if (i == k - 2) {
                    rotationsOnSegments.add(deformationCreator.deformCurves(
                            smallArcsResults.get(i - 1).getSecondRotation(),
                            smallArcsResults.get(i).getFirstRotation(), degree));
                } else {
                    rotationsOnSegments.add(smallArcsResults.get(i - 1).getSecondRotation());
                }
            }
        }

        return null;
    }

    private DoubleFunction<DoubleMatrix> deformReversedCurves(CurvesDeformationCreator deformationCreator,
                                                              DoubleFunction<DoubleMatrix> firstRotationNotReversed,
                                                              DoubleFunction<DoubleMatrix> secondRotation,
                                                              int degree) {
        DoubleMatrix firstRotationFixMatrix = RotationCreator.getInstance()
                .createReversedRotationByRotation(firstRotationNotReversed.apply(1.0));
        DoubleFunction<DoubleMatrix> firstRotation = new DoubleFunction<>(
                u -> firstRotationFixMatrix.multiply(firstRotationNotReversed.apply(1.0 - u)),
                0.0, 1.0
        );
        DoubleFunction<DoubleMatrix> resultRotationNotReversed = deformationCreator
                .deformCurves(secondRotation, firstRotation, degree);
        DoubleMatrix resultRotationFixMatrix = RotationCreator.getInstance()
                .createReversedRotationByRotation(resultRotationNotReversed.apply(1.0));
        DoubleFunction<DoubleMatrix> resultRotation = new DoubleFunction<>(
                u -> resultRotationFixMatrix.multiply(resultRotationNotReversed.apply(1.0 - u)),
                0.0, 1.0
        );
        return resultRotation;
    }

    @Override
    protected void validateVerticesList(List<Pair<ThreeDoubleVector, Double>> verticesWithTangentAnglesList) {
        super.validateVerticesList(verticesWithTangentAnglesList);
        double radius = verticesWithTangentAnglesList.get(0).getLeft().getNorm();
        if (verticesWithTangentAnglesList.stream().anyMatch(
                verticeWithTangentAngle -> ArithmeticOperations.doubleNotEquals(verticeWithTangentAngle.getLeft().getNorm(), radius))) {
            throw new InterpolationException("All vertices must belong to the same sphere");
        }
    }

}