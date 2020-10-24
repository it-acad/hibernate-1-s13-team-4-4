package com.softserve.itacademy.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(name= "first_name")
    @NotBlank(message = "The firstname cannot be empty")
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+\\-[A-Z][a-z]+|[A-Z][a-z]+")
    private String firstName;


    @Column(name= "last_name")
    @NotBlank(message = "The lastname cannot be empty")
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+\\-[A-Z][a-z]+|[A-Z][a-z]+")
    private String lastName;

    @Column
    @NotBlank
    @Email
    private String email;

    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<ToDo> toDoList;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "collaborators")
    private Set<ToDo> collaboratorsToDos;


    public User() {  }

    public long getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
