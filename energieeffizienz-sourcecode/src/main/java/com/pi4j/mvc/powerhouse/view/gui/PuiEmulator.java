package com.pi4j.mvc.powerhouse.view.gui;

import com.pi4j.mvc.powerhouse.App;
import com.pi4j.mvc.powerhouse.game.SimulationObject;
import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ViewMixin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


import java.util.HashMap;
import java.util.Map;

public class PuiEmulator extends ScrollPane implements ViewMixin<Model, MainController> {

    // for each PUI component, declare a corresponding JavaFX-control
    private Button done;
    private Button stop;
    private Map<SimulationObject, Label> ledMap;
    private Map<SimulationObject, Button> buttonMap;

    public PuiEmulator(MainController controller) {
        init(controller);
    }

    @Override
    public void initializeSelf() {
        setPrefWidth(1024);
        setPrefHeight(600);
    }

    @Override
    public void initializeParts() {
        ledMap = new HashMap<>();
        buttonMap = new HashMap<>();
        for (SimulationObject o : App.getSimulationObjects()) {
            ledMap.put(o, new Label("Led"));
            buttonMap.put(o, new Button(o.getName()));
        }
        done = new Button("→");
        done.setStyle("-fx-font-size: 48");
        stop = new Button("STOP");
        stop.setStyle("-fx-font-size: 48");
        // done.setStyle("-fx-background-color: #00ff16");
    }

    @Override
    public void layoutParts() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(done, stop);
        setPadding(new Insets(20));
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);

        for (var x : ledMap.values()) {
            vBox.getChildren().add(x);
        }
        for (var x : buttonMap.values()) {
            vBox.getChildren().add(x);
        }

        setContent(vBox);
        setFitToWidth(true);
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
        //trigger the same actions as the real PUI
        for (Map.Entry<SimulationObject, Button> button : buttonMap.entrySet()) {
            button.getValue().setOnMousePressed(event -> controller.changeStatus(button.getKey()));
        }

        done.setOnMouseClicked(event -> controller.nextScene());
        stop.setOnMouseClicked(event -> controller.stopGame());
    }

    @Override
    public void setupModelToUiBindings(Model model) {
        //observe the same values as the real PUI
        for (SimulationObject o : model.statusMap.keySet()) {
            onChangeOf(model.statusMap.get(o))
                .convertedBy(active -> active ? o.getName() + ": on" : o.getName() + ": off")
                .update(ledMap.get(o).textProperty());
            onChangeOf(model.statusMap.get(o))
                .execute((oldValue, newValue) -> {
                    if (newValue) {
                        buttonMap.get(o).setStyle("-fx-border-color: #00ff16");
                    } else {
                        buttonMap.get(o).setStyle("-fx-border-color: #ff0000");
                    }
                });
            onChangeOf(model.statusMap.get(o)).execute((oldValue, newValue) -> {
                System.out.println("changed: " + oldValue);
                System.out.println(newValue + "\n");
            });
        }
    }
}
