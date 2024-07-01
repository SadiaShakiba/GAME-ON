package com.example.onlinegamingplatformaoop;

import Database.dbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Accounts implements Initializable {
    @FXML
    private Label userLabel;
    @FXML
    private Label userLabel1;

    @FXML
    private Button exitBtn;

    @FXML
    private TableView<modelTable> table;

    @FXML
    private TableColumn<modelTable,Integer> game_lost;

    @FXML
    private TableColumn<modelTable, Integer> game_score;

    @FXML
    private TableColumn<modelTable,Integer> game_highScore;

    @FXML
    private TableColumn<modelTable, String> game_name;

    @FXML
    private TableColumn<modelTable,Integer> played_game;

    @FXML
    private TableColumn<modelTable,Double> ratio;
    @FXML
    private TableColumn<modelTable,Integer> winnings;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField balance;


                                    // EXIT TO LOG IN PAGE

    @FXML
    private void exitGame(ActionEvent event) throws IOException {
        if (event.getSource() == exitBtn){
            Parent root = FXMLLoader.load(getClass().getResource("LoginFile.fxml"));
            //bodyPane.getChildren().setAll(root);
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,599,332);
            stage.setX(300);
            stage.setY(200);
            stage.setScene(scene);
            stage.show();
            //System.exit(0);
        }
    }


    ObservableList<modelTable> observableList = FXCollections.observableArrayList();

    String accounts_name;

                                  // FETCHING DATA FROM DATABASE

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    ResultSet newResult = null;
    PreparedStatement preparedStatement;
    String data;
    private void getQuery(){
        query="SELECT coins FROM `user_account` WHERE username='"+accounts_name+"'";
    }
    private void loadData() throws SQLException {
        resultSet=connection.createStatement().executeQuery(query);
        while (resultSet.next()){
            data=resultSet.getString(1);

        }
        userLabel1.setText(data);
        balance.setText(data);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                      // CONNECTING DATABASE USING dbConnect CLASS

        try {
            connection= dbConnect.getConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("G:/advance oop/OnlineGamingPlatformAoop/Storage/temp_file.txt"));
            String[] check_line= {};
            String line= bufferedReader.readLine();
            check_line = line.split(" ");
            accounts_name=check_line[0];
            //System.out.println(line);
            userLabel.setText(accounts_name);
            name.setText(accounts_name);
            //userLabel1.setText(coins);
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e);
        }

        getQuery();  // SQL QUERY
        try {
            loadData(); // LOADING DATA FROM DATABASE
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            newResult=connection.createStatement().executeQuery("SELECT game_name,total_played,won,lost,winning_percent,score,high_score FROM `game_details` WHERE username='"+accounts_name+"'");
            while (newResult.next()){
                String dummy_score = newResult.getString("score");
                String dummy_HighScore = newResult.getString("high_score");
                String dummy_won = newResult.getString("won");
                String dummy_lost = newResult.getString("lost");
                String dummy_percent = newResult.getString("winning_percent");
                if(dummy_score==null || dummy_HighScore==null ){
                    observableList.add(new modelTable(newResult.getString("game_name"),(Integer.parseInt(newResult.getString("total_played"))),(Integer.parseInt(newResult.getString("won"))),(Integer.parseInt(newResult.getString("lost"))),0,0,(Double.parseDouble(newResult.getString("winning_percent")))));
                }
                else if(dummy_won==null || dummy_lost==null ||  dummy_percent==null){
                    observableList.add(new modelTable(newResult.getString("game_name"),(Integer.parseInt(newResult.getString("total_played"))),0,0,(Integer.parseInt(newResult.getString("score"))),(Integer.parseInt(newResult.getString("high_score"))),0.0));
                }
                else{
                    observableList.add(new modelTable(newResult.getString("game_name"),(Integer.parseInt(newResult.getString("total_played"))),(Integer.parseInt(newResult.getString("won"))),(Integer.parseInt(newResult.getString("lost"))),(Integer.parseInt(newResult.getString("score"))),(Integer.parseInt(newResult.getString("high_score"))),(Double.parseDouble(newResult.getString("winning_percent")))));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        game_name.setCellValueFactory(new PropertyValueFactory<modelTable,String>("game_name"));
        played_game.setCellValueFactory(new PropertyValueFactory<modelTable,Integer>("played"));
        winnings.setCellValueFactory(new PropertyValueFactory<modelTable,Integer>("won"));
        game_lost.setCellValueFactory(new PropertyValueFactory<modelTable,Integer>("lost"));
        ratio.setCellValueFactory(new PropertyValueFactory<modelTable,Double>("percentage"));
        game_score.setCellValueFactory(new PropertyValueFactory<modelTable,Integer>("score"));
        game_highScore.setCellValueFactory(new PropertyValueFactory<modelTable,Integer>("highScore"));
        table.setItems(observableList);
//
//        boolean found=false;
//
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt"));
//            String[] check_line= {};
//            String line= bufferedReader.readLine();
//            //System.out.println(line);
//            while (line != null){
//                String file_name,coins_amount;
//                check_line = line.split(" ");
//                file_name=check_line[0];
//                coins_amount=check_line[2];
//                if((file_name.equals(accounts_name))){
//                    //System.out.println("Found");
//                    found=true;
//                    coins=coins_amount;
//                    break;
//                }else{
//                    found=false;
//                }
//                //System.out.println(check_line[i]);
//                line = bufferedReader.readLine();
//            }
//            if(found){
//                // System.out.println("Found");
//                userLabel1.setText(coins);
//            }else{
//                System.out.println("Not found");
//            }
//            bufferedReader.close();
//        }catch (IOException e){
//            System.out.println(e);
//        }


    }
}
