package com.core.objects.frames;

import com.core.Encrypter;
import com.core.Main;
import com.core.MainFrame;
import com.core.objects.component.CustomButton;
import com.core.objects.component.CustomGridPane;
import com.core.objects.component.InfoLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by agaspari on 6/19/2017.
 */
public class PasswordFrame {
    private CustomGridPane frame;
    private PasswordField txtPassword;
    private Button btnSubmit, btnReset;
    private InfoLabel lblInfo;
    private Encrypter encrypter;
    private File password;
    public PasswordFrame(){
        encrypter = new Encrypter();
        password = new File("password.wver");
        frame = new CustomGridPane();
        frame.addColumns(2);
        frame.setStyle(" -fx-background-color: #3c3c3c;");
        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        btnSubmit = new CustomButton("Submit");
        btnReset = new CustomButton("Reset Data");
        lblInfo = new InfoLabel("Error: Test");
        btnSubmit.setOnAction(
            e -> {
                try {
                    if(encrypter.decryptText(readFile(password.getPath(), Charset.defaultCharset())).equals(txtPassword.getText())){
                        Scene scene = new Scene(new MainFrame().getFrame(), 375, 275);
                        scene.getStylesheets().add("/resources/stylesheet.css");
                        Main.getPrimaryStage().setScene(scene);
                        lblInfo.dismiss();
                    }else{
                        lblInfo.showError("Error: Invalid Password.");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.consume();
            }
        );

        btnReset.setOnAction(
            e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Reset Data");
                alert.setHeaderText("Clicking OK will clear ALL data.");
                alert.setContentText("This process is irreversible, are you sure you want to do this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    new File("contact-list.wver").delete();
                    new File("password.wver").delete();
                    Main.getPrimaryStage().setScene(new Scene(new NewPasswordFrame().getFrame(), 300, 275));
                } else {
                    //Do Nothing.
                }
                e.consume();
            }
        );
        frame.add(txtPassword, 0, 0, 2, 1);
        frame.add(btnSubmit, 0, 1, 1, 1);
        frame.add(btnReset, 1, 1, 1, 1);
        frame.add(lblInfo, 0, 2, 2, 1);
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public GridPane getFrame(){
        return frame;
    }
}
