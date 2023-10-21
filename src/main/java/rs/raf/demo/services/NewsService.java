package rs.raf.demo.services;
import rs.raf.demo.entities.News;
import rs.raf.demo.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class NewsService {

    @Inject
    private NewsRepository newsRepository;

    public List<News> allNews(int page) {
        return this.newsRepository.allNews(page);
    }

    public List<News> allMostVisitedNews() {
        return this.newsRepository.allMostVisitedNews();
    }

    public List<News> allNewsForCategory(int page, int categoryId) {
        return this.newsRepository.allNewsForCategory(page, categoryId);
    }

    public List<News> searchNews(int page, String query) {
        return this.newsRepository.searchNews(page, query);
    }

    public void registerVisit(int newsId){
        this.newsRepository.registerVisit(newsId);
    }

    public void deleteNews(int newsId) {
        this.newsRepository.deleteNews(newsId);
    }
    public void likeNews(int newsId, String sessionId) {
        this.newsRepository.likeNews(newsId, sessionId);
    }

    public void dislikeNews(int newsId, String sessionId) {
        this.newsRepository.dislikeNews(newsId, sessionId);
    }

    public News addNews(String title, String content, Date dateCreated, int authorId, int categoryId) {
        return this.newsRepository.addNews(title, content, dateCreated, authorId, categoryId);
    }

    public News findNews(int newsId) {
        return this.newsRepository.findNews(newsId);
    }

    public News updateNews(int newsId, int categoryId, String title, String content) {
        return this.newsRepository.updateNews(newsId, categoryId, title, content);
    }

    public Integer reactions(int newsId) {
        return this.newsRepository.reactions(newsId);
    }


}
