package com.ran.engine.rendering.world;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final List<DisplayableObject> displayableObjects;
    private List<StaticObject> staticObjects;
    private final Camera camera;

    public World(List<DisplayableObject> displayableObjects, Camera camera) {
        this.displayableObjects = displayableObjects;
        this.camera = camera;
        initializeStaticObjects();
    }

    public List<DisplayableObject> getDisplayableObjects() {
        return displayableObjects;
    }

    public List<StaticObject> getStaticObjects() {
        return staticObjects;
    }

    public Camera getCamera() {
        return camera;
    }

    private void initializeStaticObjects() {
        staticObjects = new ArrayList<>();
        for (DisplayableObject displayableObject: displayableObjects) {
            if (displayableObject instanceof StaticObject) {
                staticObjects.add((StaticObject)displayableObject);
            }
        }
    }
    
}