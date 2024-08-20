package com.inditex.core.domain.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDates {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime convertToLocalDateTime(final String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public static String convertToString(final LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }
}
