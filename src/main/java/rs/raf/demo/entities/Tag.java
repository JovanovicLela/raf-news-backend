package rs.raf.demo.entities;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Tag {
    private int tagId;

    @NotNull(message = "Keyword field is required")
    @NotEmpty(message = "Keyword field is required")
    private String keyword;

    public Tag() {}

    public Tag(int tagId, String keyword) {
        this.tagId = tagId;
        this.keyword = keyword;
    }
}
