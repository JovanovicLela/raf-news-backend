package rs.raf.demo.repositories.news;
import rs.raf.demo.entities.News;

import java.util.Date;
import java.util.List;

public interface NewsRepository {

    List<News> allNews(int page);

    List<News> allNewsForCategory(int page, int categoryId);

    List<News> allMostVisitedNews();

    List<News> searchNews(int page, String query);

    void registerVisit(int newsId);

    void deleteNews(int newsId);

    void likeNews(int newsId, String sessionId);

    void dislikeNews(int newsId, String sessionId);

    Integer reactions(int newsId);

    News addNews(String title, String content, Date dateCreated, int authorId, int categoryId);

    News findNews(int newsId);

    News updateNews(int newsId, int categoryId, String title, String content);





}
