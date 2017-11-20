package com.example.alina.todolist;
import com.example.alina.todolist.validators.Validator;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Alina on 13.11.2017.
 */
public class NumberValidatorBuilderTest {
    @Test
    public void testMinInvalidRule() {
        @SuppressWarnings("unchecked")
        Validator<Integer> validator = new Validator.NumberValidatorBuilder<Integer>()
                .setMin(5)
                .build();
        boolean result = validator.validate(2);
        assertFalse(result);
    }
    @Test
    public void testMinValidRule() {
        Validator<Integer> validator = new Validator.NumberValidatorBuilder<Integer>()
                .setMin(5)
                .build();
        boolean result = validator.validate(7);
        assertTrue(result);
    }
    @Test
    public void testMaxValidRule() {
        Validator<Double> validator = new Validator.NumberValidatorBuilder<Double>()
                .setMax(10.0)
                .build();
        boolean result = validator.validate(9.0);
        assertTrue(result);
    }
    @Test
    public void testMaxInvalidRule() {
        Validator<Integer> validator = new Validator.NumberValidatorBuilder<Integer>()
                .setMax(10)
                .build();
        boolean result = validator.validate(12);
        assertFalse(result);
    }
    @Test
    public void testRangeInvalidRule() {
        Validator<BigDecimal> validator = new Validator.NumberValidatorBuilder<BigDecimal>()
                .setToRange(new BigDecimal(1.5), new BigDecimal(20.5))
                .build();
        boolean result = validator.validate(new BigDecimal(10.4));
        assertTrue(result);
    }
    @Test
    public void testRangeReverseValidRule() {
        Validator<Double> validator = new Validator.NumberValidatorBuilder<Double>()
                .setToRange(10.0, 1.2)
                .build();
        boolean result = validator.validate(9.0);
        assertTrue(result);
    }
}