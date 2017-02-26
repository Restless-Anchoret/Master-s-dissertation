package com.ran.engine.opengl.handlers.keyboard;

import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.opengl.runner.ApplicationState;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class DisplaySettingsSwitchHandler implements EventHandler {

    private ApplicationState applicationState;

    public DisplaySettingsSwitchHandler(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    public void handleEvent() {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_S:
                changeSmoothing();
                break;
            case Keyboard.KEY_V:
                changeVerticalSync();
                break;
            case Keyboard.KEY_F:
                changeFullscreen();
                break;
            case Keyboard.KEY_A:
                changeDisplayMode(-1);
                break;
            case Keyboard.KEY_D:
                changeDisplayMode(1);
                break;
        }
    }

    private void changeSmoothing() {
        applicationState.setSmoothingTurnedOn(!applicationState.isSmoothingTurnedOn());
        if (applicationState.isSmoothingTurnedOn()) {
            turnOnSmothing();
        } else {
            turnOffSmoothing();
        }
    }

    private void changeVerticalSync() {
        applicationState.setVerticalSyncTurnedOn(!applicationState.isVerticalSyncTurnedOn());
        Display.setVSyncEnabled(applicationState.isVerticalSyncTurnedOn());
    }

    private void changeFullscreen() {
        try {
            Display.setFullscreen(!Display.isFullscreen());
        } catch (LWJGLException ex) {
            // todo: replace by logging
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private void changeDisplayMode(int indexDelta) {
        try {
            DisplayMode[] displayModes = Display.getAvailableDisplayModes();
            int newDisplayModeIndex = applicationState.getCurrentDisplayModeIndex() + indexDelta;
            newDisplayModeIndex = (newDisplayModeIndex + displayModes.length) % displayModes.length;
            Display.setDisplayMode(displayModes[newDisplayModeIndex]);
            updateOrtho();
            applicationState.setCurrentDisplayModeIndex(newDisplayModeIndex);
        } catch (LWJGLException ex) {
            // todo: replace by logging
            ex.printStackTrace();
            System.exit(0);
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

    public static void updateOrtho() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

}