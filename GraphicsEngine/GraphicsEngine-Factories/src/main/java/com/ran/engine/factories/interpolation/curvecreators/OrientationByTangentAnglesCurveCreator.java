package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.function.DoubleMultifunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.factories.interpolation.exception.InterpolationException;
import com.ran.engine.factories.interpolation.input.SimpleInputParameters;
import com.ran.engine.factories.interpolation.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrientationByTangentAnglesCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<Quaternion, Double>, Quaternion, SimpleInputParameters> {

    private static final Logger LOG = LoggerFactory.getLogger(OrientationByTangentAnglesCurveCreator.class);

    private static final OrientationByTangentAnglesCurveCreator INSTANCE = new OrientationByTangentAnglesCurveCreator();

    public static OrientationByTangentAnglesCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<Quaternion> interpolateCurve(
            List<Pair<Quaternion, Double>> quaternionsWithTangentAnglesList, SimpleInputParameters parameters, int degree) {
        LOG.trace("quaternionsWithTangentAnglesList = {}, parameters = {}, degree = {}",
                quaternionsWithTangentAnglesList, parameters, degree);
        validateVerticesList(quaternionsWithTangentAnglesList);

        double t0 = parameters.getT0();
        double t1 = parameters.getT1();
        int k = quaternionsWithTangentAnglesList.size();
        LOG.trace("t0 = {}, t1 = {}, k = {}", t0, t1, k);

        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        OrientationArcsBuilder arcsBuilder = OrientationArcsBuilder.getInstance();
        OrientationBigArcsBuilder bigArcsBuilder = OrientationBigArcsBuilder.getInstance();
        TangentOrientationBuilder tangentBuilder = TangentOrientationBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();

        LOG.trace("Before calling OrientationArcsBuilder");
        List<OrientationArcsBuilder.Result> smallArcsResults = new ArrayList<>(k - 2);
        for (int i = 1; i < k - 1; i++) {
            smallArcsResults.add(arcsBuilder.buildArcsBetweenQuaternionsOnThreeDimensionalSphere(
                    quaternionsWithTangentAnglesList.get(i - 1).getLeft(),
                    quaternionsWithTangentAnglesList.get(i).getLeft(),
                    quaternionsWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }
        LOG.trace("smallArcsResults = {}", smallArcsResults);

        LOG.trace("Before calling OrientationBigArcsBuilder");
        List<OrientationBigArcsBuilder.Result> bigArcsResults = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            bigArcsResults.add(bigArcsBuilder.buildOrientationBigArcsBetweenQuaternions(
                    quaternionsWithTangentAnglesList.get(i).getLeft(),
                    quaternionsWithTangentAnglesList.get(i + 1).getLeft()
            ));
        }
        LOG.trace("bigArcsResults = {}", bigArcsResults);

        LOG.trace("Before calling TangentOrientationBuilder");
        List<TangentOrientationBuilder.Result> tangentBuilderResults = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            LOG.trace("Point #{} = {}", i, quaternionsWithTangentAnglesList.get(i));
            if (quaternionsWithTangentAnglesList.get(i).getRight() == null) {
                LOG.trace("Tangle angle is null");
                tangentBuilderResults.add(null);
            } else {
                LOG.trace("Processing angles");
                Double forwardAngle = null, backAngle = null;
                if (i + 1 < k) {
                    if (quaternionsWithTangentAnglesList.get(i + 1).getRight() != null || i + 1 == k - 1) {
                        forwardAngle = Math.abs(bigArcsResults.get(i).getAngle());
                    } else {
                        forwardAngle = Math.abs(smallArcsResults.get(i).getFirstAngle());
                    }
                }
                if (i - 1 >= 0) {
                    if (quaternionsWithTangentAnglesList.get(i - 1).getRight() != null || i - 1 == 0) {
                        backAngle = Math.abs(bigArcsResults.get(i - 1).getAngle());
                    } else {
                        backAngle = Math.abs(smallArcsResults.get(i - 2).getSecondAngle());
                    }
                }
                LOG.trace("forwardAngle = {}, backAngle = {}", forwardAngle, backAngle);
                tangentBuilderResults.add(tangentBuilder.buildTangentOrientation(
                        quaternionsWithTangentAnglesList.get(i).getLeft(),
                        quaternionsWithTangentAnglesList.get(i).getRight(),
                        forwardAngle,
                        backAngle
                ));
            }
        }

        List<Pair<Double, Double>> rotationAngles = new ArrayList<>(k - 2);
        for (int i = 0; i < k - 2; i++) {
            if (quaternionsWithTangentAnglesList.get(i + 1).getRight() == null) {
                rotationAngles.add(smallArcsResults.get(i).getAngles());
            } else {
                rotationAngles.add(tangentBuilderResults.get(i + 1).getAngles());
            }
        }
        LOG.trace("rotationAngles = {}", rotationAngles);

        LOG.trace("Before building rotationsOnSegments");
        List<DoubleFunction<Quaternion>> rotationsOnSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            LOG.trace("Building rotation between points {} and {}: {} and {}",
                    i, i + 1, quaternionsWithTangentAnglesList.get(i), quaternionsWithTangentAnglesList.get(i + 1));
            if (quaternionsWithTangentAnglesList.get(i).getRight() != null &&
                    quaternionsWithTangentAnglesList.get(i + 1).getRight() != null) {
                LOG.trace("Angles are set on both points");
                DoubleFunction<Quaternion> firstDeformedCurve = deformationCreator.deformCurves(
                        tangentBuilderResults.get(i).getForwardRotation(),
                        bigArcsResults.get(i).getRotation(), degree);
                DoubleFunction<Quaternion> secondDeformedCurve = deformationCreator.deformCurvesWithCommonEnd(
                        bigArcsResults.get(i).getRotation(),
                        tangentBuilderResults.get(i + 1).getBackRotation(), degree);
                rotationsOnSegments.add(deformationCreator.deformCurves(
                        firstDeformedCurve, secondDeformedCurve, degree));
            } else if (quaternionsWithTangentAnglesList.get(i).getRight() != null) {
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
            } else if (quaternionsWithTangentAnglesList.get(i + 1).getRight() != null) {
                LOG.trace("Angle is set only on the second point");
                if (i == 0) {
                    rotationsOnSegments.add(deformationCreator.deformCurvesWithCommonEnd(
                            bigArcsResults.get(i).getRotation(),
                            tangentBuilderResults.get(i + 1).getBackRotation(), degree));
                } else {
                    rotationsOnSegments.add(deformationCreator.deformCurvesWithCommonEnd(
                            smallArcsResults.get(i - 1).getSecondRotation(),
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
        List<DoubleFunction<Quaternion>> curveSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            LOG.trace("Building curve between {} and {} points");
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<Quaternion> currentRotation = rotationsOnSegments.get(i);
            Quaternion currentOrientation = quaternionsWithTangentAnglesList.get(i).getLeft();
            LOG.trace("startTime = {}, endTime = {}, currentOrientation = {}", startTime, endTime, currentOrientation);
            DoubleFunction<Quaternion> curveSegmentWithoutAligning = new DoubleFunction<>(
                    point -> currentRotation.apply(point).multiply(currentOrientation), 0.0, 1.0
            );
            DoubleFunction<Quaternion> alignedCurveSegment =
                    curveSegmentWithoutAligning.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(curveSegments);
    }

    @Override
    protected void validateVerticesList(List<Pair<Quaternion, Double>> quaternionsWithTangentAnglesList) {
        super.validateVerticesList(quaternionsWithTangentAnglesList);
        if (quaternionsWithTangentAnglesList.stream().anyMatch(
                quaternionWithTangentAngle -> !quaternionWithTangentAngle.getLeft().isIdentity())) {
            throw new InterpolationException("All quaternions must be identity");
        }
    }

}
