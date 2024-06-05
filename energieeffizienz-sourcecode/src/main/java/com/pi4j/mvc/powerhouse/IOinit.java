package com.pi4j.mvc.powerhouse;

import com.pi4j.mvc.powerhouse.components.base.PIN;
import com.pi4j.mvc.powerhouse.game.SimulationObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

final class IOinit {
    // TODO ask where this class should be located, maybe util
    private IOinit() {
        throw new UnsupportedOperationException("Instantiation is not supported.");
    }
    public static List<SimulationObject> init(InputStream in) throws IOException {

        List<SimulationObject> simulationObjects = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(in)) {
            try (BufferedReader buffered = new BufferedReader(reader)) {
                // to skip title row
                String line = buffered.readLine();
                while ((line = buffered.readLine()) != null) {
                    String[] parts = line.split(";");
                    String name = parts[0];
                    // PIN ledPin = PIN.valueOf(parts[1]);
                    I2CPin i2CPin = I2CPin.valueOf(parts[1]);
                    int i2cDeviceAddress = Integer.parseInt(parts[2]);
                    PIN buttonPin = PIN.valueOf(parts[3]);
                    simulationObjects.add(new SimulationObject(name, i2CPin, i2cDeviceAddress, buttonPin, false));
                }
            }
        }
        return simulationObjects;
    }
}
