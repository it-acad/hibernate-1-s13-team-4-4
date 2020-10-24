package com.softserve.itacademy.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "states")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "The name cannot be empty")
    @Pattern(regexp = "[a-zA-Z0-9 _-]$", message = "The state should contain only: latin letters, numbers, dash, space and underscore")
    @Column(name = "name", nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9-\\- _]{0,20}$")
    private String name;

    @OneToMany(mappedBy = "state")
    private List<Task> tasks;

    public State() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "State {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                "} ";
    }
}