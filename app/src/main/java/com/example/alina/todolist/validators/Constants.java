package com.example.alina.todolist.validators;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Alina on 07.11.2017.
 */

public final class Constants {

    private Constants() {}

    private static final String DATE_FORMAT_STRING = "dd MMMM yyyy";
    public  static  final SimpleDateFormat DATE_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.getDefault());
    }
}
