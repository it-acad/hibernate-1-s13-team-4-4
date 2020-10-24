package com.softserve.itacademy.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "task_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(name = "name")
    @NotBlank(message = "The taskName cannot be empty")
    @Length(min = 3, max = 200, message = "The taskName must consist of minimum 3 and maximum 200 any symbols")
    private String name;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", referencedColumnName = "id")
    private ToDo todo;

    public Task() {    }

    public long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Priority getPriority() { return priority; }

    public void setPriority(Priority priority) { this.priority = priority; }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ToDo getTodo() {
        return todo;
    }

    public void setTodo(ToDo todo) {
        this.todo = todo;
    }

    @Override
    public String toString() {
        return "Task {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", priority = " + priority +
                '}';
    }
}
