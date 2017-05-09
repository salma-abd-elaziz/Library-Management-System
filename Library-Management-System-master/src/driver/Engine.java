package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 * Created by LENOVO on 5/1/2017.
 */
public class Engine {
    public static String LOGGED_IN_USER = null;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/book_store";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "1111";
    static final String MgrSecurityCode = "0000";

    public static int IS_MANAGER = 0;

    public static Connection CONNECTION = null;
    public static Statement STATEMENT = null;
    public static int WINDOW_WIDTH=900;
    public static int WINDOW_HEIGHT=600;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            CONNECTION = DriverManager.getConnection(DB_URL, USER, PASS);
            STATEMENT = CONNECTION.createStatement();
            System.out.println("Connection succeeded");
            LogInUI logInUi = new LogInUI();

        } catch (Exception e) {
        }
    }
}
