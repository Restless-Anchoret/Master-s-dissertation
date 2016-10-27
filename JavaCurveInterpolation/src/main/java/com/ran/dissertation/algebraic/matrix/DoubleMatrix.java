package com.ran.dissertation.algebraic.matrix;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.exception.CreationException;
import java.util.Arrays;

public class DoubleMatrix implements AlgebraicObject<DoubleMatrix> {

    public static DoubleMatrix createZeroSquareMatrix(int dimension) {
        return createZeroMatrix(dimension, dimension);
    }
    
    public static DoubleMatrix createZeroMatrix(int lines, int columns) {
        return new DoubleMatrix(new double[lines][columns]);
    }
    
    public static DoubleMatrix createIdentityMatrix(int dimension) {
        double[][] matrix = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            matrix[i][i] = 1.0;
        }
        return new DoubleMatrix(matrix);
    }
    
    private final double[][] matrix;
    private final int columns;
    private final int lines;

    public DoubleMatrix(double[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new CreationException("Initialization matrix should be not null and contain at least one element");
        }
        this.matrix = matrix;
        this.lines = matrix.length;
        this.columns = matrix[0].length;
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public double get(int line, int column) {
        return matrix[line][column];
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Arrays.deepHashCode(this.matrix);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoubleMatrix other = (DoubleMatrix) obj;
        if (this.columns != other.columns || this.lines != other.lines) {
            return false;
        }
        for (int i = 0; i < this.lines; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (ArithmeticOperations.doubleNotEquals(this.get(i, j), other.get(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public DoubleMatrix add(DoubleMatrix other) {
        checkDimensionsEquality(this, other);
        double[][] resultMatrix = new double[this.lines][this.columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = this.get(i, j) + other.get(i, j);
            }
        }
        return new DoubleMatrix(resultMatrix);
    }

    @Override
    public DoubleMatrix substract(DoubleMatrix other) {
        checkDimensionsEquality(this, other);
        double[][] resultMatrix = new double[this.lines][this.columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = this.get(i, j) - other.get(i, j);
            }
        }
        return new DoubleMatrix(resultMatrix);
    }
    
    private static void checkDimensionsEquality(DoubleMatrix first, DoubleMatrix second) {
        if (first.getColumns() != second.getColumns() || first.getLines() != second.getLines()) {
            throw new AlgebraicException("Comparising of matrixes with different dimensions");
        }
    }

    @Override
    public DoubleMatrix multiply(int number) {
        return multiply((double)number);
    }

    @Override
    public DoubleMatrix multiply(double number) {
        double[][] resultMatrix = new double[this.lines][this.columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = this.get(i, j) * number;
            }
        }
        return new DoubleMatrix(resultMatrix);
    }

    @Override
    public DoubleMatrix multiply(DoubleMatrix other) {
        if (this.getColumns() != other.getLines()) {
            throw new AlgebraicException("Multiplying matrixes of not suitable dimensions");
        }
        double[][] resultMatrix = new double[this.getLines()][other.getColumns()];
        for (int i = 0; i < this.getLines(); i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                for (int k = 0; k < this.columns; k++) {
                    resultMatrix[i][j] += this.get(i, k) * other.get(k, j);
                }
            }
        }
        return new DoubleMatrix(resultMatrix);
    }

    @Override
    public double scalarMultiply(DoubleMatrix other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "DoubleMatrix{" + "matrix=" + Arrays.deepToString(matrix) + ", columns=" + columns + ", lines=" + lines + '}';
    }

}