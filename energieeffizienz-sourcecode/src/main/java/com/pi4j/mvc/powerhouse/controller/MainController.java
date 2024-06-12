package com.pi4j.mvc.powerhouse.controller;

import com.pi4j.mvc.powerhouse.game.SimulationObject;
import com.pi4j.mvc.powerhouse.model.Model;
import com.pi4j.mvc.powerhouse.util.mvcbase.ControllerBase;
import com.pi4j.mvc.powerhouse.view.gui.End;
import com.pi4j.mvc.powerhouse.view.gui.GameScene;
import com.pi4j.mvc.powerhouse.view.gui.Intro;
import com.pi4j.mvc.powerhouse.view.gui.Level;
import com.pi4j.mvc.powerhouse.view.gui.Result;
import com.pi4j.mvc.powerhouse.view.gui.Start;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainController extends ControllerBase<Model> {
    private Scene primaryScene;
    private GameScene currentGameScene;
    private final Map<GameScene, Parent> sceneCache = new HashMap<>();

    public MainController(Model model) {
        super(model);
    }

    // the logic we need in our application
    // these methods can be called from GUI and PUI (and from nowhere else)
    public void nextScene() {
        switch (currentGameScene) {
        case START -> switchTo(GameScene.INTRO);
        case INTRO, RESULT -> switchTo(GameScene.LEVEL);
        case LEVEL -> switchTo(GameScene.RESULT);
        case END -> switchTo(GameScene.START);
        default -> throw new IllegalArgumentException("No game scene provided");
        }
    }


    public void switchTo(GameScene gameScene) {
        // load end scene if no more questions
        currentGameScene = gameScene;
        if (gameScene == GameScene.START) {
            resetGame();
            if (sceneCache.containsKey(GameScene.START)) {
                primaryScene.setRoot(sceneCache.get(GameScene.START));
                System.out.println("loaded from cache");
            } else {
                Parent root = new Start(this);
                sceneCache.put(GameScene.START, root);
                primaryScene.setRoot(root);
                System.out.println("created new Scene");
            }
        }
        if (gameScene == GameScene.INTRO) {
            Parent root = new Intro(this);
            primaryScene.setRoot(root);
            return;
        }
        if (model.currentQuestionIndex.getValue() == model.questions.size()) {
            currentGameScene = GameScene.END;
            Parent root = new End(this);
            checkAnswer();
            primaryScene.setRoot(root);
            return;
        }
        if (gameScene == GameScene.LEVEL) {
            if (sceneCache.containsKey(GameScene.LEVEL)) {
                primaryScene.setRoot(sceneCache.get(GameScene.LEVEL));
                System.out.println("loaded from cache");
            } else {
                Parent root = new Level(this);
                sceneCache.put(GameScene.LEVEL, root);
                primaryScene.setRoot(root);
                System.out.println("created new Scene");
            }
            resetQuestion();
            activateStartingObjectsForNextQuestion();
        } else if (gameScene == GameScene.RESULT) {
//            if (sceneCache.containsKey(GameScene.RESULT)) {
//                primaryStage.setScene(sceneCache.get(GameScene.RESULT));
//                System.out.println("loaded from cache");
//            } else {
//                Scene nextScene = new Scene(new Result(this));
//                sceneCache.put(GameScene.RESULT, nextScene);
//                primaryStage.setScene(nextScene);
//                System.out.println("created new Scene");
//            }

            Parent root = new Result(this);
            sceneCache.put(GameScene.RESULT, root);
            primaryScene.setRoot(root);
            System.out.println("created new Scene");
            checkAnswer();
            nextQuestion();
        }
    }

    public void changeStatus(SimulationObject simulationObject) {
        toggleValue(model.statusMap.get(simulationObject));
    }

    public void checkAnswer() {
        // TODO write this cleaner
        if (model.currentQuestionIndex.getValue() < 0
            || model.currentQuestionIndex.getValue() >= model.questions.size()) {
            return;
        }

        List<SimulationObject> answers = model.questions.get(model.currentQuestionIndex.getValue()).getAnswers();
        boolean correct = true;
        if (answers.isEmpty()) {
            for (SimulationObject o : model.statusMap.keySet()) {
                if (model.statusMap.get(o).getValue()) {
                    correct = false;
                }
            }
        } else {
            for (SimulationObject o : model.statusMap.keySet()) {
                if (answers.contains(o)) {
                    if (!model.statusMap.get(o).getValue()) {
                        correct = false;
                    }
                } else if (model.statusMap.get(o).getValue()) {
                    correct = false;
                }
            }
        }
        int points = correct
            ? model.questions.get(model.currentQuestionIndex.getValue()).getDifficulty().getPointsWin()
            : model.questions.get(model.currentQuestionIndex.getValue()).getDifficulty().getPointsFail();
        setValue(model.questionStatus, correct);
        setValue(model.score, model.score.getValue() + points);
    }

    public void nextQuestion() {
        if (model.currentQuestionIndex.getValue() <= model.questions.size()) {
            increaseValue(model.currentQuestionIndex);
        }
    }

    public void stopGame() {
        switchTo(GameScene.START);
    }

    private void resetGame() {
        setValue(model.currentQuestionIndex, 0);
        setValue(model.score, 0);
    }

    public void setPrimaryScene(Scene primaryScene) {
        this.primaryScene = primaryScene;
    }

    public void setCurrentGameScene(GameScene currentGameScene) {
        this.currentGameScene = currentGameScene;
    }

    private void resetQuestion() {
        setValue(model.questionStatus, false);
        for (SimulationObject o : model.simulationObjects) {
            setValue(model.statusMap.get(o), false);
        }
    }

    private void activateStartingObjectsForNextQuestion() {
        for (SimulationObject o : model.questions.get(model.currentQuestionIndex.getValue()).getActiveAtStart()) {
            changeStatus(o);
        }
    }
}
