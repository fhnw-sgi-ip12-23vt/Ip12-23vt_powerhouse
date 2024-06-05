package com.pi4j.mvc.powerhouse;

import com.pi4j.Pi4J;
import com.pi4j.mvc.powerhouse.controller.MainController;
import com.pi4j.mvc.powerhouse.game.Question;
import com.pi4j.mvc.powerhouse.game.SimulationObject;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.view.gui.GameScene;
import com.pi4j.mvc.powerhouse.view.gui.PuiEmulator;
import com.pi4j.mvc.powerhouse.view.gui.Start;
import com.pi4j.mvc.powerhouse.view.pui.SimulationObjectPUI;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.List;

public class App extends Application {

    private final InputStream QUESTIONS_FILE = this.getClass().getResourceAsStream("questions.json");
    private final InputStream IOOBJECTS_FILE = this.getClass().getResourceAsStream("ioAll.csv");


    private MainController controller;
    private SimulationObjectPUI pui;

    static List<Question> questions; // need to find a way to delete these
    static List<SimulationObject> simulationObjects; // need to find a way to delete these

    @Override
    public void start(Stage stage) throws Exception {
        simulationObjects = IOinit.init(IOOBJECTS_FILE);

        // that's your 'information hub'.
        Model model = new Model(simulationObjects);
        controller = new MainController(model);

        model.questions = QuestionReader.read(QUESTIONS_FILE, model.simulationObjects);
        questions = model.questions;
        Model.MAX_SCORE = calculateMaxScore();

        //both gui and pui are working on the same controller
        pui = new SimulationObjectPUI(controller, Pi4J.newAutoContext());
        // on desktop, it's convenient to have a very basic emulator
        // for the PUI to test the interaction between GUI and PUI
        startGui(new Start(controller));
        startPUIEmulator(new PuiEmulator(controller));
    }


    @Override
    public void stop() {
        controller.shutdown();
        pui.shutdown();
    }

    private void startPUIEmulator(Parent puiEmulator) {
        Scene emulatorScene = new Scene(puiEmulator);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("PUI Emulator");
        secondaryStage.setScene(emulatorScene);
        secondaryStage.show();
    }

    private void startGui(Parent start) {
        Scene guiScene = new Scene(start);
        guiScene.setCursor(Cursor.NONE);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Powerhouse");
        primaryStage.setScene(guiScene);
        primaryStage.setFullScreen(true);
        controller.setPrimaryScene(guiScene);
        controller.setCurrentGameScene(GameScene.START);
        primaryStage.show();
    }

    private int calculateMaxScore() {
        int maxScore = 0;
        for (Question q : questions) {
            maxScore += q.getDifficulty().getPointsWin();
        }
        return maxScore;
    }

    public static List<Question> getQuestions() {
        return questions;
    }

    public static List<SimulationObject> getSimulationObjects() {
        return simulationObjects;
    }
}
