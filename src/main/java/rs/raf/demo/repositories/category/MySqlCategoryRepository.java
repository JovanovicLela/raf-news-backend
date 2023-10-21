package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {

    @Override
    public List<Category> allCategories() {

        List<Category> categories = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from category");

            while (resultSet.next()) {
                 int categoryId = resultSet.getInt("categoryId");
                 String name = resultSet.getString("name");
                 String description = resultSet.getString("description");
                 Category category = new Category(categoryId, name, description);
                 categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public Category addCategory(String name, String description) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"categoryId"};
            preparedStatement = connection.prepareStatement("insert into category (name, description) values (?, ?)", generatedColumns);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next())
                    category = new Category(resultSet.getInt("categoryId"), name, description);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public Category findCategory(int categoryId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from category where categoryId like ?");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                category = new Category(categoryId, name, description);
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
        return category;
    }

    @Override
    public Category updateCategory(int categoryId, String name, String description) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from category where categoryId = ? ");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("update category as c set c.name = ?, c.description = ? where c.categoryId = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getResultSet();

            if (resultSet.next())
                category = new Category(categoryId, name, description);

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

        return category;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("delete from category where categoryId = ?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
