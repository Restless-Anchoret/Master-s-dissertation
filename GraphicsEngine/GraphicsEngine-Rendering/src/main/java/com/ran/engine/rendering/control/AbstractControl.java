package com.ran.engine.rendering.control;

import com.ran.engine.rendering.world.WorldObject;

public abstract class AbstractControl implements Control {

    private static final String DEFAULT_CONTROL_NAME = "Control";

    private WorldObject worldObject = null;
    private final String name;

    public AbstractControl(String name) {
        this.name = name;
    }

    public AbstractControl() {
        this(DEFAULT_CONTROL_NAME);
    }

    public WorldObject getWorldObject() {
        return worldObject;
    }

    protected abstract void doIncreaseParameter();
    protected abstract void doDecreaseParameter();

    @Override
    public void increaseParameter() {
        doIncreaseParameter();
        parameterChanged();
    }

    @Override
    public void decreaseParameter() {
        doDecreaseParameter();
        parameterChanged();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setWorldObject(WorldObject worldObject) {
        this.worldObject = worldObject;
    }

    protected void parameterChanged() {
        worldObject.recreate();
    }
    
}
