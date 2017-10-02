package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.LineEvaluator;
import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.common.VectorManipulator;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.line.Line;
import com.ran.engine.algebra.vector.TwoDoubleVector;

public class CircleArcsBuilder {

    private static CircleArcsBuilder INSTANCE = new CircleArcsBuilder();

    public static CircleArcsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildCircle(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint, TwoDoubleVector thirdPoint) {
        if (LineEvaluator.arePointsOnOneLine(firstPoint, secondPoint, thirdPoint)) {
            SegmentsBuilder segmentsBuilder = SegmentsBuilder.getInstance();
            SegmentsBuilder.Result firstSegmentsResult = segmentsBuilder.buildSegment(firstPoint, secondPoint);
            SegmentsBuilder.Result secondSegmentsResult = segmentsBuilder.buildSegment(secondPoint, thirdPoint);
            return new Result(
                    firstSegmentsResult.getSegment(), secondSegmentsResult.getSegment(),
                    0.0, 0.0,
                    firstSegmentsResult.getLength(), secondSegmentsResult.getLength()
            );
        }

        Line firstPerpendicular = LineEvaluator.evaluateMiddlePerpendicularLine(firstPoint, secondPoint);
        Line secondPerpendicular = LineEvaluator.evaluateMiddlePerpendicularLine(secondPoint, thirdPoint);
        TwoDoubleVector circleCenter = LineEvaluator.evaluateLinesIntersection(
                firstPerpendicular, secondPerpendicular);

        TwoDoubleVector firstVector = firstPoint.substract(circleCenter);
        TwoDoubleVector secondVector = secondPoint.substract(circleCenter);
        TwoDoubleVector thirdVector = thirdPoint.substract(circleCenter);

        double firstAngle = VectorManipulator.countAngleBetweenVectors(firstVector, secondVector);
        double secondAngle = VectorManipulator.countAngleBetweenVectors(secondVector, thirdVector);
        if (firstAngle + secondAngle > Math.PI * 2.0) {
            firstAngle -= Math.PI * 2.0;
            secondAngle -= Math.PI * 2.0;
        }

        double radius = firstVector.getNorm();
        double firstLength = Math.abs(firstAngle) * radius / 2.0;
        double secondLength = Math.abs(secondAngle) * radius / 2.0;

        DoubleFunction<TwoDoubleVector> firstArc = buildArc(circleCenter, firstVector, radius, firstAngle);
        DoubleFunction<TwoDoubleVector> secondArc = buildArc(circleCenter, secondVector, radius, secondAngle);

        return new Result(
                firstArc, secondArc,
                firstAngle, secondAngle,
                firstLength, secondLength
        );
    }

    private DoubleFunction<TwoDoubleVector> buildArc(TwoDoubleVector center, TwoDoubleVector startVector,
                                                     double radius, double angle) {
        double startAngle = VectorManipulator.countVectorAngle(startVector);
        return new DoubleFunction<>(point -> {
                double currentAngle = startAngle + angle * point;
                double x = Math.cos(currentAngle) * radius;
                double y = Math.sin(currentAngle) * radius;
                return new TwoDoubleVector(center.getX() + x, center.getY() + y);
            }, 0.0, 1.0);
    }

    public static class Result {

        private final DoubleFunction<TwoDoubleVector> firstArc;
        private final DoubleFunction<TwoDoubleVector> secondArc;
        private final double firstAngle;
        private final double secondAngle;
        private final double firstArcLength;
        private final double secondArcLength;

        public Result(DoubleFunction<TwoDoubleVector> firstArc, DoubleFunction<TwoDoubleVector> secondArc,
                      double firstAngle, double secondAngle, double firstArcLength, double secondArcLength) {
            this.firstArc = firstArc;
            this.secondArc = secondArc;
            this.firstAngle = firstAngle;
            this.secondAngle = secondAngle;
            this.firstArcLength = firstArcLength;
            this.secondArcLength = secondArcLength;
        }

        public DoubleFunction<TwoDoubleVector> getFirstArc() {
            return firstArc;
        }

        public DoubleFunction<TwoDoubleVector> getSecondArc() {
            return secondArc;
        }

        public double getFirstAngle() {
            return firstAngle;
        }

        public double getSecondAngle() {
            return secondAngle;
        }

        public double getFirstArcLength() {
            return firstArcLength;
        }

        public double getSecondArcLength() {
            return secondArcLength;
        }

        public Pair<Double, Double> getAngles() {
            return new Pair<>(firstAngle, secondAngle);
        }

        public Pair<Double, Double> getArcsLengths() {
            return new Pair<>(firstArcLength, secondArcLength);
        }

    }

}