package com.ran.dissertation.interpolation;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import java.util.function.BiFunction;

public class GroupMultiplicationOperationFactory {
    
    private GroupMultiplicationOperationFactory() { }
    
    public static <T extends AlgebraicObject<T>> BiFunction<T, T, T> getMultiplicationOperation() {
        return (firstObject, secondObject) -> firstObject.multiply(secondObject);
    }
    
    public static <T extends AlgebraicObject<T>> BiFunction<T, T, T> getSummationOperation() {
        return (firstObject, secondObject) -> firstObject.add(secondObject);
    }
    
}