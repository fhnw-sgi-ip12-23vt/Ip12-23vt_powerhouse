package com.pi4j.mvc.powerhouse.game;

import com.pi4j.mvc.powerhouse.I2CPin;
import com.pi4j.mvc.powerhouse.components.base.PIN;

public class SimulationObject {
    private final String name;
    private PIN ledPin;
    private I2CPin i2cPin = null;
    private int i2cDeviceAddress;
    private final PIN buttonPin;
    private boolean on;

    public SimulationObject(String name, PIN ledPin, PIN buttonPin, boolean on) {
        this.name = name;
        this.ledPin = ledPin;
        this.buttonPin = buttonPin;
        this.on = on;
    }

    public SimulationObject(String name, I2CPin ledPin, int i2cDeviceAddress, PIN buttonPin, boolean on) {
        this.name = name;
        this.i2cPin = ledPin;
        this.i2cDeviceAddress = i2cDeviceAddress;
        this.buttonPin = buttonPin;
        this.on = on;

    }

    public String getName() {
        return name;
    }

    public boolean isOn() {
        return on;
    }

    public PIN getLedPin() {
        return ledPin;
    }

    public I2CPin getI2cPin() {
        return i2cPin;
    }

    public Integer getI2cDeviceAddress() {
        return i2cDeviceAddress;
    }

    public PIN getButtonPin() {
        return buttonPin;
    }
}

