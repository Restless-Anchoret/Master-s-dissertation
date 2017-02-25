package com.ran.engine.factories.interpolation;

import com.ran.engine.rendering.algebraic.exception.CreationException;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.vector.SingleDouble;
import java.util.ArrayList;
import java.util.List;

public class PolynomsCreator {

    private static final PolynomsCreator INSTANCE = new PolynomsCreator();
    
    public static PolynomsCreator getInstance() {
        return INSTANCE;
    }
    
    public DoubleFunction<SingleDouble> createBernsteinPolynom(int n, int m) {
        if (n < 0 || m < 0 || n < m) {
            throw new CreationException("Incorrect parameters while creating Bernstein polynom");
        }
        long combinations = countCombinations(n, m);
        return new DoubleFunction<>(point -> new SingleDouble(combinations * power(1 - point, n - m) * power(point, m)), 0.0, 1.0);
    }
    
    public DoubleFunction<SingleDouble> createSmoothingPolynom(int k) {
        if (k < 0) {
            throw new CreationException("Incorrect parameters while creating smoothing polynom");
        }
        List<DoubleFunction<SingleDouble>> bernsteinPolynoms = new ArrayList<>(k + 1);
        for (int i = k + 1; i <= 2 * k + 1; i++) {
            bernsteinPolynoms.add(createBernsteinPolynom(2 * k + 1, i));
        }
        return new DoubleFunction<>(point -> new SingleDouble(bernsteinPolynoms.stream()
                .mapToDouble(polynom -> polynom.apply(point).getValue()).sum()), 0.0, 1.0);
    }
    
    private long countCombinations(int n, int m) {
        if (n < 0 || m < 0 || n < m) {
            throw new CreationException("Incorrect parameters while combinations counting");
        }
        if (m * 2 < n) {
            m = n - m;
        }
        long result = 1;
        for (int i = m + 1; i <= n; i++) {
            result *= i;
        }
        for (int i = 1; i <= n - m; i++) {
            result /= i;
        }
        return result;
    }
    
    private double power(double x, int power) {
        double result = 1.0;
        for (int i = 0; i < power; i++) {
            result *= x;
        }
        return result;
    }
    
}