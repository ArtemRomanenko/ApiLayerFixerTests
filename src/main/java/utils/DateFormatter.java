package utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static String formattedDate(OffsetDateTime offsetDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return offsetDateTime.format(formatter);
    }
}
