package com.pi4j.mvc.powerhouse.controller;

import com.pi4j.mvc.powerhouse.components.base.PIN;
import com.pi4j.mvc.powerhouse.game.SimulationObject;
import com.pi4j.mvc.powerhouse.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainControllerTest {
    List<SimulationObject> simulationObjects;
    Model model;
    MainController controller;

    @BeforeEach
    public void setup() {
        simulationObjects = new ArrayList<>();
        simulationObjects.add(new SimulationObject("Fernseher", PIN.D11, PIN.D16, false));
        simulationObjects.add(new SimulationObject("Kuehlschrank", PIN.D11, PIN.D16, false));
        simulationObjects.add(new SimulationObject("Aquarium", PIN.D11, PIN.D16, false));

        model = new Model(simulationObjects);
        controller = new MainController(model);
    }

    @Test
    public void toggleStatusTest() {
        controller.changeStatus(simulationObjects.get(0));
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(true, model.statusMap.get(simulationObjects.get(0)).getValue());

        controller.changeStatus(simulationObjects.get(0));
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(false, model.statusMap.get(simulationObjects.get(0)).getValue());
    }
}
