package com.core;

import com.core.objects.frames.NewPasswordFrame;
import com.core.objects.frames.PasswordFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;

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

    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        Main.pStage = pStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop(){
        if(!MainFrame.getSaved()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Information");
            alert.setHeaderText("Would you like to save the data you entered or edited?");
            alert.setContentText("You have unsaved data, would you like to save now?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                MainFrame.save();
            } else {}
        }
    }
}