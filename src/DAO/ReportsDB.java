package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import utils.TimeZone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReportsDB {

    /**
     * Retrieves a list of appointment Types from the database.
     * @return list of appointment Types.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<String> getAllAppointmentTypes() throws SQLException, Exception{
        ObservableList<String> allAppointmentTypes= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT DISTINCT Type FROM appointments";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            String type = result.getString("Type");
            allAppointmentTypes.add(type);
        }
        DBConnection.closeConnection();
        return allAppointmentTypes;
    }

    /**
     * Retrieves a list of all appointments from the database from the specified month and appointment type and counts them.
     * @param month specified appointment month
     * @param apptType specified appointment type
     * @return count of all appointments by month and type
     * @throws Exception
     */
    public static int getMonthTypeCount(int month, String apptType) throws Exception {
        ObservableList<Appointment> thisMonthAppointments= FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.openConnection().prepareStatement("SELECT * FROM appointments WHERE MONTH(start) = ? AND TYPE = ?");
        ps.setInt(1, month);
        ps.setString(2, apptType);
        ResultSet result= ps.executeQuery();
        while(result.next()) {
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime startTime = TimeZone.timestampToLocalDT(result.getTimestamp("Start"));
            LocalDateTime endTime = TimeZone.timestampToLocalDT(result.getTimestamp("End"));
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = ContactDB.getContactName(contactID);

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, customerID, userID, contactID, contactName);
            thisMonthAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return thisMonthAppointments.size();
    }

    /**
     * Retrieves a list of all appointments for specified contact.
     * @param contact specified contact
     * @return list of all appointments for specified contact.
     * @throws Exception
     */
    public static ObservableList<Appointment> getContactAppointments(String contact) throws Exception {
        ObservableList<Appointment> contactAppts= FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT * FROM appointments WHERE Contact_ID =?");
        ps.setInt(1, ContactDB.getContactID(contact));
        ResultSet result= ps.executeQuery();
        while(result.next()){
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime startTime = TimeZone.timestampToLocalDT(result.getTimestamp("Start"));
            LocalDateTime endTime = TimeZone.timestampToLocalDT(result.getTimestamp("End"));
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            String contactName = ContactDB.getContactName(contactID);

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, customerID, userID, contactID, contactName);
            contactAppts.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return contactAppts;
    }

    /**
     * Retrieves a list of all customers by specified location from the database.
     * @param divisionID division ID of customer
     * @param country country of customer
     * @return list of all customers by specified location.
     * @throws Exception
     */
    public static ObservableList<Customer> getCustomerByLocation(int divisionID, String country) throws Exception {
        ObservableList<Customer> customersByLocation= FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT * FROM customers WHERE Division_ID =?");
        ps.setInt(1, divisionID);
        ResultSet result= ps.executeQuery();
        while(result.next()){
            int customerID = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String lastUpdatedBy = result.getString("Last_Updated_By");
            String division = FirstLevelDivisionDB.getDivisionName(divisionID);


            Customer customerResult = new Customer(customerID, name, address, postalCode, phone, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, divisionID, division, country);
            customersByLocation.add(customerResult);
        }
        DBConnection.closeConnection();
        return customersByLocation;
    }
}
