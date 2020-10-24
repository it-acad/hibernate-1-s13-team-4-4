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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTests {
    private static ToDo validToDo;
    private static User owner;
    private static Role role;
    private static List<Task> tasks;
    private static Task task;

    @BeforeAll
    public static void init(){
        owner = new User();
        owner.setFirstName("Valid-FirstName");
        owner.setLastName("Valid-LastName");
        owner.setEmail("test@gmail.com");
        owner.setRole(role);
        task = new Task();
        task.setName("Task#1");
        tasks = new ArrayList<>();
        tasks.add(task);
        validToDo = new ToDo();
        validToDo.setTitle("Title#1");
        validToDo.setCreatedAt(LocalDateTime.now());
        validToDo.setOwner(owner);
//        validToDo.setCollaborators(null);
        validToDo.setTasks(tasks);
        role = new Role();
        role.setName("TRAINEE");


    }
    @Test
    public void createValidToDo(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(validToDo);
        assertEquals(0,violations.size());
    }
    @Test
    public void toDoWithNullTitle(){
        ToDo toDo = new ToDo();
        toDo.setTitle(null);
        toDo.setOwner(owner);
        toDo.setTasks(tasks);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1,violations.size());
    }
    @Test
    public void toDoWithNullTimeStamp(){
        ToDo toDo = new ToDo();
        toDo.setTitle("Title#1");
        toDo.setCreatedAt(null);
        toDo.setOwner(owner);
        toDo.setTasks(tasks);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1,violations.size());
    }
    @ParameterizedTest
    @MethodSource("validTitleValues")
    public void checkCorrectToDoTitles(String input){
        ToDo toDo = new ToDo();
        toDo.setTitle(input);
        toDo.setOwner(owner);
        toDo.setTasks(tasks);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(0,violations.size());

    }
    @ParameterizedTest
    @MethodSource("invalidTitleValues")
    public void checkInvalidToDoTitles(String input,String errorValue){
        ToDo toDo = new ToDo();
        toDo.setTitle(input);
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setOwner(owner);
        toDo.setTasks(tasks);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1,violations.size());
        assertEquals(errorValue,violations.iterator().next().getInvalidValue());
    }
    @ParameterizedTest
    @MethodSource("provideInvalidOwner")
    public void checkInvalidOwnerInToDo(User input,User errorValue){
        ToDo toDo = new ToDo();
        toDo.setTitle("Title#1");
        toDo.setOwner(input);
        toDo.setTasks(tasks);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);

        assertEquals(1,violations.size());
        assertEquals(errorValue,violations.iterator().next().getInvalidValue());
    }
    @Test
    public void checkToDoToStringWithValidTimeStamp(){
        ToDo toDo = new ToDo();
        assertEquals(toDo.toString(),"ToDo{id=0, title='null', createdAt="+LocalDateTime.now()+", owner=null, tasks=null, collaborators=null}");
    }
    @Test
    public void checkToDoToStringWithTitleValue(){
        ToDo toDo = new ToDo();
        toDo.setTitle("Title");
        assertEquals(toDo.toString(),"ToDo{id=0, title='Title', createdAt="+LocalDateTime.now()+", owner=null, tasks=null, collaborators=null}");
    }
    private static Stream<Arguments> provideInvalidOwner(){
        return Stream.of(
                Arguments.of(null,null)
        );
    }
    private static Stream<Arguments> validTitleValues(){
        return Stream.of(
                Arguments.of("Title#1","Title#1"),
                Arguments.of("Новий тест","Новий тест"),
                Arguments.of("Do Homework","Do Homework")
        );
    }
    private static Stream<Arguments> invalidTitleValues(){
        return Stream.of(
                Arguments.of("ds","ds"),
                Arguments.of(null,null)

        );
    }
}
