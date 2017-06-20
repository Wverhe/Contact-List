package com.core.objects.component;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Created by agaspari on 6/20/2017.
 */
public class CustomButton extends Button{
    public CustomButton(String text){
        super(text);
        setTextFill(Color.WHITE);
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: #4CAF50; -fx-border-radius: 4px;");
        setCursor(Cursor.HAND);
        setOnMouseEntered(
            e -> setStyle("-fx-background-color: #45a049")
        );
        setOnMouseExited(
            e -> setStyle("-fx-background-color: #4CAF50;")
        );
    }
}
