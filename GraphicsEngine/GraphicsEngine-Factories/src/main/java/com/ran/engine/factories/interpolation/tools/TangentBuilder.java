package com.ran.engine.factories.interpolation.tools;

import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

public class TangentBuilder {

    private static final TangentBuilder INSTANCE = new TangentBuilder();

    public static TangentBuilder getInstance() {
        return INSTANCE;
    }

    public Result buildTangent(ThreeDoubleVector point,
                               double tangentAngle,
                               Double forwardRotationAngle,
                               Double backRotationAngle) {

        return null;
    }

    public static class Result {
        private final DoubleFunction<DoubleMatrix> forwardRotation;
        private final DoubleFunction<DoubleMatrix> backRotation;

        public Result(DoubleFunction<DoubleMatrix> forwardRotation, DoubleFunction<DoubleMatrix> backRotation) {
            this.forwardRotation = forwardRotation;
            this.backRotation = backRotation;
        }

        public DoubleFunction<DoubleMatrix> getForwardRotation() {
            return forwardRotation;
        }

        public DoubleFunction<DoubleMatrix> getBackRotation() {
            return backRotation;
        }
    }

}