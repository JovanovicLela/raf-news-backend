package rs.raf.demo.entities;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Comment {

    private int commentId;

    @NotNull(message = "News is required")
    private Integer newsId;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String authorName;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private Date dateCreated;

    public Comment() {}

    public Comment(Integer newsId, String authorName, String content, Date dateCreated) {
        this.newsId = newsId;
        this.authorName = authorName;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public Comment(int commentId, Integer newsId, String authorName, String content, Date dateCreated) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.authorName = authorName;
        this.content = content;
        this.dateCreated = dateCreated;
    }

}
