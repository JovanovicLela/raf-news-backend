package rs.raf.demo.resources;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Path("/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getNewsComments(@PathParam("newsId") int newsId) {
        return this.commentService.getNewsComments(newsId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@Valid Comment comment) {
        return this.commentService.addComment(comment.getNewsId(), comment.getAuthorName(), comment.getContent(), comment.getDateCreated());
    }



}
