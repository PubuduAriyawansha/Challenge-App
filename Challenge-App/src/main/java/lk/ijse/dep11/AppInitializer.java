package lk.ijse.dep11;

import javafx.application.Application;
import javafx.stage.Stage;
import lk.ijse.dep11.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) {

        try (Connection connection = DbConnection.getInstance().getConnection()){
            launch(args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
