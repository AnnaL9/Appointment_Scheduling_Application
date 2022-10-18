package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    /**
     * Retrieves a list of all contacts from database.
     * @return list of all contacts.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{
        ObservableList<Contact> allContacts= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int contactID = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String email = result.getString("Email");
            Contact contactResult = new Contact(contactID, contactName, email);
            allContacts.add(contactResult);
        }
        DBConnection.closeConnection();
        return allContacts;
    }

    /**
     * Retrieves a list of contact names from the database.
     * @return list of contact names.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<String> getAllContactNames() throws SQLException, Exception{
        ObservableList<String> allContactNames= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT Contact_Name FROM contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            String contactName = result.getString("Contact_Name");
            allContactNames.add(contactName);
        }
        DBConnection.closeConnection();
        return allContactNames;
    }

    /**
     * Retrieves the contact ID for specified contact name.
     * @param contact contact name
     * @return contact ID
     * @throws SQLException
     * @throws Exception
     */
    public static int getContactID(String contact) throws SQLException, Exception{
        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
        "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        ps.setString(1, contact);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int contactID = rs.getInt(1);
        DBConnection.closeConnection();
        return contactID;
    }

    /**
     * Retrieves the contact name for specified contact ID.
     * @param contactID contact ID
     * @return contact name
     * @throws SQLException
     * @throws Exception
     */
    public static String getContactName(int contactID) throws SQLException, Exception{
        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?");
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String contactName = rs.getString(1);
        DBConnection.closeConnection();
        return contactName;
    }
}
