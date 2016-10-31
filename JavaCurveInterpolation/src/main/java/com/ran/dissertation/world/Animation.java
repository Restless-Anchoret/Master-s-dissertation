package com.ran.dissertation.world;

import java.util.Map;

public class Animation {

    private DisplayableObject displayableObject;
    private Map<Double, Orientation> timeToOrientations;

    public Animation(DisplayableObject displayableObject, Map<Double, Orientation> timeToOrientations) {
        this.displayableObject = displayableObject;
        this.timeToOrientations = timeToOrientations;
    }

    public DisplayableObject getDisplayableObject() {
        return displayableObject;
    }

    public Map<Double, Orientation> getTimeToOrientations() {
        return timeToOrientations;
    }
    
}