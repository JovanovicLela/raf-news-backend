package rs.raf.demo.repositories.tag;

import rs.raf.demo.entities.News;
import rs.raf.demo.entities.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> allTag();

    List<Tag> allTags(int newsId);

    List<News> allNewsByTag(int tagId);

    Tag addTag(int newsId, String keyword);

    Tag findTag(int tagId);

    Tag findTagByKeyword(String keyword);
}
