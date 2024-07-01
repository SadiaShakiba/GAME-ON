module com.example.onlinegamingplatformaoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.onlinegamingplatformaoop to javafx.fxml;
    exports com.example.onlinegamingplatformaoop;
    exports Database;
    exports Server;
}