package com.pi4j.mvc.powerhouse.view.gui;

import com.pi4j.mvc.powerhouse.App;
import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.game.Question;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ViewMixin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Level extends BorderPane implements ViewMixin<Model, MainController> {

    private final List<Question> questions = App.getQuestions();
    private Label currentQuestion;
    private Button nextQuestion;
    private Label status;
    private Button checkAnswer;
    private Button done;
    private ImageView electronix;
    private ImageView weather;

    public Level(MainController controller) {
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
        currentQuestion = new Label(questions.get(0).getQuestion());
        currentQuestion.setWrapText(true);
        currentQuestion.setTextAlignment(TextAlignment.CENTER);
        currentQuestion.setAlignment(Pos.CENTER);
        status = new Label("incorrect");
        status.setStyle("-fx-text-fill: #ff0000");
        nextQuestion = new Button("Next question");
        checkAnswer = new Button("Check answer");
        done = new Button("Done");
        weather = new ImageView();
        weather.setId("weather");
        weather.setFitHeight(200);
        weather.setFitWidth(200);
        weather.setPreserveRatio(true);
        electronix = new ImageView();
        electronix.setFitWidth(200);
        electronix.setFitHeight(200);
        electronix.setPreserveRatio(true);
    }

    @Override
    public void layoutParts() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(200, 0, 0, 0));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(currentQuestion);

        /*
        use these to test
        vbox.getChildren().add(currentQuestion);
        vbox.getChildren().add(status);
        vbox.getChildren().add(checkAnswer);
        vbox.getChildren().add(nextQuestion);
        vbox.getChildren().add(done);
         */

        setLeft(weather);
        setMargin(electronix, new Insets(0, 0, 60, 0));
        setBottom(electronix);
        setCenter(vBox);
    }

    @Override
    public void setupUiToActionBindings(MainController controller) {
        checkAnswer.setOnMouseClicked(event -> controller.checkAnswer());
        nextQuestion.setOnMouseClicked(event -> controller.nextQuestion());
        done.setOnMouseClicked(event -> controller.nextScene());
    }

    @Override
    public void setupModelToUiBindings(Model model) {
        onChangeOf(model.currentQuestionIndex)
            .execute(((oldValue, newValue) -> {
                if (!(model.currentQuestionIndex.getValue() >= questions.size())) {
                    currentQuestion.setText(questions.get(newValue).getQuestion());
                }
            }));
        onChangeOf(model.currentQuestionIndex)
            .execute(((oldValue, newValue) -> {
                if (!(model.currentQuestionIndex.getValue() >= questions.size())) {
                    electronix.setStyle("-fx-image: url(" + model.questions.get(newValue).getImagePath() + ")");
                }
            }));
        onChangeOf(model.currentQuestionIndex)
            .execute(((oldValue, newValue) -> {
                if (!(model.currentQuestionIndex.getValue() >= questions.size())) {
                    weather.setStyle("-fx-image: url(" + model.questions.get(newValue).getImagePathWeather() + ")");
                }
            }));
        onChangeOf(model.questionStatus)
            .convertedBy(correct -> correct ? "correct" : "incorrect")
            .update(status.textProperty());

        onChangeOf(model.questionStatus)
            .execute((oldValue, newValue) -> {
                if (newValue) {
                    status.setStyle("-fx-border-color: #00ff16");
                } else {
                    status.setStyle("-fx-border-color: #ff0000");
                }
            });
    }
}
