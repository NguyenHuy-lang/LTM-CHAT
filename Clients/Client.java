package com.example.message_client;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e) {
            e.printStackTrace();
            closeEveryThing(socket, bufferedWriter, bufferedReader);
        }
    }


    public void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            closeEveryThing(socket, bufferedWriter, bufferedReader);
        }
    }

    public void receiveMessageFromServer(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()) {
                    try{
                        String messageFromServer = bufferedReader.readLine();
                        ChatController.addLabel(messageFromServer, vBox);
                    }catch (Exception e) {
                        e.printStackTrace();
                        closeEveryThing(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }
    public void closeEveryThing(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }

            if(socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
