package rs.raf.demo.repositories.user;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User findUser(String email) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        User user = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from user where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Integer userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isActive = resultSet.getBoolean("isActive");

                user = new User(userId, email, name, lastname, password, isAdmin, isActive);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }
    @Override
    public User getUser(int userId) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from user where userId = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isActive = resultSet.getBoolean("isActive");

                user = new User(userId, email, name, lastname, password, isAdmin, isActive);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> allUsers() {

        List<User> allUsers = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from user");
           // int currentPage = (pageNumber - 1) * 10;


            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isActive = resultSet.getBoolean("isActive");

                User user = new User(userId, email, name, lastname, password, isAdmin, isActive);
                allUsers.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return allUsers;
    }

    @Override
    public User addUser(String email, String name, String lastname, String password, boolean isAdmin) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"userId"};
            preparedStatement = connection.prepareStatement("insert into user (email, name, lastname, password, isAdmin, isActive) values (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, password);
            preparedStatement.setBoolean(5, isAdmin);
            preparedStatement.setBoolean(6, true);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                user = new User(resultSet.getInt("userId"), email, name, lastname, password, isAdmin, true);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User updateUser(int userId, String email, String name, String lastname, boolean isAdmin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from user where userId = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("select * from user where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("update user as u set u.email = ?, u.name = ?, u.lastname = ?, u.isAdmin = ? where u.userId = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastname);
            preparedStatement.setBoolean(4, isAdmin);
            preparedStatement.setInt(5, userId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getResultSet();

/*
            if (resultSet == null || !resultSet.next())
                throw new Exception();
*/


            if (resultSet.next())
                user = new User(userId, email, name, lastname, resultSet.getString("password"), isAdmin, resultSet.getBoolean("isActive"));

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return user;
    }
}
