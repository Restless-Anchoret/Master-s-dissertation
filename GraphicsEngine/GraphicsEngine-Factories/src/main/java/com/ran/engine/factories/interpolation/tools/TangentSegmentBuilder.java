package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.TwoDoubleVector;

public class TangentSegmentBuilder {

    private static final TangentSegmentBuilder INSTANCE = new TangentSegmentBuilder();

    public static TangentSegmentBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildTangent(TwoDoubleVector point,
                                              double tangentAngle,
                                              Double forwardLength,
                                              Double backLength) {
        DoubleFunction<TwoDoubleVector> forwardSegment = createTangentSegment(
                point, tangentAngle, forwardLength);
        DoubleFunction<TwoDoubleVector> backSegment = createTangentSegment(
                point, tangentAngle + Math.PI, backLength);
        return new Result(forwardSegment, backSegment, forwardLength, backLength);
    }

    private DoubleFunction<TwoDoubleVector> createTangentSegment(TwoDoubleVector point,
                                                                 double tangentAngle,
                                                                 Double segmentLength) {
        if (segmentLength == null) {
            return null;
        }
        TwoDoubleVector shiftVector = new TwoDoubleVector(
                segmentLength * Math.cos(tangentAngle),
                segmentLength * Math.sin(tangentAngle));
        TwoDoubleVector farPoint = point.add(shiftVector);
        return SegmentsBuilder.getInstance().buildSegment(point, farPoint).getSegment();
    }

    public static class Result {
        private DoubleFunction<TwoDoubleVector> forwardSegment;
        private DoubleFunction<TwoDoubleVector> backSegment;
        private Double forwardLength;
        private Double backLength;

        public Result(DoubleFunction<TwoDoubleVector> forwardSegment,
                      DoubleFunction<TwoDoubleVector> backSegment,
                      Double forwardLength, Double backLength) {
            this.forwardSegment = forwardSegment;
            this.backSegment = backSegment;
            this.forwardLength = forwardLength;
            this.backLength = backLength;
        }

        public DoubleFunction<TwoDoubleVector> getForwardSegment() {
            return forwardSegment;
        }

        public DoubleFunction<TwoDoubleVector> getBackSegment() {
            return backSegment;
        }

        public Double getForwardLength() {
            return forwardLength;
        }

        public Double getBackLength() {
            return backLength;
        }

        public Pair<Double, Double> getLengths() {
            return new Pair<>(forwardLength, backLength);
        }
    }

}
