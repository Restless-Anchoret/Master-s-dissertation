package com.ran.engine.rendering.core;

import com.ran.engine.rendering.world.World;

import java.awt.Color;
import java.util.List;

public class RenderingInfo {

    private final static Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private List<World> worlds;
    private int currentWorldIndex = 0;
    private RenderingMode renderingMode;
    private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

    public RenderingInfo(List<World> worlds, RenderingMode renderingMode) {
        this.worlds = worlds;
        this.renderingMode = renderingMode;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public World getCurrentWorld() {
        return worlds.get(currentWorldIndex);
    }

    public int getCurrentWorldIndex() {
        return currentWorldIndex;
    }

    public void setCurrentWorldIndex(int currentWorldIndex) {
        this.currentWorldIndex = currentWorldIndex;
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