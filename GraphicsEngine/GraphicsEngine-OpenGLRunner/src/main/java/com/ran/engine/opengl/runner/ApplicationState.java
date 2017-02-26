package com.ran.engine.opengl.runner;

public class ApplicationState {

    private long lastFrameTime;
    private int currentDisplayModeIndex;
    private boolean smoothingTurnedOn;
    private boolean verticalSyncTurnedOn;

    public int getCurrentDisplayModeIndex() {
        return currentDisplayModeIndex;
    }

    public void setCurrentDisplayModeIndex(int currentDisplayModeIndex) {
        this.currentDisplayModeIndex = currentDisplayModeIndex;
    }

    public boolean isSmoothingTurnedOn() {
        return smoothingTurnedOn;
    }

    public void setSmoothingTurnedOn(boolean smoothingTurnedOn) {
        this.smoothingTurnedOn = smoothingTurnedOn;
    }

    public boolean isVerticalSyncTurnedOn() {
        return verticalSyncTurnedOn;
    }

    public void setVerticalSyncTurnedOn(boolean verticalSyncTurnedOn) {
        this.verticalSyncTurnedOn = verticalSyncTurnedOn;
    }

    public long getLastFrameTime() {
        return lastFrameTime;
    }

    public void setLastFrameTime(long lastFrameTime) {
        this.lastFrameTime = lastFrameTime;
    }

}