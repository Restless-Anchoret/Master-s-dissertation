package com.ran.dissertation.world;

import java.util.List;

public class World {

    private final List<DisplayableObject> displayableObjects;
    private final List<Animation> animations;
    private final Camera camera;

    public World(List<DisplayableObject> displayableObjects, List<Animation> animations, Camera camera) {
        this.displayableObjects = displayableObjects;
        this.animations = animations;
        this.camera = camera;
    }

    public List<DisplayableObject> getDisplayableObjects() {
        return displayableObjects;
    }

    public List<Animation> getAnimations() {
        return animations;
    }

    public Camera getCamera() {
        return camera;
    }
    
}