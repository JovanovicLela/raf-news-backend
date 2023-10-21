package rs.raf.demo.resources;
import rs.raf.demo.entities.News;
import rs.raf.demo.services.NewsService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNews(@QueryParam("page") int page) {
        return this.newsService.allNews(page);
    }

    @GET
    @Path("/most-visited")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allMostVisitedNews() {
        return this.newsService.allMostVisitedNews();
    }

    @GET
    @Path("/news-for-category")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getNewsForCategory(@QueryParam("page") int page, @QueryParam("categoryId") int categoryId) {
        return this.newsService.allNewsForCategory(page, categoryId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News addNews(@Valid News news) {
        return this.newsService.addNews(news.getTitle(), news.getContent(), news.getDateCreated(), news.getAuthorId(), news.getCategoryId());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findNews(@PathParam("id") int newsId) {
        return this.newsService.findNews(newsId);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> searchNews(@QueryParam("page") int page, @QueryParam("query") String query) {
        return this.newsService.searchNews(page, query);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News updateNews(@PathParam("id") int newsId, @Valid News news) {
        return this.newsService.updateNews(newsId, news.getCategoryId(), news.getTitle(), news.getContent());
    }

    @POST
    @Path("/register-visit/{id}")
    public void registerVisit(@PathParam("id") int newsId) {
         this.newsService.registerVisit(newsId);
    }

    @DELETE
    public void deleteNews(@QueryParam("newsId") int newsId) {
        this.newsService.deleteNews(newsId);
    }

    @GET
    @Path("/like/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void likeNews(@PathParam("id") int newsId, @Context HttpServletRequest request) {
        this.newsService.likeNews(newsId, request.getSession(true).getId());
    }


    @GET
    @Path("/dislike/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void dislikeNews(@PathParam("id") int newsId, @Context HttpServletRequest request) {
        this.newsService.dislikeNews(newsId, request.getSession(true).getId());
    }

    @GET
    @Path("/reactions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer reactions(@PathParam("id") int newsId) {
        return this.newsService.reactions(newsId);
    }

}
