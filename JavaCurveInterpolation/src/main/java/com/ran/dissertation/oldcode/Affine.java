package com.ran.dissertation.oldcode;

public class Affine {

    private Matrix matrix = null;

    private Affine() {
        matrix = new Matrix(4, 4);
    }

    public static Affine affineIdentical() {
        Affine result = new Affine();
        for (int i = 0; i < 4; i++) {
            result.matrix.set(i, i, 1.0);
        }
        return result;
    }

    public static Affine affineTransfer(double x, double y, double z) {
        Affine result = new Affine();
        for (int i = 0; i < 4; i++) {
            result.matrix.set(i, i, 1.0);
        }
        result.matrix.set(0, 3, x);
        result.matrix.set(1, 3, y);
        result.matrix.set(2, 3, z);
        return result;
    }

    public static Affine affineRotationX(double angle) {
        Affine result = new Affine();
        result.matrix.set(0, 0, 1.0);
        result.matrix.set(1, 1, Math.cos(angle));
        result.matrix.set(1, 2, -Math.sin(angle));
        result.matrix.set(2, 1, Math.sin(angle));
        result.matrix.set(2, 2, Math.cos(angle));
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineRotationY(double angle) {
        Affine result = new Affine();
        result.matrix.set(0, 0, Math.cos(angle));
        result.matrix.set(0, 2, Math.sin(angle));
        result.matrix.set(1, 1, 1.0);
        result.matrix.set(2, 0, -Math.sin(angle));
        result.matrix.set(2, 2, Math.cos(angle));
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineRotationZ(double angle) {
        Affine result = new Affine();
        result.matrix.set(0, 0, Math.cos(angle));
        result.matrix.set(0, 1, -Math.sin(angle));
        result.matrix.set(1, 0, Math.sin(angle));
        result.matrix.set(1, 1, Math.cos(angle));
        result.matrix.set(2, 2, 1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineScaling(double kx, double ky, double kz) {
        Affine result = new Affine();
        result.matrix.set(0, 0, kx);
        result.matrix.set(1, 1, ky);
        result.matrix.set(2, 2, kz);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineScaling(double k) {
        Affine result = new Affine();
        result.matrix.set(0, 0, k);
        result.matrix.set(1, 1, k);
        result.matrix.set(2, 2, k);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflection() {
        Affine result = new Affine();
        result.matrix.set(0, 0, -1.0);
        result.matrix.set(1, 1, -1.0);
        result.matrix.set(2, 2, -1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflectionX() {
        Affine result = new Affine();
        result.matrix.set(0, 0, 1.0);
        result.matrix.set(1, 1, -1.0);
        result.matrix.set(2, 2, -1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflectionY() {
        Affine result = new Affine();
        result.matrix.set(0, 0, -1.0);
        result.matrix.set(1, 1, 1.0);
        result.matrix.set(2, 2, -1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflectionZ() {
        Affine result = new Affine();
        result.matrix.set(0, 0, -1.0);
        result.matrix.set(1, 1, -1.0);
        result.matrix.set(2, 2, 1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflectionYZ() {
        Affine result = new Affine();
        result.matrix.set(0, 0, -1.0);
        result.matrix.set(1, 1, 1.0);
        result.matrix.set(2, 2, 1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflectionZX() {
        Affine result = new Affine();
        result.matrix.set(0, 0, 1.0);
        result.matrix.set(1, 1, -1.0);
        result.matrix.set(2, 2, 1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineReflectionXY() {
        Affine result = new Affine();
        result.matrix.set(0, 0, 1.0);
        result.matrix.set(1, 1, 1.0);
        result.matrix.set(2, 2, -1.0);
        result.matrix.set(3, 3, 1.0);
        return result;
    }

    public static Affine affineCoordinateChanging(Camera camera) {
        Point3D normVector = camera.getNormVector();
        Point3D vertVector = camera.getVertVector();

        Point3D baseK = normVector.normalized();
        Point3D baseI = vertVector.vectorMultiply(normVector).normalized();
        Point3D baseJ = normVector.vectorMultiply(baseI).normalized();
        Point3D[] bases = new Point3D[]{baseI, baseJ, baseK};

        Affine transfer = Affine.affineIdentical();
        for (int i = 0; i < 3; i++) {
            transfer.matrix.set(i, 0, bases[i].getX());
            transfer.matrix.set(i, 1, bases[i].getY());
            transfer.matrix.set(i, 2, bases[i].getZ());
            transfer.matrix.set(i, 3, -bases[i].scalarMultiply(camera.getPosition()));
        }

        return transfer;
    }

    public static Affine affineProjection(Camera camera) {
        Affine projection = Affine.affineIdentical();
        projection.matrix.set(2, 2, 0.0);
        projection.matrix.set(3, 2, -camera.getDistanceReverse());

        return projection;
    }

    public Affine join(Affine rhs) {
        Affine result = new Affine();
        result.matrix = this.matrix.multiply(rhs.matrix);
        return result;
    }

    public Affine overPoint(Point3D point) {
        Affine result = Affine.affineTransfer(point.getX(), point.getY(), point.getZ());
        result = result.join(this);
        result = result.join(Affine.affineTransfer(-point.getX(), -point.getY(), -point.getZ()));
        return result;
    }

    public Matrix transform(Matrix matrix) {
        return this.matrix.multiply(matrix);
    }

}
