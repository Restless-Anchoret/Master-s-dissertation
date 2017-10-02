package com.ran.engine.rendering.world;

import java.util.List;

public class WorldObjectContent {

    private AnimationInfo animationInfo;
    private List<WorldObjectPart> worldObjectParts;

    public WorldObjectContent(AnimationInfo animationInfo, List<WorldObjectPart> worldObjectParts) {
        this.animationInfo = animationInfo;
        this.worldObjectParts = worldObjectParts;
    }

    public AnimationInfo getAnimationInfo() {
        return animationInfo;
    }

    public List<WorldObjectPart> getWorldObjectParts() {
        return worldObjectParts;
    }

}
