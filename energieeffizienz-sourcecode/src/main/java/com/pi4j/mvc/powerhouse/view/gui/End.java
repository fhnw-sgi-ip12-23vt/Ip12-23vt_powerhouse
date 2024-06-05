package com.pi4j.mvc.powerhouse.view.gui;

import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ViewMixin;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class End extends VBox implements ViewMixin<Model, MainController> {

    private Label title;
    private Label score;
    private BorderPane borderPane;
    private Rectangle energieBarometerOutline;
    private Rectangle energieBarometerAmount;
    private ImageView electronix;
    private int barometerHeight = 300;

    public End(MainController controller) {
        init(controller);
    }

    @Override
    public void initializeSelf() {
        setPrefWidth(1024);
        setPrefHeight(600);
        this.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
    }

    @Override
    public void initializeParts() {
        title = new Label("Du hast es geschafft! Elektronix ist sehr stolz auf dich und ist überglücklich.");
        score = new Label();
        borderPane = new BorderPane();
        energieBarometerAmount = new Rectangle(30, 0);
        energieBarometerAmount.setFill(Color.GREEN);
        energieBarometerOutline = new Rectangle(30, Model.MAX_SCORE * (barometerHeight / Model.MAX_SCORE));
        energieBarometerOutline.setFill(null);
        energieBarometerOutline.setStroke(Color.BLACK);
        electronix = new ImageView(new Image(this.getClass().getResourceAsStream("E-3.png")));
        electronix.setFitHeight(200);
        electronix.setFitWidth(200);
        electronix.setId("electronix-happy-end");
    }

    @Override
    public void layoutParts() {
        title.setStyle("-fx-font-size: 24");
        title.setWrapText(true);
        title.setCenterShape(true);
        title.setTextAlignment(TextAlignment.CENTER);

        setPadding(new Insets(20));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(title, electronix);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.BASELINE_CENTER);

        InfoBoxCreator infoBoxCreator = new InfoBoxCreator();
        HBox infoBox = infoBoxCreator.createInfoBox("Stop", "Neustart");

        borderPane.setCenter(box);
        borderPane.setRight(stackPane);
        borderPane.setBottom(infoBox);

        getChildren().add(borderPane);

        Timeline timeline = new Timeline(new KeyFrame(
            Duration.ZERO, new KeyValue(energieBarometerAmount.heightProperty(), 0)
        ), new KeyFrame(Duration.seconds(2), new KeyValue(energieBarometerAmount.heightProperty(), 200)));
        timeline.play();
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
    }

    @Override
    public void setupModelToUiBindings(Model model) {
        onChangeOf(model.score)
            .execute((oldValue, newValue) -> score.setText("Score: " + newValue));
    }



}
