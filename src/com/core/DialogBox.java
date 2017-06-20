package com.core;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by agaspari on 6/20/2017.
 */
public class DialogBox extends Application {
    private static String response;
    private GridPane frame;
    private Label lblPrompt;
    private ArrayList<Button> buttons;

    public DialogBox(String prompt, String... options){
        frame = new GridPane();
        lblPrompt = new Label(prompt);
        for(String option : options){
            buttons.add(new Button(option));
        }
        frame.add(lblPrompt, 0, 0);
        for(int i = 0; i < buttons.size(); i++){
            frame.add(buttons.get(i), i, 1);
        }
    }

    public static String getResponse(){
        return response;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
