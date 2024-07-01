package com.example.onlinegamingplatformaoop;

import Database.dbConnect;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
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
import java.util.ResourceBundle;

public class flappyBird implements Initializable {

    AnimationTimer gameLoop;

    @FXML
    private AnchorPane plane;

    @FXML
    private Rectangle bird;

    @FXML
    private Text score;

    @FXML
    private Button exitGame;

    private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = 0,previous_score=0;

    private Bird birdComponent;
    private ObstaclesHandler obstaclesHandler;
    String nameOfTheGame="FLAPPY BIRD",gameName_check;
    String accounts_name,username_check;
    int total_match=0,new_score=0,high_score=0;
    boolean found=false;
    String query = null,fetch_query=null,update_query=null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;

    ArrayList<Rectangle> obstacles = new ArrayList<>();


    private void setGameNameQuery(){query="INSERT INTO `game_details` (`username`,`game_name`,`score`,`high_score`) VALUES(?,?,?,?)";}
    private void fetchQuery(){fetch_query="SELECT username,game_name,total_played,score,high_score  FROM `game_details`";}
    private void updateQuery(){update_query="UPDATE `game_details` SET game_name=?, total_played=?,score=?,high_score=? WHERE username='"+accounts_name+"'";}



    private void insert(){
        try{
            // FETCHING DATA TO CHECK IT'S EXISTENCE
            fetchQuery();
            resultSet=connection.createStatement().executeQuery(fetch_query);
            while (resultSet.next()){
                username_check=resultSet.getString(1);
                gameName_check=resultSet.getString(2);
                new_score=resultSet.getInt(3);
                high_score=resultSet.getInt(4);

                if(username_check.equals(accounts_name)){
                    found=true;
                    break;
                }
            }

            if(!found){
                    setGameNameQuery();
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setString(1,accounts_name);
                    preparedStatement.setString(2,nameOfTheGame);
                    preparedStatement.setInt(3,total_match);
                    preparedStatement.setInt(4,scoreCounter);

                    if(previous_score>scoreCounter){
                        preparedStatement.setInt(5,previous_score);
                    }
                    else{
                        preparedStatement.setInt(5,scoreCounter);
                        previous_score=scoreCounter;

                    }
                    preparedStatement.execute();
                    }

                else if (found) {
                    if(!gameName_check.equals(nameOfTheGame)) {
                        setGameNameQuery();
                        preparedStatement=connection.prepareStatement(query);
                        preparedStatement.setString(1,accounts_name);
                        preparedStatement.setString(2,nameOfTheGame);
                        preparedStatement.setInt(3,total_match);
                        preparedStatement.setInt(4,scoreCounter);
                        preparedStatement.execute();
                    }
                    else{
                        updateQuery();
                        preparedStatement=connection.prepareStatement(update_query);
                        preparedStatement.setString(1,nameOfTheGame);
                        preparedStatement.setInt(2,total_match);
                        preparedStatement.setInt(3,scoreCounter+previous_score);
                        if(previous_score>scoreCounter){
                            preparedStatement.setInt(4,previous_score);
                        }
                        else{
                            preparedStatement.setInt(4,scoreCounter);
                            previous_score=scoreCounter;

                        }
                        preparedStatement.execute();

                    }
//                updated_won=won+won_data;
//                updated_totalMatch=total_match+total_match_data;
//                converted_won=(double)updated_won;
//                converted_totalMatch=(double)updated_totalMatch;
//                result_percent = (converted_won/converted_totalMatch)*100.0;
//                updateQuery();
//                preparedStatement=connection.prepareStatement(update_query);
//                preparedStatement.setInt(1,total_match_data+total_match);
//                preparedStatement.setInt(2,won_data+won);
//                preparedStatement.setInt(3,lost_data+lost);
//                preparedStatement.setDouble(4,result_percent);
//                preparedStatement.execute();
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

        int jumpHeight = 75;
        birdComponent = new Bird(bird, jumpHeight);
        double planeHeight = 600;
        double planeWidth = 400;
        obstaclesHandler = new ObstaclesHandler(plane, planeHeight, planeWidth);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        load();

        gameLoop.start();

        try {
            connection= dbConnect.getConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE){
            birdComponent.fly();
            accelerationTime = 0;
        }
    }


    //Called every game frame
    private void update() {
        gameTime++;
        accelerationTime++;
        double yDelta = 0.02;
        birdComponent.moveBirdY(yDelta * accelerationTime);

        if(pointChecker(obstacles, bird)){
            scoreCounter++;
            score.setText(String.valueOf(scoreCounter));
        }

        obstaclesHandler.moveObstacles(obstacles);
        if(gameTime % 500 == 0){
            obstacles.addAll(obstaclesHandler.createObstacles());
        }

        if(birdComponent.isBirdDead(obstacles, plane)){
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
            total_match++;
//            setGameNameQuery();
//            insert();
            resetGame();
        }
    }

    //Everything called once, at the game start
    private void load(){
        obstacles.addAll(obstaclesHandler.createObstacles());
    }

    private void resetGame(){
        bird.setY(0);
        plane.getChildren().removeAll(obstacles);
        obstacles.clear();
        gameTime = 0;
        accelerationTime = 0;
        scoreCounter = 0;
        score.setText(String.valueOf(scoreCounter));
    }



    private boolean pointChecker(ArrayList<Rectangle> obstacles, Rectangle bird){
        for (Rectangle obstacle: obstacles) {
            int birdPositionX = (int) (bird.getLayoutX() + bird.getX());
            if(((int)(obstacle.getLayoutX() + obstacle.getX()) == birdPositionX)){
                return true;
            }
        }
        return false;
    }

                                         // EXIT FROM GAME

    @FXML
    private void exit(ActionEvent event) throws IOException {
        if (event.getSource()==exitGame){
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,1100,530);
            stage.setX(300);
            stage.setScene(scene);
            stage.show();
        }

    }
}