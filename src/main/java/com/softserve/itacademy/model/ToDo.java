package com.softserve.itacademy.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "todo_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @NotBlank(message = "The title cannot be empty")
    @Size(min = 3,max = 120)
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @CreationTimestamp
    @Column(name= "created_at")
    @NotNull
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName= "id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "todo_collaborator",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "collaborator_id"))
    private List<User> collaborators;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Task> tasks;


    public ToDo() {  }

    public long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public LocalDate getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<User> collaborators) {
        this.collaborators = collaborators;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + LocalDate.now() +
                ", owner=" + owner +
                ", collaborators=" + collaborators +
                ", tasks=" + tasks +
                '}';
    }
}
