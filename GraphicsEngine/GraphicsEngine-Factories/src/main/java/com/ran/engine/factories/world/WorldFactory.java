package com.ran.engine.factories.world;

import com.ran.engine.rendering.world.World;

@FunctionalInterface
public interface WorldFactory {

    World createWorld();
    
}