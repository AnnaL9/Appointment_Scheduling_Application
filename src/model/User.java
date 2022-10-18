package model;

/**
 * model for user
 * @author Anna Lyons
 */
public class User {
    private String userName;
    private int userID;
    private String password;

    /**
     * Constructor
     * @param userName username
     * @param userID user ID
     */
    public User(String userName, int userID, String password) {
        this.userName = userName;
        this.userID = userID;
        this.password = password;
    }

    /**
     * Getter for username
     * @return userName
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Getter for user ID
     * @return userID
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword(){
        return password;
    }

}
