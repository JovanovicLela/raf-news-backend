package rs.raf.demo.services;
import rs.raf.demo.entities.News;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    @Inject
    private TagRepository tagRepository;

    public List<Tag> allTag() {
        return this.tagRepository.allTag();
    }

    public List<Tag> allTags(int newsId) {
        return this.tagRepository.allTags(newsId);
    }

    public List<News> allNewsByTag(int tagId) {
        return this.tagRepository.allNewsByTag(tagId);
    }

    public Tag addTag(int newsId, String keyword) {
        return this.tagRepository.addTag(newsId, keyword);
    }

    public Tag findTag(int tagId) {
        return this.tagRepository.findTag(tagId);
    }

    public Tag findTagByKeyword(String keyword) {
        return this.tagRepository.findTagByKeyword(keyword);
    }


}
