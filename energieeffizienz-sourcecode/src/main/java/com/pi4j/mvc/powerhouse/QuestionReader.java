package com.pi4j.mvc.powerhouse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pi4j.mvc.powerhouse.game.Difficulty;
import com.pi4j.mvc.powerhouse.game.Question;
import com.pi4j.mvc.powerhouse.game.SimulationObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

final class QuestionReader {
    private QuestionReader() {
        throw new UnsupportedOperationException("Instantiation is not supported.");
    }

    public static List<Question> read(InputStream in, List<SimulationObject> simulationObjects) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            try (BufferedReader buffered = new BufferedReader(reader)) {
                List<Question> questions = new ArrayList<>();
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
                for (var o : jsonObject.keySet()) {
                    Difficulty difficulty =
                        Difficulty.valueOf(jsonObject.get(o).getAsJsonObject().get("difficulty").getAsString());
                    String question = jsonObject.get(o).getAsJsonObject().get("question").getAsString();

                    List<SimulationObject> activeAtStart = new ArrayList<>();
                    for (var s : jsonObject.get(o).getAsJsonObject().get("activeAtStart").getAsJsonArray().asList()) {
                        String current = s.getAsString();
                        for (SimulationObject simulationObject : simulationObjects) {
                            if (simulationObject.getName().equals(current)) {
                                activeAtStart.add(simulationObject);
                            }
                        }
                    }

                    List<SimulationObject> answers = new ArrayList<>();
                    for (var s : jsonObject.get(o).getAsJsonObject().get("answer").getAsJsonArray().asList()) {
                        String current = s.getAsString();
                        for (SimulationObject simulationObject : simulationObjects) {
                            if (simulationObject.getName().equals(current)) {
                                answers.add(simulationObject);
                            }
                        }
                    }
                    String winMessage = jsonObject.get(o).getAsJsonObject().get("winMessage").getAsString();
                    String failMessage = jsonObject.get(o).getAsJsonObject().get("failMessage").getAsString();
                    String imagePath = jsonObject.get(o).getAsJsonObject().get("imagePath").getAsString();
                    String imagePathWeather = jsonObject.get(o).getAsJsonObject().get("imagePathWeather").getAsString();
                    questions.add(
                        new Question(difficulty, question, activeAtStart, answers, winMessage, failMessage, imagePath,
                            imagePathWeather));
                }
                return questions;
            }
        }
    }
}
