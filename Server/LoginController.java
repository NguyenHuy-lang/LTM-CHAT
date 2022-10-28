package com.example.loginjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button button_login;
    @FXML
    Label label_username;
    @FXML
    Label label_password;
    @FXML
    TextField textField_username;
    @FXML
    TextField textField_password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = textField_username.getText();
                String password = textField_password.getText();
                DBUtils.loginUser(event, username,password);
            }
        });
    }
}
