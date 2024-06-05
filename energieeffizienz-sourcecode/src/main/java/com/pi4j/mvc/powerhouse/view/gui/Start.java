package com.pi4j.mvc.powerhouse.view.gui;

import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ViewMixin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class Start extends VBox implements ViewMixin<Model, MainController> {

    private Label title;
    private Label startMessage;
    private Button start;
    private ImageView logo;
    private ImageView elektronixStart;


    public Start(MainController controller) {
        init(controller);
        setId("start");

    }

    @Override
    public void initializeSelf() {

        setPrefWidth(1024);
        setPrefHeight(600);
        this.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
    }

    @Override
    public void initializeParts() {
        startMessage = new Label("Press → to start");
        start = new Button("Start");
        logo = new ImageView();
        logo.setId("logo");
        logo.setFitHeight(600);
        logo.setFitWidth(500);
        logo.setPreserveRatio(true);
        elektronixStart = new ImageView();
        elektronixStart.setId("elektronixStart");
        elektronixStart.setFitHeight(500);
        elektronixStart.setFitWidth(300);
        elektronixStart.setPreserveRatio(true);

    }

    @Override
    public void layoutParts() {
        setPadding(new Insets(20));
        setSpacing(20);
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(logo);
        getChildren().add(elektronixStart);
        getChildren().add(startMessage);

        // getChildren().add(start);
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
        start.setOnMouseClicked(event -> controller.nextScene());
    }

    @Override
    public void setupModelToUiBindings(Model model) {
    }

}
