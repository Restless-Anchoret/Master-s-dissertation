package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.tools.*;
import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.function.DoubleMultifunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.DoubleVector;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InterpolatedByTangentAnglesSphereCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<ThreeDoubleVector, Double>, ThreeDoubleVector, SimpleInputParameters> {

    private static final Logger LOG = LoggerFactory.getLogger(InterpolatedByTangentAnglesSphereCurveCreator.class);

    private static final InterpolatedByTangentAnglesSphereCurveCreator INSTANCE = new InterpolatedByTangentAnglesSphereCurveCreator();

    public static InterpolatedByTangentAnglesSphereCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<Pair<ThreeDoubleVector, Double>> verticesWithTangentAnglesList,
                                                              SimpleInputParameters parameters, int degree) {
        LOG.trace("verticesWithTangentAnglesList = {}, parameters = {}, degree = {}",
                verticesWithTangentAnglesList, parameters, degree);
        validateVerticesList(verticesWithTangentAnglesList);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = verticesWithTangentAnglesList.size();
        LOG.trace("t0 = {}, t1 = {}, k = {}", t0, t1, k);

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        ArcsBuilder arcsBuilder = ArcsBuilder.getInstance();
        BigArcsBuilder bigArcsBuilder = BigArcsBuilder.getInstance();
        TangentBuilder tangentBuilder = TangentBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        LOG.trace("Before calling ArcsBuilder");
        List<ArcsBuilder.Result> smallArcsResults = new ArrayList<>(k - 2);
        for (int i = 1; i < k - 1; i++) {
            smallArcsResults.add(arcsBuilder.buildArcsBetweenVerticesOnSphere(
                    verticesWithTangentAnglesList.get(i - 1).getLeft(),
                    verticesWithTangentAnglesList.get(i).getLeft(),
                    verticesWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }
        LOG.trace("smallArcsResults = {}", smallArcsResults);

        LOG.trace("Before calling BigArcsBuilder");
        List<BigArcsBuilder.Result> bigArcsResults = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            bigArcsResults.add(bigArcsBuilder.buildBigArcBetweenVerticesOnSphere(
                    verticesWithTangentAnglesList.get(i).getLeft(),
                    verticesWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }
        LOG.trace("bigArcsResults = {}", bigArcsResults);

        LOG.trace("Before calling TangentBuilder");
        List<TangentBuilder.Result> tangentBuilderResults = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            LOG.trace("Point #{} = {}", i, verticesWithTangentAnglesList.get(i));
            if (verticesWithTangentAnglesList.get(i).getRight() == null) {
                LOG.trace("Tangle angle is null");
                tangentBuilderResults.add(null);
            } else {
                LOG.trace("Processing angles");
                Double forwardAngle = null, backAngle = null;
                if (i + 1 < k) {
                    if (verticesWithTangentAnglesList.get(i + 1).getRight() != null || i + 1 == k - 1) {
                        forwardAngle = Math.abs(bigArcsResults.get(i).getAngle());
                    } else {
                        forwardAngle = Math.abs(smallArcsResults.get(i).getFirstAngle());
                    }
                }
                if (i - 1 >= 0) {
                    if (verticesWithTangentAnglesList.get(i - 1).getRight() != null || i - 1 == 0) {
                        backAngle = Math.abs(bigArcsResults.get(i - 1).getAngle());
                    } else {
                        backAngle = Math.abs(smallArcsResults.get(i - 2).getSecondAngle());
                    }
                }
                LOG.trace("forwardAngle = {}, backAngle = {}", forwardAngle, backAngle);
                tangentBuilderResults.add(tangentBuilder.buildTangent(
                        verticesWithTangentAnglesList.get(i).getLeft(),
                        verticesWithTangentAnglesList.get(i).getRight(),
                        forwardAngle,
                        backAngle
                ));
            }
        }

        List<Pair<Double, Double>> rotationAngles = new ArrayList<>(k - 2);
        for (int i = 0; i < k - 2; i++) {
            if (verticesWithTangentAnglesList.get(i + 1).getRight() == null) {
                rotationAngles.add(smallArcsResults.get(i).getAngles());
            } else {
                rotationAngles.add(tangentBuilderResults.get(i + 1).getAngles());
            }
        }
        LOG.trace("rotationAngles = {}", rotationAngles);

        LOG.trace("Before building rotationsOnSegments");
        List<DoubleFunction<DoubleMatrix>> rotationsOnSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            LOG.trace("Building rotation between points {} and {}: {} and {}",
                    i, i + 1, verticesWithTangentAnglesList.get(i), verticesWithTangentAnglesList.get(i + 1));
            if (verticesWithTangentAnglesList.get(i).getRight() != null &&
                    verticesWithTangentAnglesList.get(i + 1).getRight() != null) {
                LOG.trace("Angles are set on both points");
                DoubleFunction<DoubleMatrix> firstDeformedCurve = deformationCreator.deformCurves(
                        tangentBuilderResults.get(i).getForwardRotation(),
                        bigArcsResults.get(i).getRotation(), degree);
                DoubleFunction<DoubleMatrix> secondDeformedCurve = deformReversedCurves(
                        deformationCreator, bigArcsResults.get(i).getRotation(),
                        tangentBuilderResults.get(i + 1).getBackRotation(), degree);
                rotationsOnSegments.add(deformationCreator.deformCurves(
                        firstDeformedCurve, secondDeformedCurve, degree));
            } else if (verticesWithTangentAnglesList.get(i).getRight() != null) {
                LOG.trace("Angle is set only on the first point");
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
                LOG.trace("Angle is set only on the second point");
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
                LOG.trace("Angles are not set on both points");
                if (i == 0) {
                    rotationsOnSegments.add(smallArcsResults.get(i).getFirstRotation());
                } else if (i == k - 2) {
                    rotationsOnSegments.add(smallArcsResults.get(i - 1).getSecondRotation());
                } else {
                    rotationsOnSegments.add(deformationCreator.deformCurves(
                            smallArcsResults.get(i - 1).getSecondRotation(),
                            smallArcsResults.get(i).getFirstRotation(), degree));
                }
            }
        }

        List<Double> timeMoments = timeMomentsUtil.countTimeMoments(rotationAngles, t0, t1, k);
        LOG.trace("timeMoments = {}", timeMoments);

        LOG.trace("Before building curveSegments");
        List<DoubleFunction<ThreeDoubleVector>> curveSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            LOG.trace("Building curve between {} and {} points");
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<DoubleMatrix> currentRotation = rotationsOnSegments.get(i);
            DoubleVector currentVertice = verticesWithTangentAnglesList.get(i).getLeft().getDoubleVector();
            LOG.trace("startTime = {}, endTime = {}, currentVertice = {}", startTime, endTime, currentVertice);
            DoubleFunction<ThreeDoubleVector> curveSegmentWithoutAligning = new DoubleFunction<>(
                    point -> new ThreeDoubleVector(currentRotation.apply(point).multiply(currentVertice)), 0.0, 1.0
            );
            DoubleFunction<ThreeDoubleVector> alignedCurveSegment =
                    curveSegmentWithoutAligning.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(curveSegments);
    }

    private DoubleFunction<DoubleMatrix> deformReversedCurves(CurvesDeformationCreator deformationCreator,
                                                              DoubleFunction<DoubleMatrix> firstRotationNotReversed,
                                                              DoubleFunction<DoubleMatrix> secondRotation,
                                                              int degree) {
        LOG.trace("Before deforming of reversed curves");
        DoubleMatrix firstRotationFixMatrix = RotationCreator.getInstance()
                .createReversedRotationByRotation(firstRotationNotReversed.apply(1.0));
        LOG.trace("firstRotationFixMatrix = {}", firstRotationFixMatrix);
        DoubleFunction<DoubleMatrix> firstRotation = new DoubleFunction<>(
                u -> firstRotationFixMatrix.multiply(firstRotationNotReversed.apply(1.0 - u)),
                0.0, 1.0
        );
        DoubleFunction<DoubleMatrix> resultRotationNotReversed = deformationCreator
                .deformCurves(secondRotation, firstRotation, degree);
        DoubleMatrix resultRotationFixMatrix = RotationCreator.getInstance()
                .createReversedRotationByRotation(resultRotationNotReversed.apply(1.0));
        LOG.trace("resultRotationFixMatrix = {}", resultRotationFixMatrix);
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
            String message = "All vertices must belong to the same sphere";
            LOG.error(message);
            throw new InterpolationException(message);
        }
    }

}