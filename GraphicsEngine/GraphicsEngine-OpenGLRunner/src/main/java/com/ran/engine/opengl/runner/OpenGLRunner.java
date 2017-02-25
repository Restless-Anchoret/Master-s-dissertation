package com.ran.engine.opengl.runner;

import com.ran.engine.factories.world.WorldFactory;
import com.ran.engine.opengl.delegate.OpenGLRenderingDelegate;
import com.ran.engine.opengl.handlers.EventHandler;
import com.ran.engine.opengl.handlers.keyboard.*;
import com.ran.engine.opengl.handlers.mouse.CameraControlHandler;
import com.ran.engine.rendering.core.RenderingEngine;
import com.ran.engine.rendering.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

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
        updateApplicationState();
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
                new AnimationControlHandler(),
                new WorldSwitchHandler(renderingEngine),
                new SmoothingSwitchHandler(),
                new RenderingModeSwitchHandler(),
                new ScreenshotSaverHandler(),
                new DisplayModeSwitchHandler()
        );
    }

    private void initOpenGL() {
        try {
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(true);
            Display.setVSyncEnabled(true);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        SmoothingSwitchHandler.turnOnSmothing();
    }

    private void updateApplicationState() {

    }

    private void runMainCycle() {
        while (!Display.isCloseRequested()) {
            handleEvents();
            updateApplicationState();
            renderingEngine.updateAnimationForDeltaTime(0.0);
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

    private void finalizeOpenGL() {
        Display.destroy();
    }

}