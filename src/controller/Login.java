package controller;

import DAO.UserDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utils.Alerts;
import utils.Log;
import utils.Translation;

import java.sql.SQLException;
import java.time.ZoneId;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public Label schedulerLoginLabel;
    public Label locationLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public TextField usernameTextField;
    public PasswordField passwordField;
    public Button loginButton;


    private Stage stage;
    private Scene scene;

    /**
     * Initializes the login screen, populates location label, shows all labels in appropriate language depending on user location.
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId z = ZoneId.systemDefault();
        String s = z.getId();

        locationLabel.textProperty().setValue(s);
        schedulerLoginLabel.textProperty().setValue(Translation.translate(5));
        usernameLabel.textProperty().setValue(Translation.translate(6));
        passwordLabel.textProperty().setValue(Translation.translate(7));
        loginButton.textProperty().setValue(Translation.translate(8));


    }

    /**
     * Logs the user in if the username and password fields are correct.
     * Shows alert if username or password incorrect.
     * @param actionEvent selection of Login Button.
     */
    public void loginButtonAction(ActionEvent actionEvent) {
        try {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            ObservableList<User> allUsers;
            try {
                allUsers = UserDB.getAllUsers();

            } catch (SQLException e) {
                allUsers = FXCollections.observableArrayList();
                Alerts.showAlert(1);
            }
            boolean loginSuccess = false;
            for (int i = 0; i < allUsers.size(); i++) {
                if (username.equals(allUsers.get(i).getUserName()) && password.equals(allUsers.get(i).getPassword())) {
                    loginSuccess = true;
                    Appointments.loggedInUser = username;
                    break;
                }
            }
            if (!loginSuccess) {
                Alerts.showAlert((2));
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            //Logs the login attempts
            Log.log(loginSuccess, username);

        } catch (Exception e) {
            System.out.println(e);
            Alerts.showAlert(3);
        }
    }
}
