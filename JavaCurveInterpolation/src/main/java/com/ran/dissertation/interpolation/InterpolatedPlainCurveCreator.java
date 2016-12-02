package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.function.DoubleMultifunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import java.util.ArrayList;
import java.util.List;

public class InterpolatedPlainCurveCreator {

    private static final InterpolatedPlainCurveCreator INSTANCE = new InterpolatedPlainCurveCreator();
    
    public static InterpolatedPlainCurveCreator getInstance() {
        return INSTANCE;
    }
    
    private InterpolatedPlainCurveCreator() { }
    
    public DoubleFunction<SingleDouble> interpolatePlainCurve(List<Pair<Double, Double>> pointsWithValues, int degree) {
        int k = pointsWithValues.size();
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
            parabolasList.add(buildParabola(pointsWithValues.get(i),
                    pointsWithValues.get(i + 1), pointsWithValues.get(i + 2)));
        }
        List<DoubleFunction<SingleDouble>> pList = new ArrayList<>(k - 3);
        List<DoubleFunction<SingleDouble>> qList = new ArrayList<>(k - 3);
        for (int i = 0; i < k - 3; i++) {
            pList.add(new DoubleFunction<>(parabolasList.get(i)
                    .superposition(xFunctionsList.get(i))
                    .substract(fConstantList.get(i)))
            );
            qList.add(new DoubleFunction<>(parabolasList.get(i + 1)
                    .superposition(xFunctionsList.get(i))
                    .substract(fConstantList.get(i)))
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
    
    private DoubleFunction<SingleDouble> buildConvertFunction(double x0, double x1) {
        return new DoubleFunction<>(point -> new SingleDouble(x0 + (x1 - x0) * point), 0.0, 1.0);
    }
    
    private DoubleFunction<SingleDouble> buildReverseConvertFunction(double x0, double x1) {
        return new DoubleFunction<>(point -> new SingleDouble((point - x0) / (x1 - x0)), x0, x1);
    }
    
    private DoubleFunction<SingleDouble> buildParabola(Pair<Double, Double> firstPoint,
            Pair<Double, Double> secondPoint, Pair<Double, Double> thirdPoint) {
        // Если на одной прямой, то вернуть прямую, иначе - параболу
        double x1 = firstPoint.getLeft();
        double x2 = secondPoint.getLeft();
        double x3 = thirdPoint.getLeft();
        
        double f1 = firstPoint.getRight();
        double f2 = secondPoint.getRight();
        double f3 = thirdPoint.getRight();
        
        double a11 = x2 * x2 - x1 * x1;
        double a12 = x2 - x1;
        double a21 = x3 * x3 - x1 * x1;
        double a22 = x3 - x1;
        
        double f1New = f2 - f1;
        double f2New = f3 - f1;
        
        double a = (f2New * a11 - f1New * a21) / (a22 * a11 - a12 * a21);
        double b = (f1New  - a12 * a) / a11;
        double c = f1 - x1 * x1 * a - x1 * b;
        return new DoubleFunction<>(point -> new SingleDouble(a * point * point + b * point + c), x1, x3);
    }
    
}