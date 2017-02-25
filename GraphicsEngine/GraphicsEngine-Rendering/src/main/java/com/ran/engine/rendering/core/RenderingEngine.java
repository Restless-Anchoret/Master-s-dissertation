package com.ran.engine.rendering.core;

import com.ran.engine.rendering.world.DisplayableObject;
import com.ran.engine.rendering.world.World;

import java.util.ArrayList;
import java.util.List;

public class RenderingEngine {

    private final RenderingInfo renderingInfo;
    private RenderingDelegate renderingDelegate;
    private final List<RenderingAction> renderingActions;

    public RenderingEngine(World world, RenderingDelegate renderingDelegate, RenderingMode renderingMode) {
        this.renderingInfo = new RenderingInfo(world, renderingMode);
        this.renderingDelegate = renderingDelegate;
        this.renderingActions = new ArrayList<>();
        this.renderingActions.add(WorldRenderingAction.getInstance());
    }

    public RenderingEngine(World world, RenderingDelegate renderingDelegate) {
        this(world, renderingDelegate, RenderingMode.DEFAULT_MODE);
    }

    public RenderingInfo getRenderingInfo() {
        return renderingInfo;
    }

    public RenderingDelegate getRenderingDelegate() {
        return renderingDelegate;
    }

    public void setRenderingDelegate(RenderingDelegate renderingDelegate) {
        this.renderingDelegate = renderingDelegate;
    }

    public List<RenderingAction> getRenderingActions() {
        return renderingActions;
    }

    public void updateAnimationForDeltaTime(double deltaTime) {
        for (DisplayableObject displayableObject: renderingInfo.getWorld().getDisplayableObjects()) {
            displayableObject.updateAnimationForDeltaTime(deltaTime);
        }
    }

    public void performRendering() {
        for (RenderingAction renderingAction: renderingActions) {
            renderingAction.performRendering(renderingDelegate, renderingInfo);
        }
    }

}
