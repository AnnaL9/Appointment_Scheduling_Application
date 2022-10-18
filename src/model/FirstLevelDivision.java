package model;


public class FirstLevelDivision {
    private final int divisionID;
    private final String division;
    private final int countryID;


    /**
     * Constructor for First Level Division
     * @param divisionID
     * @param division
     * @param countryID
     */
    public FirstLevelDivision(int divisionID, String division, int countryID){
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Getter for division ID
     * @return division ID
     */
    public int getDivisionID(){
        return divisionID;
    }

    /**
     * Getter for division
     * @return division
     */
    public String getDivision(){
        return division;
    }

    /**
     * Getter for country ID
     * @return country ID
     */
    public int getCountryID(){
        return countryID;
    }



}
