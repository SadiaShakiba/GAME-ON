package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {
    public static String HOST="127.0.0.1";
    public static int PORT=3306;
    public static String DB_NAME="gameon";
    public static String USER_NAME="root";
    public static String PASSWORD="";
    public static Connection connection;

    public static Connection getConnect() throws SQLException {
        connection= DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",HOST,PORT,DB_NAME),USER_NAME,PASSWORD);
        return connection;
    }



}
