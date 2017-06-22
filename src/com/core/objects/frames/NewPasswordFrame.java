package com.core.objects.frames;

import com.core.Encrypter;
import com.core.Main;
import com.core.MainFrame;
import com.core.objects.component.CustomButton;
import com.core.objects.component.CustomGridPane;
import com.core.objects.component.CustomLabel;
import com.core.objects.component.InfoLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by agaspari on 6/19/2017.
 */
public class NewPasswordFrame {
    private CustomGridPane frame;
    private CustomLabel lblPassword, lblRepeatPassword;
    private InfoLabel infoLabel;
    private PasswordField txtPassword, txtRepeatPassword;
    private Button btnSet;
    private Encrypter encrypter;
    private File password;
    public NewPasswordFrame(){
        encrypter = new Encrypter();
        frame = new CustomGridPane();
        frame.addColumns(2);
        frame.setStyle(" -fx-background-color: #3c3c3c;");
        lblPassword = new CustomLabel("Password: ");
        lblRepeatPassword = new CustomLabel("Repeat Password: ");
        txtPassword = new PasswordField();
        txtRepeatPassword = new PasswordField();
        btnSet = new CustomButton("Set");
        btnSet.setMinWidth(100);
        infoLabel = new InfoLabel("Error: Test");
        btnSet.setOnAction(
            e ->{
                if(!txtPassword.getText().equals(txtRepeatPassword.getText())){
                    infoLabel.showError("Error: Passwords don't match.");
                }else if(txtPassword.getText().length() < 6){
                    infoLabel.showError("Error: Password is too short.");
                }else{
                    try {
                        password = new File("password.wver");
                        password.createNewFile();
                        FileWriter fw = new FileWriter(password);
                        fw.write(encrypter.encryptText(txtPassword.getText()));
                        fw.close();
                        txtPassword.setText("");
                        txtRepeatPassword.setText("");
                        infoLabel.dismiss();
                        Main.getPrimaryStage().setScene(new Scene(new MainFrame().getFrame(), 375, 275));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                frame.setVisible(false);
                e.consume();
            }
        );

        frame.add(lblPassword, 0, 0);
        frame.add(lblRepeatPassword, 0, 1);
        frame.add(txtPassword, 1, 0);
        frame.add(txtRepeatPassword, 1 ,1);
        frame.add(btnSet, 1, 2);
        frame.add(infoLabel, 0, 3, 2, 1);
    }

    public GridPane getFrame(){
        return frame;
    }
}
