package com.music.social.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



@Entity
@Table(name = "users")
public class User {
 
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    
    @NotEmpty(message = "Username must not be blank")
    private String username;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email must not be blank")
    private String email;

    @Size(min = 6, message = "Password must be longer than 6 characters")
    private String password;

    @OneToOne
    private Image image;

    public User() {
    }

    /**
    *
    * @param username take a username to assign the user
    * @param email assigns an email address
    * @param password assigns a password to user object
    */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
