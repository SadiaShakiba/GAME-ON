package com.example.onlinegamingplatformaoop;

import Database.dbConnect;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GameConfirmation04 implements Initializable {
    @FXML
    private Button noBtn;

    @FXML
    private Button yesBtn4;

    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Text loadingText;

    @FXML
    private void Btn_No(ActionEvent event) throws IOException {
        if (event.getSource()==noBtn){
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,1100,530);
            stage.setX(300);
            stage.setScene(scene);
            stage.show();
        }
    }

    //  DEDUCTING COINS FROM THE ACCOUNT

    String accounts_name,update_coins;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    String data;
    int total_coins;

    LoadingScreen LoadingScreen04;

    private void fetchQuery(){
        query="SELECT coins FROM `user_account` WHERE username='"+accounts_name+"'";
    }

    private void updateQuery(){
        query="UPDATE `user_account` SET coins=?  WHERE username='"+accounts_name+"'";
    }

    @FXML
    private void Btn_yes4(ActionEvent event) throws IOException, SQLException {
        if (event.getSource()==yesBtn4){
            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader("G:/advance oop/OnlineGamingPlatformAoop/Storage/temp_file.txt"));
                String[] check_line= {};
                String line= bufferedReader.readLine();
                check_line = line.split(" ");
                accounts_name=check_line[0];
                bufferedReader.close();
            }catch (IOException e){
                System.out.println(e);
            }

            connection= dbConnect.getConnect(); // CONNECTING THE DATABASE
            fetchQuery(); // RUN THE SQL QUERY
            resultSet=connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                data=resultSet.getString(1);
            }
            total_coins=Integer.parseInt(data);

            if(total_coins==0){
                Parent root = FXMLLoader.load(getClass().getResource("insufficient_balance.fxml"));
                Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
                Scene scene = new Scene(root,431,148);
                stage.setX(450);
                stage.setScene(scene);
                stage.show();
            }
            else{
                total_coins=total_coins-200;
                update_coins=Integer.toString(total_coins);

                updateQuery();
                preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,update_coins);
                preparedStatement.execute();

                Parent root = FXMLLoader.load(getClass().getResource("brickBreakerFront.fxml"));
                Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
                Scene scene = new Scene(root,600,400);
                stage.setX(500);
                stage.setY(120);
                stage.setScene(scene);
                stage.show();
//                Client clientObj = new Client();
//                clientObj.start(clientObj.dummy_stage);

            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yesBtn4.setVisible(false);
        LoadingScreen04 = new LoadingScreen(progressIndicator, loadingText,yesBtn4);
        Thread thread = new Thread(LoadingScreen04);
        thread.setDaemon(true);
        thread.start();

    }
}

class LoadingScreen04 implements Runnable {

    ProgressIndicator progressIndicator;
    Text loadingText;
    Button yesBtn4;

    public LoadingScreen04(ProgressIndicator progressIndicator, Text loadingText, Button yesBtn4) {
        this.progressIndicator = progressIndicator;
        this.loadingText = loadingText;
        this.yesBtn4=yesBtn4;
    }

    @Override
    public void run() {
        while(progressIndicator.getProgress() <= 1) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    progressIndicator.setProgress(progressIndicator.getProgress() + 0.1);
                }
            });
            synchronized (this){
                try{
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
        loadingText.setText("Done Loading");
        yesBtn4.setVisible(true);
    }
}

