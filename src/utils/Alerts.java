package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {
    /**
     * Shows different type of alert messages to choose from throughout application
     * @param alertType
     */
    public static void showAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);


        switch (alertType) {
            case 1:
                alertError.setTitle(Translation.translate(1));
                alertError.setHeaderText(Translation.translate(3));
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle(Translation.translate(1));
                alertError.setHeaderText(Translation.translate(2));
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle(Translation.translate(1));
                alertError.setHeaderText(Translation.translate(4));
                alertError.showAndWait();
                break;
            case 4:
                alert.setTitle("Upcoming Appointment");
                alert.setHeaderText("You have an appointment within 15 minutes");
                alert.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Delete unsuccessful.");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Save Unsuccessful");
                alertError.setHeaderText("Field cannot be empty or invalid.");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Save Unsuccessful");
                alertError.setHeaderText("The appointment day or time that you have chosen is outside of business hours or invalid.");
                alertError.showAndWait();
                break;
            case 8:
                alertError.setTitle("Save Unsuccessful");
                alertError.setHeaderText("There is an appointment overlap. Please pick another date or time.");
                alertError.showAndWait();
                break;
            case 9:
                alertError.setTitle("Error");
                alertError.setHeaderText("SQL Error.");
                alertError.showAndWait();
                break;
            case 10:
                alertError.setTitle("Save Unsuccessful");
                alertError.setHeaderText("Save Unsuccessful.");
                alertError.showAndWait();
                break;
            case 11:
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("You have no upcoming appointments.");
                alert.showAndWait();
                break;
            case 12:
                alert.setTitle("Error");
                alert.setHeaderText("Please select a customer to update.");
                alert.showAndWait();
                break;
            case 13:
                alert.setTitle("Error");
                alert.setHeaderText("Please select an appointment to update.");
                alert.showAndWait();
                break;
            case 14:
                alert.setTitle("Error");
                alert.setHeaderText("Please delete all appointments associated with this customer before deleting the customer.");
                alert.showAndWait();
                break;

        }
    }
}
