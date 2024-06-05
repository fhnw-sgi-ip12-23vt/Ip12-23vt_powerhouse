package com.pi4j.mvc.powerhouse.view.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class InfoBoxCreator {

    public HBox createInfoBox(String leftText, String rightText) {
        Label leftLabel = new Label(leftText);
        Label rightLabel = new Label(rightText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hbox = new HBox(leftLabel, spacer, rightLabel);
        hbox.setAlignment(Pos.BOTTOM_CENTER);

        return hbox;
    }
}
