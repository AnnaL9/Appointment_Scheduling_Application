package controller;

import DAO.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public ComboBox<String> monthComboBox;
    public ComboBox<String> typeComboBox;
    public TextField countTextField;
    public ComboBox<String> contactComboBox;
    public Button submitButton2;
    public TableView<Appointment> report2TableView;
    public TableColumn<Appointment, String> apptIDCol2;
    public TableColumn<Appointment, String> titleCol2;
    public TableColumn<Appointment, String> descriptionCol2;
    public TableColumn<Appointment, String> locationCol2;
    public TableColumn<Appointment, String> contactCol2;
    public TableColumn<Appointment, String> typeCol2;
    public TableColumn<Appointment, String> startCol2;
    public TableColumn<Appointment, String> endCol2;
    public TableColumn<Appointment, String> custIDCol2;
    public TableColumn<Appointment, String> userIDCol2;
    public ComboBox<String> countryComboBox;
    public ComboBox<String> firstLevelDivisionComboBox;
    public Button submitButton3;
    public TableView<Customer> report3TableView;
    public TableColumn<Appointment, String> custIDCol3;
    public TableColumn<Customer, String> nameCol3;
    public TableColumn<Customer, String> addressCol3;
    public TableColumn<Customer, String> postalCodeCol3;
    public TableColumn<Customer, String> divisionCol3;
    public TableColumn<Customer, String> countryCol3;
    public TableColumn<Customer, String> phoneCol3;
    public Button submitButton1;
    private Stage stage;
    private Scene scene;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes
     * * LAMBDA EXPRESSION adds listener for country ComboBox - a lambda expression is justified here because it has a callback and is simpler than writing it's own function.
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Disable Count text field
        countTextField.setEditable(false);

        //List of months to populate the month ComboBox
        String[] monthList = {
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };

        //Populate month ComboBox
        try {
            monthComboBox.setItems(FXCollections.observableArrayList(monthList));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Populate Type ComboBox
        try {
            typeComboBox.setItems(ReportsDB.getAllAppointmentTypes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Populate Contact ComboBox
        try {
            contactComboBox.setItems(ContactDB.getAllContactNames());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Populate Country ComboBox
        try {
            countryComboBox.setItems(CountryDB.getAllCountryNames());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Adds a listener for Country ComboBox
        countryComboBox.valueProperty().addListener((ChangeListener) (observableValue, o, t1) -> populateFLD());

    }

    /**
     * Brings user to Appointments screen.
     * @param actionEvent selection of Appointments Button
     * @throws IOException
     */
    public void appointmentsButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Brings user to Customers screen.
     * @param actionEvent selection of Customers Button.
     * @throws IOException
     */
    public void customersButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates First Level Division ComboBox.
     */
    public void populateFLD() {
        if (countryComboBox.getSelectionModel().getSelectedItem() == null){
            countryComboBox.getSelectionModel().clearSelection();
        } else {
            String selectedCountry = String.valueOf(countryComboBox.getSelectionModel().getSelectedItem());
            try {
                int selectedCountryID = CountryDB.getCountryID(selectedCountry);
                firstLevelDivisionComboBox.setItems(FirstLevelDivisionDB.getAllDivisionNames(selectedCountryID));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Populates Count for Appointments by Month and Type
     */
    public void populateCount() {
        if (monthComboBox.getSelectionModel().getSelectedItem() == null && typeComboBox.getSelectionModel().getSelectedItem() == null){
            countTextField.clear();
        } else {
            try {
                int selectedMonthNumber = monthComboBox.getSelectionModel().getSelectedIndex() + 1;
                String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
                int count = ReportsDB.getMonthTypeCount(selectedMonthNumber, selectedType);
                countTextField.setText(String.valueOf(count));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Populates or Refreshes list of appointments in the report 2 tableview for specified contact
     */
    public void refreshReport2Table(String contact){
        ObservableList<Appointment> contactAppointments = null;
        try {
            contactAppointments = ReportsDB.getContactAppointments(contact);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showAppointmentsReport2(contactAppointments);
    }

    /**
     * Populates the tableview with appointments.
     * LAMBDA EXPRESSION sets the start and end columns in the tableview to specified date time format
     * @param appointmentList list of appointments to show in tableview.
     */
    public void showAppointmentsReport2(ObservableList<Appointment> appointmentList) {
        apptIDCol2.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol2.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol2.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol2.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol2.setCellValueFactory(Appointment -> new SimpleStringProperty(Appointment.getValue().getStartTime().format(formatter)));
        endCol2.setCellValueFactory(Appointment -> new SimpleStringProperty(Appointment.getValue().getEndTime().format(formatter)));
        custIDCol2.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol2.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactCol2.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        report2TableView.setItems(appointmentList);
    }

    /**
     * Populates or Refreshes list of appointments in the report 3 tableview for specified location
     */
    public void refreshReport3Table(int divisionID, String country){
        ObservableList<Customer> customerByLocation = null;
        try {
            customerByLocation = ReportsDB.getCustomerByLocation(divisionID, country);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showCustomersReport3(customerByLocation);
    }

    /**
     * Populates the tableview with appointments.
     * LAMBDA EXPRESSION sets the start and end columns in the tableview to specified date time format
     * @param customerList list of appointments to show in tableview.
     */
    public void showCustomersReport3(ObservableList<Customer> customerList) {
        custIDCol3.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol3.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol3.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol3.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        divisionCol3.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol3.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneCol3.setCellValueFactory(new PropertyValueFactory<>("phone"));
        report3TableView.setItems(customerList);
    }

    /**
     * Populates report 1 - count appointments by month & type
     * @param actionEvent
     */
    public void submitButton1Action(ActionEvent actionEvent) {
        populateCount();
    }

    /**
     * Populates report 2 tableview based on specified contact
     * @param actionEvent selection of the Submit Button for Contact Schedule Report
     */
    public void submitButton2Action(ActionEvent actionEvent) {
        refreshReport2Table(contactComboBox.getValue());
    }

    /**
     * Populate report 3 tableview based on location entered
     * @param actionEvent selection of the Submit Button for Customers by Location Report
     * @throws Exception
     */
    public void submitButton3Action(ActionEvent actionEvent) throws Exception {
        refreshReport3Table(FirstLevelDivisionDB.getDivisionID(firstLevelDivisionComboBox.getSelectionModel().getSelectedItem()), countryComboBox.getValue());
    }

}
