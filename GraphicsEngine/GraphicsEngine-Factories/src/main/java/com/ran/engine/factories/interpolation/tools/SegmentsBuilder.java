package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

public class SegmentsBuilder {

    private static SegmentsBuilder INSTANCE = new SegmentsBuilder();

    public static SegmentsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildSegment(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint) {
        DoubleFunction<TwoDoubleVector> segment = countSegment(firstPoint, secondPoint);
        double length = countLength(firstPoint, secondPoint);
        return new Result(segment, length);
    }

    private DoubleFunction<TwoDoubleVector> countSegment(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint) {
        return new DoubleFunction<>(
                point -> new TwoDoubleVector(firstPoint.getX() + (secondPoint.getX() - firstPoint.getX()) * point,
                    firstPoint.getY() + (secondPoint.getY() - firstPoint.getY()) * point),
                    0.0, 1.0);
    }

    private double countLength(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint) {
        double xDiff = firstPoint.getX() - secondPoint.getX();
        double yDiff = firstPoint.getY() - secondPoint.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public static class Result {

        private final DoubleFunction<TwoDoubleVector> segment;
        private final double length;

        public Result(DoubleFunction<TwoDoubleVector> segment, double length) {
            this.segment = segment;
            this.length = length;
        }

        public DoubleFunction<TwoDoubleVector> getSegment() {
            return segment;
        }

        public double getLength() {
            return length;
        }

    }

}