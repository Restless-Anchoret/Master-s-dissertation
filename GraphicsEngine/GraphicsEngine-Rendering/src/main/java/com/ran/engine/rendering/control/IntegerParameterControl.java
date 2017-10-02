package com.ran.engine.rendering.control;

public class IntegerParameterControl extends AbstractControl {

    private Integer minimumValue = null;
    private Integer maximumValue = null;
    private int controlValue;
    private int controlValueStep;

    public IntegerParameterControl(Integer minimumValue, Integer maximumValue,
                                   int defaultControlValue, int controlValueStep) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.controlValue = defaultControlValue;
        this.controlValueStep = controlValueStep;
    }

    public IntegerParameterControl(Integer minimumValue, Integer maximumValue, int defaultControlValue) {
        this(minimumValue, maximumValue, defaultControlValue, 1);
    }

    public IntegerParameterControl(int defaultControlValue, int controlValueStep) {
        this(null, null, defaultControlValue, controlValueStep);
    }

    public IntegerParameterControl(int defaultControlValue) {
        this(null, null, defaultControlValue, 1);
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

    public Integer getMinimumValue() {
        return minimumValue;
    }

    public Integer getMaximumValue() {
        return maximumValue;
    }

    public int getControlValue() {
        return controlValue;
    }

    public int getControlValueStep() {
        return controlValueStep;
    }

}
