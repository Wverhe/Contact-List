package com.core.objects.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by agaspari on 6/21/2017.
 */
public class CustomLabel extends Label {
    public CustomLabel(String text){
        super(text);
        setAlignment(Pos.CENTER);
        setPrefWidth(Double.MAX_VALUE);
        setTextFill(Color.WHITE);
        setFont(javafx.scene.text.Font.font("Tahoma", FontWeight.SEMI_BOLD, 12));
    }
}
