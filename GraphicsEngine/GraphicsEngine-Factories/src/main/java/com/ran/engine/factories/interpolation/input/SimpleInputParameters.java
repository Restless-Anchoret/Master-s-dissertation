package com.ran.engine.factories.interpolation.input;

public class SimpleInputParameters implements InputParameters {

    private double t0, t1;

    public SimpleInputParameters(double t0, double t1) {
        this.t0 = t0;
        this.t1 = t1;
    }

    public double getT0() {
        return t0;
    }

    public double getT1() {
        return t1;
    }

}