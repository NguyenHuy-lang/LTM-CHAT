package com.example.loginjavafx;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private TextField textField_boxChat;
    @FXML
    private ScrollBar ScrollBar_message;
    @FXML
    private VBox Vbox_message;
    private Server server;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            server = new Server(new ServerSocket(1234));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ScrollBar_message.setValue((Double) newValue);
            }
        });

        server.receiveMessageFromClient(Vbox_message);
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = textField_boxChat.getText();
                if(!messageToSend.isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: red;" +
                            "-fx-background-color:orangered;" +
                            " -fx-background-radius: 20px");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934,0.945,0.996));

                    hBox.getChildren().add(textFlow);
                    Vbox_message.getChildren().add(hBox);
                    server.sendMessageToClient(messageToSend);
                    textField_boxChat.clear();
                }
            }
        });
    }
    public static void addLabel(String messageFromClient, VBox vBox) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: blue;" + " -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hbox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hbox);
            }
        });
    }
}
