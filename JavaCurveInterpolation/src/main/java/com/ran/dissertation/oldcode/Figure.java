package com.ran.dissertation.oldcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class Figure {

    public static class Edge {

        public int first, second;

        private Edge(int f, int s) {
            first = f;
            second = s;
        }
    }

    private Matrix sourceVertices;
    private Collection<Edge> edges;
    private Affine affine;
    private Matrix currentVertices;
    private Point2DInt[] displayVertices;

    private Figure(Matrix vertices, Collection<Edge> edges) {
        sourceVertices = vertices;
        this.edges = edges;
        affine = Affine.affineIdentical();
        currentVertices = affine.transform(sourceVertices);
        displayVertices = new Point2DInt[vertices.getColumns()];
    }

    public static Figure figureFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Figure.class.getResourceAsStream(fileName)));
            StringTokenizer tok;

            Matrix matrix;
            int m, k;

            tok = new StringTokenizer(reader.readLine());
            m = Integer.parseInt(tok.nextToken());
            matrix = new Matrix(4, m);

            double x, y, z;
            for (int i = 0; i < m; i++) {
                tok = new StringTokenizer(reader.readLine());
                x = Double.parseDouble(tok.nextToken());
                y = Double.parseDouble(tok.nextToken());
                z = Double.parseDouble(tok.nextToken());
                matrix.set(0, i, x);
                matrix.set(1, i, y);
                matrix.set(2, i, z);
                matrix.set(3, i, 1.0);
            }

            tok = new StringTokenizer(reader.readLine());
            k = Integer.parseInt(tok.nextToken());

            int first, second;
            Collection<Edge> list = new ArrayList<>(k);
            for (int i = 0; i < k; i++) {
                tok = new StringTokenizer(reader.readLine());
                first = Integer.parseInt(tok.nextToken());
                second = Integer.parseInt(tok.nextToken());
                list.add(new Edge(first - 1, second - 1));
            }

            reader.close();
            return new Figure(matrix, list);
        } catch (Exception e) {
            return null;
        }
    }

    public int getVerticesQuantity() {
        return sourceVertices.getColumns();
    }

    public Matrix getVertices() {
        return currentVertices;
    }

    public Collection<Edge> getEdges() {
        return edges;
    }

    public void defaultAffine() {
        this.affine = Affine.affineIdentical();
        currentVertices = affine.transform(sourceVertices);
    }

    public void applyAffine(Affine affine) {
        this.affine = affine.join(this.affine);
        currentVertices = this.affine.transform(sourceVertices);
    }

    public Point2DInt[] displayVerticesForCamera(Camera camera) {
        Affine transfer = Affine.affineCoordinateChanging(camera);
        Matrix matrixTransfer = transfer.transform(currentVertices);

        Affine projection = Affine.affineProjection(camera);
        Matrix matrix = projection.transform(matrixTransfer);

        double x, y;
        for (int i = 0; i < matrix.getColumns(); i++) {
            if (matrixTransfer.get(2, i) < 0.0) {
                x = matrix.get(0, i) / matrix.get(3, i);
                y = matrix.get(1, i) / matrix.get(3, i);
                displayVertices[i] = camera.findDisplayCoordinates(new Point3D(x, y, 0));
            } else {
                displayVertices[i] = null;
            }
        }
        return displayVertices;
    }

}
