package model;


public class Country {
    private int countryID;
    private String country;


    /**
     * Constructor for Country
     *
     * @param countryID
     * @param country
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Getter for country ID
     *
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Getter for country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }
}

