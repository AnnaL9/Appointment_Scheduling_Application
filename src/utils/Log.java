package utils;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Log {
    /**
     * Creates a log of login attempts with username, timestamp, and if it was successful or not.
     * @param loginSuccessful Boolean - true, if login was successful, false if login was unsuccessful
     * @param userName attempted account's username
     * @throws IOException
     */
    public static void log(boolean loginSuccessful, String userName) throws IOException {
        //Get timestamp of attempted login
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp localDT = Timestamp.valueOf(localDateTime);

        String logString = "Username: " + userName + " Attempted login: " + localDT + " Login Successful: " + loginSuccessful + "\n";

        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.write(logString);
            pw.close();
        }
        catch (IOException io) {
            System.out.println("File IO Exception" + io.getMessage());
        }
    }

}
