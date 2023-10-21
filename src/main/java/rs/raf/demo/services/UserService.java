package rs.raf.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.user.UserRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {

    @Inject
    UserRepository userRepository;

    public User getUser(int userId) {
        return this.userRepository.getUser(userId);
    }

    public List<User> allUsers() {
        return this.userRepository.allUsers();
    }

    public User addUser(String email, String name, String lastname, String password, boolean isAdmin) {
        return this.userRepository.addUser(email, name, lastname, password, isAdmin);
    }

    public User updateUser(int userId, String email, String name, String lastname, boolean isAdmin) {
        return this.userRepository.updateUser(userId, email, name, lastname, isAdmin);
    }


    public String login(String username, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(username);
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedono poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(String.valueOf(user.getUserId()))
                .withClaim("isAdmin", user.isAdmin())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String id = jwt.getSubject();
        User user = this.userRepository.getUser(Integer.parseInt(id));

        if (user == null){
            return false;
        }

        return true;
    }
}
