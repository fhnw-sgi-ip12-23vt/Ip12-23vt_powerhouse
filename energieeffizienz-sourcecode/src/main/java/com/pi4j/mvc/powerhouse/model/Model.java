package com.pi4j.mvc.powerhouse.model;

import com.pi4j.mvc.powerhouse.game.Question;
import com.pi4j.mvc.powerhouse.game.SimulationObject;
import com.pi4j.mvc.powerhouse.util.mvcbase.ObservableValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In MVC the 'Model' mainly consists of 'ObservableValues'.
 * <p>
 * There should be no need for additional methods.
 * <p>
 * All the application logic is handled by the 'Controller'
 */
public class Model {
    public static int MAX_SCORE;

    public List<SimulationObject> simulationObjects;
    public List<Question> questions;

    public Map<SimulationObject, ObservableValue<Boolean>> statusMap;

    public ObservableValue<Integer> currentQuestionIndex = new ObservableValue<>(0);

    public ObservableValue<Boolean> questionStatus = new ObservableValue<>(false);

    public ObservableValue<Integer> score = new ObservableValue<>(0);

    public Model(List<SimulationObject> simulationObjects) {
        this.simulationObjects = simulationObjects;
        statusMap = new HashMap<>() {{
            for (SimulationObject o : simulationObjects) {
                put(o, new ObservableValue<>(false));
            }
        }};
    }
}
