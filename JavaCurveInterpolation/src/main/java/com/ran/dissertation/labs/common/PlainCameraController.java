package com.ran.dissertation.labs.common;

import com.ran.dissertation.controller.DefaultPaintStrategy;
import com.ran.dissertation.labs.lab1.LabFirstWorldFactory;
import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.ui.ImagePanelListener;
import com.ran.dissertation.world.Camera;

public class PlainCameraController implements ImagePanelListener {

    private static final double DEFAULT_ZOOM_STEP = 0.75;
    
    private final Camera camera;
    private final double zoomStep;
    private final DefaultPaintStrategy paintStrategy;

    public PlainCameraController(Camera camera, DefaultPaintStrategy paintStrategy, double zoomStep) {
        this.camera = camera;
        this.paintStrategy = paintStrategy;
        this.zoomStep = zoomStep;
    }
    
    public PlainCameraController(Camera camera, DefaultPaintStrategy paintStrategy) {
        this(camera, paintStrategy, DEFAULT_ZOOM_STEP);
    }
    
    @Override
    public void mouseDraggedLeftMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
        double lensWidth = camera.getLensWidth();
        double lensHeight = lensWidth * height / width;
        int diffX = previousX - nextX;
        int diffY = nextY - previousY;
        double movementX = lensWidth / width * diffX;
        double movementZ = lensHeight / height * diffY;
        camera.moveX(movementX);
        camera.moveZ(movementZ);
        paintStrategy.setWorld(LabFirstWorldFactory.getInstance().createWorldForCamera(camera, width));
        imagePanel.repaint();
    }

    @Override
    public void mouseDraggedMiddleMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
    }

    @Override
    public void mouseDraggedRightMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
    }

    @Override
    public void mouseWheelMoved(ImagePanel imagePanel, int x, int y, int width, int height, int notches) {
        double factor = Math.pow(zoomStep, -notches);
        double previousLensWidth = camera.getLensWidth();
        double previousLensHeight = previousLensWidth * height / width;
        double newLensWidth = previousLensWidth * factor;
        camera.setLensWidth(newLensWidth);
        
        int centerX = width / 2;
        int centerY = height / 2;
        int diffX = x - centerX;
        int diffY = centerY - y;
        double worldX = previousLensWidth * diffX / width;
        double worldZ = previousLensHeight * diffY / height;
        double movementX = worldX * (1.0 - factor);
        double movementZ = worldZ * (1.0 - factor);
        camera.moveX(movementX);
        camera.moveZ(movementZ);
        
        paintStrategy.setWorld(LabFirstWorldFactory.getInstance().createWorldForCamera(camera, width));
        imagePanel.repaint();
    }

}