package com.example.onlinegamingplatformaoop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("LoginFile.fxml"));
        Scene scene = new Scene(root,599,332);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Online Gaming Platform");
        primaryStage.setScene(scene);
        primaryStage.setX(300);
        primaryStage.setY(200);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
