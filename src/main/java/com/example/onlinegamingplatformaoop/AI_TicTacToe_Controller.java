package com.example.onlinegamingplatformaoop;

import Database.dbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class AI_TicTacToe_Controller implements Initializable {
    Random random = new Random();

    ArrayList<Button> buttons;

    AdversarialSearchTicTacToe ticTacToeAI = new AdversarialSearchTicTacToe();


    @FXML
    private Button exitBtn;

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Text winnerText;


                                 // INSERTING GAME DETAILS INTO DATABASE


    int won=0,lost=0;
    String query = null,fetch_query=null,update_query=null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    String game_name="TIC-TAC-TOE";
    String accounts_name;
    Double ratio,result_percent,converted_won,converted_totalMatch;
    int total_match,total_match_data,won_data,lost_data,updated_won,updated_totalMatch;
    String username_check;
    boolean found=false;

    private void setGameNameQuery(){query="INSERT INTO `game_details` (`username`,`game_name`,`total_played`,`won`,`lost`,`winning_percent`) VALUES(?,?,?,?,?,?)";}
    private void fetchQuery(){fetch_query="SELECT username,total_played,won,lost  FROM `game_details`";}
    private void updateQuery(){update_query="UPDATE `game_details` SET total_played=?,won=?,lost=?,winning_percent=? WHERE username='"+accounts_name+"'";}

    private void insert(){
        try{
                          // FETCHING DATA TO CHECK IT'S EXISTENCE
            fetchQuery();
            resultSet=connection.createStatement().executeQuery(fetch_query);
            while (resultSet.next()){
                username_check=resultSet.getString(1);
                total_match_data=resultSet.getInt(2);
                won_data=resultSet.getInt(3);
                lost_data=resultSet.getInt(4);

                if(username_check.equals(accounts_name)){
                    found=true;
                    break;
                }
            }

            if(!found){
                if(won==0 && lost!=0){
                    ratio = (won/total_match)*100.0;
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setString(1,accounts_name);
                    preparedStatement.setString(2,game_name);
                    preparedStatement.setInt(3,total_match);
                    preparedStatement.setInt(4,won);
                    preparedStatement.setInt(5,lost);
                    preparedStatement.setDouble(6,ratio);
                    preparedStatement.execute();

                }
                else if (won!=0 && lost==0) {
                    ratio = (won/total_match)*100.0;
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setString(1,accounts_name);
                    preparedStatement.setString(2,game_name);
                    preparedStatement.setInt(3,total_match);
                    preparedStatement.setInt(4,won);
                    preparedStatement.setInt(5,lost);
                    preparedStatement.setDouble(6,ratio);
                    preparedStatement.execute();
                }

            } else if (found) {
                updated_won=won+won_data;
                updated_totalMatch=total_match+total_match_data;
                converted_won=(double)updated_won;
                converted_totalMatch=(double)updated_totalMatch;
                result_percent = (converted_won/converted_totalMatch)*100.0;
                updateQuery();
                preparedStatement=connection.prepareStatement(update_query);
                preparedStatement.setInt(1,total_match_data+total_match);
                preparedStatement.setInt(2,won_data+won);
                preparedStatement.setInt(3,lost_data+lost);
                preparedStatement.setDouble(4,result_percent);
                preparedStatement.execute();
            }


//            else if(won!=0){
//                System.out.println("I'm inside 2nd ELSE IF statement");
//                ratio = (won/total_match)*100.0;
//                System.out.println("Ratio: "+ratio);
//                store_ratio = Double.toString(ratio);
//                store_won = Integer.toString(won);
//                store_lost = Integer.toString(lost);
//                preparedStatement=connection.prepareStatement(query);
////                preparedStatement.setString(1,accounts_name);
////                preparedStatement.setString(2,game_name);
////                preparedStatement.setString(3,store_total_match);
////                preparedStatement.setString(4,store_won);
////                preparedStatement.setString(5,store_lost);
////                preparedStatement.setString(6,store_ratio);
////                preparedStatement.execute();
//            }

        }catch (SQLException e){
            System.out.println(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });

                                    // DATABASE CONNECT

        try {
            connection= dbConnect.getConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("Tic-Tac-Toe");
        pickButton(random.nextInt(9));
    }

    @FXML
    private void exitGame(ActionEvent event) throws IOException {
        if (event.getSource()== exitBtn){
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            //bodyPane.getChildren().setAll(root);
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,1100,530);
            stage.setX(300);
            stage.setScene(scene);
            stage.show();
        }
    }


    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            button.setText("O");
            button.setDisable(true);
            makeAIMove();
            checkIfGameIsOver();
        });
    }

    public void makeAIMove(){
        int move = ticTacToeAI.minMaxDecision(getBoardState());
        pickButton(move);
    }

    private void pickButton(int index) {
        buttons.get(index).setText("X");
        buttons.get(index).setDisable(true);
    }

    public State getBoardState(){
        String[] board = new String[9];

        for (int i = 0; i < buttons.size(); i++) {
            board[i] = buttons.get(i).getText();
        }

        return new State(0,board);
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            //X winner
            if (line.equals("XXX")) {
                winnerText.setText("AI won!");
                lost++;
                total_match = won+lost;
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/temp_file.txt"));
                    String[] check_line= {};
                    String read_line= bufferedReader.readLine();
                    check_line = read_line.split(" ");
                    accounts_name=check_line[0];
                    bufferedReader.close();
                }catch (IOException e){
                    System.out.println(e);
                }
                setGameNameQuery();
                insert();


            }

            //O winner
            else if (line.equals("OOO")) {
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/temp_file.txt"));
                    String[] check_line= {};
                    String read_line= bufferedReader.readLine();
                    check_line = read_line.split(" ");
                    accounts_name=check_line[0];
                   // new_account=accounts_name;
                    bufferedReader.close();
                }catch (IOException e){
                    System.out.println(e);
                }
                winnerText.setText("You won!");
                won++;
                total_match = won+lost;
                setGameNameQuery();
                insert();

            }
        }
    }
}
