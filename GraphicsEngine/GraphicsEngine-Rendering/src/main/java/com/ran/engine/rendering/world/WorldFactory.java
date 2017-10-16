package com.ran.engine.rendering.world;

import java.util.Collections;

@FunctionalInterface
public interface WorldFactory {

    static WorldFactory empty() {
        return () -> new World(Collections.emptyList(), new Camera());
    }

    World createWorld();
    
}