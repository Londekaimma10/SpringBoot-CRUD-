package com.example.mySpringBootProject.Model;
import lombok.Getter;
import lombok.Setter;

import  javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="USERS")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID", insertable = false, nullable = false)
    private Long id;

    @NotEmpty(message = "Email cannot be empty!")
    @Email
    @Column(name = "EMAIL_ADDRESS", nullable = false, unique = true)
    private String emailAddress;

    @NotNull(message= "First Name can not be null")
    @Size(min = 2, message = "First must at least have two characters")
    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;

    @NotNull(message= "Last Name can not be null")
    @Size(min = 2, message = "Last name must at least have two characters")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 6, message = "Password must have a minimum of 6 characters")
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", insertable = false,updatable = false)
    private Roles roles;

    public Users() {
    }

    public Users(String emailAddress, String firstName, String lastName, String password, Long roleId, Roles roles) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roleId = roleId;
        this.roles = roles;
    }
}