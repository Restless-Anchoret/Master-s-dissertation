package com.ran.engine.opengl.runner;

import com.ran.engine.factories.world.WorldFactory;
import com.ran.engine.opengl.delegate.OpenGLRenderingDelegate;
import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.opengl.handlers.keyboard.*;
import com.ran.engine.opengl.handlers.mouse.CameraControlHandler;
import com.ran.engine.rendering.core.RenderingEngine;
import com.ran.engine.rendering.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OpenGLRunner {

    private final int FPS_GARDIAN = 100;

    private final List<WorldFactory> worldFactories;
    private final ApplicationState applicationState = new ApplicationState();
    private RenderingEngine renderingEngine = null;
    private List<EventHandler> mouseEventHandlers = null;
    private List<EventHandler> keyboardEventHandlers = null;

    public OpenGLRunner(List<WorldFactory> worldFactories) {
        this.worldFactories = worldFactories;
    }

    public void run() {
        List<World> worlds = createWorlds();
        renderingEngine = new RenderingEngine(worlds, new OpenGLRenderingDelegate());
        mouseEventHandlers = createMouseEventHandlers();
        keyboardEventHandlers = createKeyboardEventHandlers();
        initOpenGL();
        initApplicationState();
        runMainCycle();
        finalizeOpenGL();
    }

    private List<World> createWorlds() {
        return worldFactories.stream().map(WorldFactory::createWorld).collect(Collectors.toList());
    }

    private List<EventHandler> createMouseEventHandlers() {
        return Collections.singletonList(new CameraControlHandler(renderingEngine));
    }

    private List<EventHandler> createKeyboardEventHandlers() {
        return Arrays.asList(
                new AnimationControlHandler(renderingEngine),
                new WorldSwitchHandler(renderingEngine),
                new RenderingModeSwitchHandler(),
                new ScreenshotSaverHandler(),
                new DisplaySettingsSwitchHandler(applicationState)
        );
    }

    private void initOpenGL() {
        try {
            DisplayMode displayModeForSetting = Display.getDesktopDisplayMode();
            Display.setDisplayMode(displayModeForSetting);
            Display.setFullscreen(true);
            Display.setVSyncEnabled(true);
            Display.create();

            DisplayMode[] displayModes = Display.getAvailableDisplayModes();
            for (int i = 0; i < displayModes.length; i++) {
                if (displayModeForSetting.equals(displayModes[i])) {
                    applicationState.setCurrentDisplayModeIndex(i);
                    break;
                }
            }

            DisplaySettingsSwitchHandler.updateOrtho();
            DisplaySettingsSwitchHandler.turnOnSmothing();
        } catch (LWJGLException e) {
            // todo: replace by logging
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void initApplicationState() {
        applicationState.setVerticalSyncTurnedOn(true);
        applicationState.setSmoothingTurnedOn(true);
        long time = getTime();
        applicationState.setLastFrameTime(time);
        applicationState.setLastFpsMeasurementTime(time);
    }

    private void runMainCycle() {
        while (!Display.isCloseRequested()) {
            handleEvents();
            updateApplicationState();
            renderingEngine.updateAnimationForDeltaTime(getDelta());
            renderingEngine.performRendering();
            Display.update();
            Display.sync(FPS_GARDIAN);
        }
    }

    private void handleEvents() {
        while (Mouse.next()) {
            for (EventHandler eventHandler: mouseEventHandlers) {
                eventHandler.handleEvent();
            }
        }
        while (Keyboard.next()) {
            for (EventHandler eventHandler: keyboardEventHandlers) {
                eventHandler.handleEvent();
            }
        }
    }

    private void updateApplicationState() {
        if (getTime() - applicationState.getLastFpsMeasurementTime() > 1000) {
            applicationState.setLastFpsMeasurementResult(applicationState.getFpsCounter());
            applicationState.setFpsCounter(0);
            applicationState.setLastFpsMeasurementTime(applicationState.getLastFpsMeasurementTime() + 1000);
        }
        applicationState.setFpsCounter(applicationState.getFpsCounter() + 1);
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private double getDelta() {
        long time = getTime();
        double delta = (time - applicationState.getLastFrameTime()) / 1000.0;
        applicationState.setLastFrameTime(time);
        return delta;
    }

    private void finalizeOpenGL() {
        Display.destroy();
    }

}