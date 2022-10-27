package practice.askmaterest.services.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Post;
import practice.askmaterest.model.Tag;

import java.util.List;
import java.util.Set;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAll(Pageable pageable, Sort sort);

    List<Post> findAllByTags(Set<Tag> tags, Pageable pageable, Sort sort);
}
