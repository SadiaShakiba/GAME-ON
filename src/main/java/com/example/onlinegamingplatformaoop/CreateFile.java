package com.example.onlinegamingplatformaoop;

import java.io.File;

public class CreateFile {
    public static void main(String[] args) {
        // For storing User Name
        File directory = new File("Storage");
        directory.mkdir();
        File UserInput = new File("C:/Users/USER/IdeaProjects/OnlineGamingPlatformAoop/Storage/input.txt");
        try {
            UserInput.createNewFile();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
