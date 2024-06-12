package com.pi4j.mvc.powerhouse;

public final class AppStarter {
    private AppStarter() {
        throw new UnsupportedOperationException("Instantiation is not supported.");
    }
    public static void main(String[] args) {
        App.launch(App.class, args);
    }
}
