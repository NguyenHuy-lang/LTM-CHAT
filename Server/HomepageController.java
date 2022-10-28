package com.example.loginjavafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    @FXML
    private Label label_welcome;
    public void setUserInformation(String username) {
        label_welcome.setText(username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
