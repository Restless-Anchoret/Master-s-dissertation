package com.ran.engine.factories.interpolation.input;

public class EmptyInputParameters implements InputParameters {

    private static EmptyInputParameters INSTANCE = new EmptyInputParameters();

    public static EmptyInputParameters getInstance() {
        return INSTANCE;
    }

    private EmptyInputParameters() { }

}