package com.ran.engine.opengl.handlers.keyboard;

import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.rendering.core.RenderingEngine;
import org.lwjgl.input.Keyboard;

public class WorldSwitchHandler implements EventHandler {

    private final RenderingEngine renderingEngine;

    public WorldSwitchHandler(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

    @Override
    public void handleEvent() {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        int currentWorldIndex = renderingEngine.getRenderingInfo().getCurrentWorldIndex();
        int worldsQuantity = renderingEngine.getRenderingInfo().getWorlds().size();
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_NUMPAD8:
                currentWorldIndex++;
                break;
            case Keyboard.KEY_NUMPAD2:
                currentWorldIndex--;
                break;
            default:
                return;
        }
        currentWorldIndex = (currentWorldIndex + worldsQuantity) % worldsQuantity;
        renderingEngine.getRenderingInfo().setCurrentWorldIndex(currentWorldIndex);
    }

}
