package com.pi4j.mvc.powerhouse.view.gui;

import com.pi4j.mvc.powerhouse.App;
import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ViewMixin;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Result extends BorderPane implements ViewMixin<Model, MainController> {

    private Label status;
    private Label message;
    private Label score;
    private Button nextQuestion;
    private Rectangle energieBarometerOutline;
    private Rectangle energieBarometerAmount;
    private ImageView iamgeCorrect;
    private ImageView imageFalse;
    private Button testAnim;
    private Button testDown;

    private static final int BAROMETER_HEIGHT = 300; // Height of the "Energiebarometers"


    public Result(MainController controller) {
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
        status = new Label();
        status.setStyle("-fx-font-size: 24");
        message = new Label();
        message.setWrapText(true);
        score = new Label();

        nextQuestion = new Button("Next Question");
        energieBarometerAmount = new Rectangle(30, 0);
        energieBarometerAmount.setFill(Color.GREEN);
        energieBarometerOutline = new Rectangle(30, Model.MAX_SCORE * ((double) BAROMETER_HEIGHT / Model.MAX_SCORE));
        energieBarometerOutline.setFill(null);
        energieBarometerOutline.setStroke(Color.BLACK);

        iamgeCorrect = new ImageView();
        iamgeCorrect.setFitWidth(300);
        iamgeCorrect.setFitHeight(300);
        iamgeCorrect.setPreserveRatio(true);
        iamgeCorrect.setImage(new Image(getClass().getResource("/img/Richtig.png").toExternalForm()));

        imageFalse = new ImageView();
        imageFalse.setFitWidth(300);
        imageFalse.setFitHeight(300);
        imageFalse.setPreserveRatio(true);
        imageFalse.setImage(new Image(getClass().getResource("/img/Falsch.png").toExternalForm()));

        testAnim = new Button("test");
        testDown = new Button("test down");
    }

    @Override
    public void layoutParts() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 20, 20, 20));
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(iamgeCorrect, imageFalse, message, score);

        StackPane centerStackPane = new StackPane(vbox);
        centerStackPane.setAlignment(Pos.TOP_CENTER);

        setCenter(centerStackPane);

        StackPane barometerStackPane = new StackPane();
        barometerStackPane.getChildren().addAll(energieBarometerAmount, energieBarometerOutline);

        StackPane.setAlignment(energieBarometerAmount, Pos.BOTTOM_CENTER);

        barometerStackPane.setAlignment(Pos.BOTTOM_CENTER);
        barometerStackPane.setPadding(new Insets(0, 50, 0, 0));

        VBox verticalCenteringVBox = new VBox();
        verticalCenteringVBox.getChildren().add(barometerStackPane);
        verticalCenteringVBox.setAlignment(Pos.CENTER);

        verticalCenteringVBox.setPadding(new Insets(50, 0, 0, 50));

        setRight(verticalCenteringVBox);

        iamgeCorrect.setVisible(false);
        imageFalse.setVisible(false);
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
        nextQuestion.setOnMouseClicked(event -> controller.nextScene());
        testAnim.setOnMouseClicked(event -> animateProgress(Model.MAX_SCORE));
        testDown.setOnMouseClicked(event -> animateProgress(25));
    }

    @Override
    public void setupModelToUiBindings(Model model) {
        onChangeOf(model.questionStatus)
                .execute((oldValue, newValue) -> {
                    iamgeCorrect.setVisible(newValue);  // Show "imageCorrect" if true
                    imageFalse.setVisible(!newValue);  // Show "imageFalse" if false
                });

       onChangeOf(model.questionStatus)
            .execute((oldValue, newValue) -> {
                if (newValue) {
                    this.setId("result-correct");
                } else {
                    this.setId("result-incorrect");
                }
            });

        onChangeOf(model.questionStatus)
            .convertedBy(correct -> correct
                ? App.getQuestions().get(model.currentQuestionIndex.getValue()).getWinMessage()
                : App.getQuestions().get(model.currentQuestionIndex.getValue()).getFailMessage())
            .update(message.textProperty());


        onChangeOf(model.score)
            .execute((oldValue, newValue) -> score.setText("Score: " + newValue));
        onChangeOf(model.score)
            .execute((oldValue, newValue) -> animateProgress(newValue));
    }

    private void animateProgress(int amount) {
        int newHeight = amount * (BAROMETER_HEIGHT / Model.MAX_SCORE);
        if (newHeight >= 0) {
            if (newHeight < energieBarometerAmount.getHeight()) {
                // reverse
                energieBarometerAmount.setFill(Color.RED);
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(energieBarometerAmount.heightProperty(), energieBarometerAmount.getHeight())
                ), new KeyFrame(Duration.seconds(2), new KeyValue(energieBarometerAmount.heightProperty(), newHeight)));
                timeline.play();
            } else {
                energieBarometerAmount.setFill(Color.GREEN);
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.ZERO, new KeyValue(energieBarometerAmount.heightProperty(), 0)
                ), new KeyFrame(Duration.seconds(2), new KeyValue(energieBarometerAmount.heightProperty(), newHeight)));
                timeline.play();
            }
        }
    }
}
