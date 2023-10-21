package rs.raf.demo.entities;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class User {

    private int userId;

    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email field is required")
    private String email;

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Lastname is required")
    @NotEmpty(message = "Lastname is required")
    private String lastname;;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;

    @NotNull(message = "Is Admin field is required")
    private boolean isAdmin;

    private boolean isActive;

    public User() {
    }

    public User(int userId, String email, String name, String lastname, String password, boolean isAdmin, boolean isActive) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }
}
