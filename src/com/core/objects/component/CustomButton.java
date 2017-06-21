package com.core.objects.component;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

/**
 * Created by agaspari on 6/20/2017.
 */
public class CustomButton extends Button{
    public CustomButton(String text){
        super(text);
        setTextFill(Color.WHITE);
        setFont(javafx.scene.text.Font.font("Tahoma", FontWeight.BOLD, 10));
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: #1d8893; -fx-border-radius: 4px;");
        setCursor(Cursor.HAND);
        setOnMouseEntered(
            e -> {
                setStyle("-fx-background-color: #27b6c4");
                e.consume();
            }
        );
        setOnMouseExited(
            e -> {
                setStyle("-fx-background-color: #1d8893;");
                e.consume();
            }
        );
    }
}