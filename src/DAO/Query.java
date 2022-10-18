package DAO;

import java.sql.Statement;
import java.sql.ResultSet;
import static DAO.DBConnection.connection;
import static DAO.DBConnection.openConnection;

public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    /**
     * Makes a query in the database
     * @param q query
     */
    public static void makeQuery(String q){
        query =q;
        try{
            stmt=openConnection().createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * Gets results from query from database.
     * @return results from query
     */
    public static ResultSet getResult(){
        return result;
    }
}
