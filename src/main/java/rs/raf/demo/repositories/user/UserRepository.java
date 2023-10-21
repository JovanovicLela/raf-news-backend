package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.User;

import java.util.List;

public interface UserRepository {

    User getUser(int userId);

    User findUser(String email);

    List<User> allUsers();

    User addUser(String email, String name, String lastname, String password, boolean isAdmin);

    User updateUser(int userId, String email, String name, String lastname, boolean isAdmin);
}
