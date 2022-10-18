package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.TimeZone;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class AppointmentDB {

    /**
     * Retrieves all appointments from the database.
     * @return list of all appointments.
     * @throws Exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws Exception{
        ObservableList<Appointment> allAppointments= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String ps="SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID";
        Query.makeQuery(ps);
        ResultSet result=Query.getResult();
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
            String contactName = result.getString("Contact_Name");

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, customerID, userID, contactID, contactName);
            allAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return allAppointments;
    }

    /**
     * Retrieves a list of appointments in current month from database.
     * @return list of appointments in current month.
     * @throws Exception
     */
    public static ObservableList<Appointment> getMonthView() throws Exception {
        ObservableList<Appointment> thisMonthAppointments= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String ps="SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE MONTH(start) = MONTH(NOW())";
        Query.makeQuery(ps);
        ResultSet result=Query.getResult();
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
            String contactName = result.getString("Contact_Name");

            Appointment appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, customerID, userID, contactID, contactName);
            thisMonthAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return thisMonthAppointments;
    }

    /**
     * Retrieves a list of appointments in current week from database.
     * @return list of appointments in current week.
     * @throws Exception
     */
    public static ObservableList<Appointment> getWeekView() throws Exception {
        ObservableList<Appointment> thisWeekAppointments= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String ps="SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE WEEK(start) = WEEK(NOW())";
        Query.makeQuery(ps);
        ResultSet result=Query.getResult();
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
                String contactName = result.getString("Contact_Name");

                Appointment appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, customerID, userID, contactID, contactName);
                thisWeekAppointments.add(appointmentResult);
            }
        DBConnection.closeConnection();
        return thisWeekAppointments;
    }

    /**
     * Retrieves a list of appointments that are starting within 15 minutes of current time from the database.
     * @return a list of upcoming appointments within 15 minutes.
     * @throws Exception
     */
    public static ObservableList<Appointment> getUpcomingAppointments() throws Exception {
        ObservableList<Appointment> upcomingAppointments= FXCollections.observableArrayList();
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
        System.out.println(currentTime);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime.getTime());

        // add 15 minutes
        cal.setTimeInMillis(currentTime.getTime());
        cal.add(Calendar.MINUTE, 15);
        Timestamp nowPlus15 = new Timestamp(cal.getTime().getTime());
        System.out.println(nowPlus15);

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT * FROM appointments "
                        + "WHERE start between ? and ?");
        ps.setTimestamp(1, currentTime);
        ps.setTimestamp(2, nowPlus15);
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
            upcomingAppointments.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return upcomingAppointments;
    }

    /**
     * Retrieves a list of appointments that are overlapping with appointment being created from the database.
     * @param start appointment start time
     * @param end appointment end time
     * @return list of appointments that overlap the appointment trying to be added.
     * @throws Exception
     */
    public static ObservableList<Appointment> getOverlappingAppts(Timestamp start, Timestamp end) throws Exception {
        System.out.println("Searching appointments in database...");
        ObservableList<Appointment> overlappingAppts= FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT * FROM appointments "
                        + "WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) ");
        ps.setTimestamp(1, start);
        ps.setTimestamp(2, end);
        ps.setTimestamp(3, start);
        ps.setTimestamp(4, end);
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
            overlappingAppts.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return overlappingAppts;
    }

    /**
     * Retrieves a list of appointments that are overlapping with appointment being updated from the database.
     * Excludes itself from the search results to prevent unnecessary overlap.
     * @param start appointment start time
     * @param end appointment end time
     * @param apptID appoint ID of appointment being updated.
     * @return list of appointments that overlap the appointment trying to be added.
     * @throws Exception
     */
    public static ObservableList<Appointment> getOverlappingApptsUpdate(Timestamp start, Timestamp end, int apptID) throws Exception {
        System.out.println("Searching appointments in database...");
        ObservableList<Appointment> overlappingAppts= FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT * FROM appointments "
                        + "WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end)" +
                        "AND appointment_ID !=?");
        ps.setTimestamp(1, start);
        ps.setTimestamp(2, end);
        ps.setTimestamp(3, start);
        ps.setTimestamp(4, end);
        ps.setInt(5, apptID);
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
            overlappingAppts.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return overlappingAppts;
    }

    /**
     * Saves new appointment into the database.
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startUTC appointment start time in UTC
     * @param endUTC appointment end time in UTC
     * @param localDT current local date and time of user's computer
     * @param userName username of current user adding appointment
     * @param customerID customer ID of which customer the appointment is with
     * @param userID user ID of current user adding appointment
     * @param contactID contact ID of company contact that the appointment is with
     * @throws SQLException
     */
    public static void saveNewAppointment(String title, String description, String location, String type, Timestamp startUTC,
                                          Timestamp endUTC, Timestamp localDT, String userName, int customerID, int userID, int contactID) throws SQLException {
        System.out.println("Inserting new appointment into database...");

        Query.makeQuery("select max(Appointment_ID) from appointments");
        ResultSet result = Query.getResult();
        result.next();
        int newID = result.getInt(1) + 1; // max id + 1

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, " +
                        "User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, newID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, startUTC);
        ps.setTimestamp(7, endUTC);
        ps.setTimestamp(8, localDT);
        ps.setString(9, userName);
        ps.setTimestamp(10, localDT);
        ps.setString(11, userName);
        ps.setInt(12, customerID);
        ps.setInt(13, userID);
        ps.setInt(14, contactID);
        ps.executeUpdate();

        System.out.println("Insert successful.");

        DBConnection.closeConnection();

    }

    /**
     * Updates selected appointment in the database.
     * @param appointmentID appointment ID of appointment being updated
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startUTC appointment start time in UTC
     * @param endUTC appointment end time in UTC
     * @param localDT current local date and time of user's computer
     * @param userName username of current user adding appointment
     * @param customerID customer ID of which customer the appointment is with
     * @param userID user ID of current user adding appointment
     * @param contactID contact ID of company contact that the appointment is with
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, Timestamp startUTC,
                                          Timestamp endUTC, Timestamp localDT, String userName, int customerID, int userID, int contactID) throws SQLException {
        System.out.println("Updating appointment in database...");

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "UPDATE appointments " +
                        "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? " +
                        "WHERE Appointment_ID=?");
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startUTC);
        ps.setTimestamp(6, endUTC);
        ps.setTimestamp(7, localDT);
        ps.setString(8, userName);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, appointmentID);
        ps.executeUpdate();

        System.out.println("Update successful.");

        DBConnection.closeConnection();
    }

    /**
     * Deletes appointment from the database.
     * @param appointmentID appointment ID
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        PreparedStatement ps = DBConnection.openConnection().prepareStatement("DELETE FROM Appointments WHERE Appointment_ID = ?");
        ps.setInt(1, appointmentID);
        ps.executeUpdate();

        System.out.println("Delete successful.");

        DBConnection.closeConnection();

    }

    /**
     * Retrieves a list of all appointments associated with a specific customer.
     * @param custID customer's ID associated with appointments.
     * @return list of all appointments for specific customer.
     * @throws Exception
     */
    public static ObservableList<Appointment> getAppointmentsForCustomer(int custID) throws Exception {
        ObservableList<Appointment> customerAppts= FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT * FROM appointments WHERE Customer_ID =?");
        ps.setInt(1, custID);
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
            customerAppts.add(appointmentResult);
        }
        DBConnection.closeConnection();
        return customerAppts;
    }

}
