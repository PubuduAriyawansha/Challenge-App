package lk.ijse.dep11.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private DbConnection(){
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/Appliaction.properties"));
            String url = properties.getProperty("db_app_url");
            String user = properties.getProperty("db_app_user");
            String passwd = properties.getProperty("db_app_password");
            connection= DriverManager.getConnection(url,user,passwd);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final Connection connection;
    public Connection getConnection(){
        return connection;
    }

    private static DbConnection dbConnection;
    public static DbConnection getInstance(){
        return dbConnection==null? dbConnection=new DbConnection(): dbConnection;
    }
}
