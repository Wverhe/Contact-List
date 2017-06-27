package com.core.objects.frames;

import com.core.Encrypter;
import com.core.Main;
import com.core.MainFrame;
import com.core.objects.component.CustomButton;
import com.core.objects.component.CustomGridPane;
import com.core.objects.component.CustomLabel;
import com.core.objects.component.InfoLabel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by agaspari on 6/19/2017.
 */
public class NewPasswordFrame extends CustomGridPane {
    private CustomLabel lblPassword, lblRepeatPassword;
    private InfoLabel infoLabel;
    private PasswordField txtPassword, txtRepeatPassword;
    private Button btnSet;
    private Encrypter encrypter;
    private File password;
    public NewPasswordFrame(){
        encrypter = new Encrypter();
        addColumns(2);
        setStyle(" -fx-background-color: #3c3c3c;");
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
                        Main.getPrimaryStage().setScene(new Scene(new MainFrame(), 375, 275));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                setVisible(false);
                e.consume();
            }
        );

        add(lblPassword, 0, 0);
        add(lblRepeatPassword, 0, 1);
        add(txtPassword, 1, 0);
        add(txtRepeatPassword, 1 ,1);
        add(btnSet, 1, 2);
        add(infoLabel, 0, 3, 2, 1);
    }
}
