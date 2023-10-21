package rs.raf.demo.repositories.comment;

import rs.raf.demo.entities.Comment;

import java.util.Date;
import java.util.List;

public interface CommentRepository {

    List<Comment> getNewsComments(int newsId);

    Comment addComment(int newsId, String authorName, String content, Date dateCreated);
}
