package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class FigureEdge {

    private ThreeDoubleVector firstVertice;
    private ThreeDoubleVector secondVertice;

    public FigureEdge(ThreeDoubleVector firstVertice, ThreeDoubleVector secondVertice) {
        this.firstVertice = firstVertice;
        this.secondVertice = secondVertice;
    }

    public ThreeDoubleVector getFirstVertice() {
        return firstVertice;
    }

    public ThreeDoubleVector getSecondVertice() {
        return secondVertice;
    }

    @Override
    public String toString() {
        return "FigureEdge{" + "firstVertice=" + firstVertice + ", secondVertice=" + secondVertice + '}';
    }
    
}