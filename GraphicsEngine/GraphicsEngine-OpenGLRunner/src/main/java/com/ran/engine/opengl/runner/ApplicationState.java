package com.ran.engine.opengl.runner;

public class ApplicationState {

    private long lastFrameTime;

    private long lastFpsMeasurementTime;
    private int fpsCounter = 0;
    private int lastFpsMeasurementResult = 0;

    private int currentDisplayModeIndex;
    private boolean smoothingTurnedOn;
    private boolean verticalSyncTurnedOn;

    public long getLastFrameTime() {
        return lastFrameTime;
    }

    public void setLastFrameTime(long lastFrameTime) {
        this.lastFrameTime = lastFrameTime;
    }

    public long getLastFpsMeasurementTime() {
        return lastFpsMeasurementTime;
    }

    public void setLastFpsMeasurementTime(long lastFpsMeasurementTime) {
        this.lastFpsMeasurementTime = lastFpsMeasurementTime;
    }

    public int getFpsCounter() {
        return fpsCounter;
    }

    public void setFpsCounter(int fpsCounter) {
        this.fpsCounter = fpsCounter;
    }

    public int getLastFpsMeasurementResult() {
        return lastFpsMeasurementResult;
    }

    public void setLastFpsMeasurementResult(int lastFpsMeasurementResult) {
        this.lastFpsMeasurementResult = lastFpsMeasurementResult;
    }

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

}