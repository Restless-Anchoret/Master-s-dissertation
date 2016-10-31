package com.ran.dissertation.world;

import java.util.List;

public class World {

    private final List<DisplayableObject> displayableObjects;
    private final List<Animation> animations;

    public World(List<DisplayableObject> displayableObjects, List<Animation> animations) {
        this.displayableObjects = displayableObjects;
        this.animations = animations;
    }

    public List<DisplayableObject> getDisplayableObjects() {
        return displayableObjects;
    }

    public List<Animation> getAnimations() {
        return animations;
    }
    
}