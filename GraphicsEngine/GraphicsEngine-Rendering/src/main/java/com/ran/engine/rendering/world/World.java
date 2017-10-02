package com.ran.engine.rendering.world;

import com.ran.engine.rendering.control.Control;

import java.util.List;
import java.util.stream.Collectors;

public class World {

    private final List<WorldObject> worldObjects;
    private final Camera camera;

    private List<Control> controls;
    private int chosenControlIndex = -1;

    public World(List<WorldObject> worldObjects, Camera camera) {
        this.worldObjects = worldObjects;
        this.camera = camera;
        initializeControls();
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Control> getControls() {
        return controls;
    }

    public int getChosenControlIndex() {
        return chosenControlIndex;
    }

    public void setChosenControlIndex(int chosenControlIndex) {
        this.chosenControlIndex = chosenControlIndex;
    }

    public Control getCurrentControl() {
        return controls.get(chosenControlIndex);
    }

    private void initializeControls() {
        controls = worldObjects.stream()
                .flatMap(worldObject -> worldObject.getWorldObjectCreator()
                        .getControls().stream())
                .collect(Collectors.toList());
    }
    
}
