package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDB {

    /**
     * Retrieves a list of all countries with country ID and country name from the database
     * @return list of all countries
     * @throws Exception
     */
    public static ObservableList<Country> getAllCountries() throws Exception{
        ObservableList<Country> allCountries= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String ps="SELECT * FROM countries";
        Query.makeQuery(ps);
        ResultSet result=Query.getResult();
        while(result.next()){
            int countryID = result.getInt("Country_ID");
            String country = result.getString("Country");

            Country countryResult = new Country(countryID, country);
            allCountries.add(countryResult);
        }
        DBConnection.closeConnection();
        return allCountries;
    }

    /**
     * Retrieves a list of all country names from the database
     * @return list of all country names
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<String> getAllCountryNames() throws SQLException, Exception{
        ObservableList<String> allCountryNames= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT country FROM countries";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            String country = result.getString("country");
            allCountryNames.add(country);
        }
        DBConnection.closeConnection();
        return allCountryNames;
    }

    /**
     * Retrieves the country ID from specified country name from the database
     * @param country country name
     * @return countryID
     * @throws SQLException
     * @throws Exception
     */
    public static int getCountryID(String country) throws SQLException, Exception{
        PreparedStatement ps = DBConnection.openConnection().prepareStatement(
                "SELECT Country_ID FROM countries WHERE country = ?");
        ps.setString(1, country);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryID = rs.getInt(1);
        DBConnection.closeConnection();
        return countryID;
    }


}
