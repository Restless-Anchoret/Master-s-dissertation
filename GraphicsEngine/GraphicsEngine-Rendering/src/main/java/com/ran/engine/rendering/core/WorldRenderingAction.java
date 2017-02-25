package com.ran.engine.rendering.core;

public class WorldRenderingAction implements RenderingAction {

    private static final WorldRenderingAction INSTANCE = new WorldRenderingAction();

    public static WorldRenderingAction getInstance() {
        return INSTANCE;
    }

    @Override
    public void performRendering(RenderingDelegate delegate, RenderingInfo info) {
        delegate.clear(info.getBackgroundColor());

    }

}