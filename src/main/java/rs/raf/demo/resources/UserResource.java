package rs.raf.demo.resources;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.demo.entities.User;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.requests.UpdateRequest;
import rs.raf.demo.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId") int userId) {
        return this.userService.getUser(userId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> allUsers() {
        return this.userService.allUsers();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@Valid User user) {
        return this.userService.addUser(user.getEmail(), user.getName(), user.getLastname(), DigestUtils.sha256Hex(user.getPassword()), user.isAdmin());
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("userId") int userId, @Valid UpdateRequest user) {
        return this.userService.updateUser(userId, user.getEmail(), user.getName(), user.getLastname(), user.isAdmin());
    }

    @POST
    @Path("/logIn")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }
}
