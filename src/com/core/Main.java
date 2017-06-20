package com.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    private static Stage pStage;
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

        primaryStage.setTitle("Contact Book");
        primaryStage.setScene(new Scene(passwordForm, 300, 275));
        primaryStage.show();
    }

    static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        Main.pStage = pStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}