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

public class StateTests {
    private static State state;

    @BeforeAll
    public static void init(){
        state = new State();
        state.setName("State_10-24");
    }
    @Test
    public void createValidState(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(0, violations.size());
    }
    @Test
    public void stateWithValidName(){
        State state = new State();
        state.setName("New_state");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(0,violations.size());
    }
    @ParameterizedTest
    @MethodSource("provideInvalidStateName")
    public void stateWithInvalidName(String input,String errorValue){
        State state = new State();
        state.setName(input);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }
    private static Stream<Arguments> provideInvalidStateName(){
        return Stream.of(
                Arguments.of("invalidновый.##&", "invalidновый.##&"),
                Arguments.of("Новий стейт", "Новий стейт"),
                Arguments.of("", "")

        );
    }
}
