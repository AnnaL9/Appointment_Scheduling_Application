package model;

import java.sql.Timestamp;


public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String division;
    private String country;

    /**
     * Constructor for Customers
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     */
    public Customer (int customerID, String customerName, String address, String postalCode,
                     String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                     String lastUpdatedBy, int divisionID, String division, String country){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.division = division;
        this.country = country;

    }

    /**
     * Getter for customer ID
     * @return customer ID
     */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * Getter for customer name
     * @return customer name
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * Getter for address
     * @return address of customer
     */
    public String getAddress(){
        return address;
    }

    /**
     * Getter of postal code
     * @return postal code for customer
     */
    public String getPostalCode(){
        return postalCode;
    }

    /**
     * Getter of phone
     * @return customer's phone number
     */
    public String getPhone(){
        return phone;
    }

    /**
     * Getter of create date
     * @return date customer was created
     */
    public Timestamp getCreateDate(){
        return createDate;
    }

    /**
     * Getter of created by
     * @return which user created the customer
     */
    public String getCreatedBy(){
        return createdBy;
    }

    /**
     * Getter of last update
     * @return when the last time the customer was updated
     */
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     * Getter of last updated by
     * @return who last updated the customer
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     * Getter of division ID
     * @return division ID
     */
    public int getDivisionID(){
        return divisionID;
    }

    /**
     * Getter of division
     * @return division
     */
    public String getDivision(){
        return division;
    }

    /**
     * Getter of country
     * @return country
     */
    public String getCountry(){
        return country;
    }
}
