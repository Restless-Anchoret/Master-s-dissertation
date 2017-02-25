package com.ran.engine.opengl.handlers.keyboard;

import com.ran.engine.opengl.handlers.EventHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class SmoothingSwitchHandler implements EventHandler {

    private boolean smoothingTurnedOn = true;

    @Override
    public void handleEvent() {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_S) {
            if (smoothingTurnedOn) {
                turnOffSmoothing();
            } else {
                turnOnSmothing();
            }
            smoothingTurnedOn = !smoothingTurnedOn;
        }
    }

    public static void turnOnSmothing() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void turnOffSmoothing() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_POINT_SMOOTH);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
    }

}