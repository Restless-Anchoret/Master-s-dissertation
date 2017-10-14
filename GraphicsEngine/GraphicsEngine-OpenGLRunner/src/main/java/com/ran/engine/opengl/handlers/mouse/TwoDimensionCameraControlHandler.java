package com.ran.engine.opengl.handlers.mouse;

import com.ran.engine.rendering.core.RenderingEngine;
import com.ran.engine.rendering.world.Camera;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class TwoDimensionCameraControlHandler extends CameraControlHandler {

    private static final double MOVE_FACTOR = 3.5;
    private static final double ZOOM_STEP = 0.75;

    TwoDimensionCameraControlHandler(RenderingEngine renderingEngine) {
        super(renderingEngine);
    }

    @Override
    public void handleEvent() {
        Camera camera = renderingEngine.getRenderingInfo().getCurrentWorld().getCamera();
        int x = Mouse.getX();
        int y = Mouse.getY();
        int previousX = Mouse.getX() - Mouse.getDX();
        int previousY = Mouse.getY() - Mouse.getDY();
        int deltaWheel = Mouse.getDWheel() / 120;
        int width = Display.getWidth();
        int height = Display.getHeight();
        if (Mouse.isButtonDown(0)) {
            moveCamera(camera, previousX, previousY, x, y, width, height);
        }
        zoomCamera(camera, x, y, width, height, deltaWheel);
    }

    private void moveCamera(Camera camera, int previousX, int previousY, int nextX, int nextY, int width, int height) {
        double lensWidth = camera.getLensWidth();
        double lensHeight = lensWidth * height / width;
        int diffX = previousX - nextX;
        int diffY = previousY - nextY;
        double movementX = lensWidth / width * diffX;
        double movementZ = lensHeight / height * diffY;
        camera.moveX(movementX * MOVE_FACTOR);
        camera.moveZ(movementZ * MOVE_FACTOR);
    }

    private void zoomCamera(Camera camera, int x, int y, int width, int height, int notches) {
        double factor = Math.pow(ZOOM_STEP, notches);
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
    }

}
