package com.pi4j.mvc.powerhouse.view.gui;

import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ViewMixin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class Intro extends VBox implements ViewMixin<Model, MainController> {

    private Label title;
    private Label score;
    private BorderPane borderPane;
    private Rectangle energieBarometerOutline;
    private Rectangle energieBarometerAmount;
    private ImageView elektronixIntro;
    private int BarometerHeight = 300;
    private Button intro;

    public Intro(MainController controller) {
        init(controller);
        setId("intro");

    }

    @Override
    public void initializeSelf() {
        setPrefWidth(1024);
        setPrefHeight(600);
        this.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
    }

    @Override
    public void initializeParts() {
        title = new Label(
            "Das Energiemonster Elektronix hat keine Energie mehr. "
                + "Indem du in deinem Haus Energie sparst, lädt sich Elektronix wieder auf.");
        score = new Label();
        borderPane = new BorderPane();
        energieBarometerAmount = new Rectangle(30, 0);
        energieBarometerAmount.setFill(Color.GREEN);
        energieBarometerOutline = new Rectangle(30, Model.MAX_SCORE * (BarometerHeight / Model.MAX_SCORE));
        energieBarometerOutline.setFill(null);
        energieBarometerOutline.setStroke(Color.BLACK);
        elektronixIntro = new ImageView();
        elektronixIntro.setId("elektronixIntro");
        elektronixIntro.setFitHeight(500);
        elektronixIntro.setFitWidth(200);
        elektronixIntro.setPreserveRatio(true);
        intro = new Button("intro");
    }

    @Override
    public void layoutParts() {
        title.setStyle("-fx-font-size: 30");
        title.setWrapText(true);
        title.setCenterShape(true);
        title.setTextAlignment(TextAlignment.CENTER);

        setPadding(new Insets(20));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(title, elektronixIntro);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.BASELINE_CENTER);
        stackPane.getChildren().addAll(energieBarometerAmount, energieBarometerOutline);

        borderPane.setCenter(box);
        borderPane.setRight(stackPane);

        getChildren().add(borderPane);
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
        intro.setOnMouseClicked(event -> controller.nextScene());
    }

    @Override
    public void setupModelToUiBindings(Model model) {
    }

}
