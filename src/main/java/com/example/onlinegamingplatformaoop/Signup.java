package com.example.onlinegamingplatformaoop;

import Database.dbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.ResourceBundle;

public class Signup implements Initializable {
    @FXML
    private PasswordField confirmpass;

    @FXML
    private PasswordField email;

    @FXML
    private PasswordField newpass;

    @FXML
    private TextField username;

    @FXML
    private Button exitGame;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;


    private void getQuery(){
        query="INSERT INTO `user_account` (`username`,`coins`) VALUES(?,?)";
    }
    private void insert(){

        try{
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username.getText());
            preparedStatement.setString(2,"2000");
            preparedStatement.execute();

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    @FXML
    private void signupBtn(ActionEvent event) throws IOException, SQLException {
        connection=dbConnect.getConnect();
        String s1 = username.getText();
        String s2 = newpass.getText();
        String s3 = confirmpass.getText();
        String coins_amount="2000";

        if(!s2.equals(s3)){
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InvalidEntry.fxml"));
//            Parent root = fxmlLoader.load();
//            Parent root = FXMLLoader.load(getClass().getResource("InvalidEntry.fxml"));
//            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
//            Scene scene = new Scene(root,586.4,79.2);
//            stage.setX(500);
//            stage.setY(320);
//            stage.setScene(scene);
//            stage.show();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Occurred");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry!");
            alert.showAndWait();
            username.setText(" ");
            newpass.setText(null);
            confirmpass.setText(null);
        }else{
            try {
                File file = new File("G:/advance oop/OnlineGamingPlatformAoop/Storage/input.txt");
                FileWriter fileWriter = new FileWriter(file,true);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                printWriter.format("%s %s %s\n",s1,s2,coins_amount);
                printWriter.close();

//
//            Formatter formatter = new Formatter("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt");
//            formatter.format("%s %s %s\n",s1,s2,s3);
//            formatter.close();
            }catch (IOException e){
                System.out.println(e);
            }
            getQuery();
            insert();


            Parent root = FXMLLoader.load(getClass().getResource("SignUp_Notification.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,409,88);
            stage.setX(500);
            stage.setY(320);
            stage.setScene(scene);
            stage.show();
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("successful");
//            alert.setHeaderText(null);
//            alert.setContentText("Successfully Created");
//            alert.showAndWait();

        }

    }

                                             // EXIT GAME
    @FXML
    private void exit(ActionEvent event) throws IOException{
        if (event.getSource()==exitGame){
            Parent root = FXMLLoader.load(getClass().getResource("LoginFile.fxml"));
            //bodyPane.getChildren().setAll(root);
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,599,332);
            stage.setX(300);
            stage.setY(200);
            stage.setScene(scene);
            stage.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
