package rs.raf.demo.services;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.repositories.comment.CommentRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public List<Comment> getNewsComments(int newsId) {
        return this.commentRepository.getNewsComments(newsId);
    }

    public Comment addComment(int newsId, String authorName, String content, Date dateCreated) {
        return this.commentRepository.addComment(newsId, authorName, content, dateCreated);
    }



}
