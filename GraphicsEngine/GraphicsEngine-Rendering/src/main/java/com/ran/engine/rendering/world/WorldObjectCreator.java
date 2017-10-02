package com.ran.engine.rendering.world;

import com.ran.engine.rendering.control.Control;

import java.util.Collections;
import java.util.List;

public interface WorldObjectCreator {

    WorldObjectContent create();

    default List<Control> getControls() {
        return Collections.emptyList();
    }

}
