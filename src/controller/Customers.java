package controller;

import DAO.*;
import javafx.beans.value.ChangeListener;
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
import model.Customer;
import utils.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class Customers implements Initializable {
    public TableView<Customer> customerTableView;
    public RadioButton addCustomerRadio;
    public RadioButton updateCustomerRadio;
    public Button deleteCustomerButton;
    public TextField custIDTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneNumberTextField;
    public ComboBox<String> countryComboBox;
    public ComboBox<String> firstLevelDivisionComboBox;
    public Button saveButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public TableColumn<Customer, Integer> custIDCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> postalCodeCol;
    public TableColumn<Customer, String> divisionCol;
    public TableColumn<Customer, String> countryCol;
    public TableColumn<Customer, String> phoneCol;

    private Stage stage;
    private Scene scene;

    /**
     * Initializes, disables customer ID text field from being edited, selects add customer radio button, populates ComboBoxes, and populates the tableview to show all customers
     * LAMBDA EXPRESSION adds listener for country ComboBox
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Disable customer ID text field
        custIDTextField.setEditable(false);

        //Set add customer radio button to default
        addCustomerRadio.setSelected(true);

        //Populate Country ComboBox
        try {
            countryComboBox.setItems(CountryDB.getAllCountryNames());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Adds a listener for Country ComboBox
        countryComboBox.valueProperty().addListener((ChangeListener) (observableValue, o, t1) -> populateFLD());


        //Populate all customers in tableview
        refreshAllCustomersTable();

    }


    /**
     * Clears all inputs in the fields to enter a new customer
     * @param actionEvent Selection of the Add Customer Radio button
     */
    public void addCustomerRadioAction(ActionEvent actionEvent) {
        custIDTextField.clear();
        nameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        phoneNumberTextField.clear();
        countryComboBox.getSelectionModel().clearSelection();
        firstLevelDivisionComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Populates all fields with information from the selected customer.
     * If no customer is selected, gives an error message and sets the selected button back to Add Customer Radio Button.
     * @param actionEvent Selection of Update Customer Radio Button.
     */
    public void updateCustomerRadioAction(ActionEvent actionEvent) {
        Customer selectedCustomer = (Customer) customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer==null) {
            Alerts.showAlert(12);
            addCustomerRadio.setSelected(true);

        } else {
            custIDTextField.setText(String.valueOf(selectedCustomer.getCustomerID()));
            nameTextField.setText(selectedCustomer.getCustomerName());
            addressTextField.setText(selectedCustomer.getAddress());
            postalCodeTextField.setText(selectedCustomer.getPostalCode());
            phoneNumberTextField.setText(selectedCustomer.getPhone());
            countryComboBox.setValue(selectedCustomer.getCountry());
            firstLevelDivisionComboBox.setValue(selectedCustomer.getDivision());
        }

    }

    /**
     * Deletes the selected customer.
     * Provides an alert to confirm that you would like to delete the customer.
     * Provides an alert if the customer has appointments that need to be deleted first.
     * Provides an alert after successful deletion of customer.
     * Refreshes the tableview after the customer has been deleted.
     * @param actionEvent selection of Delete Customer Button.
     */
    public void deleteCustomerButtonAction(ActionEvent actionEvent) {
        Customer selectedCustomer = (Customer) customerTableView.getSelectionModel().getSelectedItem();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this record?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (AppointmentDB.getAppointmentsForCustomer(selectedCustomer.getCustomerID()).isEmpty()) {
                    CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());

                    //Refreshes list of all customers
                    refreshAllCustomersTable();

                    //Shows message that confirms that the appointment was deleted with the appointment ID & type
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Delete Successful.");
                    alert2.setHeaderText("Customer ID: " + selectedCustomer.getCustomerID() + ",  Name: " + selectedCustomer.getCustomerName() + ", was successfully deleted.");
                    alert2.showAndWait();
                } else {
                    Alerts.showAlert(14);
                }
            }
        } catch (SQLException e) {
            Alerts.showAlert(5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves new customer if Add Customer Radio Button is selected, or updates selected customer if Update Customer Radio Button is selected.
     * Refreshes the tableview after successful save.
     * Shows alert if save unsuccessful.
     * @param actionEvent Selection of Save Button.
     */
    public void saveButtonAction(ActionEvent actionEvent) {
        try{
            boolean saveSuccessful = true;

            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            String country = (String) countryComboBox.getSelectionModel().getSelectedItem();
            String firstLevelDivision = (String) firstLevelDivisionComboBox.getSelectionModel().getSelectedItem();
            int divisionID = FirstLevelDivisionDB.getDivisionID(firstLevelDivision);
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp localDT = Timestamp.valueOf(localDateTime);
            String userName = Appointments.loggedInUser;

            if (!checkValidInput(name, address, postalCode, phone, country, firstLevelDivision)) {
                saveSuccessful = false;
            }

            //Saves new appointment
            if (addCustomerRadio.isSelected() && saveSuccessful) {
                CustomerDB.saveNewCustomer(name, address, postalCode, phone, localDT, userName, divisionID);

                //Refreshes list of all appointments
                refreshAllCustomersTable();
            }

            //Updates selected appointment
            if (updateCustomerRadio.isSelected() && saveSuccessful){
                int customerID = Integer.parseInt(custIDTextField.getText());
                CustomerDB.updateCustomer(customerID, name, address, postalCode, phone, localDT, userName, divisionID);

                //Refreshes list of all appointments
                refreshAllCustomersTable();
            }

        } catch (Exception e){
            System.out.println(e);
            Alerts.showAlert(10);
        }
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
     * Brings user to Reports screen.
     * @param actionEvent selection of Reports Button.
     * @throws IOException
     */
    public void reportsButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the tableview with customers
     * @param customerList list of customers to show in tableview
     */
    public void showCustomers(ObservableList<Customer> customerList) {
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTableView.setItems(customerList);
    }

    /**
     * Populates or Refreshes list of all customers in the tableview
     */
    public void refreshAllCustomersTable () {
        ObservableList<Customer> allCustomers = null;
        try {
            allCustomers = CustomerDB.getAllCustomers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showCustomers(allCustomers);
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
     * Checks all fields for valid input.
     * @param name customer's name
     * @param address customer's address
     * @param postalCode customer's postal code
     * @param phone customer's phone number
     * @param country customer's country
     * @param firstLevelDivision customer's first level division
     * @return Boolean - true if all input is valid, false if input invalid.
     * @throws Exception
     */
    public boolean checkValidInput(String name, String address, String postalCode, String phone, String country, String firstLevelDivision) throws Exception {
        System.out.println("Checking if input is valid...");

        //If inputs fields are left blank
        if (name == null || address == null || postalCode == null ||
                phone == null || country == null || country == null || firstLevelDivision == null) {
            Alerts.showAlert(6);
            System.out.println("Input invalid - field left blank.");
            return false;
        } else {
            System.out.println("Input is valid");
            return true;
        }

    }

}


