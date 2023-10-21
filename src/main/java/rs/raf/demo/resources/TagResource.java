package rs.raf.demo.resources;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.News;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;
/*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTag() {
        return this.tagService.allTag();
    }*/

    @GET
    @Path("/tags-by-news")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTags(@QueryParam("newsId") int newsId) {
        return this.tagService.allTags(newsId);
    }

    @GET
    @Path("/news-by-tag")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByTag(@QueryParam("tagId") int tagId) {
        return this.tagService.allNewsByTag(tagId);
    }


    @POST
    @Path("/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag addTag(@PathParam("newsId") int newsId, @Valid Tag tag) {
        return this.tagService.addTag(newsId, tag.getKeyword());
    }

    @GET
    @Path("/find-by-keyword")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findTagByKeyword(@QueryParam("keyword") String keyword) {
        return this.tagService.findTagByKeyword(keyword);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findById(@QueryParam("tagId") int tagId) {
        return this.tagService.findTag(tagId);
    }

}
