package rs.raf.demo.entities;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class News {

    private int newsId;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private Date dateCreated;

    private int timesVisited;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    private Integer authorId;

    public News() {
    }

    public News(Integer newsId, String title, String content, Date dateCreated, int timesVisited, Integer authorId, Integer categoryId) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.timesVisited = timesVisited;
        this.authorId = authorId;
        this.categoryId = categoryId;

    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
