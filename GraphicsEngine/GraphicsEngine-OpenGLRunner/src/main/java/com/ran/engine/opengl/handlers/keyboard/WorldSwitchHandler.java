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
        if (Keyboard.getEventKeyState()) {
            int currentWorldIndex = renderingEngine.getRenderingInfo().getCurrentWorldIndex();
            if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                currentWorldIndex++;
                if (currentWorldIndex == renderingEngine.getRenderingInfo().getWorlds().size()) {
                    currentWorldIndex = 0;
                }
            } else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                currentWorldIndex--;
                if (currentWorldIndex < 0) {
                    currentWorldIndex = renderingEngine.getRenderingInfo().getWorlds().size() - 1;
                }
            }
            renderingEngine.getRenderingInfo().setCurrentWorldIndex(currentWorldIndex);
        }
    }

}
