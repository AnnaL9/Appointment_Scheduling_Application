package model;

import javafx.application.Application;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    /**
     * Constructor for appointment
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startTime
     * @param endTime
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @param contactName
     */
    public Appointment (int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime,
                        Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerID,
                        int userID, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    //
    /**
     * getter for Appointments ID
     * @return ID of the appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Getter for Title
     * @return title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for Description
     * @return description of appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for Location
     * @return location of appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for Type
     * @return type of appointment
     */
    public String getType() {
        return  type;
    }

    /**
     * Getter for start time
     * @return start date and time of appointment
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for end time
     * @return end date and time of appointment
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for create date time
     * @return date appointment was created
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Getter for created by
     * @return the user that created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Getter for last update
     * @return last appointment update time
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Getter for last updated by
     * @return who last updated the appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * getter for customer ID
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Getter for user ID
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for contact ID
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Getter for contact name
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

}
