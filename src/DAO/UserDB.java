package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {
    /**
     * Validates username entered is in the database
     * @param userName
     * @return user results
     * @throws SQLException
     * @throws Exception
     */
    public static User getUser(String userName) throws SQLException, Exception{
        DBConnection.openConnection();
        String sqlStatement="select * FROM users WHERE User_Name  = '" + userName+ "'";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        while(result.next()){
            String userNameG=result.getString("User_Name");
            int userID=result.getInt("User_ID");
            String password=result.getString("Password");
            userResult= new User(userName, userID, password);
            return userResult;
        }
        DBConnection.closeConnection();
        return null;
    }

    /**
     * Retrieves a list of all users from the database.
     * @return list of all users.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            String userName=result.getString("User_Name");
            int userID=result.getInt("User_ID");
            String password=result.getString("Password");
            User userResult= new User(userName, userID, password);
            allUsers.add(userResult);
        }
        DBConnection.closeConnection();
        return allUsers;
    }
}
