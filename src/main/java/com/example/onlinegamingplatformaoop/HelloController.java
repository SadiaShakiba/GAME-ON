
                                        // MAIN CONTROLLER CLASS

package com.example.onlinegamingplatformaoop;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelloController implements Initializable {

    public String UserName;


    @FXML
    private Label arcadeGameId;

    @FXML
    private Button exitBtn;
    @FXML
    private ImageView ImageSlider;
    @FXML
    private Label bodylabel;

    @FXML
    private Label UserLabel;


    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private AnchorPane pane;
    @FXML
    private Pane bodyPane;

    @FXML
    private Button multgame01;

    @FXML
    private Button exitGame;


    @FXML
    private Button game1btn;
    @FXML
    private Button singlePlayerBtn;
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button game2Btn;

    @FXML
    private Button game3Btn;

    @FXML
    private Button game4Btn;

                                            // IMAGE SLIDER (NOT WORKING...!)

    int SlideCount;
    public void SlideShow(){
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(new Image("../Images/slider1.jpg"));
        images.add(new Image("../Images/slider2.jpg"));
        images.add(new Image("../Images/slider3.jpg"));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),event ->{
            ImageSlider.setImage(images.get(SlideCount));
            SlideCount++;
            if(SlideCount==3){
                SlideCount=0;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void loginBtn(ActionEvent event) throws IOException {

        String s1 = userName.getText();
        String s2 = password.getText();

                                        // STORING DATA INTO A FILE


        try {
            Formatter formatter = new Formatter("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt");
            formatter.format(s1);
            formatter.close();
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
                        // READING DATA FROM FILE ( NOT WORKING...!)


        try {
            Scanner scanner = new Scanner("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt");
            while (scanner.hasNext()){
                 UserName = scanner.next();
            }
            scanner.close();
        }catch (Exception e){
            System.out.println(e);
        }


                                 // LOG IN CHECKING

//        if(s1.equals("mahir1234") && s2.equals("1234")){
//               Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//               //SlideShow();
//               Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
//               Scene scene = new Scene(root,1100,530);
//               stage.setScene(scene);
//               stage.show();
//            //UserLabel.setText("Nothing");
//        }
//        else{
//            Parent root = FXMLLoader.load(getClass().getResource("Login_Notification.fxml"));
//            //SlideShow();
//            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
//            Scene scene = new Scene(root,565,79);
//            stage.setX(500);
//            stage.setY(320);
//            stage.setScene(scene);
//            stage.show();
//        }
    }

                                // TIC TAC TOE GAME LAUNCH

    @FXML
    private void playGame(ActionEvent event) throws IOException{
        if (event.getSource()==game1btn){
            Parent root = FXMLLoader.load(getClass().getResource("game_confirmation.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,404,220);
            stage.setX(500);
            stage.setY(220);
            stage.setScene(scene);
            stage.show();
        } else if (event.getSource()==game2Btn){
            Parent root = FXMLLoader.load(getClass().getResource("game_confirmation2.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,404,220);
            stage.setX(500);
            stage.setY(220);
            stage.setScene(scene);
            stage.show();

        } else if (event.getSource()==game3Btn) {
            Parent root = FXMLLoader.load(getClass().getResource("game_confirmation3.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,404,220);
            stage.setX(500);
            stage.setY(220);
            stage.setScene(scene);
            stage.show();

        } else if (event.getSource()==game4Btn) {
            Parent root = FXMLLoader.load(getClass().getResource("game_confirmation4.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,404,220);
            stage.setX(500);
            stage.setY(220);
            stage.setScene(scene);
            stage.show();

        }
        else if (event.getSource()==multgame01) {
            Parent root = FXMLLoader.load(getClass().getResource("game_confirmation5.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,404,220);
            stage.setX(500);
            stage.setY(220);
            stage.setScene(scene);
            stage.show();

        }
    }



                                 // TIC TAC TOE FRONT PAGE
   @FXML
   private void singlePlayerMode (ActionEvent event) throws IOException{
       if (event.getSource()==singlePlayerBtn){
           Parent root = FXMLLoader.load(getClass().getResource("AI_TicTacToe.fxml"));
           Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
           Scene scene = new Scene(root,600,400);
           stage.setX(500);
           stage.setY(120);
           stage.setScene(scene);
           stage.show();
       }
   }

                                    // FLAPPY BIRD GAME

    @FXML
   private void playGame2(ActionEvent event) throws IOException {
        if (event.getSource()==game2Btn){
            Parent root = FXMLLoader.load(getClass().getResource("flappyBird.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,400,600);
            scene.getRoot().requestFocus();
            stage.setX(500);
            stage.setY(120);
            stage.setScene(scene);
            stage.show();
        }
    }


                                        // MEMORY GAME

    @FXML
   private void playGame3(ActionEvent event) throws IOException {
        if (event.getSource()==game3Btn){
            Parent root = FXMLLoader.load(getClass().getResource("memoryGame.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,600,400);
            stage.setX(500);
            stage.setY(120);
            stage.setScene(scene);
            stage.show();
        }
    }

                                    // BRICK BREAKER GAME

    @FXML
    private void playGame4(ActionEvent event) throws IOException {
        if (event.getSource()==game4Btn){
            Parent root = FXMLLoader.load(getClass().getResource("brickBreaker.fxml"));
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,600,400);
            stage.setX(500);
            stage.setY(120);
            stage.setScene(scene);
            stage.show();

        }
    }




   @FXML
   private void exit(ActionEvent event) throws IOException{
       if (event.getSource()==exitGame){
           Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
           Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
           Scene scene = new Scene(root,1100,530);
           stage.setX(300);
           stage.setScene(scene);
           stage.show();
       }

    }

                             // CONTROLLER COMMUNICATION
//    String user_name;
//    public void set_name(String user){
//        user_name = user;
//    }



                                        // EXIT BUTTON

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


                                     // MENU BAR BUTTONS
    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
        if (event.getSource() == btn2) {
            pane= FXMLLoader.load(getClass().getResource("page2.fxml"));
            bodyPane.getChildren().setAll(pane);

        } else if (event.getSource() == btn3) {
            // btn3.setStyle("-fx-background-color:#f15b72");
            pane= FXMLLoader.load(getClass().getResource("page3.fxml"));
            bodyPane.getChildren().setAll(pane);

        } else if (event.getSource() == btn4) {
           // btn4.setStyle("-fx-background-color:#f15b72");
            pane= FXMLLoader.load(getClass().getResource("page4.fxml"));
            bodyPane.getChildren().setAll(pane);


        } else if (event.getSource() == btn5) {
            // bodylabel.setText("Settings");
           // btn5.setStyle("-fx-background-color:#f15b72");
            pane = FXMLLoader.load(getClass().getResource("page5.fxml"));
            bodyPane.getChildren().setAll(pane);
            //btn5.requestFocus();
//            btn5.setOnMouseClicked((MouseEvent e)->{
//                btn5.setStyle("-fx-background-color:#f15b72");
//                btn4.setStyle("-fx-background-color:#283B63");
//                btn3.setStyle("-fx-background-color:#283B63");
//                btn2.setStyle("-fx-background-color:#283B63");
//            });

           // Accounts accounts_controller = pane.getController();
            // HAVE TO IMPLEMENT FILE TO READ THE NAME
        }
    }

}
