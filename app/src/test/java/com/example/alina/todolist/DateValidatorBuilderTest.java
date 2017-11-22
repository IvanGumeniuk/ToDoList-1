package com.example.alina.todolist;

import com.example.alina.todolist.validators.Validator;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Alina on 13.11.2017.
 */
public class DateValidatorBuilderTest {
    @Test
    public void testMinDateValidRule() {
        Calendar calendarMin = Calendar.getInstance();
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setMin(calendarMin.getTime())
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 12);
        boolean result = validator.validate(calendar.getTime());
        assertTrue(result);
    }

    @Test
    public void testMinDateInValidRule() {
        Calendar calendarMin = Calendar.getInstance();
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setMin(calendarMin.getTime())
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        boolean result = validator.validate(calendar.getTime());
        assertFalse(result);
    }

    @Test
    public void testMaxDateValidRule() {
        Calendar calendarMax = Calendar.getInstance();
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setMax(calendarMax.getTime())
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -12);
        boolean result = validator.validate(calendar.getTime());
        assertTrue(result);
    }

    @Test
    public void testMaxDateInValidRule() {
        Calendar calendarMax = Calendar.getInstance();
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setMax(calendarMax.getTime())
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        boolean result = validator.validate(calendar.getTime());
        assertFalse(result);
    }

    @Test
    public void testRangeValidRule() {
        Calendar calendarMax = Calendar.getInstance();
        Calendar calendarMin = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendarMax.add(Calendar.YEAR, 20);
        calendarMin.add(Calendar.YEAR, -20);
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setToRange(calendarMin.getTime(), calendarMax.getTime())
                .build();
        boolean result = validator.validate(calendar.getTime());
        assertTrue(result);
    }

    @Test
    public void testRangeInValidRule() {
        Calendar calendarMax = Calendar.getInstance();
        Calendar calendarMin = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendarMax.add(Calendar.YEAR, -18);
        calendarMin.add(Calendar.YEAR, -20);
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setToRange(calendarMin.getTime(), calendarMax.getTime())
                .build();
        boolean result = validator.validate(calendar.getTime());
        assertFalse(result);
    }

    @Test
    public void testRangeValidReversedRule() {
        Calendar calendarMax = Calendar.getInstance();
        Calendar calendarMin = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendarMax.add(Calendar.YEAR, -18);
        calendarMin.add(Calendar.YEAR, -20);
        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setToRange(calendarMax.getTime(), calendarMin.getTime())
                .build();
        boolean result = validator.validate(calendar.getTime());
        assertFalse(result);
    }

}