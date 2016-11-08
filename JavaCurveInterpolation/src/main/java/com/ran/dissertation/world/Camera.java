package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class Camera {

    private static final double ANGLE_Z_UP_EDGE = 23.0 * Math.PI / 48.0;
    
    private static final double DEFAULT_ANGLE_XOY = 0.0;
    private static final double DEFAULT_ANGLE_Z = 0.0;
    private static final ThreeDoubleVector DEFAULT_POSITION = new ThreeDoubleVector(0.0, 6.0, 4.0);
    private static final ThreeDoubleVector DEFAULT_VERTICAL_VECTOR = new ThreeDoubleVector(0.0, 0.0, 1.0);
    private static final double DEFAULT_DISTANCE_BEHIND = 4.0;
    private static final double DEFAULT_DISPLAY_WIDTH = 8.0;
    
    private double angleXOY;
    private double angleZ;
    private ThreeDoubleVector position;
    private ThreeDoubleVector normVector;
    private ThreeDoubleVector verticalVector;
    private double distanceBehind;
    private double lensWidth;

    public Camera(double angleXOY, double angleZ, ThreeDoubleVector position,
            ThreeDoubleVector verticalVector, double distanceBehind, double lensWidth) {
        this.angleXOY = angleXOY;
        this.angleZ = angleZ;
        this.position = position;
        this.verticalVector = verticalVector;
        this.distanceBehind = distanceBehind;
        this.lensWidth = lensWidth;
        updateNormVector();
    }

    public Camera() {
        this(DEFAULT_ANGLE_XOY, DEFAULT_ANGLE_Z, DEFAULT_POSITION, DEFAULT_VERTICAL_VECTOR,
                DEFAULT_DISTANCE_BEHIND, DEFAULT_DISPLAY_WIDTH);
    }

    public double getAngleXOY() {
        return angleXOY;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public ThreeDoubleVector getPosition() {
        return position;
    }

    public ThreeDoubleVector getNormVector() {
        return normVector;
    }

    public ThreeDoubleVector getVerticalVector() {
        return verticalVector;
    }

    public double getDistanceBehind() {
        return distanceBehind;
    }

    public double getLensWidth() {
        return lensWidth;
    }

    public void changeAngleXOY(double angle) {
        angleXOY += angle;
        updateNormVector();
    }

    public void changeAngleZ(double angle) {
        if (-ANGLE_Z_UP_EDGE <= angleZ + angle && angleZ + angle <= ANGLE_Z_UP_EDGE) {
            angleZ += angle;
            updateNormVector();
        }
    }

    public void moveX(double step) {
        position = new ThreeDoubleVector(position.getX() + step, position.getY(), position.getZ());
    }

    public void moveY(double step) {
        position = new ThreeDoubleVector(position.getX(), position.getY() + step, position.getZ());
    }

    public void moveZ(double step) {
        position = new ThreeDoubleVector(position.getX(), position.getY(), position.getZ() + step);
    }
    
    public void moveRight(double step) {
        position = new ThreeDoubleVector(position.getX() + step * Math.cos(-angleXOY),
                position.getY() + step * Math.sin(-angleXOY), position.getZ());
    }
    
    public void moveLeft(double step) {
        position = new ThreeDoubleVector(position.getX() - step * Math.cos(-angleXOY),
                position.getY() - step * Math.sin(-angleXOY), position.getZ());
    }
    
    public void moveForward(double step) {
        position = new ThreeDoubleVector(position.getX() + step * Math.sin(angleXOY),
                position.getY() + step * Math.cos(angleXOY), position.getZ());
    }
    
    public void moveBack(double step) {
        position = new ThreeDoubleVector(position.getX() - step * Math.sin(angleXOY),
                position.getY() - step * Math.cos(angleXOY), position.getZ());
    }

    public void zoom(double step) {
        position = new ThreeDoubleVector(
                position.getX() - normVector.getX() * step,
                position.getY() - normVector.getY() * step,
                position.getZ() - normVector.getZ() * step
        );
    }

    private void updateNormVector() {
        normVector = new ThreeDoubleVector(Math.sin(angleXOY), Math.cos(angleXOY), Math.tan(angleZ)).normalized();
    }
    
}