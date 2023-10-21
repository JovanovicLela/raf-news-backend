package rs.raf.demo.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateRequest {

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Lastname is required")
    @NotEmpty(message = "Lastname is required")
    private String lastname;

    @NotNull(message = "Is Admin field is required")
    private Boolean isAdmin;


    public UpdateRequest() {
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
