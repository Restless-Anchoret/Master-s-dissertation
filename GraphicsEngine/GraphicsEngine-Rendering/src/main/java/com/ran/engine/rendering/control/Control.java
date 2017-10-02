package com.ran.engine.rendering.control;

import com.ran.engine.rendering.world.WorldObject;

public interface Control {

    void increaseParameter();
    void decreaseParameter();

    String getName();
    void setWorldObject(WorldObject worldObject);

}
