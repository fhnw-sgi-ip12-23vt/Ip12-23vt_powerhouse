package com.pi4j.mvc.powerhouse.view.pui;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.mvc.powerhouse.App;
import com.pi4j.mvc.powerhouse.I2CController;
import com.pi4j.mvc.powerhouse.I2CLed;
import com.pi4j.mvc.powerhouse.components.SimpleButton;
import com.pi4j.mvc.powerhouse.components.base.PIN;
import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.game.SimulationObject;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.PuiBase;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;

public class SimulationObjectPUI extends PuiBase<Model, MainController> {

    private Map<SimulationObject, I2CLed> ledMap;
    private Map<SimulationObject, SimpleButton> buttonMap;
    private I2CController i2CController;
    private Map<Integer, I2C> outputs;
    private SimpleButton next;
    private SimpleButton stop;

    public SimulationObjectPUI(MainController controller, Context pi4J) {
        super(controller, pi4J);
    }

    @Override
    public void initializeParts() {
        ledMap = new HashMap<>();
        buttonMap = new HashMap<>();
        i2CController = new I2CController();
        outputs = new HashMap<>();
        next = new SimpleButton(pi4J, PIN.D26, true, 10000L);
        stop = new SimpleButton(pi4J, PIN.D27, true, 1000L);
        for (SimulationObject o : App.getSimulationObjects()) {
            ledMap.put(o, new I2CLed(o.getI2cPin(), createOutput(o.getI2cDeviceAddress()), i2CController));
            buttonMap.put(o, new SimpleButton(pi4J, o.getButtonPin(), true, 1000L));
        }
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
        next.onUp(() -> Platform.runLater(controller::nextScene));
        stop.onUp(() -> Platform.runLater(controller::stopGame));
        for (Map.Entry<SimulationObject, SimpleButton> button : buttonMap.entrySet()) {
            button.getValue().onUp(() -> controller.changeStatus(button.getKey()));
        }
    }

    @Override
    public void setupModelToUiBindings(Model model) {
        for (SimulationObject o : model.statusMap.keySet()) {
            onChangeOf(model.statusMap.get(o))
                .execute((oldValue, newValue) -> {
                    if (newValue) {
                        ledMap.get(o).on();
                    } else {
                        ledMap.get(o).off();
                    }
                });
        }
    }

    private I2C createOutput(int deviceAddress) {
        if (!outputs.containsKey(deviceAddress)) {
            I2CProvider i2CProvider = pi4J.provider("linuxfs-i2c");
            I2CConfig i2cConfig = I2C.newConfigBuilder(pi4J).bus(1).device(deviceAddress).build();
            I2C output = i2CProvider.create(i2cConfig);
            outputs.put(deviceAddress, output);
            return output;
        } else {
            return outputs.get(deviceAddress);
        }
    }
}
