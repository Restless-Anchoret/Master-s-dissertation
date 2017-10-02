package com.ran.engine.rendering.world;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractWorldFactory implements WorldFactory {

    protected abstract List<WorldObjectCreator> getWorldObjectCreators();
    protected abstract Camera getCamera();

    @Override
    public World createWorld() {
        List<WorldObject> worldObjects = getWorldObjectCreators().stream()
                .map(WorldObject::new).collect(Collectors.toList());
        return new World(worldObjects, getCamera());
    }

}
