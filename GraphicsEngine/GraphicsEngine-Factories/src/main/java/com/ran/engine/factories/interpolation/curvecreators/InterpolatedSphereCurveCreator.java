package com.ran.engine.factories.interpolation.curvecreators;

import com.ran.engine.factories.interpolation.ArcsBuilder;
import com.ran.engine.factories.interpolation.CurvesDeformationCreator;
import com.ran.engine.factories.interpolation.TimeMomentsUtil;
import com.ran.engine.rendering.algebraic.common.ArithmeticOperations;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.exception.AlgebraicException;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.function.DoubleMultifunction;
import com.ran.engine.rendering.algebraic.matrix.DoubleMatrix;
import com.ran.engine.rendering.algebraic.vector.DoubleVector;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class InterpolatedSphereCurveCreator extends AbstractInterpolatedCurveCreator<
        ThreeDoubleVector, ThreeDoubleVector, Pair<Double, Double>> {

    private static final InterpolatedSphereCurveCreator INSTANCE = new InterpolatedSphereCurveCreator();

    public static InterpolatedSphereCurveCreator getInstance() {
        return INSTANCE;
    }

    @Override
    public DoubleFunction<ThreeDoubleVector> interpolateCurve(List<ThreeDoubleVector> vertices,
            Pair<Double, Double> parameters, int degree) {
        double t0 = parameters.getLeft();
        double t1 = parameters.getRight();
        validateVertices(vertices);
        int k = vertices.size();
        CurvesDeformationCreator deformationCreator = CurvesDeformationCreator.getInstance();
        ArcsBuilder arcsBuilder = ArcsBuilder.getInstance();
        TimeMomentsUtil timeMomentsUtil = TimeMomentsUtil.getInstance();
        
        List<DoubleFunction<DoubleMatrix>> rotationsOnSegments = new ArrayList<>(k - 1);
        List<Pair<Double, Double>> rotationAngles = new ArrayList<>(k - 2);
        ArcsBuilder.Result currentArcsBuildingResult =
                arcsBuilder.buildArcsBetweenVerticesOnSphere(vertices.get(0), vertices.get(1), vertices.get(2));
        rotationsOnSegments.add(currentArcsBuildingResult.getFirstRotation());
        rotationAngles.add(currentArcsBuildingResult.getAngles());
        
        for (int i = 1; i < k - 2; i++) {
            ArcsBuilder.Result nextArcsBuildingResult =
                    arcsBuilder.buildArcsBetweenVerticesOnSphere(vertices.get(i), vertices.get(i + 1), vertices.get(i + 2));
            DoubleFunction<DoubleMatrix> deformedFunction = deformationCreator.deformCurves(
                    currentArcsBuildingResult.getSecondRotation(),
                    nextArcsBuildingResult.getFirstRotation(), degree);
            rotationsOnSegments.add(deformedFunction);
            rotationAngles.add(nextArcsBuildingResult.getAngles());
            currentArcsBuildingResult = nextArcsBuildingResult;
        }
        rotationsOnSegments.add(currentArcsBuildingResult.getSecondRotation());
        
        List<Double> timeMoments = timeMomentsUtil.countTimeMoments(rotationAngles, t0, t1, k);
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
                    curveSegmentWithoutAligning.superposition(timeMomentsUtil.buildAligningFunction(startTime, endTime));
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
    
}