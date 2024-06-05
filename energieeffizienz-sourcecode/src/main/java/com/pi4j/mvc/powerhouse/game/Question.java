package com.pi4j.mvc.powerhouse.game;

import java.util.List;

public class Question {
    private final Difficulty difficulty;
    private final String question;
    private final List<SimulationObject> activeAtStart;
    private final List<SimulationObject> answers;
    private final String winMessage;
    private final String failMessage;
    private final String imagePath;
    private final String imagePathWeather;


    public Question(Difficulty difficulty, String question, List<SimulationObject> activeAtStart,
                    List<SimulationObject> answers, String winMessage, String failMessage, String imagePath,
                    String imagePathWeather) {
        this.difficulty = difficulty;
        this.question = question;
        this.activeAtStart = activeAtStart;
        this.answers = answers;
        this.winMessage = winMessage;
        this.failMessage = failMessage;
        this.imagePath = imagePath;
        this.imagePathWeather = imagePathWeather;

    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<SimulationObject> getActiveAtStart() {
        return activeAtStart;
    }

    public List<SimulationObject> getAnswers() {
        return answers;
    }

    public String getWinMessage() {
        return winMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImagePathWeather() {
        return imagePathWeather;
    }

}
