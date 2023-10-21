package rs.raf.demo.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;

    public LoginRequest() {
    }


}
