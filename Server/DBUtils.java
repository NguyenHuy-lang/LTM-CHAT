package com.example.loginjavafx;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
public class DBUtils {
    public static void changeScene(ActionEvent event, String fxml, String title, String username) {
        Parent root = null;
        if (username != null) {
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxml));
                root = loader.load();
                HomepageController homepageController = loader.getController();
                homepageController.setUserInformation(username);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }
    public static void loginUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login-fx", "root", "123456789");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM user_system where username = ? and pass = ?");
            psCheckUserExist.setString(1, username);
            psCheckUserExist.setString(2, password);
            resultSet = psCheckUserExist.executeQuery();
            if(resultSet.isBeforeFirst()) {
                changeScene(event, "homePage.fxml", "welcome", username);
            } else {
                System.out.println("Username is used");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username and Password are wrong !");
                alert.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(resultSet!=null) {
                try{
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null) {
                try{
                    psCheckUserExist.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try{
                    psInsert.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void signUpUser(ActionEvent event, String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login-fx", "root", "123456789");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM user_system where username = ?");
            psCheckUserExist.setString(1, username);
            resultSet = psCheckUserExist.executeQuery();
            if(resultSet.isBeforeFirst()) {
                System.out.println("Username is used");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("Insert into user_system (username, pass) Values(?,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
                changeScene(event, "homePage.fxml", "welcome", username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(resultSet!=null) {
                try{
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null) {
                try{
                    psCheckUserExist.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try{
                    psInsert.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
