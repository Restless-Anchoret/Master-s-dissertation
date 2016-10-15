package com.ran.interpolation;

public class Matrix {

    private double[][] matrix = null;
    private int columns, lines;

    public Matrix(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        matrix = new double[lines][columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = 0.0;
            }
        }
    }

    public Matrix(double[][] matrix, int columns, int lines) {
        this.matrix = matrix;
        this.columns = columns;
        this.lines = lines;
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

    public void set(int line, int column, double value) {
        matrix[line][column] = value;
    }

    public Matrix add(Matrix rhs) {
        if (this.columns != rhs.columns || this.lines != rhs.lines) {
            return null;
        }
        Matrix result = new Matrix(this.lines, this.columns);
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                result.matrix[i][j] = this.matrix[i][j] + rhs.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(double number) {
        Matrix result = new Matrix(lines, columns);
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                result.matrix[i][j] = matrix[i][j] * number;
            }
        }
        return result;
    }

    public Matrix multiply(Matrix rhs) {
        if (this.columns != rhs.lines) {
            return null;
        }
        Matrix result = new Matrix(this.lines, rhs.columns);
        for (int i = 0; i < this.lines; i++) {
            for (int j = 0; j < rhs.columns; j++) {
                for (int k = 0; k < this.columns; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * rhs.matrix[k][j];
                }
            }
        }
        return result;
    }

}
