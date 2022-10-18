package main;

import DAO.DBConnection;
import controller.Appointments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Appointment Scheduling Application
 * @author Anna Lyons
 */
public class Main extends Application {
    @Override
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }


}
