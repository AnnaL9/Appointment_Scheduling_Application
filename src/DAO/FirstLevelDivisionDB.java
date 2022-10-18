package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FirstLevelDivisionDB {

    /**
     * Retrieves a list of all divisions with division ID, division, and country ID from the database.
     * @return list of all divisions.
     * @throws Exception
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws Exception{
        ObservableList<FirstLevelDivision> allDivisions= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String ps="SELECT * FROM first_level_divisions";
        Query.makeQuery(ps);
        ResultSet result=Query.getResult();
        while(result.next()){
            int divisionID = result.getInt("Division_ID");
            String division = result.getString("Division");
            int countryID = result.getInt("Country_ID");

            FirstLevelDivision divisionResult = new FirstLevelDivision(divisionID, division, countryID);
            allDivisions.add(divisionResult);
        }
        DBConnection.closeConnection();
        return allDivisions;
    }

    /**
     * Retrieves a list of all division names from database for a specific country.
     * @param countryID country ID
     * @return list of all division names for specified country.
     * @throws Exception
     */
    public static ObservableList<String> getAllDivisionNames(int countryID) throws Exception{
        ObservableList<String> allDivisionNames= FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.openConnection().prepareStatement("SELECT division\n" +
                "FROM first_level_divisions \n" +
                "WHERE Country_ID = ?");
        ps.setInt(1, countryID);
        ResultSet result=ps.executeQuery();
        while(result.next()){
            String division = result.getString("division");
            allDivisionNames.add(division);
        }
        DBConnection.closeConnection();
        return allDivisionNames;
    }

    /**
     * Retrieves the division ID from the database from the division name.
     * @param division division name
     * @return division ID
     * @throws Exception
     */
    public static int getDivisionID(String division) throws Exception {
        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT Division_ID FROM first_level_divisions WHERE division = ?");
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int divisionID = rs.getInt(1);
        DBConnection.closeConnection();
        return divisionID;
    }

    /**
     * Retrieves division name from the database from the division ID.
     * @param divisionID division ID
     * @return division name
     * @throws Exception
     */
    public static String getDivisionName(int divisionID) throws Exception {
        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT Division FROM first_level_divisions WHERE Division_ID = ?");
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String division = rs.getString(1);
        DBConnection.closeConnection();
        return division;
    }
}
