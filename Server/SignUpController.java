package com.example.loginjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    Label label_username;
    @FXML
    Label label_password;
    @FXML
    TextField textField_username;
    @FXML
    TextField textField_password;
    @FXML
    Button button_dangky;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_dangky.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = textField_username.getText();
                String password = textField_password.getText();
                System.out.println(username + " " + password);
                try {
                    DBUtils.signUpUser(event, username, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
