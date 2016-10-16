package com.ran.dissertation.oldcode;

public class Point3D {

    private double x, y, z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point3D point3D = (Point3D) o;

        if (Double.compare(point3D.x, x) != 0) {
            return false;
        }
        if (Double.compare(point3D.y, y) != 0) {
            return false;
        }
        if (Double.compare(point3D.z, z) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Point3D vectorMultiply(Point3D rhs) {
        double x = this.z * rhs.y - this.y * rhs.z;
        double y = this.x * rhs.z - this.z * rhs.x;
        double z = this.y * rhs.x - this.x * rhs.y;
        return new Point3D(x, y, z);
    }

    public double scalarMultiply(Point3D rhs) {
        return this.x * rhs.x + this.y * rhs.y + this.z * rhs.z;
    }

    public Point3D normalized() {
        double norm = Math.sqrt(x * x + y * y + z * z);
        return new Point3D(x / norm, y / norm, z / norm);
    }
}
