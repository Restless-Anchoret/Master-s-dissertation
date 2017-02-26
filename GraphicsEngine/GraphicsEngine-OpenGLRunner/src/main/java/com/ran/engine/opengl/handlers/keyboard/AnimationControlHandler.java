package com.ran.engine.opengl.handlers.keyboard;

import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.rendering.core.RenderingEngine;
import com.ran.engine.rendering.world.DisplayableObject;
import org.lwjgl.input.Keyboard;

public class AnimationControlHandler implements EventHandler {

    private RenderingEngine renderingEngine;

    public AnimationControlHandler(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

    @Override
    public void handleEvent() {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        int chosenObjectIndex = renderingEngine.getRenderingInfo().getCurrentWorld().getChosenObjectIndex();
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_LEFT:
                if (chosenObjectIndex != -1) {
                    chosenObjectIndex--;
                }
                break;
            case Keyboard.KEY_RIGHT:
                chosenObjectIndex++;
                break;
            case Keyboard.KEY_SPACE:
                if (chosenObjectIndex != -1) {
                    DisplayableObject displayableObject = renderingEngine.getRenderingInfo()
                            .getCurrentWorld().getDisplayableObjects().get(chosenObjectIndex);
                    displayableObject.setAnimationTurnedOn(!displayableObject.isAnimationTurnedOn());
                }
                return;
            default:
                return;
        }
        int objectsQuantity = renderingEngine.getRenderingInfo().getCurrentWorld().getDisplayableObjects().size();
        chosenObjectIndex = (chosenObjectIndex + objectsQuantity) % objectsQuantity;
        renderingEngine.getRenderingInfo().getCurrentWorld().setChosenObjectIndex(chosenObjectIndex);
    }

}