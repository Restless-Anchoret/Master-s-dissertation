package com.ran.engine.rendering.core;

/**
 * Created by RestlessAnchoret on 25.02.2017.
 */
public interface RenderingAction {

    void performRendering(RenderingDelegate delegate, RenderingInfo info);

}