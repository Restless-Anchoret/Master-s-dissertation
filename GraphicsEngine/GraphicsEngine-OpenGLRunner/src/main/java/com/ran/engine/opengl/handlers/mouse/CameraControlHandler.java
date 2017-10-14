package com.ran.engine.opengl.handlers.mouse;

import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.rendering.core.RenderingEngine;

public abstract class CameraControlHandler implements EventHandler {

    public static CameraControlHandler create(RenderingEngine renderingEngine, CameraControlMode cameraControlMode) {
        switch (cameraControlMode) {
            case TWO_DIMENSION:
                return new TwoDimensionCameraControlHandler(renderingEngine);
            case THREE_DIMENTION:
                return new ThreeDimensionCameraControlHandler(renderingEngine);
            default:
                throw new UnsupportedOperationException("Unknown camera control mode");
        }
    }

    protected final RenderingEngine renderingEngine;

    public CameraControlHandler(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

}
