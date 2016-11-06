package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.function.DoubleMultifunction;
import com.ran.dissertation.algebraic.matrix.DoubleMatrix;
import com.ran.dissertation.algebraic.vector.DoubleVector;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.util.ArrayList;
import java.util.List;

public class InterpolatedCurveCreator {

    private static final InterpolatedCurveCreator INSTANCE = new InterpolatedCurveCreator();
    
    public static InterpolatedCurveCreator getInstance() {
        return INSTANCE;
    }
    
    private InterpolatedCurveCreator() { }
    
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> vertices,
            int degree, double t0, double t1) {
        validateVertices(vertices);
        int k = vertices.size();
        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        
        List<DoubleFunction<DoubleMatrix>> rotationsOnSegments = new ArrayList<>(k - 1);
        List<Pair<Double, Double>> rotationAngles = new ArrayList<>(k - 2);
        SegmentsBuildingResult currentSegmentsBuildingResult =
                buildSegmentsBetweenVerticesOnSphere(vertices.get(0), vertices.get(1), vertices.get(2));
        rotationsOnSegments.add(currentSegmentsBuildingResult.getMatrixFunctions().getLeft());
        rotationAngles.add(currentSegmentsBuildingResult.getAngles());
        
        for (int i = 1; i < k - 2; i++) {
            SegmentsBuildingResult nextSegmentsBuildingResult =
                    buildSegmentsBetweenVerticesOnSphere(vertices.get(i), vertices.get(i + 1), vertices.get(i + 2));
            DoubleFunction<DoubleMatrix> deformedFunction = deformationCreator.deformCurves(
                    currentSegmentsBuildingResult.getMatrixFunctions().getRight(),
                    nextSegmentsBuildingResult.getMatrixFunctions().getLeft(), degree);
            rotationsOnSegments.add(deformedFunction);
            rotationAngles.add(nextSegmentsBuildingResult.getAngles());
            currentSegmentsBuildingResult = nextSegmentsBuildingResult;
        }
        rotationsOnSegments.add(currentSegmentsBuildingResult.getMatrixFunctions().getRight());
        
        List<Double> timeMoments = countTimeMoments(rotationAngles, t0, t1, k);
        List<DoubleFunction<ThreeDoubleVector>> curveSegments = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            double startTime = timeMoments.get(i);
            double endTime = timeMoments.get(i + 1);
            DoubleFunction<DoubleMatrix> currentRotation = rotationsOnSegments.get(i);
            DoubleVector currentVertice = vertices.get(i).getDoubleVector();
            DoubleFunction<ThreeDoubleVector> curveSegmentWithoutAligning = new DoubleFunction<>(
                    point -> new ThreeDoubleVector(currentRotation.apply(point).multiply(currentVertice)), 0.0, 1.0
            );
            DoubleFunction<ThreeDoubleVector> alignedCurveSegment =
                    curveSegmentWithoutAligning.superposition(buildAligningFunction(startTime, endTime));
            curveSegments.add(alignedCurveSegment);
        }
        return DoubleMultifunction.makeMultifunction(curveSegments);
    }
    
    private void validateVertices(List<ThreeDoubleVector> vertices) {
        if (vertices.size() < 3) {
            throw new AlgebraicException("Interpolation requires at least 3 vertices");
        }
        double radius = vertices.get(0).getNorm();
        if (vertices.stream().anyMatch(vertice -> ArithmeticOperations.doubleNotEquals(vertice.getNorm(), radius))) {
            throw new AlgebraicException("All vertices must belong to the same sphere");
        }
    }
    
    private SegmentsBuildingResult buildSegmentsBetweenVerticesOnSphere(
            ThreeDoubleVector p1, ThreeDoubleVector p2, ThreeDoubleVector p3) {
        ThreeDoubleVector a = (p3.substract(p2)).multiply(p1.substract(p2));
        double aNorm = a.getNorm();
        if (ArithmeticOperations.doubleEquals(aNorm, 0.0)) {
            throw new AlgebraicException("Every three sequential vertices must not coincide");
        }
        
        double mixedProduction = p1.mixedMultiply(p2, p3);
        ThreeDoubleVector n = a.multiply(1.0 / aNorm);
        ThreeDoubleVector c = n.multiply(mixedProduction / aNorm);
        
        ThreeDoubleVector r1 = p1.substract(c);
        ThreeDoubleVector r2 = p2.substract(c);
        ThreeDoubleVector r3 = p3.substract(c);
        
        ThreeDoubleVector n1 = r1.multiply(r2);
        ThreeDoubleVector n2 = r2.multiply(r3);
        
        double n1Norm = n1.getNorm();
        double n2Norm = n2.getNorm();
        double s1 = r1.scalarMultiply(r2);
        double s2 = r2.scalarMultiply(r3);
        
        double firstAtan2 = Math.atan2(n1Norm, s1);
        double phi = -(n1.scalarMultiply(n) > 0 ? firstAtan2 : 2 * Math.PI - firstAtan2);
        
        double secondAtan2 = Math.atan2(n2Norm, s2);
        double psi = -(n2.scalarMultiply(n) > 0 ? secondAtan2 : 2 * Math.PI - secondAtan2);
        
        return new SegmentsBuildingResult(new Pair<>(
                new DoubleFunction<>(point -> RotationCreator.getInstance().createRotation(n, point * phi), 0.0, 1.0),
                new DoubleFunction<>(point -> RotationCreator.getInstance().createRotation(n, point * psi), 0.0, 1.0)),
                new Pair<>(phi, psi));
    }
    
    private static class SegmentsBuildingResult {
        private final Pair<DoubleFunction<DoubleMatrix>, DoubleFunction<DoubleMatrix>> matrixFunctions;
        private final Pair<Double, Double> angles;

        public SegmentsBuildingResult(Pair<DoubleFunction<DoubleMatrix>, DoubleFunction<DoubleMatrix>> matrixFunctions,
                Pair<Double, Double> angles) {
            this.matrixFunctions = matrixFunctions;
            this.angles = angles;
        }

        public Pair<DoubleFunction<DoubleMatrix>, DoubleFunction<DoubleMatrix>> getMatrixFunctions() {
            return matrixFunctions;
        }

        public Pair<Double, Double> getAngles() {
            return angles;
        }
    }
    
    private List<Double> countTimeMoments(List<Pair<Double, Double>> rotationAngles, double t0, double t1, int k) {
        List<Double> timeMoments = new ArrayList<>(k);
        timeMoments.add(t0);
        timeMoments.add(t1);
        double timeBeforePrevious = t0;
        double timePrevous = t1;
        for (Pair<Double, Double> angles: rotationAngles) {
            double phi = angles.getLeft();
            double psi = angles.getRight();
            double timeNext = timePrevous + (timePrevous - timeBeforePrevious) * (psi / phi);
            timeMoments.add(timeNext);
            timeBeforePrevious = timePrevous;
            timePrevous = timeNext;
        }
        return timeMoments;
    }
    
    private DoubleFunction<SingleDouble> buildAligningFunction(double t0, double t1) {
        return new DoubleFunction<>(point -> new SingleDouble((point - t0) / (t1 - t0)), t0, t1);
    }
    
}