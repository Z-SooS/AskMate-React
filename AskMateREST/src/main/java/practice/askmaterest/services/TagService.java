package practice.askmaterest.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.Tag;
import practice.askmaterest.services.daos.TagRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private TagRepo tagRepo;

    public void save(Tag tag) {
        tagRepo.save(tag);
    }

    public List<Tag> getTags() {
        return tagRepo.findAll();
    }
}
