package com.ran.dissertation.interpolation.curvecreators;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.function.DoubleMultifunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.interpolation.CurvesDeformationCreator;
import com.ran.dissertation.interpolation.GroupMultiplicationOperationFactory;
import com.ran.dissertation.interpolation.ParabolaBuilder;
import java.util.ArrayList;
import java.util.List;

public class InterpolatedPlainCurveCreator extends AbstractInterpolatedCurveCreator<
        Pair<Double, Double>, SingleDouble, Object> {
    
    @Override
    public DoubleFunction<SingleDouble> interpolateCurve(List<Pair<Double, Double>> pointsWithValues,
            Object parameters, int degree) {
        validateVertices(pointsWithValues);
        int k = pointsWithValues.size();
        ParabolaBuilder parabolaBuilder = ParabolaBuilder.getInstance();
        List<DoubleFunction<SingleDouble>> xFunctionsList = new ArrayList<>(k - 1);
        List<DoubleFunction<SingleDouble>> uFunctionsList = new ArrayList<>(k - 1);
        for (int i = 0; i < k - 1; i++) {
            double x0 = pointsWithValues.get(i).getLeft();
            double x1 = pointsWithValues.get(i + 1).getLeft();
            xFunctionsList.add(buildConvertFunction(x0, x1));
            uFunctionsList.add(buildReverseConvertFunction(x0, x1));
        }
        List<DoubleFunction<SingleDouble>> fConstantList = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            fConstantList.add(DoubleFunction.createConstantFunction(new SingleDouble(pointsWithValues.get(i).getRight())));
        }
        List<DoubleFunction<SingleDouble>> parabolasList = new ArrayList<>(k - 2);
        for (int i = 0; i < k - 2; i++) {
            parabolasList.add(parabolaBuilder.buildParabolaByThreePoints(pointsWithValues.get(i),
                    pointsWithValues.get(i + 1), pointsWithValues.get(i + 2)));
        }
        List<DoubleFunction<SingleDouble>> pList = new ArrayList<>(k - 3);
        List<DoubleFunction<SingleDouble>> qList = new ArrayList<>(k - 3);
        for (int i = 0; i < k - 3; i++) {
            pList.add(new DoubleFunction<>(parabolasList.get(i)
                    .superposition(xFunctionsList.get(i + 1))
                    .substract(fConstantList.get(i + 1)))
            );
            qList.add(new DoubleFunction<>(parabolasList.get(i + 1)
                    .superposition(xFunctionsList.get(i + 1))
                    .substract(fConstantList.get(i + 1)))
            );
        }
        List<DoubleFunction<SingleDouble>> rList = new ArrayList<>(k - 3);
        for (int i = 0; i < k - 3; i++) {
            rList.add(CurvesDeformationCreator.getInstance().deformCurves(
                    pList.get(i), qList.get(i), degree,
                    GroupMultiplicationOperationFactory.getSummationOperation())
                    .superposition(uFunctionsList.get(i + 1))
                    .add(fConstantList.get(i + 1)));
        }
        List<DoubleFunction<SingleDouble>> resultSegmentsList = new ArrayList<>(k - 1);
        resultSegmentsList.add(new DoubleFunction<>(parabolasList.get(0), pointsWithValues.get(0).getLeft(),
            pointsWithValues.get(1).getLeft()));
        for (DoubleFunction<SingleDouble> function: rList) {
            resultSegmentsList.add(function);
        }
        resultSegmentsList.add(new DoubleFunction<>(parabolasList.get(k - 3), pointsWithValues.get(k - 2).getLeft(),
            pointsWithValues.get(k - 1).getLeft()));
        return DoubleMultifunction.makeMultifunction(resultSegmentsList);
    }
    
    private void validateVertices(List<Pair<Double, Double>> vertices) {
        if (vertices.size() < 3) {
            throw new AlgebraicException("Interpolation requires at least 3 vertices");
        }
        for (int i = 0; i < vertices.size() - 1; i++) {
            if (vertices.get(i).getLeft() >= vertices.get(i + 1).getLeft()) {
                throw new AlgebraicException("Grid of vertices is not valid");
            }
        }
    }
    
    private DoubleFunction<SingleDouble> buildConvertFunction(double x0, double x1) {
        return new DoubleFunction<>(point -> new SingleDouble(x0 + (x1 - x0) * point), 0.0, 1.0);
    }
    
    private DoubleFunction<SingleDouble> buildReverseConvertFunction(double x0, double x1) {
        return new DoubleFunction<>(point -> new SingleDouble((point - x0) / (x1 - x0)), x0, x1);
    }
    
}