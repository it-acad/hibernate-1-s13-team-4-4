package com.softserve.itacademy.model;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskTests {
    private static Task validTask;


    @BeforeAll
    public static void init(){
        validTask = new Task();
        validTask.setName("Create TaskTests");
        validTask.setPriority(Priority.MEDIUM);
    }
    @Test
    public void createValidTask(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0,violations.size());
    }
    @Test
    public void createTaskWithNullName(){
        Task task = new Task();
        task.setName(null);
        task.setPriority(Priority.HIGH);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1,violations.size());

    }
    @ParameterizedTest
    @MethodSource("provideTaskNamesWithDifferentInvalidLength")
    public void taskNamesWithDifferentLength(String input,String errorValue){
        Task task = new Task();
        task.setName(input);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());

    }
    private static Stream<Arguments> provideTaskNamesWithDifferentInvalidLength(){
        return Stream.of(
                Arguments.of("jo","jo"),
                Arguments.of("Г","Г"),
                Arguments.of("","")
        );
    }
}
