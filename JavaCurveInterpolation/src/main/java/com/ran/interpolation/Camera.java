package com.ran.interpolation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Camera {

    private static final Point3D DEFAULT_VERT_VECTOR = new Point3D(0.0, 0.0, 1.0);
    private static final double LEFT = -4.0,
            RIGHT = 4.0,
            BOTTOM = -3.0,
            TOP = 3.0,
            ANGLE_UP_EDGE = 5.0 * Math.PI / 12.0;
    private static final int WIDTH = 800,
            HEIGHT = 600;

    private double angleXOY;
    private double angleZ;
    private Point3D position = null;
    private Point3D normVector = null;
    private Point3D vertVector = null;
    private double distanceReverse;
    private Camera defaultCamera = null;

    private Camera() {
    }

    private Camera(Point3D position, double angleXOY, double angleZ, double distanceReverse) {
        defaultCamera = new Camera();
        defaultCamera.position = this.position = position;
        defaultCamera.angleXOY = this.angleXOY = angleXOY;
        defaultCamera.angleZ = this.angleZ = angleZ;
        defaultCamera.normVector = countNormVector();
        defaultCamera.distanceReverse = this.distanceReverse = distanceReverse;
        defaultCamera.vertVector = vertVector = DEFAULT_VERT_VECTOR;
    }

    public static Camera cameraFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Camera.class.getResourceAsStream(fileName)));
            StringTokenizer tok;
            double x, y, z;

            tok = new StringTokenizer(reader.readLine());
            x = Double.parseDouble(tok.nextToken());
            y = Double.parseDouble(tok.nextToken());
            z = Double.parseDouble(tok.nextToken());
            Point3D position = new Point3D(x, y, z);

            tok = new StringTokenizer(reader.readLine());
            double angleXOY = Double.parseDouble(tok.nextToken());

            tok = new StringTokenizer(reader.readLine());
            double angleZ = Double.parseDouble(tok.nextToken());

            tok = new StringTokenizer(reader.readLine());
            double distanceReverse = Double.parseDouble(tok.nextToken());

            return new Camera(position, angleXOY, angleZ, distanceReverse);
        } catch (Exception e) {
            return null;
        }
    }

    public Point3D getNormVector() {
        return normVector;
    }

    public Point3D getPosition() {
        return position;
    }

    public double getDistanceReverse() {
        return distanceReverse;
    }

    public Point3D getVertVector() {
        return vertVector;
    }

    public void setDistanceReverse(double distanceReverse) {
        this.distanceReverse = distanceReverse;
    }

    public void changeAngleXOY(double angle) {
        angleXOY += angle;
        countNormVector();
    }

    public void changeAngleZ(double angle) {
        if (-ANGLE_UP_EDGE <= angleZ + angle && angleZ + angle <= ANGLE_UP_EDGE) {
            angleZ += angle;
            countNormVector();
        }
    }

    public void moveX(double step) {
        position = new Point3D(position.getX() + step, position.getY(), position.getZ());
    }

    public void moveY(double step) {
        position = new Point3D(position.getX(), position.getY() + step, position.getZ());
    }

    public void moveZ(double step) {
        position = new Point3D(position.getX(), position.getY(), position.getZ() + step);
    }

    public void zoom(double step) {
        position = new Point3D(
                position.getX() + normVector.getX() * step,
                position.getY() + normVector.getY() * step,
                position.getZ() + normVector.getZ() * step
        );
    }

    private Point3D countNormVector() {
        normVector = new Point3D(Math.sin(angleXOY), Math.cos(angleXOY), Math.sin(angleZ));
        return normVector;
    }

    public Point2DInt findDisplayCoordinates(Point3D source) {
        int x = (int) Math.round((source.getX() - LEFT) / (RIGHT - LEFT) * WIDTH);
        int y = (int) Math.round((TOP - source.getY()) / (TOP - BOTTOM) * HEIGHT);
        return new Point2DInt(x, y);
    }

    public void defaultPosition() {
        this.angleXOY = defaultCamera.angleXOY;
        this.angleZ = defaultCamera.angleZ;
        this.position = defaultCamera.position;
        this.normVector = defaultCamera.normVector;
        this.vertVector = defaultCamera.vertVector;
        this.distanceReverse = defaultCamera.distanceReverse;
    }
}
