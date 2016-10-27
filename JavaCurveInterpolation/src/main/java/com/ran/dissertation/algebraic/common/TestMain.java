package com.ran.dissertation.algebraic.common;

import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.util.Iterator;

public class TestMain {

    public static void main(String[] args) {
        DoubleFunction<ThreeDoubleVector> firstFunction = new DoubleFunction<>(
                point -> new ThreeDoubleVector(point, 1 - point, point * 2)
        );
        DoubleFunction<ThreeDoubleVector> secondFunction = new DoubleFunction<>(
                point -> new ThreeDoubleVector(1 + point, 0, 1)
        );
        DoubleFunction<ThreeDoubleVector> resultFunction = firstFunction.add(secondFunction);
        Iterator<ThreeDoubleVector> iterator = resultFunction.iteratorForGrid(0, 1, 0.1);
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
    
}