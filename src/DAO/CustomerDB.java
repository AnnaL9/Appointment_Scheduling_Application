package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomerDB {

    /**
     * Retrieves list of all customers from the database.
     * @return List of all customers.
     * @throws Exception
     */
    public static ObservableList<Customer> getAllCustomers() throws Exception{
        ObservableList<Customer> allCustomers= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String ps="SELECT *\n" +
                "FROM customers\n" +
                "JOIN First_Level_Divisions\n" +
                "ON customers.division_id = first_level_divisions.division_id \n" +
                "JOIN countries ON countries.country_id = first_level_divisions.country_id;";
        Query.makeQuery(ps);
        ResultSet result=Query.getResult();
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
            int divisionID = result.getInt("Division_ID");
            String division = result.getString("Division");
            String country = result.getString("Country");


            Customer customerResult = new Customer(customerID, name, address, postalCode, phone, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, divisionID, division, country);
            allCustomers.add(customerResult);
        }
        DBConnection.closeConnection();
        return allCustomers;
    }

    /**
     * Saves new customer in the database.
     * @param name customer's name
     * @param address customer's address
     * @param postalCode customer's postal code
     * @param phone customer's phone number
     * @param localDT current date and time of user's computer
     * @param userName username of current user
     * @param divisionID customer's division ID
     * @throws SQLException
     */
    public static void saveNewCustomer(String name, String address, String postalCode, String phone, Timestamp localDT, String userName, int divisionID) throws SQLException {
        System.out.println("Inserting new customer into database...");

        Query.makeQuery("select max(Customer_ID) from customers");
        ResultSet result = Query.getResult();
        result.next();
        int newID = result.getInt(1) + 1; // max id + 1

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, newID);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, localDT);
        ps.setString(7, userName);
        ps.setTimestamp(8, localDT);
        ps.setString(9, userName);
        ps.setInt(10, divisionID);
        ps.executeUpdate();

        System.out.println("Insert successful.");

        DBConnection.closeConnection();

    }

    /**
     * Updates existing customer in the database.
     * @param customerID customer ID
     * @param name customer's name
     * @param address customer's address
     * @param postalCode customer's postal code
     * @param phone customer's phone number
     * @param localDT current date and time of user's computer
     * @param userName username of current user
     * @param divisionID customer's division ID
     * @throws SQLException
     */
    public static void updateCustomer(int customerID, String name, String address, String postalCode, String phone, Timestamp localDT, String userName, int divisionID) throws SQLException {
        System.out.println("Updating customer in database...");

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?," +
                        "Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?");
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, localDT);
        ps.setString(6, userName);
        ps.setInt(7, divisionID);
        ps.setInt(8, customerID);
        ps.executeUpdate();

        System.out.println("Update successful.");

        DBConnection.closeConnection();
    }

    /**
     * Deletes customer from the database.
     * @param customerID customer's ID
     * @throws SQLException
     */
    public static void deleteCustomer(int customerID) throws SQLException {
        PreparedStatement ps = DBConnection.openConnection().prepareStatement("DELETE FROM Customers WHERE Customer_ID = ?");
        ps.setInt(1, customerID);
        ps.executeUpdate();

        System.out.println("Delete successful.");

        DBConnection.closeConnection();

    }
}
