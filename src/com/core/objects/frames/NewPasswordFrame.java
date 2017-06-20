package com.core.objects.frames;

import com.core.Encrypter;
import com.core.Main;
import com.core.MainFrame;
import com.core.objects.component.CustomButton;
import com.core.objects.component.CustomGridPane;
import com.core.objects.component.ErrorLabel;
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
    private Label lblPassword, lblRepeatPassword, lblError;
    private PasswordField txtPassword, txtRepeatPassword;
    private Button btnSet;
    private Encrypter encrypter;
    private File password;
    public NewPasswordFrame(){
        encrypter = new Encrypter();
        frame = new CustomGridPane();
        frame.addColumns(3);
        lblPassword = new Label("Password: ");
        lblRepeatPassword = new Label("Repeat Password: ");
        txtPassword = new PasswordField();
        txtRepeatPassword = new PasswordField();
        btnSet = new CustomButton("Set");
        btnSet.setMinWidth(100);
        lblError = new ErrorLabel("Error: Test");
        btnSet.setOnAction(
            e ->{
                if(!txtPassword.getText().equals(txtRepeatPassword.getText())){
                    lblError.setText("Error: Passwords don't match.");
                    lblError.setVisible(true);
                }else if(txtPassword.getText().length() < 6){
                    lblError.setText("Error: Password is too short.");
                    lblError.setVisible(true);
                }else{
                    try {
                        password = new File("password.wver");
                        password.createNewFile();
                        FileWriter fw = new FileWriter(password);
                        fw.write(encrypter.encryptText(txtPassword.getText()));
                        fw.close();
                        txtPassword.setText("");
                        txtRepeatPassword.setText("");
                        lblError.setVisible(false);
                        Main.getPrimaryStage().setScene(new Scene(new MainFrame().getFrame(), 300, 275));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                frame.setVisible(false);
            }
        );

        frame.add(lblPassword, 0, 0);
        frame.add(lblRepeatPassword, 0, 1);
        frame.add(txtPassword, 1, 0);
        frame.add(txtRepeatPassword, 1 ,1);
        frame.add(btnSet, 1, 2);
        frame.add(lblError, 0, 3, 3, 1);
    }

    public GridPane getFrame(){
        return frame;
    }
}
