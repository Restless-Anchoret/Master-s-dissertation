package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Figure;
import com.ran.dissertation.world.FigureEdge;
import java.util.ArrayList;
import java.util.List;

public class FigureFactory {
    
    private static final FigureFactory INSTANCE = new FigureFactory();

    public static FigureFactory getInstance() {
        return INSTANCE;
    }
    
    private FigureFactory() { }
    
    public Figure makeGrid(int xCellsQuantity, int yCellsQuantity, int zCellsQuantity,
            double xCellWidth, double yCellWidth, double zCellWidth) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<FigureEdge> figureEdges = new ArrayList<>();
        
        double xHalfSize = xCellWidth * xCellsQuantity / 2.0;
        double yHalfSize = yCellWidth * yCellsQuantity / 2.0;
        double zHalfSize = zCellWidth * zCellsQuantity / 2.0;
        
        int[][][] indexes = new int[xCellsQuantity + 1][yCellsQuantity + 1][zCellsQuantity + 1];
        int currentIndex = 0;
        for (int i = 0; i <= xCellsQuantity; i++) {
            for (int j = 0; j <= yCellsQuantity; j++) {
                for (int k = 0; k <= zCellsQuantity; k++) {
                    double x = i * xCellWidth - xHalfSize;
                    double y = j * yCellWidth - yHalfSize;
                    double z = k * zCellWidth - zHalfSize;
                    vertices.add(new ThreeDoubleVector(x, y, z));
                    indexes[i][j][k] = currentIndex;
                    if (i > 0) {
                        figureEdges.add(new FigureEdge(indexes[i - 1][j][k], currentIndex));
                    }
                    if (j > 0) {
                        figureEdges.add(new FigureEdge(indexes[i][j - 1][k], currentIndex));
                    }
                    if (k > 0) {
                        figureEdges.add(new FigureEdge(indexes[i][j][k - 1], currentIndex));
                    }
                    currentIndex++;
                }
            }
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makePlainGrid(int xCellsQuantity, int yCellsQuantity,
            double xCellWidth, double yCellWidth) {
        return makeGrid(xCellsQuantity, yCellsQuantity, 0, xCellWidth, yCellWidth, 0.0);
    }
    
    public Figure makeParallelepiped(double xWidth, double yWidth, double zWidth) {
        return makeGrid(1, 1, 1, xWidth, yWidth, zWidth);
    }
    
    public Figure makeCube(double width) {
        return makeParallelepiped(width, width, width);
    }

}