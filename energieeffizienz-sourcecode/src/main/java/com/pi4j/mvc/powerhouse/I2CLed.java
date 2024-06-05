package com.pi4j.mvc.powerhouse;

import com.pi4j.io.i2c.I2C;

public class I2CLed {
    private final I2C output;
    private final I2CPin pin;
    private final I2CController controller;

    public I2CLed(I2CPin pin, I2C output, I2CController controller) {
        this.output = output;
        this.pin = pin;
        this.controller = controller;

        if (!controller.getCurrentStates().containsKey(output)) {
            controller.getCurrentStates().put(output, 0b1111_1111);
        }
    }

    public void on() {
        int writeAddress = (controller.getCurrentStates().get(output) & ~pin.getAddress()) & 0b1111_1111;
        controller.getCurrentStates().replace(output, writeAddress);
        output.write(writeAddress);
    }

    public void off() {
        int writeAddress = (controller.getCurrentStates().get(output) | pin.getAddress()) & 0b1111_1111;
        controller.getCurrentStates().replace(output, writeAddress);
        output.write(writeAddress);
    }
}
