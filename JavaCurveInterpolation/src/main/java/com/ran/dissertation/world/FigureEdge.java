package com.ran.dissertation.world;

public class FigureEdge {

    private final int firstVerticeIndex;
    private final int secondVerticeIndex;

    public FigureEdge(int firstVerticeIndex, int secondVerticeIndex) {
        this.firstVerticeIndex = firstVerticeIndex;
        this.secondVerticeIndex = secondVerticeIndex;
    }

    public int getFirstVerticeIndex() {
        return firstVerticeIndex;
    }

    public int getSecondVerticeIndex() {
        return secondVerticeIndex;
    }

    @Override
    public String toString() {
        return "FigureEdge{" + "firstVerticeIndex=" + firstVerticeIndex +
                ", secondVerticeIndex=" + secondVerticeIndex + '}';
    }
    
}