package controller;

import DAO.AppointmentDB;
import DAO.ContactDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utils.Alerts;
import utils.TimeZone;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



public class Appointments implements Initializable {
    public ToggleGroup tgMonthWeekView;
    public RadioButton monthViewRadio;
    public RadioButton weekViewRadio;
    public RadioButton noFilterRadio;
    public TableView<Appointment> apptTableView;
    public RadioButton addApptRadio;
    public RadioButton updateApptRadio;
    public Button deleteApptButton;
    public TextField apptIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public ComboBox<String> contactComboBox;
    public TextField typeTextField;
    public DatePicker startDateField;
    public DatePicker endDateField;
    public ComboBox<String> startTimeComboBox;
    public ComboBox<String> endTimeComboBox;
    public TextField custIDTextField;
    public TextField userIDTextField;
    public Button saveButton;
    public Button customersButton;
    public Button reportsButton;

    public TableColumn<Appointment, Integer> apptIDCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, String> locationCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> startCol;
    public TableColumn<Appointment, String> endCol;
    public TableColumn<Appointment, Integer> custIDCol;
    public TableColumn<Appointment, Integer> userIDCol;
    public TableColumn<Appointment, String> contactCol;
    public ToggleGroup tgAddUpdateAppt;
    private Stage stage;
    private Scene scene;
    public static String loggedInUser;

    private final ZoneId localZoneID = ZoneId.systemDefault();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    /**
     * Initializes, selects no filter radio button, populates ComboBoxes, and populates the tableview to show all appointments
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Disable appointment ID text field
        apptIDTextField.setEditable(false);

        //Set no Filter radio button to default
        noFilterRadio.setSelected(true);

        //Set add appointment radio button to default
        addApptRadio.setSelected(true);

        //List of times for appointments to populate the start and end time ComboBoxes
        String[] timeList = {
                "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00",
                "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "24:00"
        };

        //Populate Contact ComboBox
        try {
            contactComboBox.setItems(ContactDB.getAllContactNames());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Populate Start Time ComboBox
        try {
            startTimeComboBox.setItems(FXCollections.observableArrayList(timeList));
        } catch (Exception e) {
        throw new RuntimeException(e);
        }

        //Populate End Time ComboBox
        try {
            endTimeComboBox.setItems(FXCollections.observableArrayList(timeList));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Populate all appointments in tableview
        refreshAllAppointmentsTable();

        //Show Alert for appointments within 15 minutes
        try {
            if (AppointmentDB.getUpcomingAppointments().isEmpty()){
                Alerts.showAlert(11);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("Appointment ID: " + AppointmentDB.getUpcomingAppointments().get(0).getAppointmentID() + '\n' + " Date & Time: " + AppointmentDB.getUpcomingAppointments().get(0).getStartTime().format(formatter));
                alert.showAndWait();
            }

        } catch (Exception e) {
            Alerts.showAlert(9);
        }
    }

    /**
     * Shows this month's appointments when month view radio button is selected
     * @param actionEvent selection of the month view radio button
     */
    @FXML
    public void monthViewRadioAction(ActionEvent actionEvent) {
        ObservableList<Appointment> thisMonthAppointments;
        try {
            thisMonthAppointments = AppointmentDB.getMonthView();
        } catch (Exception e) {
        throw new RuntimeException(e);
    }
    showAppointments(thisMonthAppointments);
    }

    /**
     * Shows this week's appointments when week view radio button is selected
     * @param actionEvent selection of the week view radio button
     */
    @FXML
    public void weekViewRadioAction(ActionEvent actionEvent) {
        ObservableList<Appointment> thisWeekAppointments;
        try {
            thisWeekAppointments = AppointmentDB.getWeekView();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showAppointments(thisWeekAppointments);
    }

    /**
     * Shows all appointments when the no filter radio button is selected
     * @param actionEvent selection of the no filter radio button
     */
    @FXML
    public void noFilterRadioAction(ActionEvent actionEvent) {
        refreshAllAppointmentsTable();
    }

    /**
     * Clears all inputs in the fields to enter a new appointment.
     * @param actionEvent selection of Add Appointment Radio Button.
     */
    @FXML
    public void addApptRadioAction(ActionEvent actionEvent) {
        apptIDTextField.clear();
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        contactComboBox.getSelectionModel().clearSelection();
        typeTextField.clear();
        startDateField.setValue(null);
        startTimeComboBox.getSelectionModel().clearSelection();
        endDateField.setValue(null);
        endTimeComboBox.getSelectionModel().clearSelection();
        custIDTextField.clear();
        userIDTextField.clear();

    }

    /**
     * Populates all fields with information from the selected Appointment.
     * If no Appointment is selected, gives an error message and sets the selected button back to Add Appointment Radio Button.
     * @param actionEvent selection of Update Appointment Radio Button.
     */
    @FXML
    public void updateApptRadioAction(ActionEvent actionEvent) {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment==null) {
            Alerts.showAlert(13);
            addApptRadio.setSelected(true);

        } else {
            String startDate = String.valueOf(selectedAppointment.getStartTime().toLocalDate());
            String startTime = String.valueOf(selectedAppointment.getStartTime().toLocalTime());
            String endDate = String.valueOf(selectedAppointment.getEndTime().toLocalDate());
            String endTime = String.valueOf(selectedAppointment.getEndTime().toLocalTime());

            apptIDTextField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            titleTextField.setText(selectedAppointment.getTitle());
            descriptionTextField.setText(selectedAppointment.getDescription());
            locationTextField.setText(selectedAppointment.getLocation());
            contactComboBox.setValue(selectedAppointment.getContactName());
            typeTextField.setText(selectedAppointment.getType());
            startDateField.setValue(LocalDate.parse(startDate));
            startTimeComboBox.setValue(startTime);
            endDateField.setValue(LocalDate.parse(endDate));
            endTimeComboBox.setValue(endTime);
            custIDTextField.setText(String.valueOf(selectedAppointment.getCustomerID()));
            userIDTextField.setText(String.valueOf(selectedAppointment.getUserID()));
        }
    }

    /**
     * Saves new Appointment if Add Appointment Radio Button is selected, or updates selected Appointment if Update Appointment Radio Button is selected.
     * Refreshes the tableview after successful save.
     * Shows alert if save unsuccessful.
     * @param actionEvent selection of save button.
     * @throws Exception
     */
    @FXML
    public void saveButtonAction(ActionEvent actionEvent) throws Exception {
        try{
            boolean saveSuccessful = true;

            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String contact = contactComboBox.getValue();
            String type = typeTextField.getText();
            LocalDate startDate = startDateField.getValue(); //Local time - appointment start date
            LocalTime startTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem()); //Local time - appointment start time
            LocalDate endDate = endDateField.getValue(); //Local time - appointment end date
            LocalTime endTime = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem()); //Local time - appointment end time
            Timestamp startUTC = TimeZone.localDTToTimestamp(LocalDateTime.of(startDate, startTime));
            Timestamp endUTC = TimeZone.localDTToTimestamp(LocalDateTime.of(endDate, endTime));
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp localDT = Timestamp.valueOf(localDateTime);
            String userName = loggedInUser;
            int customerID = Integer.parseInt(custIDTextField.getText());
            int userID = Integer.parseInt(userIDTextField.getText());
            int contactID = ContactDB.getContactID(contact);


            if (!checkValidInput(title, description, type, contact, location, startDate, endDate, startTime, endTime)) {
                saveSuccessful = false;
            }

            //Saves new appointment
            if (addApptRadio.isSelected() && saveSuccessful) {
                AppointmentDB.saveNewAppointment(title, description, location, type, startUTC, endUTC, localDT, userName, customerID, userID, contactID);

                //Refreshes list of all appointments
                refreshAllAppointmentsTable();
            }

            //Updates selected appointment
            if (updateApptRadio.isSelected() && saveSuccessful){
                int appointmentID = Integer.parseInt(apptIDTextField.getText());
                AppointmentDB.updateAppointment(appointmentID, title, description, location, type, startUTC, endUTC, localDT, userName, customerID, userID, contactID);

                //Refreshes list of all appointments
                refreshAllAppointmentsTable();
            }

        } catch (Exception e){
            System.out.println(e);
            Alerts.showAlert(10);
        }
    }

    /**
     * Deletes the selected Appointment.
     * Provides an alert to confirm that you would like to delete the customer.
     * Provides an alert after successful deletion of appointment listing the Appointment ID and Type of appointment deleted.
     * Refreshes the tableview after the appointment has been deleted.
     * @param actionEvent selection of delete appointment button.
     */
    @FXML
    public void deleteApptAction(ActionEvent actionEvent) {
        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this record?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                AppointmentDB.deleteAppointment(selectedAppointment.getAppointmentID());

                //Refreshes list of all appointments
                refreshAllAppointmentsTable();

                //Shows message that confirms that the appointment was deleted with the appointment ID & type
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Delete Successful.");
                alert2.setHeaderText("Appointment ID: " + selectedAppointment.getAppointmentID() + ",  Type: " + selectedAppointment.getType() + ", was successfully deleted.");
                alert2.showAndWait();
            }
        } catch (SQLException e) {
            Alerts.showAlert(5);
        }
    }

    /**
     * Brings user to Customers screen.
     * @param actionEvent selection of Customers Button.
     * @throws IOException
     */
    @FXML
    public void customersButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Brings user to Reports screen.
     * @param actionEvent selection of Reports Button.
     * @throws IOException
     */
    @FXML
    public void reportsButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates or Refreshes list of all appointments in the tableview
     */
    public void refreshAllAppointmentsTable(){
        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = AppointmentDB.getAllAppointments();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showAppointments(allAppointments);
    }

    /**
     * Populates the tableview with appointments.
     * LAMBDA EXPRESSION: sets the start and end columns in the tableview to specified date time format -
     * For every appointment in the start and end columns, I get the value of the current appointment start and end time and format them with the specified format and set them as string values.
     * A lambda is justified because the setCellValueFactory uses a callback as a parameter and is simpler than writing another function.
     * @param appointmentList list of appointments to show in tableview.
     */
    public void showAppointments(ObservableList<Appointment> appointmentList) {
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(Appointment -> new SimpleStringProperty(Appointment.getValue().getStartTime().format(formatter)));
        endCol.setCellValueFactory(Appointment -> new SimpleStringProperty(Appointment.getValue().getEndTime().format(formatter)));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTableView.setItems(appointmentList);
    }

    /**
     * Checks all fields for valid input.
     * @param title title of appointment
     * @param description description of appointment
     * @param type type of appointment
     * @param contact appointment contact
     * @param location appointment location
     * @param startDate appointment start date
     * @param endDate appointment end date
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @return Boolean - true if all input is valid, false if input invalid.
     * @throws Exception
     */
    public boolean checkValidInput(String title, String description, String type, String contact, String location, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) throws Exception {
        System.out.println("Checking if input is valid...");

        LocalDateTime startDT = LocalDateTime.of(startDate, startTime); //Local time - appointment start date and time
        LocalDateTime endDT = LocalDateTime.of(endDate, endTime); //Local time - appointment end date and time

        ZonedDateTime zStartDT = startDT.atZone(localZoneID); //Zoned Date Time format of local start date time
        ZonedDateTime zEndDT = endDT.atZone(localZoneID); //Zoned Date Time format of local end date time

        Timestamp startUTC = TimeZone.localDTToTimestamp(startDT);
        Timestamp endUTC = TimeZone.localDTToTimestamp(endDT);


        //If inputs fields are left blank
        if (title == null || description == null || location == null ||
                contact == null || type == null || startDateField.getValue() == null || endDateField.getValue() == null) {
            Alerts.showAlert(6);
            System.out.println("Input invalid - field left blank.");
            return false;
        } //If appointment is outside of business hours
        else if (!checkBusinessHours(zStartDT, zEndDT)) {
            Alerts.showAlert(7);
            System.out.println("Input invalid - appointment is outside of business hours.");
        return false;
        }
        else if (!checkOverlap(startUTC, endUTC)) {
            Alerts.showAlert(8);
            System.out.println("Input invalid - There is an overlapping appointment.");
            return false;
        }
        else {
            System.out.println("Input is valid");
            return true;
        }
    }

    /**
     * Checks appointment to make sure there are no appointment overlaps.
     * @param startUTC Timestamp of start date & time in UTC
     * @param endUTC Timestamp of end date & time in UTC
     * @return Boolean - true if there are no overlapping appointments, false if there is an overlapping appointment.
     * @throws SQLException
     */
    private boolean checkOverlap(Timestamp startUTC, Timestamp endUTC) throws Exception {
        System.out.println("Checking for appointment overlap...");
        if (addApptRadio.isSelected() && AppointmentDB.getOverlappingAppts(startUTC, endUTC).isEmpty() || (updateApptRadio.isSelected() && AppointmentDB.getOverlappingApptsUpdate(startUTC, endUTC, apptTableView.getSelectionModel().getSelectedItem().getAppointmentID()).isEmpty())) {
            System.out.println("There are no overlapping appointments.");
            return true;
            } else return false;
        }

        /**
         * Checks to make sure the appointment day/time is during business hours (Weekdays from 8 AM to 10 PM EST)
         * @param zStartDT ZonedDateTime format of start date & time of appointment.
         * @param zEndDT ZonedDateTime format of end date & time of appointment.
         * @return Boolean - true if the appointment is within business hours, false if the appointment is not within business hours.
         */
        private boolean checkBusinessHours (ZonedDateTime zStartDT, ZonedDateTime zEndDT) {
            System.out.println("Checking if appointment is within business hours...");
            //Start and end times converted to EST in Zoned Date Time format
            //Grabs hour (int) for start and end times for comparisons
            ZonedDateTime startEST = zStartDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime endEST = zEndDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            int startHour = startEST.getHour();
            int endHour = endEST.getHour();

            //Business days converted to int for comparison
            int businessDayStart = DayOfWeek.MONDAY.getValue();
            int businessDayEnd = DayOfWeek.FRIDAY.getValue();

            //Appointment day converted to int for comparison
            int startDay = startEST.getDayOfWeek().getValue();
            int endDay = endEST.getDayOfWeek().getValue();

            // If appointment start time is before business hours or after business hours
            // If appointment end time is before business hours or after business hours
            // If appointment start time is after appointment end time
            if (startHour < 8 || startHour > 22 || startHour > endHour || endHour < 8 || endHour > 22) {
                return false;

                //If appointment start day is before Monday
                //If appointment start day is after Friday
                //If appointment end day is before Monday
                //If appointment end day is after Friday
            } else if (startDay < businessDayStart || startDay > businessDayEnd ||
                    endDay < businessDayStart || endDay > businessDayEnd || startDay > endDay) {
                System.out.println("Appointment is outside of business hours.");
                return false;
            } else
                return true;
        }
}

