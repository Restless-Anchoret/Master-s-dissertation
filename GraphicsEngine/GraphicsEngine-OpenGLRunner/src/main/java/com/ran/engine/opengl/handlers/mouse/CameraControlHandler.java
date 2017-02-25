package com.ran.engine.opengl.handlers.mouse;

import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.rendering.core.RenderingEngine;
import com.ran.engine.rendering.world.Camera;
import org.lwjgl.input.Mouse;

public class CameraControlHandler implements EventHandler {

    private static final double MOVE_STEP = 0.05;
    private static final double ANGLE_STEP = Math.PI / 288.0;
    private static final double ZOOM_STEP = 1.0 / 120.0;

    private final RenderingEngine renderingEngine;

    public CameraControlHandler(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

    @Override
    public void handleEvent() {
        Camera camera = renderingEngine.getRenderingInfo().getCurrentWorld().getCamera();
        int deltaX = Mouse.getDX();
        int deltaY = Mouse.getDY();
        int deltaWheel = Mouse.getDWheel();
        if (Mouse.isButtonDown(0)) {
            camera.moveRight(deltaX * MOVE_STEP);
            camera.moveBack(deltaY * MOVE_STEP);
        } else if (Mouse.isButtonDown(1)) {
            camera.moveZ(deltaY * MOVE_STEP);
        } else if (Mouse.isButtonDown(2)) {
            camera.changeAngleXOY(-deltaX * ANGLE_STEP);
            camera.changeAngleZ(-deltaY * ANGLE_STEP);
        }
        camera.zoom(deltaWheel * ZOOM_STEP);
    }

}
