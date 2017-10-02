package com.ran.engine.rendering.control;

public class DoubleParameterControl extends AbstractControl {

    private Double minimumValue;
    private Double maximumValue;
    private double controlValue;
    private double controlValueStep;

    public DoubleParameterControl(Double minimumValue, Double maximumValue,
                                  double defaultControlValue, double controlValueStep) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.controlValue = defaultControlValue;
        this.controlValueStep = controlValueStep;
    }

    public DoubleParameterControl(Double minimumValue, Double maximumValue,
                                  double defaultControlValue) {
        this(minimumValue, maximumValue, defaultControlValue, 1.0);
    }

    public DoubleParameterControl(double defaultControlValue, double controlValueStep) {
        this(null, null, defaultControlValue, controlValueStep);
    }

    public DoubleParameterControl(double defaultControlValue) {
        this(null, null, defaultControlValue, 1.0);
    }

    @Override
    protected void doIncreaseParameter() {
        if (maximumValue == null || controlValue + controlValueStep <= maximumValue) {
            controlValue += controlValueStep;
        }
    }

    @Override
    protected void doDecreaseParameter() {
        if (minimumValue == null || controlValue - controlValueStep >= minimumValue) {
            controlValue -= controlValueStep;
        }
    }

    public Double getMinimumValue() {
        return minimumValue;
    }

    public Double getMaximumValue() {
        return maximumValue;
    }

    public double getControlValue() {
        return controlValue;
    }

    public double getControlValueStep() {
        return controlValueStep;
    }

}
