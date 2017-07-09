package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;

public class SegmentsBuilder {

    private static SegmentsBuilder INSTANCE = new SegmentsBuilder();

    public static SegmentsBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildSegment(TwoDoubleVector firstPoint, TwoDoubleVector secondPoint) {
        return null;
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