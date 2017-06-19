package com.core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by agaspari on 6/19/2017.
 */
//TODO: Style more.
public class PasswordFrame {
    private GridPane frame;
    private PasswordField txtPassword;
    private Button btnSubmit;
    private Label lblPassword, lblError;
    private Encrypter encrypter;
    private File password;
    public PasswordFrame(){
        encrypter = new Encrypter();
        password = new File("password.wver");
        frame = new GridPane();
        frame.setAlignment(Pos.TOP_CENTER);
        frame.setVgap(5);
        frame.setHgap(5);
        frame.setPadding(new Insets(5));
        lblPassword = new Label("Password: ");
        txtPassword = new PasswordField();
        btnSubmit = new Button("Submit");
        lblError = new Label("Error: Test");
        lblError.setMinWidth(200);
        lblError.setAlignment(Pos.CENTER);
        lblError.setTextFill(Color.web("#800000"));
        lblError.setStyle("-fx-border-color: #800000; -fx-background-color: rgba(128, 0, 0, .3)");
        lblError.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        lblError.setVisible(false);
        btnSubmit.setOnAction(
            e -> {
                try {
                    if(encrypter.decryptText(readFile(password.getPath(), Charset.defaultCharset())).equals(txtPassword.getText())){
                        Main.getPrimaryStage().setScene(new Scene(new MainFrame().getFrame(), 300, 275));
                        lblError.setVisible(false);
                    }else{
                        lblError.setText("Error: Invalid Password.");
                        lblError.setVisible(true);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        );

        frame.add(lblPassword, 0, 0);
        frame.add(txtPassword, 1, 0);
        frame.add(btnSubmit, 0, 1);
        frame.add(lblError, 0, 2, 3, 1);
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public GridPane getFrame(){
        return frame;
    }
}
