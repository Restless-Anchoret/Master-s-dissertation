package com.ran.engine.opengl.handlers.keyboard;

import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.rendering.core.RenderingEngine;
import org.lwjgl.input.Keyboard;

public class ControlSwitchHandler implements EventHandler {

    private RenderingEngine renderingEngine;

    public ControlSwitchHandler(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

    @Override
    public void handleEvent() {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        int chosenControlIndex = renderingEngine.getRenderingInfo().getCurrentWorld().getChosenControlIndex();
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_LEFT:
                if (chosenControlIndex != -1) {
                    chosenControlIndex--;
                }
                break;
            case Keyboard.KEY_RIGHT:
                chosenControlIndex++;
                break;
            case Keyboard.KEY_UP:
                if (chosenControlIndex != -1) {
                    renderingEngine.getRenderingInfo().getCurrentWorld()
                            .getCurrentControl().increaseParameter();
                }
                return;
            case Keyboard.KEY_DOWN:
                if (chosenControlIndex != -1) {
                    renderingEngine.getRenderingInfo().getCurrentWorld()
                            .getCurrentControl().decreaseParameter();
                }
                return;
            default:
                return;
        }
        int objectsQuantity = renderingEngine.getRenderingInfo().getCurrentWorld().getControls().size();
        if (objectsQuantity == 0) {
            return;
        }
        chosenControlIndex = (chosenControlIndex + objectsQuantity) % objectsQuantity;
        renderingEngine.getRenderingInfo().getCurrentWorld().setChosenControlIndex(chosenControlIndex);
    }

}
