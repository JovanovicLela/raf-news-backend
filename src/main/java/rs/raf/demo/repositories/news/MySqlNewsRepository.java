package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.News;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    @Override
    public List<News> allNews(int page) {
        List<News> allNews = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news order by dateCreated desc limit 10 offset ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date dateCreated = resultSet.getDate("dateCreated");
                int timesVisited = resultSet.getInt("timesVisited");
                int authorId = resultSet.getInt("authorId");
                int categoryId = resultSet.getInt("categoryId");

                News news = new News(newsId, title, content, dateCreated, timesVisited, authorId, categoryId);
                allNews.add(news);
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
        return allNews;
    }


    @Override
    public List<News> allNewsForCategory(int page, int categoryId) {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news where categoryId = ? order by dateCreated desc limit 10 offset ? ");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date dateCreated = resultSet.getDate("dateCreated");
                int timesVisited = resultSet.getInt("timesVisited");
                int authorId = resultSet.getInt("authorId");

                news.add(new News(newsId, title, content, dateCreated, timesVisited, authorId, categoryId));
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

        return news;
    }

    @Override
    public List<News> allMostVisitedNews() {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news where dateCreated between now() - interval 30 day and now() order by timesVisited desc limit 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date dateCreated = resultSet.getDate("dateCreated");
                int timesVisited = resultSet.getInt("timesVisited");
                int authorId = resultSet.getInt("authorId");
                int categoryId = resultSet.getInt("categoryId");

                news.add(new News(newsId, title, content, dateCreated, timesVisited, authorId, categoryId));
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

        return news;
    }

    @Override
    public News addNews(String title, String content, Date dateCreated, int authorId, int categoryId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"newsId"};
            preparedStatement = connection.prepareStatement("insert into news (title, content, dateCreated, authorId, categoryId) values (?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(4, authorId);
            preparedStatement.setInt(5, categoryId);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                news = new News(resultSet.getInt(1), title, content, dateCreated, 0, authorId, categoryId);
            // java.sql.Date.valueOf(LocalDate.now())

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News findNews(int newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news where newsId like ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date dateCreated = resultSet.getDate("dateCreated");
                int timesVisited = resultSet.getInt("timesVisited");
                int authorId = resultSet.getInt("authorId");
                int categoryId = resultSet.getInt("categoryId");
                news = new News(newsId, title, content, dateCreated, timesVisited, authorId, categoryId);
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
        return news;
    }

    @Override
    public List<News> searchNews(int page, String query) {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news where title like ? or content like ? order by dateCreated desc limit 10 offset ?");
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setInt(3, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date dateCreated = resultSet.getTimestamp("dateCreated");
                int timesVisited = resultSet.getInt("timesVisited");
                int authorId = resultSet.getInt("authorId");
                int categoryId = resultSet.getInt("categoryId");

                news.add(new News(newsId, title, content, dateCreated, timesVisited, authorId, categoryId));
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

        return news;
    }

    @Override
    public void deleteNews(int newsId) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("delete from news_likes where newsId = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("delete from comment where newsId = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.setInt(1, newsId);
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("delete from news where newsId = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public News updateNews(int newsId, int categoryId, String title, String content) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from news where newsId = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();
            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("update news as n set n.categoryId = ?, n.title = ?, n.content = ? where n.newsId = ?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, content);
            preparedStatement.setInt(4, newsId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next())
                news = new News(newsId, title, content, resultSet.getDate("dateCreated"),
                        resultSet.getInt("timesVisited"), resultSet.getInt("authorId"), categoryId);

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)
                this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public void registerVisit(int newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from news where newsId = ? ");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();
            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("update news as n set n.timesVisited = n.timesVisited + 1 where n.newsId = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();

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

    }

    @Override
    public void likeNews(int newsId, String sessionId) {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from news_likes where newsId = ? and sessionId = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.setString(2, sessionId);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("insert into news_likes (newsId, sessionId, value) values (?, ?, ?)");
                preparedStatement.setInt(1, newsId);
                preparedStatement.setString(2, sessionId);
                preparedStatement.setInt(3, +1);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public void dislikeNews(int newsId, String sessionId) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from news_likes where newsId = ? and sessionId = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.setString(2, sessionId);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("insert into news_likes (newsId, sessionId, value) values (?, ?, ?)");
                preparedStatement.setInt(1, newsId);
                preparedStatement.setString(2, sessionId);
                preparedStatement.setInt(3, -1);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public Integer reactions(int newsId) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select sum(value) as total from news_likes where newsId = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return count;
    }
}
