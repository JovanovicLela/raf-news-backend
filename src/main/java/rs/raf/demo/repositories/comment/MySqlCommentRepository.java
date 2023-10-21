package rs.raf.demo.repositories.comment;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public List<Comment> getNewsComments(int newsId) {
        List<Comment> comments = new ArrayList<Comment>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from comment where newsId = ? order by dateCreated desc");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int commentId = resultSet.getInt("commentId");
                String authorName = resultSet.getString("authorName");
                String content = resultSet.getString("content");
                Date dateCreated = resultSet.getDate("dateCreated");
                comments.add(new Comment(commentId, newsId, authorName, content, dateCreated));
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

        return comments;
    }

    @Override
    public Comment addComment(int newsId, String authorName, String content, Date dateCreated) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Comment comment = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns={"commentId"};
            preparedStatement = connection.prepareStatement("insert into comment (newsId, authorName, content, dateCreated) values (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, newsId);
            preparedStatement.setString(2, authorName);
            preparedStatement.setString(3, content);
            preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                comment = new Comment(resultSet.getInt(1), newsId, authorName, content, dateCreated);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }
}
