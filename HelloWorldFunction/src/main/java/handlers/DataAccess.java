package handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccess {

    private static final String DB_NAME = "postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String HOSTNAME = "database-1.chqrsln9e0ai.us-east-2.rds.amazonaws.com";
    private static final String PORT = "5432";
    private static final String JDBC_URL = "jdbc:postgresql://" + HOSTNAME + ":" + PORT + "/" + DB_NAME + "?user=" + USERNAME + "&password=" + PASSWORD + "&ssl=false";

    public Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(JDBC_URL);
            return con;
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return null;
    }

}
