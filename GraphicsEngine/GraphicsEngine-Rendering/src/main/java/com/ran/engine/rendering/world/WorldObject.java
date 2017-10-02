package com.ran.engine.rendering.world;

import com.ran.engine.rendering.control.Control;

import java.util.ArrayList;
import java.util.List;

public class WorldObject {

    private WorldObjectContent worldObjectContent;
    private WorldObjectCreator worldObjectCreator;
    private List<Control> controls;

    public WorldObject(WorldObjectCreator worldObjectCreator) {
        this.worldObjectCreator = worldObjectCreator;
        this.controls = new ArrayList<>();
        this.controls.addAll(worldObjectCreator.getControls());
        this.controls.forEach(control -> control.setWorldObject(this));
        recreate();
    }

    public WorldObjectContent getWorldObjectContent() {
        return worldObjectContent;
    }

    public WorldObjectCreator getWorldObjectCreator() {
        return worldObjectCreator;
    }

    public List<Control> getControls() {
        return controls;
    }

    public void recreate() {
        worldObjectContent = worldObjectCreator.create();
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        worldObjectContent.getAnimationInfo().setCurrentOrientation(currentOrientation);
        for (WorldObjectPart part: worldObjectContent.getWorldObjectParts()) {
            part.setCurrentFigureVertices(null);
        }
    }

    public void updateCurrentFiguresVertices() {
        for (WorldObjectPart part: worldObjectContent.getWorldObjectParts()) {
            if (part.getCurrentFigureVertices() != null) {
                return;
            }
            part.setCurrentFigureVertices(OrientationMapper.getInstance()
                    .orientVertices(part.getFigure().getVertices(),
                            worldObjectContent.getAnimationInfo().getCurrentOrientation()));
        }
    }

    public void updateAnimationForDeltaTime(double deltaTime) {
        AnimationInfo animationInfo = worldObjectContent.getAnimationInfo();
        animationInfo.updateAnimationForDeltaTime(deltaTime);
        setCurrentOrientation(new Orientation(animationInfo.getCurrentOrientation().getOffset(),
                animationInfo.getAnimationFunction().apply(animationInfo.getCurrentAnimationTime())));
    }

}
