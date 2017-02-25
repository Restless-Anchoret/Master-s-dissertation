package com.ran.engine.rendering.core;

import com.ran.engine.rendering.world.World;

import java.awt.*;

public class RenderingInfo {

    private final static Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private World world;
    private RenderingMode renderingMode;
    private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

    public RenderingInfo(World world, RenderingMode renderingMode) {
        this.world = world;
        this.renderingMode = renderingMode;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public RenderingMode getRenderingMode() {
        return renderingMode;
    }

    public void setRenderingMode(RenderingMode renderingMode) {
        this.renderingMode = renderingMode;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}