package com.pi4j.mvc.powerhouse;

import com.pi4j.io.i2c.I2C;

import java.util.HashMap;
import java.util.Map;

public class I2CController {
    private final Map<I2C, Integer> currentStates = new HashMap<>();

    public Map<I2C, Integer> getCurrentStates() {
        return currentStates;
    }
}
