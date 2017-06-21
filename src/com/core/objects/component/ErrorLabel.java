package com.core.objects.component;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.*;
import javafx.scene.text.*;

/**
 * Created by agaspari on 6/20/2017.
 */
public class ErrorLabel extends Label {
    public ErrorLabel(String text){
        super(text);
        setPrefWidth(Double.MAX_VALUE);
        setTextFill(javafx.scene.paint.Color.web("#800000"));
        setAlignment(Pos.CENTER);
        setStyle("-fx-border-color: #800000; -fx-background-color: rgba(128, 0, 0, .3); -fx-padding:10px;");
        setFont(javafx.scene.text.Font.font("Tahoma", FontWeight.BOLD, 12));
        setVisible(false);
    }
}
