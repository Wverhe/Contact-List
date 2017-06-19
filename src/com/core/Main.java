package com.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    //TODO: Move frame releated stuff to its own class
    private static Stage pStage;
    private TabPane mainFrame;
    private GridPane passwordForm;
    private File password;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        password = new File("password.wver");
        if(password.exists()){
            passwordForm = new PasswordFrame().getFrame();
        }else{
            passwordForm = new NewPasswordFrame().getFrame();
        }
        mainFrame = new MainFrame().getFrame();

        primaryStage.setTitle("Contact Book");
        primaryStage.setScene(new Scene(passwordForm, 300, 275));
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        this.pStage = pStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}