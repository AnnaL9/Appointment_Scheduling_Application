package utils;

import java.sql.Timestamp;
import java.time.*;


public class TimeZone {


    /**
     * Converts timestamp in UTC from the database to LocalDateTime of user's local time
     * @param timestamp Timestamp
     * @return LocalDateTime of Timestamp
     */
    public static LocalDateTime timestampToLocalDT (Timestamp timestamp){
        return timestamp.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Converts LocalDateTime of user's local time to timestamp in UTC
     * @param ldt LocalDateTime
     * @return Timestamp of LocalDateTime
     */
    public static Timestamp localDTToTimestamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
    }
}
