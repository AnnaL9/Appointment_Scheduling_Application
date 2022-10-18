package model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Constructor for contact
     * @param contactID
     * @param contactName
     * @param email
     */
    public Contact (int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter for contact ID
     * @return contactID
     */
    public int getContactID(){
        return contactID;
    }

    /**
     * Getter for contact name
     * @return contact name
     */
    public String getContactName(){
        return contactName;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getEmail(){
        return email;
    }
}
