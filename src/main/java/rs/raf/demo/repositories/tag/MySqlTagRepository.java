package rs.raf.demo.repositories.tag;
import rs.raf.demo.entities.News;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.MySqlAbstractRepository;
import rs.raf.demo.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagRepository extends MySqlAbstractRepository implements TagRepository {

    @Inject
    private NewsRepository newsRepository;

    @Override
    public List<Tag> allTags(int newsId) {
        List<Tag> tags = new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news_tags where newsId = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                tags.add(findTag(resultSet.getInt("tagId")));

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);

        }

        return tags;
    }

    @Override
    public Tag addTag(int newsId, String keyword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Tag tag = null;

        try {
            connection = this.newConnection();

            tag = findTagByKeyword(keyword);

            if (tag == null) {
                String[] generatedColumns={"tagId"};
                preparedStatement = connection.prepareStatement("insert into tag (keyword) values (?)", generatedColumns);
                preparedStatement.setString(1, keyword);

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next())
                    tag = new Tag(resultSet.getInt(1), keyword);
            }


            preparedStatement = connection.prepareStatement("insert into news_tags (newsId, tagId) values (?, ?)");
            preparedStatement.setInt(1, newsId);
            preparedStatement.setInt(2, tag.getTagId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public Tag findTag(int tagId) {
        Tag tag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from tag where tagId = ?");
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                tag = new Tag(tagId, resultSet.getString("keyword"));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public Tag findTagByKeyword(String keyword) {
        Tag tag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from tag as t where t.keyword like ?");
            preparedStatement.setString(1, keyword);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                tag = new Tag(resultSet.getInt(1), keyword);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public List<News> allNewsByTag(int tagId) {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news_tags where tagId = ?");
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                news.add(newsRepository.findNews(resultSet.getInt("newsId")));

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);

        }

        return news;
    }

    @Override
    public List<Tag> allTag() {
        List<Tag> tags = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from tag");

            while (resultSet.next()) {
                int tagId = resultSet.getInt("tagId");
                String keyword = resultSet.getString("keyword");
                Tag tag = new Tag(tagId, keyword);
                synchronized (this) {
                    tags.add(tag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tags;
    }
}
