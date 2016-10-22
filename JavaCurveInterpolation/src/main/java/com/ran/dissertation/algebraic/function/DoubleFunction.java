package com.ran.dissertation.algebraic.function;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.exception.FunctionParameterOutOfBoundsException;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import java.util.function.Function;

public class DoubleFunction<T extends AlgebraicObject<T>> implements AlgebraicObject<DoubleFunction<T>>, Function<Double, T> {

    private final Function<Double, T> function;
    private final double minParameterValue;
    private final double maxParameterValue;
    
    public DoubleFunction(Function<Double, T> function, double minParameterValue, double maxParameterValue) {
        this.function = function;
        this.minParameterValue = minParameterValue;
        this.maxParameterValue = maxParameterValue;
    }

    public DoubleFunction(Function<Double, T> function) {
        this(function, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public double getMinParameterValue() {
        return minParameterValue;
    }

    public double getMaxParameterValue() {
        return maxParameterValue;
    }

    private Function<Double, T> getFunction() {
        return function;
    }
    
    @Override
    public T apply(Double point) {
        if (point < minParameterValue || point > maxParameterValue) {
            throw new FunctionParameterOutOfBoundsException(point, minParameterValue, maxParameterValue);
        }
        return function.apply(point);
    }
    
    @Override
    public DoubleFunction<T> add(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).add(other.getFunction().apply(point))
        );
    }

    @Override
    public DoubleFunction<T> substract(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).substract(other.getFunction().apply(point))
        );
    }

    @Override
    public DoubleFunction<T> multiply(int number) {
        return new DoubleFunction<>(
                point -> function.apply(point).multiply(number),
                minParameterValue, maxParameterValue
        );
    }

    @Override
    public DoubleFunction<T> multiply(double number) {
        return new DoubleFunction<>(
                point -> function.apply(point).multiply(number),
                minParameterValue, maxParameterValue
        );
    }

    @Override
    public DoubleFunction<T> multiply(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).multiply(other.apply(point))
        );
    }

    @Override
    public double scalarMultiply(DoubleFunction<T> other) {
        throw new UnsupportedOperationException();
    }
    
    private DoubleFunction<T> joinFunctions(DoubleFunction<T> first, DoubleFunction<T> second, Function<Double, T> function) {
        return new DoubleFunction<>(
                function,
                Math.max(first.getMinParameterValue(), second.getMinParameterValue()),
                Math.min(first.getMaxParameterValue(), second.getMaxParameterValue())
        );
    }
    
    public DoubleFunction<T> superposition(DoubleFunction<SingleDouble> singleDoubleFunction) {
        return new DoubleFunction<>(
                point -> this.apply(singleDoubleFunction.apply(point).getValue())
        );
    }
    
}