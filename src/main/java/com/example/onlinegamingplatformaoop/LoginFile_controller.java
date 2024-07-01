package com.example.onlinegamingplatformaoop;

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
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginFile_controller implements Initializable {
    @FXML
    private PasswordField password;

    @FXML
    private Button singnupBtn;

    @FXML
    private TextField userName;

    @FXML
    private Button closeBtn;

                                     // LOG INTO SYSTEM
    @FXML
    private void loginBtn(ActionEvent event) throws IOException {
        boolean found=false;

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("G:/advance oop/OnlineGamingPlatformAoop/Storage/input.txt"));
            String[] check_line= {};
            String line= bufferedReader.readLine();
            //System.out.println(line);
            while (line != null){
                String file_name,file_pass;
                check_line = line.split(" ");
                file_name=check_line[0];
                file_pass=check_line[1];
              if((file_name.equals(userName.getText())) && (file_pass.equals(password.getText()))){
                        //System.out.println("Found");
                        found=true;
                        break;
                    }else{
                        found=false;
                    }
                    //System.out.println(check_line[i]);
                line = bufferedReader.readLine();
            }
            if(found){
               // System.out.println("Found");
                    String user_name = userName.getText();
                    String coin_amount = "2000";

                //Socket socket = new Socket("localhost",8080);
//                DataOutputStream out = new DataOutputStream( socket.getOutputStream());
//                DataInputStream in = new DataInputStream( socket.getInputStream());


                // STORING INTO TEMPORARY FILE

                try {
                    File file = new File("G:/advance oop/OnlineGamingPlatformAoop/Storage/temp_file.txt");
                    FileWriter fileWriter = new FileWriter(file);
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    printWriter.format("%s %s\n",user_name,coin_amount);
                    printWriter.close();
//
//            Formatter formatter = new Formatter("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt");
//            formatter.format("%s %s %s\n",s1,s2,s3);
//            formatter.close();
                }catch (IOException e){
                    System.out.println(e);
                }
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
                    Parent root = fxmlLoader.load();

                    // CREATING OBJECT OF CONTROLLER FOR CROSS COMMUNICATION
                   // NOT WORKING...!!

//                    HelloController controller_instance = fxmlLoader.getController();
//                    controller_instance.set_name(user_name);
                    Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
                    Scene scene = new Scene(root,1100,530);
                    stage.setScene(scene);
                    stage.show();
            }else{
                //System.out.println("Not Found");
//                    Parent root = FXMLLoader.load(getClass().getResource("Login_Notification.fxml"));
//                    Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
//                    Scene scene = new Scene(root,565,79);
//                    stage.setX(500);
//                    stage.setY(320);
//                    stage.setScene(scene);
//                    stage.show();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Occurred");
                alert.setHeaderText(null);
                alert.setContentText("Username or Password doesn't match");
                alert.showAndWait();
                userName.setText(" ");
                password.setText(null);
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e);
        }

//        try {
//            Scanner scanner = new Scanner("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt");
//            while (scanner.hasNext()){
//                  user_name = scanner.next();
////                pass_word = scanner.next();
////                System.out.println(user_name+" "+pass_word);
//                System.out.println(user_name);
//
////                if(userName.getText().equals(user_name) && (password.getText().equals(pass_word))){
////                    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
////                    //SlideShow();
////                    Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
////                    Scene scene = new Scene(root,1100,530);
////                    stage.setScene(scene);
////                    stage.show();
////                }
////                else{
////                    Parent root = FXMLLoader.load(getClass().getResource("Login_Notification.fxml"));
////                    //SlideShow();
////                    Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
////                    Scene scene = new Scene(root,565,79);
////                    stage.setX(500);
////                    stage.setY(320);
////                    stage.setScene(scene);
////                    stage.show();
////                }
//            }
//            scanner.close();
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }

                              // SIGN UP INTO SYSTEM
    @FXML
    private void signUpBtn(ActionEvent event) throws IOException {
        if(event.getSource() == singnupBtn){
            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            //SlideShow();
            Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root,453,472);
            stage.setX(500);
            stage.setY(200);
            stage.setScene(scene);
            stage.show();
        }
    }


                                      // CLOSING THE PROGRAM
    @FXML
    private void closed(ActionEvent event) {
        if(event.getSource()==closeBtn){
            System.exit(0);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
