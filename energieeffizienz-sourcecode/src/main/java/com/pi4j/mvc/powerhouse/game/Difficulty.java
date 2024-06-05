package com.pi4j.mvc.powerhouse.game;

public enum Difficulty {
   EINSTIEGSLEVEL("einsteigslevel", 0, 0), EASY("easy", 10, -5), MEDIUM("medium", 20, -10), HARD("hard", 30, -15);

    private final String name;
    private final int pointsWin;
    private final int pointsFail;

    Difficulty(String name, int pointsWin, int pointsFail) {
        this.name = name;
        this.pointsWin = pointsWin;
        this.pointsFail = pointsFail;
    }

    public int getPointsFail() {
        return pointsFail;
    }

    public int getPointsWin() {
        return pointsWin;
    }

    public String getName() {
        return name;
    }
}
