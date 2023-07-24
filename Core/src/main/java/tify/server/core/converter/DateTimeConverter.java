package tify.server.core.converter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import tify.server.core.exception.DateFormatException;

public class DateTimeConverter {

    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw DateFormatException.EXCEPTION;
        }
    }
}
